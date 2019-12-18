package com.dennn66.tasktracker.controllers;

import com.dennn66.gwt.common.TaskDto;
import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.entities.User;
import com.dennn66.tasktracker.services.TaskService;
import com.dennn66.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RestController
@CrossOrigin
public class TaskRestController {
    private TaskService taskService;
    private UserService userService;
//    private int tasksPerPage;
//    private Long pageNumber;
//    private Map<String, String> params;
//    private PageRequest pageRequest;

    public TaskRestController() {
//        tasksPerPage = 5;
//        pageNumber = 1L;
//        params = new HashMap<String, String>();
//        pageRequest = PageRequest.of(
//                pageNumber.intValue() - 1,
//                tasksPerPage,
//                Sort.Direction.ASC,
//                "id");
    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/v1/tasks", method = RequestMethod.GET)
    public List<TaskDto> showAllTasks(@RequestParam Map<String,String> params) {
        int tasksPerPage = 50;

        Long pageNumber;
        try{
            pageNumber = Long.parseLong(params.get("pageNumber"));
        } catch (NumberFormatException e){
            pageNumber = 1L;
        }
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }
        Page<Task> tasksPage = taskService.getAllTasks(params, PageRequest.of(
                pageNumber.intValue() - 1,
                tasksPerPage,
                Sort.Direction.ASC,
                "id"));
        Page<TaskDto> dtoPage = tasksPage.map(new Function<Task, TaskDto>() {
            @Override
            public TaskDto apply(Task task) {
                TaskDto dto = new TaskDto(
                        task.getId(),
                        task.getName(),
                        task.getCreator().getUsername(),
                        task.getCreator().getId(),
                        (task.getAssignee() == null)?"":task.getAssignee().getUsername(),
                        (task.getAssignee() == null)?-1L:task.getAssignee().getId(),
                        task.getDescription(),
                        task.getStatus().getDisplayValue(),
                        task.getStatus().toString()
                        );
                return dto;
            }
        });
        System.out.println(tasksPage.toList().toString());
        return dtoPage.toList();
    }

    @DeleteMapping("/v1/tasks/{id}")
    public ResponseEntity<String> removeTasks(@PathVariable Long id) {
        taskService.remove(id);
        return new ResponseEntity<String>("Successfully removed", HttpStatus.OK);
    }
    @PostMapping("/v1/tasks")
    public ResponseEntity<String> addTasks(@RequestParam Map<String,String> params) {
        System.out.println("addTasks");
        System.out.println(params.toString());
        if(params.containsKey("name")){
            Task task = new Task(params.get("name"), params.getOrDefault("description", ""));
            task.setAssignee(userService.findById(Long.parseLong(params.get("assignee"))).get());
            List<User> users = userService.getAllEnabledUsers(); // temporary solution
            task.setCreator(users.get(0));                       // temporary solution
            taskService.insert(task);
            return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/v1/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestParam Map<String,String> params) {
        System.out.println("updateTask");
        System.out.println(params.toString());
        if(id > 0){
            try{
                Optional<Task> task = taskService.findById(id);
                task.get().setStatus(Task.Status.valueOf(params.get("statusId")));
                task.get().setAssignee(userService.findById(Long.parseLong(params.get("assigneeId"))).get());
                task.get().setDescription(params.get("description"));
                task.get().setName(params.get("title"));
                taskService.update(task.get());
                return new ResponseEntity<String>("Successfully updated", HttpStatus.OK);
            } catch (Exception e){
                return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);
    }

}


package com.dennn66.tasktracker.controllers;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TaskRestController {
    private TaskService taskService;
    private UserService userService;
    private int tasksPerPage;
    private Long pageNumber;
    private Map<String, String> params;
    private PageRequest pageRequest;

    public TaskRestController() {
        tasksPerPage = 5;
        pageNumber = 1L;
        params = new HashMap<String, String>();
        pageRequest = PageRequest.of(
                pageNumber.intValue() - 1,
                tasksPerPage,
                Sort.Direction.ASC,
                "id");
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
    public List<Task> showAllTasks(@RequestParam Map<String,String> params) {
        System.out.println("showAllTasks");
        System.out.println(params.toString());
        Page<Task> tasksPage = taskService.getAllTasks(this.params, pageRequest);
        System.out.println(tasksPage.toList().toString());
        return tasksPage.toList();
    }

    @RequestMapping(path = "/v1/tasks/page", method = RequestMethod.GET)
    public PageRequest setPage(@RequestParam Long page) {
        pageNumber = (page < 1L) ? 1L : page;
        pageRequest = PageRequest.of(
                pageNumber.intValue() - 1,
                tasksPerPage,
                Sort.Direction.ASC,
                "id");
        return pageRequest;
    }
    @RequestMapping(path = "/v1/tasks", method = RequestMethod.PUT)
    public Map<String,String> setFilter(@RequestParam Map<String,String> params) {
        System.out.println("setFilter");
        System.out.println(params.toString());
        this.params.clear();
        this.params.putAll(params);
        return this.params;
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
            List<User> users = userService.getAllEnabledUsers(); // temporary solution
            task.setCreator(users.get(0));                       // temporary solution
            taskService.insert(task);
            return new ResponseEntity<String>("Successfully added", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Fail", HttpStatus.BAD_REQUEST);


    }
}


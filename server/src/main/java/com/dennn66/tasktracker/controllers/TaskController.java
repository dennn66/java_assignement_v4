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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TaskController {
    private TaskService taskService;
    private UserService userService;

    @Autowired
    public void setTaskService(TaskService taskService){
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(path = "/tasks", method = RequestMethod.GET)
    public String showAllTasks(
            Model model,
            @RequestParam Map<String,String> params
    ) {
        int tasksPerPage = 5;

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
        model.addAttribute("tasksPage", tasksPage);

        Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return "all_tasks";
    }

    // http://localhost:8189/app/task/1/details
    @GetMapping("/task/{id}/details")
    public String details(Model model, @PathVariable String id){
        Optional<Task> task = taskService.findById(Long.valueOf(id));
        List<User> users = userService.getAllEnabledUsers();
        if(task.equals(Optional.empty())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        model.addAttribute("task", task.get());
        model.addAttribute("users", users);
        return "task_details";
    }

    // GET http://localhost:8189/app/tasks/add
    @GetMapping("/task/add")
    public String add(Model model) {
        List<User> users = userService.getAllEnabledUsers();
        if(users.size() > 0) {
            Task task = new Task();
            model.addAttribute("task", task);
            model.addAttribute("users", users);
            return "task_form";
        } else {
            return "redirect:/tasks";
        }
    }

    // POST http://localhost:8189/app/task/process_form
    @PostMapping("/task/process_form")
    public String processForm(@ModelAttribute("task") Task task,
                              @ModelAttribute("action") String action,
                              @ModelAttribute("assignee") String assignee,
                              @ModelAttribute("stat") String status) {

        switch (action){
            case "Update":
                taskService.update(task);
                break;
            case "Add":
                List<User> users = userService.getAllEnabledUsers(); // temporary solution
                task.setCreator(users.get(0));                       // temporary solution
                taskService.insert(task);
                break;
            case "Delete":
                taskService.delete(task);
                break;
        }
        return "redirect:/tasks";
    }
}

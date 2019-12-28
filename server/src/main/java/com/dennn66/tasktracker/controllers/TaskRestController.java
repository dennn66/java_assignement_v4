package com.dennn66.tasktracker.controllers;

import com.dennn66.gwt.common.TaskCreateDto;
import com.dennn66.gwt.common.TaskDto;
import com.dennn66.gwt.common.ValidationErrorDto;
import com.dennn66.tasktracker.exceptions.ResourceNotFoundException;
import com.dennn66.tasktracker.services.TaskService;
import com.dennn66.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class TaskRestController {
    private TaskService taskService;
    private UserService userService;

    public TaskRestController() {    }

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/tasks")
    public ResponseEntity<List<TaskDto>> getAll(@RequestParam Map<String,String> params) {
        List<TaskDto> tasks =taskService.getAllTasks(params);
        return new ResponseEntity<>(taskService.getAllTasks(params), HttpStatus.OK);
    }

    @GetMapping("/v1/tasks/{id}")
    public ResponseEntity<TaskDto> getOne(@PathVariable Long id) {
        if (!taskService.existsById(id)) {
            throw new ResourceNotFoundException("Task with id: " + id + " not found");
        }
        return new ResponseEntity<>(taskService.findOne(id), HttpStatus.OK);
    }

    @DeleteMapping("/v1/tasks/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        taskService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/v1/tasks")
    public ResponseEntity<?> create(@RequestBody @Valid TaskCreateDto taskCreateDto,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(new ValidationErrorDto(errorMessage.toString()), HttpStatus.BAD_REQUEST);
            //return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }
        Principal principal = request.getUserPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else if (principal instanceof UsernamePasswordAuthenticationToken) {
            username = ((UsernamePasswordAuthenticationToken)principal).getName();
        }  else {
            username = principal.toString();
        }
        return new ResponseEntity<>(taskService.save(taskCreateDto,
                userService.getUserByName(username)), HttpStatus.CREATED);
    }

    @PutMapping("/v1/tasks/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid TaskDto taskDto,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError o : bindingResult.getAllErrors()) {
                errorMessage.append(o.getDefaultMessage()).append(";\n");
            }
            return new ResponseEntity<>(new ValidationErrorDto(errorMessage.toString()), HttpStatus.BAD_REQUEST);
            //return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(taskService.update(taskDto), HttpStatus.OK);
    }
}


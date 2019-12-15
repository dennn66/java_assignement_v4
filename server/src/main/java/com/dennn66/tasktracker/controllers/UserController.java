package com.dennn66.tasktracker.controllers;

import com.dennn66.tasktracker.entities.User;
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
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    // http://localhost:8189/app/users?creatorFilter=ddd&statusFilter=ALL
    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public String showAllUsers(
            Model model,
            @RequestParam Map<String,String> params
    ) {
        int usersPerPage = 5;

        Long pageNumber;
        try{
            pageNumber = Long.parseLong(params.get("pageNumber"));
        } catch (NumberFormatException e){
            pageNumber = 1L;
        }
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }
        Page<User> usersPage = userService.getAllUsers(params, PageRequest.of(
                pageNumber.intValue() - 1,
                usersPerPage,
                Sort.Direction.ASC,
                "id"));
        model.addAttribute("usersPage", usersPage);

        Iterator<Map.Entry<String, String>> entries = params.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return "all_users";
    }

    // http://localhost:8189/app/user/1/details
    @GetMapping("/user/{id}/details")
    public String details(Model model, @PathVariable String id){
        Optional<User> user = userService.findById(Long.valueOf(id));
        if(user.equals(Optional.empty())) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        model.addAttribute("user", user.get());
        return "user_details";
    }

    // GET http://localhost:8189/app/user/add
    @GetMapping("/user/add")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "user_form";
    }

    // POST http://localhost:8189/app/user/process_form
    @PostMapping("/user/process_form")
    public String processForm(@ModelAttribute("user") User user, @ModelAttribute("action") String action, @ModelAttribute("stat") String status) {

        switch (action){
            case "Update":
                userService.update(user);
                break;
            case "Add":
                userService.insert(user);
                break;
            case "Delete":
                userService.delete(user);
                break;
        }
        return "redirect:/users";
    }
}

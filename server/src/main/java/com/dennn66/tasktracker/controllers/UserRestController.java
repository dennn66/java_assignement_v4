package com.dennn66.tasktracker.controllers;

import com.dennn66.gwt.common.UserDto;
import com.dennn66.tasktracker.entities.User;
import com.dennn66.tasktracker.services.UserService;
import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class UserRestController {
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    // http://localhost:8189/app/users?creatorFilter=ddd&statusFilter=ALL
    @RequestMapping(path = "/v1/users", method = RequestMethod.GET)
    public List<UserDto> showAllEnabledUsers(
    ) {
        List<User> users = userService.getAllEnabledUsers();
        List<UserDto> userDtos = users.stream().map(this::convertToDto).collect(Collectors.toList());
        return userDtos;
    }
}

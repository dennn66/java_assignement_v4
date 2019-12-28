package com.dennn66.tasktracker.controllers;

import com.dennn66.gwt.common.UserReferenceDto;
import com.dennn66.tasktracker.mappers.UserReferenceMapper;
import com.dennn66.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class UserRestController {
    private UserService userService;
    

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(path = "/v1/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserReferenceDto>> showAllEnabledUsers(
    ) {
        return new ResponseEntity<>(UserReferenceMapper.MAPPER.
                fromUserList(userService.getAllEnabledUsers()), HttpStatus.OK);
    }
}

package com.dennn66.tasktracker.services;

import com.dennn66.tasktracker.entities.User;
import com.dennn66.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllEnabledUsers() {
        return userRepository.findAllEnabledUsers();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getUserByName(String username) {
        return  userRepository.findOneByUsername(username);
    }
}

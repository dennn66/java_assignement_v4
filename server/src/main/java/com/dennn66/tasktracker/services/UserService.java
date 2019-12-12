package com.dennn66.tasktracker.services;

import com.dennn66.tasktracker.entities.User;
import com.dennn66.tasktracker.repositories.UserRepository;
import com.dennn66.tasktracker.repositories.specifications.UserSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> getAllUsers(Map<String,String> params, Pageable pageable) {
        String statusFilter = params.get("statusFilter");
        String nameFilter = params.get("nameFilter");
        Specification<User> spec = Specification.where(null);
        if (nameFilter != null && !nameFilter.equals("null")) {
            spec = spec.and(UserSpecifications.nameContains(nameFilter));
        }
        if (statusFilter != null && !statusFilter.equals("ALL") && !statusFilter.equals("null")) {
            spec = spec.and(UserSpecifications.statusEqual(User.Status.valueOf(statusFilter)));
        }
        return userRepository.findAll(spec, pageable);
    }

    public List<User> getAllEnabledUsers() {
        Specification<User> spec = Specification.where(null);
        spec = spec.and(UserSpecifications.statusEqual(User.Status.ENABLED));
        return userRepository.findAll(spec);
    }

    public void insert(User user) {
        user.setStatus(User.Status.ENABLED);
        userRepository.save(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }
    public void update(User user) {
        userRepository.save(user);}
    public Optional<User> findById(Long id) {return userRepository.findById(id);}
}

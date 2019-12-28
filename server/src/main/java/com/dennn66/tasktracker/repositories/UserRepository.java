package com.dennn66.tasktracker.repositories;

import com.dennn66.tasktracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByUsername(String username);

    @Query("select i from User i where i.status = 0")
    List<User> findAllEnabledUsers();
}
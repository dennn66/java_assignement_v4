package com.dennn66.tasktracker.repositories.specifications;

import com.dennn66.tasktracker.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> nameContains(String word) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + word + "%");
    }
    public static Specification<User> statusEqual(User.Status status) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}

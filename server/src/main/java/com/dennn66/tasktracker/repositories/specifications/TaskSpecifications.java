package com.dennn66.tasktracker.repositories.specifications;

import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.entities.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class TaskSpecifications {
    public static Specification<Task> nameContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + word + "%");
    }


    public static Specification<Task> creatorContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> {
            Subquery<Task> sq = criteriaQuery.subquery(Task.class);
            Root<User> user = sq.from(User.class);
            sq.where(criteriaBuilder.like(user.get("username"), "%" + word + "%"));
            return criteriaBuilder.in(root).value(sq);
        };
    }
    public static Specification<Task> statusEqual(Task.Status status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}

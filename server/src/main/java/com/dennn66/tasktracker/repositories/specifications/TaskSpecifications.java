package com.dennn66.tasktracker.repositories.specifications;

import com.dennn66.tasktracker.entities.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> nameContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("name"), "%" + word + "%");
    }
    public static Specification<Task> creatorContains(String word) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("creator"), "%" + word + "%");
    }
    public static Specification<Task> statusEqual(Task.Status status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), status);
    }
}

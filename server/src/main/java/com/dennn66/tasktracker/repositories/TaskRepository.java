package com.dennn66.tasktracker.repositories;

import com.dennn66.tasktracker.entities.Task;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, Long>, JpaSpecificationExecutor<Task> {

}
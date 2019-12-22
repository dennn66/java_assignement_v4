package com.dennn66.tasktracker.services;

import com.dennn66.gwt.common.TaskDto;
import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.repositories.TaskRepository;
import com.dennn66.tasktracker.repositories.specifications.TaskSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Page<Task> getAllTasks(Map<String,String> params) {
        int tasksPerPage = 50;

        Long pageNumber;
        try{
            pageNumber = Long.parseLong(params.get("pageNumber"));
        } catch (NumberFormatException e){
            pageNumber = 1L;
        }
        if (pageNumber < 1L) {
            pageNumber = 1L;
        }
        Pageable pageable = PageRequest.of(
                pageNumber.intValue() - 1,
                tasksPerPage,
                Sort.Direction.ASC,
                "id");
        String statusFilter = params.get("statusFilter");
        String creatorFilter = params.get("creatorFilter");
        String nameFilter = params.get("nameFilter");

        Specification<Task> spec = Specification.where(null);
        if (creatorFilter != null &&
                !creatorFilter.equals("null") &&
                !creatorFilter.equals("")) {
            spec = spec.and(TaskSpecifications.creatorContains(creatorFilter));
        }
        if (nameFilter != null  &&
                !nameFilter.equals("null")  &&
                !nameFilter.equals("")) {
            spec = spec.and(TaskSpecifications.nameContains(nameFilter));
        }
        if (statusFilter != null &&
                !statusFilter.equals("ALL") &&
                !statusFilter.equals("null") &&
                !statusFilter.equals("")) {
            spec = spec.and(TaskSpecifications.statusEqual(Task.Status.valueOf(statusFilter)));
        }
        return taskRepository.findAll(spec, pageable);
    }

    public void insert(Task task) {
        task.setStatus(Task.Status.OPEN);
        taskRepository.save(task);
    }
    public void delete(Task task) {
        taskRepository.delete(task);
    }
    public void update(Task task) {taskRepository.save(task);}
    public Optional<Task> findById(Long id) {return taskRepository.findById(id);}
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

}

package com.dennn66.tasktracker.services;

import com.dennn66.gwt.common.TaskCreateDto;
import com.dennn66.gwt.common.TaskDto;
import com.dennn66.tasktracker.entities.Task;
import com.dennn66.tasktracker.entities.User;
import com.dennn66.tasktracker.mappers.TaskMapper;
import com.dennn66.tasktracker.repositories.TaskRepository;
import com.dennn66.tasktracker.repositories.specifications.TaskSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
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

    public TaskDto update(TaskDto taskDto) {
        Task task = TaskMapper.MAPPER.toTask(taskDto);
        task.setStatus(Task.Status.valueOf(taskDto.getStatusId()));
        task.setCreator(taskRepository.findById(taskDto.getId()).get().getCreator());
        taskRepository.save(task);
        return TaskMapper.MAPPER.fromTask(task);
    }
    public Optional<Task> findById(Long id) {return taskRepository.findById(id);}
    public void remove(Long id) {
        taskRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return taskRepository.existsById(id);
    }
    public TaskDto findOne(Long id) {
        return taskRepository.findById(id).map(TaskMapper.MAPPER::fromTask).get();
    }

    public TaskDto save(TaskCreateDto taskCreateDto, User creator) {
        Task task = TaskMapper.MAPPER.toTask(taskCreateDto);
        task.setStatus(Task.Status.OPEN);
        task.setCreator(creator);
        if(task.getAssignee().getId() < 0) task.setAssignee(null);
        task = taskRepository.save(task);
        return TaskMapper.MAPPER.fromTask(task);
    }

    public List<TaskDto> getAllTasks(Map<String,String> params) {
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
        return TaskMapper.MAPPER.fromTaskList(taskRepository.findAll(spec, pageable).toList());
    }
}

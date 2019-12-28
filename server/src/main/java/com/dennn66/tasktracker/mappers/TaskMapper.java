package com.dennn66.tasktracker.mappers;

import com.dennn66.gwt.common.TaskCreateDto;
import com.dennn66.gwt.common.TaskDto;
import com.dennn66.tasktracker.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TaskMapper {

    TaskMapper MAPPER = Mappers.getMapper(TaskMapper.class);


    @Mapping(source = "statusId", target = "status")
    Task toTask (TaskDto taskDto);
    Task toTask(TaskCreateDto taskCreateDto);

    @Mapping(source = "status", target = "statusId")
    @Mapping(source = "status.displayValue", target = "status")
    TaskDto fromTask(Task task);
    List<Task> toTaskList(List<TaskDto> taskDtos);
    List<TaskDto> fromTaskList(List<Task> tasks);
}

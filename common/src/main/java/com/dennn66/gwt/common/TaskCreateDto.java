package com.dennn66.gwt.common;


import javax.validation.constraints.Size;

public class TaskCreateDto {

    @Size(min = 4, message = "Task title too short")
    private String name;// название,
    private UserReferenceDto assignee; // исполнитель,
    private String description; // описание,

    public TaskCreateDto() {
    }

    public TaskCreateDto(String name, UserReferenceDto assignee, String description) {
        this.name = name;
        this.assignee = assignee;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserReferenceDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserReferenceDto assignee) {
        this.assignee = assignee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

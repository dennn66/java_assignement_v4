package com.dennn66.gwt.common;


import javax.validation.constraints.Size;

public class TaskDto {

    private Long id;//id,
    @Size(min = 4, message = "Task title too short")
    private String name;// название,
    private UserReferenceDto creator;
    private UserReferenceDto assignee;
    private String description; // описание,
    private String status; // статус
    private String statusId; // статус

    public TaskDto() {
    }

    public TaskDto(Long id, String name, UserReferenceDto creator,
                   UserReferenceDto assignee, String description,
                   String status, String statusId) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.assignee = assignee;
        this.description = description;
        this.status = status;
        this.statusId = statusId;
    }

    public TaskDto(Long id, String name, UserReferenceDto assignee, String description, String statusId) {
        this.id = id;
        this.name = name;
        this.assignee = assignee;
        this.description = description;
        this.statusId = statusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserReferenceDto getCreator() {
        return creator;
    }

    public void setCreator(UserReferenceDto creator) {
        this.creator = creator;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }
}

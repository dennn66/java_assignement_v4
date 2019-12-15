package com.dennn66.gwt.common;


public class TaskDto {

    private Long id;//id,
    private String name;// название,
    private String creator;// имя владельца задачи,
    private Long creatorId;// имя владельца задачи,
    private String assignee; // имя исполнителя,
    private Long assigneeId; // имя исполнителя,
    private String description; // описание,
    private String status; // статус
    private String statusId; // статус

    public TaskDto() {
    }

    public TaskDto(Long id, String name, String creator,
                   Long creatorId, String assignee, Long assigneeId,
                   String description, String status, String statusId) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.creatorId = creatorId;
        this.assignee = assignee;
        this.assigneeId = assigneeId;
        this.description = description;
        this.status = status;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
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

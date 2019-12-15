package com.dennn66.tasktracker.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task implements Serializable, Comparable<Task>{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;//id,
    @Column(name = "name")
    private String name;// название,

    @ManyToOne(optional = false)
    @JoinColumn(name = "creator")
    private User creator;// имя владельца задачи,

    @ManyToOne()
    @JoinColumn(name = "assignee")
    private User assignee; // имя исполнителя,

    @Column(name = "description")
    private String description; // описание,

    @Column(name = "status")
    private Status status; // статус

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public int compareTo(Task task) {
        return status.priority - task.status.priority;
    }

    public enum Status {
        OPEN("Open", 1), INPROGRESS("In Progress", 2), COMPLETED("Completed", 3);
        String name;
        int priority;

        Status(String name, int priority) {
            this.name = name;
            this.priority = priority;
        }
        public String getDisplayValue() {
            return name;
        }
    }
    public enum Filter {
        ALL("All"), OPEN("Open"), INPROGRESS("In Progress"), COMPLETED("Completed");
        String name;

        Filter(String name) {
            this.name = name;
        }
        public String getDisplayValue() {
            return name;
        }
    }

    @Override
    public String toString() {
        StringBuilder TaskString = new StringBuilder();
        return TaskString.append(id).
                append(" ").append(name).
                append(" status:").append(status).
                append(" assignee:").append(assignee).
                append(" author:").append(creator).
                append(" description: ").append(description).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Task)) return false;
        return ((Task)obj).id.equals(id) && ((Task)obj).name.equals(name);
    }

    @Override
    public int hashCode() { return id.intValue() + name.hashCode(); }
}

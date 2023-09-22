package cpts422PAJ.personalPlanner.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String taskName;

    private boolean completed;

    private String note;

    private Timestamp created;

    private Timestamp dueDate;

    public Task(String taskName, boolean completed, String note, Timestamp dueDate) {
        this.taskName = taskName;
        this.completed = completed;
        this.note = note;
        this.created = new Timestamp(System.currentTimeMillis());
        this.dueDate = dueDate;
    }

    public Task() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreated() {
        return created;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", completed=" + completed +
                ", note='" + note + '\'' +
                ", created=" + created +
                ", dueDate=" + dueDate +
                '}';
    }
}

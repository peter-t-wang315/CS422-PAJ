package cpts422PAJ.personalPlanner.entities;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    private String taskName;

    private boolean completed;

    private String note;

    private Timestamp created;

    private Date dueDate;


    @ManyToOne
//    @JoinColumn(name = "Users_id")
    private Users u;


    public Task(String taskName, boolean completed, String note, Date dueDate, Users u) {
        this.taskName = taskName;
        this.completed = completed;
        this.note = note;
        this.created = new Timestamp(System.currentTimeMillis());
        this.dueDate = dueDate;
        this.u = u;
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

    public Date getDueDate() {
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
                ", user=" + u +
                '}';
    }
}

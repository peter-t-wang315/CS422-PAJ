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
    private Tag tag;


    @ManyToOne
//    @JoinColumn(name = "Users_id")
    private Users u;


    public Task(String taskName, boolean completed, String note, Date dueDate, Users u, Tag tag) {
        this.taskName = taskName;
        this.completed = completed;
        this.note = note;
        this.created = new Timestamp(System.currentTimeMillis());
        this.dueDate = dueDate;
        this.u = u;
        this.tag = tag;
    }

    public Task() {
        this.taskName = "";
        this.completed = false;
        this.note = "";
        this.created = new Timestamp(System.currentTimeMillis());
        this.dueDate = null;
        this.u = null;
        this.tag = null;
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

    public Long getUserID() { return u.getId(); }

    public Users getUser() { return u; }

    public Tag getTag() { return tag; }

    public String getTagName() { return tag.getName(); }

    public void setUser(Users u) {
        this.u = u;
    }

    public void setCreated(Timestamp created){
        this.created = created;
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

package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;


    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Iterable<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public List<Task> getTasksForUser(Users user) {
        return taskRepository.findTasksByUserId(user.getId());
    }
    @Override
    public List<Task> getTasksByTag(Tag tag) { return taskRepository.findTasksByTagId(tag.getId()); }
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task save(Task task) {
        Date newDueDate = calculateNewDueDate(task);
        task.setDueDate(new Timestamp(newDueDate.getTime()));
        return taskRepository.save(task);
    }


    public int amountOfTasks(Long uId){
        List<Task> currTasks = new ArrayList<>();
        currTasks = taskRepository.findTasksByUserId(uId);
        return currTasks.size();
    }

    public Date calculateNewDueDate(Task task) {
        Tag tag = task.getTag();
        Date dueDate = task.getDueDate();

        if (dueDate == null) {
            dueDate = new Date();
        }
        if (tag != null && tag.getName() != null) {
            int dueDateIncrement = getDueDateIncrement(tag.getName());
            if (task.getDueDate() != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(task.getDueDate());
                cal.add(Calendar.DAY_OF_MONTH, dueDateIncrement);
                return new Date(cal.getTimeInMillis());
            }
        }
        return task.getDueDate();
    }

    public int getDueDateIncrement(String tagName) {
        switch (tagName) {
            case "Homework":
                return 7;
            case "Work":
                return 14;
            case "Life":
                return 3;
            case "none":
                return 0;
            default:
                return 0;
        }
    }



}

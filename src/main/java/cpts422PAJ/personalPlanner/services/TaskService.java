package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;

import java.util.List;

public interface TaskService {
    public Iterable<Task> findAll();

    public List<Task> getTasksForUser(Users user);

    public List<Task> getTasksByTag(Tag tag);

    public Task getTaskById(Long id);

    public Task save(Task task);

    public int amountOfTasks(Long uId);

}

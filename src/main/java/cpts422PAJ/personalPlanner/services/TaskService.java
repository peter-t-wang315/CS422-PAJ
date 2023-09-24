package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;

public interface TaskService {
    public Iterable<Task> findAll();

//    public Iterable<Task> findAllByUsers(Users users);

    public Task save(Task task);

}

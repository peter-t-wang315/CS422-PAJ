package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Users;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public Task save(Task task) {
        return null;
    }

}

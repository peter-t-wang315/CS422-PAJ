package cpts422PAJ.personalPlanner.services;

import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.repositories.TaskRepository;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

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
    public List<Task> getInProgressTasksForUser(Users user) {
        return taskRepository.findTasksByUserIdAndStatus(user.getId(), "In Progress");
    }

    @Override
    public List<Task> getDoTodayTasksForUser(Users user) {
        Date today = new Date();
        return taskRepository.findTasksByUserIdAndStatusAndDueDate(user.getId(), "To Do", today);
    }

    @Override
    public List<Task> getDueThisWeekTasksForUser(Users user) {
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, 7);  // Add 7 days to get the end of the week

        Date endOfWeek = calendar.getTime();

        return taskRepository.findTasksByUserIdAndStatusAndDueDateBetween(user.getId(), "To Do", today, endOfWeek);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task save(Task task) {

        return taskRepository.save(task);
    }

}

package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class TaskController {
    private TaskService taskService;

    private UserService userService;

    public TaskController(TaskService taskService, UserService usersService) {
        this.taskService = taskService;
        this.userService = usersService;
    }

    @RequestMapping("/")
    public String getTasks(Model model){
        Iterable<Users> all_users = userService.findAll();
        Users current_user= userService.getUserById(new Long(2));
//        model.addAttribute("tasks", taskService.getTasksForUser(current_user));
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("users", userService.findAll());
        return "index";
    }

    @RequestMapping("/editTask/{taskId}")
    public String editTask(@PathVariable Long taskId, Model model) {
        Task current_task = taskService.getTaskById(taskId);
        Long userId = null;
        String dueDate = null;
        String createdTime = null;
        model.addAttribute("currentTask", current_task);
        model.addAttribute("dueDate", dueDate);
        model.addAttribute("createdTime", createdTime);
        model.addAttribute("userId", userId)
        ;

        return "editTask";
    }

    @RequestMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam Long userId, @RequestParam String createdTime, @RequestParam String dueDate, Model model) {
        System.out.println(task);
        task.setUser(userService.getUserById(userId));
        DateFormat dueDateFormat = new SimpleDateFormat("yyyy-MM-ddTHH:mm");
        DateFormat createdDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date parsedDueDate = dueDateFormat.parse(dueDate);
            Date parsedCreatedTime = createdDateFormat.parse(createdTime);
            task.setDueDate(new Timestamp(parsedDueDate.getTime()));
            task.setCreated(new Timestamp(parsedCreatedTime.getTime()));
        } catch (ParseException e) {
            System.out.println("Broken datetimes");
        }
        taskService.save(task);
        return "redirect:/";
    }
}

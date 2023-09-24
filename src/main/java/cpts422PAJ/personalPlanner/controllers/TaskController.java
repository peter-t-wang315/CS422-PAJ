package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("tasks", taskService.findAll());
        model.addAttribute("users", userService.findAll());
        return "index";
    }
}

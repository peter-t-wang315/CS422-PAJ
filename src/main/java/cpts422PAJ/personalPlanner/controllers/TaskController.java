package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;

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
//        System.out.println("In getTasks");
//        Iterable<Users> all_users = userService.findAll();
        Long idActiveUser = userService.findActiveUser();
        Users current_user= userService.getUserById(idActiveUser);
        model.addAttribute("tasks", taskService.getTasksForUser(current_user));
        model.addAttribute("users", userService.findAll());
        return "index";
    }
}

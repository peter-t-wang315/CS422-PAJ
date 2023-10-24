package cpts422PAJ.personalPlanner.controllers;

import cpts422PAJ.personalPlanner.entities.Tag;
import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TagService;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.apache.catalina.User;
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
import java.util.Calendar;
import java.util.Date;

@Controller
public class TaskController {
    private TaskService taskService;

    private UserService userService;

    private TagService tagService;

    public TaskController(TaskService taskService, UserService usersService, TagService tagService) {
        this.taskService = taskService;
        this.userService = usersService;
        this.tagService = tagService;
    }

    @RequestMapping("/")
    public String getTasks(Model model){
//        System.out.println("In getTasks");
//        Iterable<Users> all_users = userService.findAll();
        try {
            //this means that it is an admin account
            if (userService.checkIfAdmin()){
                model.addAttribute("tasks", taskService.findAll());
                model.addAttribute("users", userService.findAll());
                return "index";
            }else{ //this is a regular user
                Long idActiveUser = userService.findActiveUser();
                Users current_user = userService.getUserById(idActiveUser);
                model.addAttribute("tasks", taskService.getTasksForUser(current_user));
                model.addAttribute("users", userService.findAll());
                return "index";
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());

            userService.logOffAllUsers();
            return "redirect:/login";
        }
    }

    @RequestMapping("/addTask")
    public String addTask(Model model) {
        Task newTask = new Task();
        Long idActiveUser = userService.findActiveUser();

        Boolean not_unique = userService.notUniqueRedirection();
        if (not_unique) {
            return "redirect:/";
        }
        else {
            System.out.println("They unique yuh");
        }

        System.out.println(newTask);
        newTask = taskService.save(newTask);
        System.out.println(newTask);
        String newDueDate = null;
        String createdTime = null;
        model.addAttribute("newTask", newTask);
        model.addAttribute("allUserIds", userService.findAll());
        model.addAttribute("newDueDate", newDueDate);
        model.addAttribute("createdTime", createdTime);
        model.addAttribute("allTags", tagService.findAll());

        if (idActiveUser == 0 ){
            userService.logOffAllUsers();
            return "redirect:/login";
        }

        return "addTask";
    }

    @RequestMapping("/editTask/{taskId}")
    public String editTask(@PathVariable Long taskId, Model model) {
        Long idActiveUser = userService.findActiveUser();
        Task current_task = taskService.getTaskById(taskId);
//        System.out.println(current_task.getUserID());
//        System.out.println(idActiveUser);
        if (idActiveUser == 0 ){
            userService.logOffAllUsers();
            return "redirect:/login";
        }

        if (!idActiveUser.equals(current_task.getUserID()) && userService.checkIfAdmin()){
            System.out.println("Bypass because admin");
        }
        else if (!idActiveUser.equals(current_task.getUserID()) && !userService.checkIfAdmin()){
            return "redirect:/";
        }
        Long userId = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String newDueDate = (current_task.getDueDate() != null) ? dateFormat.format(current_task.getDueDate()) : "";
        String createdTime = null;
        model.addAttribute("currentTask", current_task);
        model.addAttribute("allTags", tagService.findAll());
        model.addAttribute("newDueDate", newDueDate);
        model.addAttribute("createdTime", createdTime);
//        model.addAttribute("userId", userId);
        model.addAttribute("allUserIds", userService.findAll());
        return "editTask";
    }

    @RequestMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam Long userId, @RequestParam Long tagId, @RequestParam String createdTime, @RequestParam String newDueDate, Model model) {
        System.out.println(task);
        System.out.println("In updateeee");
        if (userService.checkIfAdmin()){
            task.setUser(userService.getUserById(userId));
        }
        else{
            task.setUser(userService.getUserById(userService.findActiveUser()));
        }

        Tag selectedTag = tagService.findById(tagId);
        task.setTag(selectedTag);
        DateFormat dueDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        DateFormat createdDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date parsedDueDate = dueDateFormat.parse(newDueDate);
            Date parsedCreatedTime = createdDateFormat.parse(createdTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDueDate);
            calendar.add(Calendar.DAY_OF_MONTH, selectedTag.getDueDate()); // Add the tag's dueDate value to the task's dueDate
            task.setDueDate(new Timestamp(calendar.getTimeInMillis()));
            task.setDueDate(new Timestamp(parsedDueDate.getTime()));
            task.setCreated(new Timestamp(parsedCreatedTime.getTime()));
        } catch (ParseException e) {
            System.out.println("Broken datetimes");
        }
        taskService.save(task);
        return "redirect:/";
    }
}
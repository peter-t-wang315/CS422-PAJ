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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        try {
            Long idActiveUser = userService.findActiveUser();
            Users current_user = userService.getUserById(idActiveUser);
            // Get tasks for the user
            List<Task> allTasks = taskService.getTasksForUser(current_user);

            // Separate tasks into categories: In Progress, Do Today, Due This Week
            List<Task> inProgressTasks = new ArrayList<>();
            List<Task> doTodayTasks = new ArrayList<>();
            List<Task> dueThisWeekTasks = new ArrayList<>();

            // current date for comparison
            Date currentDate = new Date();

            for (Task task : allTasks) {
                if (task.getStatus().equals("In Progress")) {
                    inProgressTasks.add(task);
                } else if (task.getStatus().equals("To Do") && task.getDueDate() != null) {
                    // Check if the task is due today
                    Date dueDate = new Date(task.getDueDate().getTime());
                    if (isSameDay(currentDate, dueDate)) {
                        doTodayTasks.add(task);
                    } else if (isDueThisWeek(currentDate, dueDate)) {
                        dueThisWeekTasks.add(task);
                    }
                }
            }

            model.addAttribute("inProgressTasks", inProgressTasks);
            model.addAttribute("doTodayTasks", doTodayTasks);
            model.addAttribute("dueThisWeekTasks", dueThisWeekTasks);
            model.addAttribute("tasks", taskService.getTasksForUser(current_user));
            model.addAttribute("users", userService.findAll());
            return "index";
        } catch (Exception e) {
            System.out.println(e.getMessage());

            userService.logOffAllUsers();
            return "redirect:/login";
        }
    }

    // Helper method to check if two dates are on the same day
    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    // Helper method to check if the due date is within the current week
    private boolean isDueThisWeek(Date currentDate, Date dueDate) {
        Calendar calCurrent = Calendar.getInstance();
        calCurrent.setTime(currentDate);
        Calendar calDueDate = Calendar.getInstance();
        calDueDate.setTime(dueDate);

        int currentWeek = calCurrent.get(Calendar.WEEK_OF_YEAR);
        int dueWeek = calDueDate.get(Calendar.WEEK_OF_YEAR);

        return calCurrent.get(Calendar.YEAR) == calDueDate.get(Calendar.YEAR) && currentWeek == dueWeek;
    }

    @RequestMapping("/addTask")
    public String addTask(Model model) {
        Task newTask = new Task();
        newTask = taskService.save(newTask);
//        System.out.println(newTask);
        Long idActiveUser = userService.findActiveUser();
        String newDueDate = null;
        String createdTime = null;
        model.addAttribute("newTask", newTask);
        model.addAttribute("userId", idActiveUser);
        model.addAttribute("newDueDate", newDueDate);
        model.addAttribute("createdTime", createdTime);
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
        if (!idActiveUser.equals(current_task.getUserID())){
            return "redirect:/";
        }
        Long userId = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String newDueDate = (current_task.getDueDate() != null) ? dateFormat.format(current_task.getDueDate()) : "";
        String createdTime = null;
        model.addAttribute("currentTask", current_task);
        model.addAttribute("newDueDate", newDueDate);
        model.addAttribute("createdTime", createdTime);
        model.addAttribute("userId", userId);

        return "editTask";
    }

    @RequestMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task, @RequestParam Long userId, @RequestParam String createdTime, @RequestParam String newDueDate, Model model) {
        System.out.println(task);
        task.setUser(userService.getUserById(userId));
        DateFormat dueDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        DateFormat createdDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date parsedDueDate = dueDateFormat.parse(newDueDate);
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
package cpts422PAJ.personalPlanner.controllers;


import cpts422PAJ.personalPlanner.entities.Task;
import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.TaskService;
import cpts422PAJ.personalPlanner.services.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.PseudoColumnUsage;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;
    private TaskService taskService;


    public LoginController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @RequestMapping("/login")
    public String getUsers(Model model){
        model.addAttribute("userNames", userService.findAll());

        return "login";
    }

    @RequestMapping(value = "/loginInfo", method = RequestMethod.POST)
    public String getUsername(@RequestParam("userName") String username, @RequestParam("passWord") String password){
//        System.out.println(username + " " + password);
        if (userService.isUser(username,password) == false){

            return "login";
        }
        userService.changeUserActivity(username,password);

        return "redirect:/";

    }

    //could make a controller just for this, seems unnecesary without other functionalities
    @RequestMapping(value= "/logOut", method = RequestMethod.POST)
    public String logOut(){
        userService.logOffUser();
        return "redirect:/login";
    }

    @RequestMapping(value="/register")
    public String register(){


        return "register";
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String registerUser(@RequestParam("email") String email, @RequestParam("userName") String username,
                               @RequestParam("passWord") String password,  @RequestParam("first") String firstName,
                               @RequestParam("last") String lastName, @RequestParam("admin") Boolean isAdmin,
                               @RequestParam("adminPassword") String adminPass){


        if(!userService.checkSameUser(username)){

            return "register";
        }
        if (isAdmin){
            String adminPassword = userService.getAdminPassword();
            if(!adminPass.equals(adminPassword)){
                isAdmin = false;
            }
        }

        Users newUser = new Users(email, username,password,firstName,lastName, isAdmin);
        System.out.println(email + " "+ username +" "+  password +" "+  username+" "+  firstName +" "+  lastName + " "+ isAdmin +" "+  adminPass);
        //make a check here to call user and task services to give tasks to them
        //we get the
        if (isAdmin){
            List<Task> taskList = new ArrayList<>();
            Iterable<Task> taskIterable = taskService.findAll();
            taskIterable.forEach(taskList::add);
//            for (int i = 0; i< 2;i++){
//                System.out.println(taskList.get(i));
//                taskList.get(i);
//            }
            Task firstTask = taskList.get(0);
            System.out.println(firstTask);
            firstTask.setTaskName("Admin CHanged");
            taskService.save(firstTask);
        }



        userService.save(newUser);



        return "redirect:/";
    }









}

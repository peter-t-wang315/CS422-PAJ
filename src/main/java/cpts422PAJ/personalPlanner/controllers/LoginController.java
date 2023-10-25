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
                               @RequestParam("last") String lastName,
                               @RequestParam("adminPassword") String adminPass){

        System.out.println("This is the password:" + "|"+ adminPass+"|");
        boolean isAdminTemp = false;

        if(!userService.checkSameUser(username)){

            return "register";
        }

            String adminPassword = userService.getAdminPassword();
            if(!adminPass.equals(adminPassword)){
                System.out.println("Not an admin!");
                isAdminTemp = false;
            }
            else{
                System.out.println("An admin account!");
                isAdminTemp = true;
            }


        if(userService.checkEmail(email)){
            System.out.println("valid email");
        }
        else{
            return "register";
        }


        Users newUser = new Users(email, username,password,firstName,lastName, isAdminTemp);
        System.out.println(email + " "+ username +" "+  password +" "+  username+" "+  firstName +" "+  lastName + " "+ isAdminTemp +" "+  adminPass);

        userService.save(newUser);

        return "redirect:/";
    }









}

package cpts422PAJ.personalPlanner.controllers;


import cpts422PAJ.personalPlanner.entities.Users;
import cpts422PAJ.personalPlanner.services.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.PseudoColumnUsage;
import java.util.List;

@Controller
public class LoginController {

    private UserService userService;


    public LoginController(UserService userService) {
        this.userService = userService;
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
                               @RequestParam("last") String lastName){

        System.out.println(email + username + password + username+ firstName + lastName);
        if(!userService.checkSameUser(username)){

            return "register";
        }

        Users newUser = new Users(email, username,password,firstName,lastName);

        userService.save(newUser);



        return "redirect:/";
    }









}

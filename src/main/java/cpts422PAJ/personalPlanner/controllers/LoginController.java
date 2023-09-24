package cpts422PAJ.personalPlanner.controllers;


import cpts422PAJ.personalPlanner.services.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

}

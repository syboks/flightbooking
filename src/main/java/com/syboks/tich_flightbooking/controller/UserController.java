package com.syboks.tich_flightbooking.controller;

import com.syboks.tich_flightbooking.entities.UserModel;
//import com.syboks.tich_flightbooking.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    //private final UserService userService;

    /*@GetMapping("/login")
    public String getLoginPage(){
        return "login_page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("user", new UserModel());
        return "registration_page";
    }
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute UserModel user){
        userService.register(user);
        return "redirect:/login?success";
    }*/
}

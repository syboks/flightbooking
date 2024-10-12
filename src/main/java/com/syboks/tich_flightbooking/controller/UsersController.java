package com.syboks.tich_flightbooking.controller;


import com.syboks.tich_flightbooking.entities.Users;
import com.syboks.tich_flightbooking.entities.UsersType;
import com.syboks.tich_flightbooking.services.UsersService;
import com.syboks.tich_flightbooking.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UsersController {
    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<Users> users = usersService.getAll();  // Assuming usersService has a method getAll() to retrieve all users
        model.addAttribute("usersList", users);
        return "users-list";  // Thymeleaf template name
    }


    @GetMapping("/register")
    public String register(Model model){
        List<UsersType> userTypes=usersTypeService.getAll();
        model.addAttribute("getAllTypes",userTypes);
        model.addAttribute("user", new Users());
        return "register";
    }
    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model){
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if(optionalUsers.isPresent()){
            model.addAttribute("error","Email already exists");
            List<UsersType> usersTypes=usersTypeService.getAll();
            model.addAttribute("getAllTypes",usersTypes);
            model.addAttribute("user",new Users());
            return "register";
        }
        System.out.println("User:: "+users);
        usersService.addNew(users);
        //return "users-list";
        return "redirect:/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        Authentication authentication=SecurityContextHolder.getContext()
                .getAuthentication();
        if(authentication!=null){
            new SecurityContextLogoutHandler().logout(request, response,
                    authentication);
        }
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        return "redirect:/";
    }
    // Edit user form
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        Users user = usersService.getUserById(id).get();  // Method to retrieve user by ID
        List<UsersType> userTypes = usersTypeService.getAll();
        model.addAttribute("user", user);
        model.addAttribute("userTypes", userTypes);
        return "edit-user";  // Thymeleaf template for editing users
    }

    // Handle user update
    @PostMapping("/users/edit/{id}")
    public String updateUser(@PathVariable("id") int id, @Valid Users user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<UsersType> userTypes = usersTypeService.getAll();
            model.addAttribute("userTypes", userTypes);
            return "edit-user";
        }
        usersService.updateUser(id,user);  // Method to update the user
        return "redirect:/users";
    }

    // Delete user
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        usersService.deleteUserById(id);  // Method to delete user by ID
        return "redirect:/users";
    }
}

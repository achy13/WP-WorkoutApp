package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.exceptions.InvalidArgumentsException;
import com.finki.wp.workoutapp.model.exceptions.PasswordsDoNotMatchException;
import com.finki.wp.workoutapp.model.exceptions.UsernameAlreadyExistsException;
import com.finki.wp.workoutapp.service.impl.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("bodyContent", "register");
        return "index";
    }

    @PostMapping
    public String registration(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword,
                               @RequestParam String email,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String image)
    {
        try{
            this.userService.register(username, password, repeatedPassword, firstName, lastName, email, image);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }

}
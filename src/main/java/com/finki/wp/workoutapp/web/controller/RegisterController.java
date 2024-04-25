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

    /*
    @PostMapping("/save")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               Model model){
        User existingUser = userService.findUserByUsername(userDto.getUsername());

        if(existingUser != null && existingUser.getUsername() != null && !existingUser.getUsername().isEmpty()){
            model.addAttribute("user", userDto);
            model.addAttribute("error", "There is already an account registered with the same username");
            model.addAttribute("bodyContent", "register");
            return "index";
        }

        userService.create(userDto);
        return "redirect:/register?success";
    }
    */

    @PostMapping
    public String registration(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String repeatedPassword,
                               @RequestParam String email,
                               @RequestParam String firstName,
                               @RequestParam String lastName)
    {
        try{
            this.userService.register(username, password, repeatedPassword, firstName, lastName, email);
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }

    }

}
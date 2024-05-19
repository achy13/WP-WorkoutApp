package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.exceptions.InvalidArgumentsException;
import com.finki.wp.workoutapp.model.exceptions.PasswordsDoNotMatchException;
import com.finki.wp.workoutapp.model.exceptions.UsernameAlreadyExistsException;
import com.finki.wp.workoutapp.service.IUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/changePassword")
    public String getChangePasswordForm(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(required = false) String error, Model model){
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        String username = userDetails.getUsername();

        model.addAttribute("username", username);
        return "changePass-form";
    }

    @PostMapping("/save")
    public String changePasswordForm(@RequestParam String username, @RequestParam String oldPassword,
                                     @RequestParam String newPassword, @RequestParam String repeatedPassword){
        try{
            this.userService.changePassword(username, oldPassword, newPassword, repeatedPassword);
            return "redirect:/user/save";
        } catch (PasswordsDoNotMatchException exception) {
            return "redirect:/user/save?error=" + exception.getMessage();
        }
    }
}

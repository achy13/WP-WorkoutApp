package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.exceptions.*;
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
        model.addAttribute("bodyContent", "changePass-form");
        return "account";
    }

    @PostMapping("/save")
    public String changePasswordForm(@RequestParam String username, @RequestParam String oldPassword,
                                     @RequestParam String newPassword, @RequestParam String repeatedPassword){
        try{
            this.userService.changePassword(username, oldPassword, newPassword, repeatedPassword);
            return "redirect:/user/changePassword";
        } catch (PasswordsDoNotMatchException | InvalidPasswordException | SamePasswordException exception) {
            return "redirect:/user/changePassword?error=" + exception.getMessage();
        }
    }

    @GetMapping("/account-settings")
    public String getAccountSettings(@AuthenticationPrincipal UserDetails userDetails, Model model){
        String username = userDetails.getUsername();
        User user = userService.findUserByUsername(username);

        model.addAttribute("user", user);
        model.addAttribute("bodyContent", "account-settings");
        return "account";
    }
}

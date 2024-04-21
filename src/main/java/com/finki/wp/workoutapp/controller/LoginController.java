package com.finki.wp.workoutapp.controller;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.exceptions.InvalidArgumentsException;
import com.finki.wp.workoutapp.model.exceptions.InvalidUserCredentialsException;
import com.finki.wp.workoutapp.service.impl.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent", "login");
        return "index";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;

        try {
            user = authService.login(request.getParameter("username"), request.getParameter("password"));
        } catch (InvalidUserCredentialsException | InvalidArgumentsException exception) {
            model.addAttribute("bodyContent", "login");
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "index";
        }

        request.getSession().setAttribute("user", user);
        return "redirect:/homePage";
    }
}


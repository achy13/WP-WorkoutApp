package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;

@Controller
@RequestMapping({"/","/homePage"})
public class MainController {

    private final ITrainingDayService trainingDayService;
    private final IUserService userService;

    public MainController(ITrainingDayService trainingDayService, IUserService userService) {
        this.trainingDayService = trainingDayService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("bodyContent", "home-page");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }

    //MeasurementsHelp
    @GetMapping("/help")
    public String getMeasurementsHelp(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("bodyContent", "measurements-help");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }
}

package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping({"/","/homePage"})
public class MainController {

    private final ITrainingDayService trainingDayService;
    private final MeasurementService measurementService;
    private final IUserService userService;

    public MainController(ITrainingDayService trainingDayService, MeasurementService measurementService, IUserService userService) {
        this.trainingDayService = trainingDayService;
        this.measurementService = measurementService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        User user = userService.findUserByUsername(userDetails.getUsername());

        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
            model.addAttribute("bodyContent", "home-page");
        } else {
            model.addAttribute("measurement", null);
            model.addAttribute("bodyContent", "add-measurement");
        }

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

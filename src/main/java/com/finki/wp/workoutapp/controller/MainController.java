package com.finki.wp.workoutapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping({"/homePage", "/"})
    public String getHomePage(Model model) {
        model.addAttribute("bodyContent", "home-page");
        return "index";
    }

    //MeasurementsHelp
    @GetMapping("/measurementsHelp")
    public String getMeasurementsHelp(Model model) {
        model.addAttribute("bodyContent", "measurements-help");
        return "index";
    }
}

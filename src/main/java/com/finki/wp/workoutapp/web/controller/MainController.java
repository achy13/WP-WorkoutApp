package com.finki.wp.workoutapp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/","/homePage"})
public class MainController {

    @GetMapping
    public String getHomePage(Model model) {
        model.addAttribute("bodyContent", "home-page");
        return "index";
    }

    //MeasurementsHelp
    @GetMapping("/help")
    public String getMeasurementsHelp(Model model) {
        model.addAttribute("bodyContent", "measurements-help");
        return "index";
    }
}

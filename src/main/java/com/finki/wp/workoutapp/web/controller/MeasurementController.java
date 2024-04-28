package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/measurement")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final IUserService userService;

    public MeasurementController(MeasurementService measurementService, IUserService userService) {
        this.measurementService = measurementService;
        this.userService = userService;
    }

    @GetMapping
    public String getMeasurementPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        System.out.println(user.getId());

        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
            model.addAttribute("bodyContent", "measurements-page");
        } else {
            model.addAttribute("measurement", null);
            model.addAttribute("bodyContent", "measurements-page");
        }
        return "index";
    }

    @PostMapping("/delete/{id}")
    public String deleteMeasurement(@PathVariable Long id) {
        this.measurementService.deleteMeasurementAndGoalsByMeasurementId(id);
        return "redirect:/measurement";
    }

    @GetMapping("/add-form")
    public String addMeasurementPage(Model model) {
        model.addAttribute("bodyContent", "add-measurement");
        return "index";
    }

    @GetMapping("/edit-form/{id}")
    public String editMeasurementPage(@PathVariable Long id, Model model) {
        if (this.measurementService.findMeasurementById(id).isPresent()) {
            Measurement measurements = this.measurementService.findMeasurementById(id).get();
            model.addAttribute("measurements", measurements);
            model.addAttribute("bodyContent", "edit-measurement");
            return "index";
        }
        return "redirect:/measurement?error=ProductNotFound";
    }

    @PostMapping("/add")
    public String saveMeasurement (@RequestParam Double weight, @RequestParam Double height, @RequestParam Integer years, @RequestParam Double shouldersSize, @RequestParam Double chestSize,
                                   @RequestParam Double hand, @RequestParam Double waist, @RequestParam Double abdomen, @RequestParam Double hip, @RequestParam Double leg) {
        this.measurementService.save(weight, height, years, shouldersSize, chestSize,
                hand, waist, abdomen, hip, leg, MeasurementType.MEASUREMENT);
        return "redirect:/measurement";
    }

    @PostMapping("/edit/{id}")
    public String saveMeasurement (@PathVariable("id") Long id,
                                   @RequestParam Double weight, @RequestParam Double height, @RequestParam Integer years, @RequestParam Double shouldersSize, @RequestParam Double chestSize,
                                   @RequestParam Double hand, @RequestParam Double waist, @RequestParam Double abdomen, @RequestParam Double hip, @RequestParam Double leg) {
        this.measurementService.edit(id, weight, height, years, shouldersSize, chestSize,
                hand, waist, abdomen, hip, leg);
        return "redirect:/measurement";
    }
}

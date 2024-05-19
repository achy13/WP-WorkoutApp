package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.IGoalService;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/goals")
public class GoalController {

    private final IGoalService goalService;
    private final MeasurementService measurementService;
    private final IUserService userService;
    private final ITrainingDayService trainingDayService;

    public GoalController(IGoalService goalService, MeasurementService measurementService, IUserService userService, ITrainingDayService trainingDayService) {
        this.goalService = goalService;
        this.measurementService = measurementService;
        this.userService = userService;
        this.trainingDayService = trainingDayService;
    }

    @GetMapping
    public String getGoalPage(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Goal> goals = this.goalService.findGoalByUser(user);
        Optional<Measurement> optionalMeasurement = this.measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()){
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }
        else {
            model.addAttribute("measurement", null);
        }
        model.addAttribute("goals", goals);
        model.addAttribute("bodyContent", "goals-page");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }

    @PostMapping("/delete/{id}")
    public String deleteGoal(@PathVariable Long id) {
        this.goalService.deleteGoalById(id);
        return "redirect:/goals";
    }

    @GetMapping("/add-form")
    public String addGoalPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("bodyContent", "goals-add");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }

    @GetMapping("/edit-form/{id}")
    public String editGoalPage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id, Model model) {
        if (this.goalService.findGoalById(id).isPresent()) {
            Goal goal = this.goalService.findGoalById(id).get();
            Optional<Measurement> optionalMeasurement = this.measurementService.findMeasurementByUserAndType(userService.findUserByUsername(userDetails.getUsername()), MeasurementType.MEASUREMENT);
            if (optionalMeasurement.isPresent()){
                Measurement measurement = optionalMeasurement.get();
                model.addAttribute("measurement", measurement);
            }
            else {
                model.addAttribute("measurement", goal.getMeasurement());
            }
            model.addAttribute("goal", goal);
            model.addAttribute("bodyContent", "goals-add");
            model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
            return "index";
        }
        return "redirect:/goals?error=GoalNotFound";
    }

    @PostMapping("/add")
    public String saveGoal () {
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Measurement measurement = this.measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT).get();
        Measurement newMeasurement = measurementService.save(measurement.getWeight(), measurement.getHeight(), measurement.getYears(),
                measurement.getShouldersSize(), measurement.getChestSize(), measurement.getHand(), measurement.getWaist(),
                measurement.getAbdomen(), measurement.getHip(), measurement.getLeg(), MeasurementType.GOAL).get();
        Goal goal = new Goal(user, newMeasurement);
        this.goalService.save(goal);
        return "redirect:/goals/edit-form/" + goal.getId();
    }

    @PostMapping("/edit/{id}")
    public String saveGoal (@PathVariable("id") Long id, @RequestParam String name, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
                                   @RequestParam Double weight, @RequestParam Double shouldersSize, @RequestParam Double chestSize,
                                   @RequestParam Double hand, @RequestParam Double waist, @RequestParam Double abdomen, @RequestParam Double hip, @RequestParam Double leg) {
        this.goalService.edit(id, name, date, weight, shouldersSize, chestSize, hand, waist, abdomen, hip, leg);
        return "redirect:/goals";
    }
}

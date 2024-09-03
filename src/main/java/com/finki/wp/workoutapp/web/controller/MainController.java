package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.MeasurementService;
import com.finki.wp.workoutapp.service.impl.GoalService;
import com.finki.wp.workoutapp.service.impl.WorkoutService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping({"/","/homePage"})
public class MainController {

    private final ITrainingDayService trainingDayService;
    private final MeasurementService measurementService;
    private final IUserService userService;
    private final GoalService goalService;
    private final WorkoutService workoutService;

    public MainController(ITrainingDayService trainingDayService, MeasurementService measurementService, IUserService userService, GoalService goalService, WorkoutService workoutService) {
        this.trainingDayService = trainingDayService;
        this.measurementService = measurementService;
        this.userService = userService;
        this.goalService = goalService;
        this.workoutService = workoutService;
    }

    @GetMapping
    public String getHomePage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        User user = userService.findUserByUsername(userDetails.getUsername());
        List<Goal> goals = goalService.findAllGoals();
        List<Workouts> workouts = workoutService.findAllWorkouts();


        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
            model.addAttribute("bodyContent", "home-page");
            model.addAttribute("name", user);
            model.addAttribute("weight", measurement.getWeight());
            if(!goals.isEmpty()) {
                Random random = new Random();
                Goal randomGoal = goals.get(random.nextInt(goals.size()));
                model.addAttribute("goal", randomGoal);
            } else {
                model.addAttribute("noGoals", true);
            }

            if(!workouts.isEmpty()) {
                Random random = new Random();
                Workouts randomWorkout = workouts.get(random.nextInt(workouts.size()));
                model.addAttribute("workout", randomWorkout);
            } else {
                model.addAttribute("noWorkouts", true);
            }
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
        User user = userService.findUserByUsername(userDetails.getUsername());
        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }
        model.addAttribute("bodyContent", "measurements-help");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }
}

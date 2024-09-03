package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.*;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/workouts")
public class WorkoutsController {

    private final MeasurementService measurementService;
    private final IWorkoutService workoutService;
    private final IExerciseService exerciseService;
    private final ICategoryService categoryService;
    private final IUserService userService;


    public WorkoutsController(MeasurementService measurementService, IWorkoutService workoutService, IExerciseService exerciseService, ICategoryService categoryService, IUserService userService) {
        this.measurementService = measurementService;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String getWorkoutPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Workouts> workouts = this.workoutService.findAllWorkouts();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }
        model.addAttribute("workouts", workouts);
        model.addAttribute("bodyContent", "workouts");
        return "index";
    }

    @GetMapping("/add-form")
    public String addWorkoutPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer age = measurementService.getAgeForUser(username);
        List<Exercise> exercises = this.exerciseService.findAllExercises();
        List<Category> categories = categoryService.findAllCategories();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("wor", new Workouts());
        model.addAttribute("exercises", exercises);
        model.addAttribute("age", age);
        model.addAttribute("bodyContent", "add-workout");
        return "index";
    }

    @PostMapping("/add")
    public String saveWorkout(@RequestParam String workoutName, @RequestParam String level, @RequestParam List<Long> exercisesId) {
        this.workoutService.save(workoutName, level, exercisesId);
        return "redirect:/workouts";
    }

    @PostMapping("/delete/{id}")
    public String deleteWorkout(@PathVariable Long id) {
        this.workoutService.deleteWorkoutById(id);
        return "redirect:/workouts";
    }
}

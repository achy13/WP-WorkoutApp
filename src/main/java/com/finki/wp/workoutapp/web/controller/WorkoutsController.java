package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.service.ICategoryService;
import com.finki.wp.workoutapp.service.IExerciseService;
import com.finki.wp.workoutapp.service.IWorkoutService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/workouts")
public class WorkoutsController {

    private final MeasurementService measurementService;
    private final IWorkoutService workoutService;
    private final IExerciseService exerciseService;
    private final ICategoryService categoryService;

    public WorkoutsController(MeasurementService measurementService, IWorkoutService workoutService, IExerciseService exerciseService, ICategoryService categoryService) {
        this.measurementService = measurementService;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String getWorkoutPage(Model model) {
        List<Workouts> workouts = this.workoutService.findAllWorkouts();
        model.addAttribute("workouts", workouts);
        model.addAttribute("bodyContent", "workouts");
        return "index";
    }

    @GetMapping("/add-form")
    public String addWorkoutPage( Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Integer age = measurementService.getAgeForUser(username);
        List<Exercise> exercises = this.exerciseService.findAllExercises();
        List<Category> categories = categoryService.findAllCategories();

        model.addAttribute("categories", categories);
        model.addAttribute("wor", new Workouts());
        model.addAttribute("exercises", exercises);
        model.addAttribute("age", age);
        model.addAttribute("bodyContent", "add-workout");
        return "index";
    }

    @PostMapping("/add")
    public String saveWorkout(@RequestParam String workoutName, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date workoutDate, @RequestParam String level, @RequestParam List<Long> exercisesId) {
        this.workoutService.save(workoutName, workoutDate, level, exercisesId);
        return "redirect:/workouts";
    }
}

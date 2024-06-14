package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.service.ICategoryService;
import com.finki.wp.workoutapp.service.IExerciseService;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    private final IExerciseService iExerciseService;
    private final ICategoryService iCategoryService;
    private final ITrainingDayService trainingDayService;

    public ExerciseController(IExerciseService iExerciseService, ICategoryService iCategoryService, ITrainingDayService trainingDayService) {
        this.iExerciseService = iExerciseService;
        this.iCategoryService = iCategoryService;
        this.trainingDayService = trainingDayService;
    }

    @GetMapping
    public String getProductPage(@AuthenticationPrincipal UserDetails userDetails,
                                 @RequestParam(required = false) String error,
                                 @RequestParam(required = false) Long categoryId,
                                 Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        String selectedCategoryName = null;
        String selectedCategoryImage = "selectedCategoryImage.jpg";
        if (categoryId != null) {
            Category category = iCategoryService.findCategoryById(categoryId);
            if (category != null) {
                selectedCategoryName = category.getName();
                selectedCategoryImage = category.getImagePath();
            }
        }

        List<Exercise> exercises;
        if(categoryId == null) {
            exercises = this.iExerciseService.findAllExercises();
        } else {
            exercises = this.iExerciseService.filter(categoryId);
        }

        model.addAttribute("selectedCategoryImage", selectedCategoryImage);
        model.addAttribute("selectedCategoryName", selectedCategoryName);
        model.addAttribute("categories", iCategoryService.findAllCategories());
        model.addAttribute("exercises", exercises);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("bodyContent","exercises-page");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }


    @GetMapping("/search")
    public String searchExercisesByName(@RequestParam String name,
                                        @RequestParam(required = false) Long categoryId,
                                        Model model) {
        String selectedCategoryName = null;
        String selectedCategoryImage = "selectedCategoryImage.jpg";
        if (categoryId != null) {
            Category category = iCategoryService.findCategoryById(categoryId);
            if (category != null) {
                selectedCategoryName = category.getName();
                selectedCategoryImage = category.getImagePath();
            }
        }

        model.addAttribute("selectedCategoryImage", selectedCategoryImage);
        model.addAttribute("selectedCategoryName", selectedCategoryName);
        model.addAttribute("selectedCategoryId", categoryId);

        if (categoryId == null) {
            model.addAttribute("exercises", Collections.emptyList());
        } else {
            List<Exercise> exercises = iExerciseService.findExercisesByNameAndCategory(name, categoryId);
            model.addAttribute("exercises", exercises);
        }
        model.addAttribute("bodyContent", "exercises-page");
        model.addAttribute("categories", iCategoryService.findAllCategories());
        return "index";
    }




    @PostMapping("/delete/{id}")
    public String deleteExercise(@PathVariable Long id) {
        Long categoryId = null;
        if (this.iExerciseService.findExerciseById(id).isPresent()) {
            Exercise exercise = this.iExerciseService.findExerciseById(id).get();
            categoryId = exercise.getCategory().getId();
        }
        this.iExerciseService.deleteExerciseById(id);
        return "redirect:/exercises?categoryId=" + categoryId;
    }

    @GetMapping("/add-form")
    public String addExercisePage(@AuthenticationPrincipal UserDetails userDetails,
                                  @RequestParam Long categoryId, Model model) {
        List<Category> categories = this.iCategoryService.findAllCategories();
        String selectedCategoryName = null;
        if (categoryId != null) {
            Category category = iCategoryService.findCategoryById(categoryId);
            if (category != null) {
                selectedCategoryName = category.getName();
            }
        }
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategoryName", selectedCategoryName);
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("bodyContent", "add-exercise");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }


    @GetMapping("/edit-form/{id}")
    public String editExercisePage(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable Long id, Model model) {
        if (this.iExerciseService.findExerciseById(id).isPresent()) {
            Exercise exercise = this.iExerciseService.findExerciseById(id).get();
            List<Category> categories = this.iCategoryService.findAllCategories();
            Long categoryId = exercise.getCategory() != null ? exercise.getCategory().getId() : null;
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("categories", categories);
            model.addAttribute("exercise", exercise);
            model.addAttribute("bodyContent", "edit-exercise");
            model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
            return "index";
        }
        return "redirect:/exercises?error=ProductNotFound";
    }

    @PostMapping("/add")
    public String saveExercise (@RequestParam String name, String image, @RequestParam String description, @RequestParam Long categoryId) {
        //Category categoryOptional = iCategoryService.findCategoryById(categoryId);
        //String categoryImage = categoryOptional.getImagePath();
        this.iExerciseService.save(name, image, description, categoryId);
        return "redirect:/exercises?categoryId=" + categoryId;
    }

    @PostMapping("/edit/{id}")
    public String saveExercise (@PathVariable("id") Long id,
                                @RequestParam String name, @RequestParam String image, @RequestParam String description, @RequestParam Long categoryId) {
        this.iExerciseService.edit(id, name, image, description, categoryId);
        return "redirect:/exercises?categoryId=" + categoryId;
    }
}

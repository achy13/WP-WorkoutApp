package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.service.ICategoryService;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/" +
        "category")
public class CategoryController {

    private final ICategoryService iCategoryService;
    private final ITrainingDayService trainingDayService;

    public CategoryController(ICategoryService iCategoryService, ITrainingDayService trainingDayService) {
        this.iCategoryService = iCategoryService;
        this.trainingDayService = trainingDayService;
    }

    @GetMapping
    public String getCategoryPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        List<Category> categories = this.iCategoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "categories-page");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }
}

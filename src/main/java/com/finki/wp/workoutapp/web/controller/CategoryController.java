package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.service.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/" +
        "category")
public class CategoryController {

    private final ICategoryService iCategoryService;

    public CategoryController(ICategoryService iCategoryService) {
        this.iCategoryService = iCategoryService;
    }

    @GetMapping
    public String getCategoryPage(Model model) {

        List<Category> categories = this.iCategoryService.findAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "categories-page");
        return "index";
    }
}

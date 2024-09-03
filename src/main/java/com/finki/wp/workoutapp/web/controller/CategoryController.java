package com.finki.wp.workoutapp.web.controller;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.service.ICategoryService;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/" +
        "category")
public class CategoryController {

    private final ICategoryService iCategoryService;
    private final ITrainingDayService trainingDayService;
    private final MeasurementService measurementService;
    private final IUserService userService;



    public CategoryController(ICategoryService iCategoryService, ITrainingDayService trainingDayService, MeasurementService measurementService, IUserService userService) {
        this.iCategoryService = iCategoryService;
        this.trainingDayService = trainingDayService;
        this.measurementService = measurementService;
        this.userService = userService;
    }

    @GetMapping
    public String getCategoryPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        List<Category> categories = this.iCategoryService.findAllCategories();
        User user = userService.findUserByUsername(userDetails.getUsername());
        Optional<Measurement> optionalMeasurement = measurementService.findMeasurementByUserAndType(user, MeasurementType.MEASUREMENT);
        if (optionalMeasurement.isPresent()) {
            Measurement measurement = optionalMeasurement.get();
            model.addAttribute("measurement", measurement);
        }
        model.addAttribute("categories", categories);
        model.addAttribute("bodyContent", "categories-page");
        model.addAttribute("hasEvent", trainingDayService.hasEvent(userDetails));
        return "index";
    }
}

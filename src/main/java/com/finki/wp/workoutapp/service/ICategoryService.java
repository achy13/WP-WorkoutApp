package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    List<Category> findAllCategories();

   Category findCategoryById(Long id);
}

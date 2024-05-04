package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.exceptions.InvalidCategoryIdException;
import com.finki.wp.workoutapp.repository.CategoryRepository;
import com.finki.wp.workoutapp.service.ICategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return this.categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(InvalidCategoryIdException::new);
    }
}

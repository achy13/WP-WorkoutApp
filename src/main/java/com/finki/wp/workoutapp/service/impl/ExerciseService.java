package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.model.exceptions.CategoryNotFoundException;
import com.finki.wp.workoutapp.model.exceptions.ExerciseNotFoundException;
import com.finki.wp.workoutapp.repository.CategoryRepository;
import com.finki.wp.workoutapp.repository.ExerciseRepository;
import com.finki.wp.workoutapp.service.ICategoryService;
import com.finki.wp.workoutapp.service.IExerciseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService implements IExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final CategoryRepository categoryRepository;
    private final ICategoryService iCategoryService;

    public ExerciseService(ExerciseRepository exerciseRepository, CategoryRepository categoryRepository, ICategoryService iCategoryService) {
        this.exerciseRepository = exerciseRepository;
        this.categoryRepository = categoryRepository;
        this.iCategoryService = iCategoryService;
    }

    @Override
    public List<Exercise> filter(Long categoryId) {
        if(categoryId != null) {
            Category category = iCategoryService.findCategoryById(categoryId);
            return exerciseRepository.findByCategory(category);
        }
        else {
            return exerciseRepository.findAll();
        }
    }

    @Override
    public List<Exercise> findAllExercises() {
        return exerciseRepository.findAll();
    }

    @Override
    public Optional<Exercise> findExerciseById(Long id) {
        return exerciseRepository.findById(id);
    }

    @Override
    public Optional<Exercise> save(String name, String image, String description, Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        Exercise exercise = new Exercise(name, image, description, category);
        Exercise savedExercise = exerciseRepository.save(exercise);
        category.getExercises().add(savedExercise);
        categoryRepository.save(category);
        return Optional.of(savedExercise);

    }

    @Override
    public void deleteExerciseById(Long id) {
        //exerciseRepository.deleteById(id);
        Optional<Exercise> exerciseOptional = this.exerciseRepository.findById(id);
        if(exerciseOptional.isPresent()) {
            Exercise exercise = exerciseOptional.get();
            Category category = exercise.getCategory();
            if(category != null) {
                category.getExercises().remove(exercise);
                categoryRepository.save(category);
            }
            exerciseRepository.deleteById(id);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }

    @Override
    public Optional<Exercise> edit(Long id, String name, String image, String description, Long category) {
        Exercise e = exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException(id));
        Category c = categoryRepository.findById(category).orElseThrow(() -> new CategoryNotFoundException(category));
        e.setName(name);
        e.setImage(image);
        e.setDescription(description);
        e.setCategory(c);
        exerciseRepository.save(e);
        return Optional.of(e);
    }


    @Override
    public List<Exercise> findExercisesByNameAndCategory(String name, Long categoryId) {
        return exerciseRepository.findByCategory_IdAndNameContainingIgnoreCase(categoryId, name);
    }

    @Override
    public List<Exercise> getExercisesByCategoryId(Long categoryId) {
        return exerciseRepository.findByCategoryId(categoryId);
    }



}

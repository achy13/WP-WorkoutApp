package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.model.Measurement;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {

    List<Exercise> filter(Long categoryId);
    List<Exercise> findAllExercises();
    Optional<Exercise> findExerciseById(Long id);
    Optional<Exercise> save (String name, String image, String description, Long categoryId);

    void deleteExerciseById(Long id);
    Optional<Exercise> edit (Long id, String name, String image, String description, Long category);

    List<Exercise> findExercisesByNameAndCategory(String name, Long categoryId);

    List<Exercise> getExercisesByCategoryId(Long categoryId);
}

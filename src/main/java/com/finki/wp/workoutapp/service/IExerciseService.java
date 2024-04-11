package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Exercise;

import java.util.List;
import java.util.Optional;

public interface IExerciseService {

    List<Exercise> findALl();

    Optional<Exercise> findALlById();

    Optional<Exercise> findAllByName();

    //(String name, String image, String description, Level level, List<Category> categories, List<Repeats> repeats) {
    Optional<Exercise> save(String name, Double price,
                           Integer quantity, Long category, Long manufacturer);

    void deleteById(Long id);

    Optional<Exercise> edit(Long id, String name, Double price,
                           Integer quantity, Long category, Long manufacturer);

}

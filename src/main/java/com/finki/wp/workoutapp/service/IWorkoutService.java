package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.model.Workouts;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IWorkoutService {
    List<Workouts> findAllWorkouts();
    Optional<Workouts> findWorkoutById(Long id);
    Workouts save (String workoutName, String level, List<Long> exercisesId);

    void deleteWorkoutById(Long id);
}

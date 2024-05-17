package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;

import java.util.List;
import java.util.Optional;

public interface IWorkoutsService {

    Workouts findByWorkoutName(String name);
    List<Workouts> findAllWorkoutsByUser(User user);
    Optional<Workouts> findWorkoutById(Long id);
}

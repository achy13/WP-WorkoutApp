package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkoutsRepository extends JpaRepository<Workouts, Long> {

    List<Workouts> findAllByUser(User user);
    Workouts findByWorkoutName(String name);
    Optional<Workouts> findById(Long id);
}

package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workouts, Long> {
}

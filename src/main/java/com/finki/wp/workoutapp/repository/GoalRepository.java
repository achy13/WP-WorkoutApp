package com.finki.wp.workoutapp.repository;


import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findAllByUser(User user);
    Optional<Goal> findGoalByMeasurement(Measurement measurement);
}

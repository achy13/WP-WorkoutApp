package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingDay, Long> {
    List<TrainingDay> findAllByUser(User user);
    TrainingDay findTrainingDayByDateAndUser(Date date, User user);
    List<TrainingDay> findByWorkouts(Workouts workouts);
}

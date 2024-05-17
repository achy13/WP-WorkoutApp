package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<TrainingDay, Long> {
    List<TrainingDay> findAllByUser(User user);
    TrainingDay findTrainingDayByDateAndUser(Date date, User user);
}

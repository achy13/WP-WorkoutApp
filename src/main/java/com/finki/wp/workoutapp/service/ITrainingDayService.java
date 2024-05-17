package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Exercise;
import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;

import javax.swing.event.ListDataEvent;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITrainingDayService {
    List<TrainingDay> findAllByUser(User user);
    TrainingDay findTrainingDayByDateAndUser(Date date, User user);

    TrainingDay save(TrainingDay training);
}

package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.repository.TrainingRepository;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingDayServiceImpl implements ITrainingDayService {

    private final TrainingRepository trainingRepository;

    public TrainingDayServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }


    @Override
    public List<TrainingDay> findAllByUser(User user) {
        return trainingRepository.findAllByUser(user);
    }


    @Override
    public TrainingDay findTrainingDayByDateAndUser(Date date, User user) {
        return trainingRepository.findTrainingDayByDateAndUser(date, user);
    }

    @Override
    public TrainingDay save(TrainingDay training) {
        return trainingRepository.save(training);
    }


}

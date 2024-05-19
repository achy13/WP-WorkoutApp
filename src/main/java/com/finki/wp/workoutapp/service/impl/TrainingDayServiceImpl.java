package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.TrainingDay;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.repository.TrainingRepository;
import com.finki.wp.workoutapp.service.ITrainingDayService;
import com.finki.wp.workoutapp.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TrainingDayServiceImpl implements ITrainingDayService {

    private final TrainingRepository trainingRepository;
    private final IUserService userService;

    public TrainingDayServiceImpl(TrainingRepository trainingRepository, IUserService userService) {
        this.trainingRepository = trainingRepository;
        this.userService = userService;
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

    @Override
    public Boolean hasEvent(UserDetails userDetails) {
        boolean hasEvent = false;
        if (userDetails != null){
            User user = userService.findUserByUsername(userDetails.getUsername());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date today = calendar.getTime();

            TrainingDay td = findTrainingDayByDateAndUser(today, user);
            hasEvent = (td != null) ? true : false;
        }
        return hasEvent;
    }


}

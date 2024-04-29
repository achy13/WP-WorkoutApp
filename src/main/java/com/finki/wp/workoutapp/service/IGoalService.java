package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IGoalService {

    List<Goal> findAllGoals();
    List<Goal> findAllGoalsByUser(User user);
    Optional<Goal> findGoalById(Long id);
    List<Goal> findGoalByUser(User user);
    Optional<Goal> findGoalByMeasurement(Measurement measurement);
    Optional<Goal> edit(Long id, String name, Date goalDate, Double weight, Double shouldersSize, Double chestSize,
                        Double hand, Double waist, Double abdomen, Double hip, Double leg);

    Optional<Goal> save(String name, Date goalDate, Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                        Double hand, Double waist, Double abdomen, Double hip, Double leg);
    Optional<Goal> save(Goal goal);
    void deleteGoalById (Long id);
}

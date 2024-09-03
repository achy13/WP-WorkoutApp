package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.model.exceptions.GoalNotFoundException;
import com.finki.wp.workoutapp.model.exceptions.MeasurementNotFoundException;
import com.finki.wp.workoutapp.repository.GoalRepository;
import com.finki.wp.workoutapp.repository.MeasurementRepository;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.service.IGoalService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GoalService implements IGoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final MeasurementRepository measurementRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository, MeasurementRepository measurementRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.measurementRepository = measurementRepository;
    }


    @Override
    public List<Goal> findAllGoals() {
        return goalRepository.findAll();
    }

//    @Override
//    public List<Goal> findAllGoalsByUser(User user) {
//        return goalRepository.findAllByUser(user);
//    }

    @Override
    public Optional<Goal> findGoalById(Long id) {
        return goalRepository.findById(id);
    }

    @Override
    public List<Goal> findGoalByUser(User user) {
        return goalRepository.findAllByUser(user);
    }

//    @Override
//    public Optional<Goal> findGoalByMeasurement(Measurement measurement) {
//        return goalRepository.findGoalByMeasurement(measurement);
//    }

    @Override
    public Optional<Goal> edit(Long id, String name, Date goalDate, Double weight, Double shouldersSize, Double chestSize, Double hand, Double waist, Double abdomen, Double hip, Double leg) {
        Goal goal = goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException(id));
        Measurement m = measurementRepository.findById(goal.getMeasurement().getId()).orElseThrow(() -> new MeasurementNotFoundException(id));
        goal.setName(name);
        goal.setGoalDate(goalDate);
        m.setWeight(weight);
        m.setAbdomen(abdomen);
        m.setHand(hand);
        m.setHip(hip);
        m.setLeg(leg);
        m.setWaist(waist);
        m.setChestSize(chestSize);
        m.setShouldersSize(shouldersSize);
        measurementRepository.save(m);
        goalRepository.save(goal);
        return Optional.of(goal);
    }

    @Override
    public Optional<Goal> save(String name, Date goalDate, Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                               Double hand, Double waist, Double abdomen, Double hip, Double leg) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Measurement measurement = new Measurement(weight,height, years, shouldersSize, chestSize, hand, waist, abdomen, hip, leg, MeasurementType.GOAL, user);
            Goal goal = new Goal(name, measurement, goalDate);
            Goal savedGoal = goalRepository.save(goal);
            user.getGoals().add(savedGoal);
            userRepository.save(user);
            return Optional.of(savedGoal);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public Optional<Goal> save(Goal goal) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Goal savedGoal = goalRepository.save(goal);
            user.getGoals().add(savedGoal);
            userRepository.save(user);
            return Optional.of(savedGoal);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteGoalById(Long id) {
        Optional<Goal> goalOptional = this.goalRepository.findById(id);
        if (goalOptional.isPresent()) {
            Goal goal = goalOptional.get();
            Measurement measurement = goal.getMeasurement();
            User user = goal.getUser();
            if (user != null) {
                user.getGoals().remove(goal);
                user.getMeasurements().remove(measurement);
                userRepository.save(user);
            }
            goalRepository.deleteById(id);

            if (measurement != null) {
                measurementRepository.delete(measurement);
            }
        } else {
            throw new RuntimeException("Goal not found with id: " + id);
        }
    }
}

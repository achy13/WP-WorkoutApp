package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.repository.WorkoutRepository;
import com.finki.wp.workoutapp.service.IExerciseService;
import com.finki.wp.workoutapp.service.IWorkoutService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkoutService implements IWorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final IExerciseService exerciseService;

    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository, IExerciseService exerciseService) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
        this.exerciseService = exerciseService;
    }

    @Override
    public List<Workouts> findAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public Optional<Workouts> findWorkoutById(Long id) {
        return workoutRepository.findById(id);
    }

    @Override
    public Workouts save(String workoutName, Date workoutDate, String level, List<Long> exercisesId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Workouts workout = new Workouts(workoutName, workoutDate, level, exercisesId.stream().map(id -> this.exerciseService.findExerciseById(id).orElseThrow(() -> new RuntimeException("no"))).collect(Collectors.toList()), user);
            return workoutRepository.save(workout);
        }else {
            throw new RuntimeException("User not found");
        }

    }

    @Override
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }
}

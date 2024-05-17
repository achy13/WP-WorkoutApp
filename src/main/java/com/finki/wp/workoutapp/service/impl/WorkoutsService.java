package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.Workouts;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.repository.WorkoutsRepository;
import com.finki.wp.workoutapp.service.IWorkoutsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutsService implements IWorkoutsService {

    private final UserRepository userRepository;
    private final WorkoutsRepository workoutsRepository;

    public WorkoutsService(UserRepository userRepository, WorkoutsRepository workoutsRepository) {
        this.userRepository = userRepository;
        this.workoutsRepository = workoutsRepository;
    }

    @Override
    public Workouts findByWorkoutName(String name) {
        return workoutsRepository.findByWorkoutName(name);
    }

    @Override
    public List<Workouts> findAllWorkoutsByUser(User user) {
        return workoutsRepository.findAllByUser(user);
    }

    @Override
    public Optional<Workouts> findWorkoutById(Long id) {
        return workoutsRepository.findById(id);
    }
}

package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.Goal;
import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;
import com.finki.wp.workoutapp.model.exceptions.MeasurementNotFoundException;
import com.finki.wp.workoutapp.repository.MeasurementRepository;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.service.IGoalService;
import com.finki.wp.workoutapp.service.MeasurementService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final IGoalService goalService;
    private final UserRepository userRepository;

    public MeasurementServiceImpl(MeasurementRepository measurementRepository, IGoalService goalService, UserRepository userRepository) {
        this.measurementRepository = measurementRepository;
        this.goalService = goalService;
        this.userRepository = userRepository;
    }

    @Override
    public List<Measurement> findAllMeasurements() {
        return this.measurementRepository.findAll();
    }

    @Override
    public List<Measurement> findAllMeasurementsByUser(User user) {
        return measurementRepository.findAllByUser(user);
    }

    @Override
    public Optional<Measurement> findMeasurementById(Long id) {
        return this.measurementRepository.findById(id);
    }

    @Override
    public Optional<Measurement> findMeasurementByUserAndType(User user, MeasurementType type) {
        return measurementRepository.findByUserAndType(user, type);
    }

    @Override
    public Optional<Measurement> save(Double weight, Double height, Integer years, Double shouldersSize, Double chestSize, Double hand, Double waist, Double abdomen, Double hip, Double leg, MeasurementType measurementType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            Measurement measurement = new Measurement(weight, height, years, shouldersSize, chestSize, hand, waist, abdomen, hip, leg, measurementType, user);
            Measurement savedMeasurement = measurementRepository.save(measurement);
            user.getMeasurements().add(savedMeasurement);
            userRepository.save(user);
            return Optional.of(savedMeasurement);
        }else {
            throw new RuntimeException("User not found");
        }
    }

    @Override
    public void deleteMeasurementById(Long id) {
        Optional<Measurement> measurementOptional = this.measurementRepository.findById(id);
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();
            User user = measurement.getUser();
            if (user != null) {
                user.getMeasurements().remove(measurement);
                userRepository.save(user); // Update the user to reflect the removal of the measurement
            }
            measurementRepository.deleteById(id); // Delete the measurement
        } else {
            throw new RuntimeException("Measurement not found with id: " + id);
        }
    }

    @Override
    public void deleteMeasurementAndGoalsByMeasurementId(Long id) {
        Optional<Measurement> measurementOptional = this.measurementRepository.findById(id);
        if (measurementOptional.isPresent()) {
            Measurement measurement = measurementOptional.get();
            User user = measurement.getUser();
            if (user != null) {
                user.getMeasurements().remove(measurement);
                user.getGoals().clear();
                userRepository.save(user); // Update the user to reflect the removal of the measurement
            }
            goalService.findAllGoals();
            measurementRepository.deleteById(id); // Delete the measurement
        } else {
            throw new RuntimeException("Measurement not found with id: " + id);
        }
    }

    @Override
    public Optional<Measurement> edit(Long id, Double weight, Double height, Integer years, Double shouldersSize,
                                      Double chestSize, Double hand, Double waist, Double abdomen, Double hip, Double leg) {
        Measurement m = measurementRepository.findById(id).orElseThrow(() -> new MeasurementNotFoundException(id));
        m.setWeight(weight);
        m.setHeight(height);
        m.setYears(years);
        m.setShouldersSize(shouldersSize);
        m.setChestSize(chestSize);
        m.setHand(hand);
        m.setWaist(waist);
        m.setAbdomen(abdomen);
        m.setHip(hip);
        m.setLeg(leg);
        measurementRepository.save(m);
        return Optional.of(m);
    }
}

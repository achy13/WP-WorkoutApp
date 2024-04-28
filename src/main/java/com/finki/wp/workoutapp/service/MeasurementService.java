package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Measurement;
import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.MeasurementType;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    List<Measurement> findAllMeasurements();
    List<Measurement> findAllMeasurementsByUser(User user);
    Optional<Measurement> findMeasurementById(Long id);
    Optional<Measurement> findMeasurementByUserAndType(User user, MeasurementType type);
    Optional<Measurement> save (Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                                Double hand, Double waist, Double abdomen, Double hip, Double leg, MeasurementType type);
    void deleteMeasurementById(Long id);
    void deleteMeasurementAndGoalsByMeasurementId(Long id) ;
    Optional<Measurement> edit (Long id, Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                                Double hand, Double waist, Double abdomen, Double hip, Double leg);

}

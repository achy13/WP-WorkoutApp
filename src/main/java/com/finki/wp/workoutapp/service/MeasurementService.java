package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Measurement;

import java.util.List;
import java.util.Optional;

public interface MeasurementService {
    List<Measurement> findAllMeasurements();
    Optional<Measurement> findMeasurementById(Long id);
    Optional<Measurement> save (Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                                Double hand, Double waist, Double abdomen, Double hip, Double leg);

    void deleteMeasurementById(Long id);
    Optional<Measurement> edit (Long id, Double weight, Double height, Integer years, Double shouldersSize, Double chestSize,
                                Double hand, Double waist, Double abdomen, Double hip, Double leg);

}

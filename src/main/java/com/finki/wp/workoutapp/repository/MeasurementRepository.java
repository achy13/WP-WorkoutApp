package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}

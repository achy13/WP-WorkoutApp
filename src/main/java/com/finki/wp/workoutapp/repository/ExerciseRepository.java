package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.Category;
import com.finki.wp.workoutapp.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByCategory (Category category);
    //void deleteByName(String name);

    List<Exercise> findByCategory_IdAndNameContainingIgnoreCase(Long categoryId, String name);

    List<Exercise> findByCategoryId(Long categoryId);
}

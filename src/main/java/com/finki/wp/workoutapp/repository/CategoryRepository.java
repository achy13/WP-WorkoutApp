package com.finki.wp.workoutapp.repository;

import com.finki.wp.workoutapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findAllByNameLike (String name);
    void deleteByName(String name);
}

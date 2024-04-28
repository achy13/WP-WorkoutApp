package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 4000)
    private String description;

    @OneToMany
    private List<Exercise> exercises;

    public Category(String name, String description, List<Exercise> exercises) {
        this.name = name;
        this.description = description;
        this.exercises = exercises;
    }
}

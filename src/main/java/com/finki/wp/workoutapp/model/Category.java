package com.finki.wp.workoutapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String imagePath;

    @Column(length = 4000)
    private String description;

    @OneToMany
    @JsonIgnore
    private List<Exercise> exercises;

    public Category(String name, String imagePath, String description, List<Exercise> exercises) {
        this.name = name;
        this.imagePath = imagePath;
        this.description = description;
        this.exercises = exercises;
    }
}

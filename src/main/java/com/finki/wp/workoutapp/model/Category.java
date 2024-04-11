package com.finki.wp.workoutapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Category {

    @Id
    private Long id;

    private String name;

    @Column(length = 4000)
    private String description;

    @ManyToMany
    private List<Exercise> exercises;

    @ManyToMany
    private List<User> users;

    public Category(String name, String description, List<Exercise> exercises, List<User> users) {
        this.name = name;
        this.description = description;
        this.exercises = exercises;
        this.users = users;
    }
}

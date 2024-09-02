package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Workouts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String workoutName;

    private String level;

    @ManyToMany
    private List<Exercise> exercises;

    @ManyToOne
    private User user;


    @ManyToMany
    private List<TrainingDay> trainingDays;

    public Workouts(String workoutName, String level, List<Exercise> exercises, User user) {
        this.workoutName = workoutName;
        this.level = level;
        this.exercises = exercises;
        this.user = user;
    }


    public Workouts(String workoutName, List<Exercise> exercises, User user) {
        this.workoutName = workoutName;
        this.exercises = exercises;
        this.user = user;
        this.trainingDays = new ArrayList<>();
    }

}

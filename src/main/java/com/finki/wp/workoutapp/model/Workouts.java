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

    private Date workoutDate;

    @ManyToMany
    private List<Exercise> exercises;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<TrainingDay> trainingDays;

    public Workouts(String workoutName, Date workoutDate, List<Exercise> exercises, User user) {
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.exercises = exercises;
        this.user = user;
        this.trainingDays = new ArrayList<>();
    }

}

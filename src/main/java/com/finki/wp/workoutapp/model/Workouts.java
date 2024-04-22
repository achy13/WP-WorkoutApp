package com.finki.wp.workoutapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Workouts {

    @Id
    private Long id;

    private String workoutName;

    private Date workoutDate;

    @ManyToMany
    private List<Exercise> exercises;

    @ManyToOne
    private User user;

    public Workouts(String workoutName, Date workoutDate, List<Exercise> exercises, User user) {
        this.workoutName = workoutName;
        this.workoutDate = workoutDate;
        this.exercises = exercises;
        this.user = user;
    }

}

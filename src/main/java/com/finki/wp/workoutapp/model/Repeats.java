package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Repeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int series;

    private int numOfRepeats;

    @ManyToMany
    private List<Exercise> exercises;


    public Repeats(int series, int numOfRepeats, List<Exercise> exercises) {
        this.series = series;
        this.numOfRepeats = numOfRepeats;
        this.exercises = exercises;
    }
}

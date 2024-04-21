package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double weight;
    private double height;
    private int years;
    private double shouldersSize;
    private double chestSize;
    private double hand;
    private double waist;
    private double abdomen;
    private double hip;
    private double leg;
    @ManyToOne
    private User user;

    public Measurement(double weight, double height, int years, double shouldersSize, double chestSize, double hand,
                       double waist, double abdomen, double hip, double leg, User user) {
        this.weight = weight;
        this.height = height;
        this.years = years;
        this.shouldersSize = shouldersSize;
        this.chestSize = chestSize;
        this.hand = hand;
        this.waist = waist;
        this.abdomen = abdomen;
        this.hip = hip;
        this.leg = leg;
        this.user = user;
    }
}


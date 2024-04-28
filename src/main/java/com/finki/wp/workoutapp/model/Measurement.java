package com.finki.wp.workoutapp.model;

import com.finki.wp.workoutapp.model.enums.MeasurementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private MeasurementType type;
    @ManyToOne
    private User user;

    public Measurement(double weight, double height, int years, double shouldersSize, double chestSize, double hand,
                       double waist, double abdomen, double hip, double leg, MeasurementType type, User user) {
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
        this.type = type;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Measurement{id=" + id + ", weight=" + weight + ", height=" + height + "}";
    }
}


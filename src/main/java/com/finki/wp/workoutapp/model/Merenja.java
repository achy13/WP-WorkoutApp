package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Merenja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double weight;

    private double height;

    private double scope;

    private int years;

    @OneToOne
    private BodyParts merenje;

    @OneToMany
    private List<User> usersMeasurments;

    public Merenja(double weight, double height, double scope, int years, BodyParts merenje, List<User> usersMeasurments) {
        this.weight = weight;
        this.height = height;
        this.scope = scope;
        this.years = years;
        this.merenje = merenje;
        this.usersMeasurments = usersMeasurments;
    }
}


package com.finki.wp.workoutapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class BodyParts {

    @Id
    private Long id;
    private double shouldersSize;
    private double chestSize;
    private double hand;
    private double waist;
    private double abdomen;
    private double hip;
    private double leg;

    public BodyParts(double shouldersSize, double chestSize, double hand, double waist, double abdomen, double hip, double leg) {
        this.shouldersSize = shouldersSize;
        this.chestSize = chestSize;
        this.hand = hand;
        this.waist = waist;
        this.abdomen = abdomen;
        this.hip = hip;
        this.leg = leg;
    }
}

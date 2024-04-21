package com.finki.wp.workoutapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Goal {

    @Id
    private Long id;

    private  String name;

    private Date goalDate;
    @OneToOne
    private Measurement measurement;

    public Goal(String name, Measurement measurement, Date goalDate) {
        this.name = name;
        this.measurement = measurement;
        this.goalDate = goalDate;
    }
}

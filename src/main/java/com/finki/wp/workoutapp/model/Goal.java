package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@Data
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;

    private Date goalDate;
    @OneToOne
    private Measurement measurement;

    @ManyToOne
    private User user;

    public Goal(String name, Measurement measurement, Date goalDate) {
        this.name = name;
        this.measurement = measurement;
        this.goalDate = goalDate;
    }

    public Goal(User user, Measurement measurement) {
        this.user = user;
        this.measurement = measurement;
    }

    @Override
    public String toString() {
        return "Goal{id=" + id + ", name=" + name + ", goal date=" + goalDate + "}";
    }
}
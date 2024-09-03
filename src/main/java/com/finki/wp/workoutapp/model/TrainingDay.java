package com.finki.wp.workoutapp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class TrainingDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date date;

    @ManyToMany
    private List<Workouts> workouts;

    public TrainingDay(User user, Date date, List<Workouts> workouts) {
        this.user = user;
        this.date = date;
        this.workouts = workouts;
    }

    public void removeWorkout(Workouts workout) {
        if (this.workouts != null) {
            this.workouts.remove(workout);
        }
    }

}

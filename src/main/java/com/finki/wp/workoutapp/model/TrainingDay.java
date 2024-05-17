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

    @Override
    public String toString() {
        if (workouts.size() == 1){
            return "{" +
                    "\"id\":" + id +
                    "\"start\":" + date +
                    "\"title\":" + workouts.get(0).getWorkoutName() +
                    '}';
        }
        else if (workouts.size() > 1){
            String str = "";
            for (int i = 0; i < workouts.size(); i++){
                if (i == workouts.size()-1){
                    str += "{" +
                            "\"id\":" + id +
                            "\"start\":" + date +
                            "\"title\":" + workouts.get(i).getWorkoutName() +
                            "}";
                }
                else {
                    str += "{" +
                            "\"id\":" + id +
                            "\"start\":" + date +
                            "\"title\":" + workouts.get(i).getWorkoutName() +
                            "},";
                }
            }
            return str;
        }
        else return "Error";

    }
}

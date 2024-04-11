package com.finki.wp.workoutapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Goal {

    @Id
    private Long id;

    private  String name;

    @OneToOne
    private Merenja merenja;

    public Goal(String name, Merenja merenja) {
        this.name = name;
        this.merenja = merenja;
    }
}

package com.finki.wp.workoutapp.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Exercise {

    @Id
    private Long id;

    private String name;

    private String image;

    private String description;

    @Enumerated(EnumType.STRING)
    private Level level;

    // Potentially to be changed because is more logic and practical every exercise to be in one category
    // For example when we would like to search exercises by their categories
    @ManyToMany
    private List<Category> categories;

    @ManyToMany
    private List<Repeats> repeats;


    public Exercise(String name, String image, String description, Level level, List<Category> categories, List<Repeats> repeats) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.level = level;
        this.categories = categories;
        this.repeats = repeats;
    }
}

package com.finki.wp.workoutapp.model;

import com.finki.wp.workoutapp.converters.UserFullNameConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "workout_users")

public class User {
    @Id
    private String username;

    private String password;

    private String email;

    @Convert(converter = UserFullNameConverter.class)
    private UserFullName fullname;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    private Merenja measure;


    public User(String username, String password, String email, UserFullName fullname, Role role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.role = role;
    }
}

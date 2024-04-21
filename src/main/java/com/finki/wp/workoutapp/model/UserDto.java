package com.finki.wp.workoutapp.model;

import com.finki.wp.workoutapp.model.enums.Role;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    @NonNull
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private Role role;
}

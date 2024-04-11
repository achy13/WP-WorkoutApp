package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.Role;
import com.finki.wp.workoutapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    User register(String email, String username, String password, String repeatPassword, String name, String surname, Role role);
}

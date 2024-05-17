package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface IUserService {
    public User register(String username, String password, String repeatPassword, String name, String surname, String email);
    UserDetails loadUserByUsername(String username);
    User findUserByUsername(String username);

}

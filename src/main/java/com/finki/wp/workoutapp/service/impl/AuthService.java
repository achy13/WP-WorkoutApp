package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.exceptions.InvalidArgumentsException;
import com.finki.wp.workoutapp.model.exceptions.InvalidUserCredentialsException;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.service.IAuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements IAuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }

        return userRepository.findByUsernameAndPassword(username, passwordEncoder.encode(password))
                .orElseThrow(InvalidUserCredentialsException::new);

    }

}

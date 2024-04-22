package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.Role;
import com.finki.wp.workoutapp.model.exceptions.InvalidUsernameOrPasswordException;
import com.finki.wp.workoutapp.model.exceptions.PasswordsDoNotMatchException;
import com.finki.wp.workoutapp.model.exceptions.UsernameAlreadyExistsException;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.service.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService  {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String username, String password, String repeatPassword, String firstName, String lastName, String email) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            throw new InvalidUsernameOrPasswordException();
        }

        if (!password.equals(repeatPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if(this.userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        User user = new User(
                username,
                passwordEncoder.encode(password),
                email,
                firstName,
                lastName,
                Role.ROLE_USER
        );

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
    }

}

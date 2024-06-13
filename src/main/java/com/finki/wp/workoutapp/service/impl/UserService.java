package com.finki.wp.workoutapp.service.impl;

import com.finki.wp.workoutapp.model.User;
import com.finki.wp.workoutapp.model.enums.Role;
import com.finki.wp.workoutapp.model.exceptions.*;
import com.finki.wp.workoutapp.repository.UserRepository;
import com.finki.wp.workoutapp.service.IUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService  {
    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

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

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public User changePassword(String username, String oldPassword, String newPassword, String repeatedPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isEmpty()){
            User user = userOptional.get();
            if (!passwordEncoder.matches(oldPassword, user.getPassword())){
                throw new InvalidPasswordException();
            }
            if (!newPassword.equals(repeatedPassword) ){
                throw new PasswordsDoNotMatchException();
            }
            if (newPassword.equals(oldPassword)){
                throw new SamePasswordException();
            }
        }
        else {
            throw new UsernameNotFoundException(username);
        }
        userOptional.get().setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(userOptional.get());
    }

    @Override
    public User update(String firstName, String lastName, String username, String email) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUser = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(authUser);
        if (!userOptional.isEmpty()){
            User user = userOptional.get();
            if (firstName == null || firstName.isEmpty()){
                throw new CannotBeEmptyException();
            }
            else {
                user.setFirstName(firstName);
            }
            if (lastName == null || lastName.isEmpty()){
                throw new CannotBeEmptyException();
            }
            else {
                user.setLastName(lastName);
            }
            if (username == null || username.isEmpty() ){
                throw new CannotBeEmptyException();
            }
            else if(userRepository.findByUsername(username).isPresent() && !user.getUsername().equals(username)) {
                throw new UsernameAlreadyExistsException(username);
            }
            else {
                user.setUsername(username);
            }
            if (email == null || email.isEmpty()){
                throw new CannotBeEmptyException();
            }
            else if (pattern.matcher(email) == null){
                throw new InvalidArgumentsException();
            }
            else {
                user.setEmail(email);
            }
        }
        else {
            throw new UsernameNotFoundException(username);
        }

        return userRepository.save(userOptional.get());
    }

}

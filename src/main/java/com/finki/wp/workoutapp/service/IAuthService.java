package com.finki.wp.workoutapp.service;

import com.finki.wp.workoutapp.model.User;

import java.util.List;

public interface IAuthService {
    User login(String username, String password);

}

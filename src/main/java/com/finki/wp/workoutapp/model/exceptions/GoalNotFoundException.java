package com.finki.wp.workoutapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GoalNotFoundException extends RuntimeException{
    public GoalNotFoundException(Long id) {
        super(String.format("Goal with id: %d was not found", id));
    }
}

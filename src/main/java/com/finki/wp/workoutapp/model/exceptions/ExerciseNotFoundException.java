package com.finki.wp.workoutapp.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExerciseNotFoundException extends RuntimeException{
    public ExerciseNotFoundException(Long id) {
        super(String.format("Measurement with id: %d was not found", id));
    }
}

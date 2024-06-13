package com.finki.wp.workoutapp.model.exceptions;

public class CannotBeEmptyException extends RuntimeException{
    public CannotBeEmptyException() {
        super("Fill all fields.");
    }
}
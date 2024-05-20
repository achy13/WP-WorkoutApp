package com.finki.wp.workoutapp.model.exceptions;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException() {
        super("Incorrect password");
    }
}

package com.finki.wp.workoutapp.model.exceptions;

public class SamePasswordException extends RuntimeException{
    public SamePasswordException() {
        super("New password cannot be the same as the old password.");
    }
}

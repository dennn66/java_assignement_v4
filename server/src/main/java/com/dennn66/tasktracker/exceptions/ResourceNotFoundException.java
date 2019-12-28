package com.dennn66.tasktracker.exceptions;

public class ResourceNotFoundException extends RestResourceException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
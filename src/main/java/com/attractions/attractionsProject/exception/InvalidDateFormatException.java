package com.attractions.attractionsProject.exception;

public class InvalidDateFormatException extends RuntimeException {

    private final String field;

    public InvalidDateFormatException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

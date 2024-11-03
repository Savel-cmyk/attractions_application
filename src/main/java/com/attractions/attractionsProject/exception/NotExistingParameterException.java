package com.attractions.attractionsProject.exception;

public class NotExistingParameterException extends RuntimeException {

    private final String parameter;

    public NotExistingParameterException(String parameter, String message) {
        super(message);
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}

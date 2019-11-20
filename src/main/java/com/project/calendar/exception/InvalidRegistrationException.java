package com.project.calendar.exception;

public class InvalidRegistrationException extends RuntimeException {
    public InvalidRegistrationException(String message) {
        super(message);
    }

    public InvalidRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRegistrationException(Throwable cause) {
        super(cause);
    }
}

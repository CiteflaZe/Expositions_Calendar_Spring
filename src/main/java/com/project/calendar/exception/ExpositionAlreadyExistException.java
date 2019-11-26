package com.project.calendar.exception;

public class ExpositionAlreadyExistException extends RuntimeException {
    public ExpositionAlreadyExistException(String message) {
        super(message);
    }

    public ExpositionAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpositionAlreadyExistException(Throwable cause) {
        super(cause);
    }
}

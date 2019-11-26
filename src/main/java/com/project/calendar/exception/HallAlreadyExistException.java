package com.project.calendar.exception;

public class HallAlreadyExistException extends RuntimeException {
    public HallAlreadyExistException(String message) {
        super(message);
    }

    public HallAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public HallAlreadyExistException(Throwable cause) {
        super(cause);
    }
}

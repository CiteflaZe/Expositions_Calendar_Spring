package com.project.calendar.exception;

public class IllegalPaginationValuesException extends RuntimeException {
    public IllegalPaginationValuesException(String message) {
        super(message);
    }

    public IllegalPaginationValuesException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPaginationValuesException(Throwable cause) {
        super(cause);
    }
}

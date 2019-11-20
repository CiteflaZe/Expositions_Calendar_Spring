package com.project.calendar.exception;

public class PDFCreationException extends RuntimeException {
    public PDFCreationException(String message) {
        super(message);
    }

    public PDFCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PDFCreationException(Throwable cause) {
        super(cause);
    }
}

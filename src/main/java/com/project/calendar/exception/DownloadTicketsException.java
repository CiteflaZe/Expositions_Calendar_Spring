package com.project.calendar.exception;

public class DownloadTicketsException extends RuntimeException {
    public DownloadTicketsException(String message) {
        super(message);
    }

    public DownloadTicketsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadTicketsException(Throwable cause) {
        super(cause);
    }
}

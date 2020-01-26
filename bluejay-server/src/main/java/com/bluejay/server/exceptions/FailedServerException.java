package com.bluejay.server.exceptions;

public class FailedServerException extends RuntimeException {

    public FailedServerException(String message) {
        super(message);
    }
}

package com.rest.innowise_group.exception;

public class ServerErrorException extends RuntimeException {

    public ServerErrorException(final String message) {
        super(message);
    }
}

package com.rest.innowise_group.exception;

public class EmailDuplicateException extends RuntimeException {

    public EmailDuplicateException(final String message) {
        super(message);
    }
}

package com.rest.innowise_group.exception;

public class TokenIsNotValid extends RuntimeException {
    public TokenIsNotValid(String message) {
        super(message);
    }
}

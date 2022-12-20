package com.rest.innowise_group.controller;

import com.rest.innowise_group.dto.ErrorDto;
import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.exception.ServerErrorException;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicateException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleEmailDuplicateException(final EmailDuplicateException e) {
        final String message = "Sorry, request is forbidden";
        final ErrorDto error = new ErrorDto(403, message);
        log.error(error.toString(), e);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getCode()));
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleServerException(final ServerErrorException e) {
        final String message = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2);
        final ErrorDto error = new ErrorDto(500, message);
        log.error(error.toString(), e);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getCode()));
    }
}

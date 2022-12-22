package com.rest.innowise_group.controller;

import com.rest.innowise_group.dto.ErrorDto;
import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.exception.ServerErrorException;
import com.rest.innowise_group.exception.TokenIsNotValid;
import com.rest.innowise_group.exception.UserNotFoundException;
import com.rest.innowise_group.model.PropertyConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final PropertyConfig propertyConfig;

    @ExceptionHandler({EmailDuplicateException.class, UserNotFoundException.class, TokenIsNotValid.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Object> handleEmailDuplicateException(RuntimeException e, String messageKey) {
        final String message = propertyConfig.getProperty(messageKey);
        final ErrorDto error = new ErrorDto(403, message);
        log.error(error.toString(), e);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getCode()));
    }

    @ExceptionHandler(ServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleServerException(ServerErrorException e) {
        final String message = e.getMessage().substring(e.getMessage().lastIndexOf(":") + 2);
        final ErrorDto error = new ErrorDto(500, message);
        log.error(error.toString(), e);
        return new ResponseEntity<>(error, HttpStatus.valueOf(error.getCode()));
    }
}

package com.rest.innowise_group.dto;

import lombok.Getter;

@Getter
public class ErrorDto extends GlobalDto {
    private final String errorMessage;

    public ErrorDto(final int code, final String errorMessage) {
        super(code);
        this.errorMessage = errorMessage;
    }
}

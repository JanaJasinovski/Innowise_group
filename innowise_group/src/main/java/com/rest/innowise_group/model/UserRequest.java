package com.rest.innowise_group.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {

    @NotBlank
    private Long key;

    @NotBlank
    private String message;
}

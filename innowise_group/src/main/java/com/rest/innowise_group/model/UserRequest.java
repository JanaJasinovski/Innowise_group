package com.rest.innowise_group.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}

package com.rest.innowise_group.model;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUtils {

    public UserResponse makeUserResponse(Optional<User> user,UserRequest userRequest) {
        return user.map(value -> UserResponse.builder()
                .userId(value.getId())
                .email(value.getEmail())
                .build()).orElse(null);
    }
}

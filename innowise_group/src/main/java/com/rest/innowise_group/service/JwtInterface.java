package com.rest.innowise_group.service;

import com.rest.innowise_group.model.User;

public interface JwtInterface {

    String generateToken(User user);

    String getUserEmailFromJwtToken(String token);
}
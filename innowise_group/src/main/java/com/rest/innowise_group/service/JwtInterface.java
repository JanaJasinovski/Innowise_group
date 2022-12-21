package com.rest.innowise_group.service;

import com.rest.innowise_group.model.User;

import java.util.Map;

public interface JwtInterface {

    Map<String, String> generateToken(User user);

    String getUserEmailFromJwtToken(String token);
}
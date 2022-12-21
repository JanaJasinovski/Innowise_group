package com.rest.innowise_group.service.impl;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.JwtInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
@Service
public class JwtImpl implements JwtInterface {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    @Override
    public String generateToken(User user) {
        return "Bearer " + Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    @Override
    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
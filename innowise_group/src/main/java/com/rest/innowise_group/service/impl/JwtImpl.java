package com.rest.innowise_group.service.impl;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.JwtInterface;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtImpl implements JwtInterface {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private String jwtExpirationInMs;

    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = "";
        jwtToken = Jwts.builder().setSubject(user.getEmail()).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        Map<String, String> jwtTokenGenerate = new HashMap<>();
        jwtTokenGenerate.put("token", jwtToken);
        jwtTokenGenerate.put("jwtExpirationInMs", jwtExpirationInMs);
        return jwtTokenGenerate;
    }

    @Override
    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
package com.rest.innowise_group.service;

import com.rest.innowise_group.dto.JwtAuthenticationResponse;
import com.rest.innowise_group.dto.UserDto;
import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.repository.UserRepository;
import com.rest.innowise_group.security.JwtTokenProvider;
import com.rest.innowise_group.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    public UserService(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public void saveUser(UserDto user) {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailDuplicateException("Sorry, request is forbidden");
        }
        User userSave = new User(user.getEmail(), user.getPassword());
        userSave.setPassword(passwordEncoder.encode(userSave.getPassword()));
        log.info("Successfully registered user with [email: {}]", userSave.getEmail());
        userRepository.save(userSave);
    }

    public JwtAuthenticationResponse checkUser(UserDto user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        log.info("User with [email: {}] has logged in", userPrincipal.getEmail());

        return new JwtAuthenticationResponse(jwt);
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public String getEmailByToken(List<UserDto> users) {
        for (UserDto user : users) {
            if ((checkUser(user).getAccessToken()).equals(jwtSecret)) {
                List<User> userList = userRepository.findAll();
                for (User userl : userList) {
                    return userl.getEmail();
                }
            }
        }
        return null;
    }

    public void getAllUsers() {
        userRepository.findAll();
    }
}

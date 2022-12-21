package com.rest.innowise_group.service.impl;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.repository.UserRepository;
import com.rest.innowise_group.service.JwtInterface;
import com.rest.innowise_group.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    private UserRepository userRepository;

    private JwtInterface jwtInterface;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtInterface jwtInterface) {
        this.userRepository = userRepository;
        this.jwtInterface = jwtInterface;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<String> getEmailsIfTokensEquals() {
        List<String> emails = new ArrayList<>();
        for (User user : getAll()) {
            if (user.getEmail().equals(jwtInterface.getUserEmailFromJwtToken(jwtSecret))) {
                emails.add(user.getEmail());
            }
        }
        return emails;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}

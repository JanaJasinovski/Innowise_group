package com.rest.innowise_group.service;

import com.rest.innowise_group.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    void saveUser(User user);

    User getUserByEmail(String name);

    List<String> getEmailsIfTokensEquals();

    List<User> getAll();

}

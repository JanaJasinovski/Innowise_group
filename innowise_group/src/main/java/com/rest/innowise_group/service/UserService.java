package com.rest.innowise_group.service;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.model.UserRequest;
import com.rest.innowise_group.model.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    boolean saveUser(User user);

    User getUserByEmail(String name);

    UserResponse isUserExist(UserRequest userRequest);

    List<String> getEmailsIfTokensEquals(String tokenRequest);

    List<User> getAll();

}

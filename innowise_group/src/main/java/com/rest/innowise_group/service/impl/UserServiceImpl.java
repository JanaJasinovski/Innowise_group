package com.rest.innowise_group.service.impl;

import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.model.UserRequest;
import com.rest.innowise_group.model.UserResponse;
import com.rest.innowise_group.model.UserUtils;
import com.rest.innowise_group.repository.UserRepository;
import com.rest.innowise_group.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${app.token}")
    private String token;

    private final UserRepository userRepository;
    private final UserUtils userUtils;


    @Override
    public boolean saveUser(User user) throws EmailDuplicateException {
        if (userRepository.existsByEmail(user.getEmail())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public UserResponse isUserExist(UserRequest userRequest) {
        Optional<User> user = Optional.ofNullable(userRepository.getByEmail(userRequest.getEmail()));
        UserResponse userResponse = null;

        if (user.isPresent() && user.get().getPassword().equals(userRequest.getPassword())) {
            userResponse = userUtils.makeUserResponse(user, userRequest);
        }
        return userResponse;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    @Override
    public List<String> getEmailsIfTokensEquals(String tokenRequest) {
        List<String> emails = new ArrayList<>();
        if (tokenRequest.equals(token)) {
            for (User user : getAll()) {
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

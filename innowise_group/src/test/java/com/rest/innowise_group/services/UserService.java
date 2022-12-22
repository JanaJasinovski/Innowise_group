package com.rest.innowise_group.services;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserService {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void saveUser() {
        User user1 = new User("gleb@gmail.com", "323424");
        boolean isUser1 = userService.saveUser(user1);
        Assertions.assertTrue(isUser1);
        User user2 = new User("natasha@gmail.com", "24234234");
        boolean isUser2 = userService.saveUser(user2);
        Assertions.assertTrue(isUser2);
    }

}

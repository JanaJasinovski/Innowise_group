package com.rest.innowise_group;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AllArgsConstructor
class InnowiseGroupApplicationTests {

    @Autowired
    private UserService userService;

    @ParameterizedTest
    void saveUserTest() {
        User user1 = new User("anton@gmail.com", "123456789");
        boolean isuser1 = userService.saveUser(user1);
        Assertions.assertTrue(isuser1);
        User user2 = new User("pavel@gmail.com", "123456789");
        boolean isuser2 = userService.saveUser(user2);
        Assertions.assertTrue(isuser2);
        User user3 = new User("masha@gmail.com", "123456789");
        boolean isuser3 = userService.saveUser(user3);
        Assertions.assertTrue(isuser3);
    }

    @Test
    void getUserByEmailTest() {
        User user1 = userService.getUserByEmail("jana@gmail.com");
        Assertions.assertEquals("jana@gmail.com", user1.getEmail());
        User user2 = userService.getUserByEmail("denis@gmail.com");
        Assertions.assertEquals("denis@gmail.com", user2.getEmail());
        User user3 = userService.getUserByEmail("yulia@gmail.com");
        Assertions.assertEquals("yulia@gmail.com", user3.getEmail());
    }

}

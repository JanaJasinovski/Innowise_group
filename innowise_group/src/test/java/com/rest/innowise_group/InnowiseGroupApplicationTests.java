package com.rest.innowise_group;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.UserService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AllArgsConstructor
class InnowiseGroupApplicationTests {

    private UserService userService;


    @Test
    void saveUserTest() {
        User user1 = new User("anton@gmail.com", "123456789");
        userService.saveUser(user1);
        Assertions.assertEquals("anton@gmail.com", user1.getEmail());
        User user2 = new User("pavel@gmail.com", "123456789");
        userService.saveUser(user2);
        Assertions.assertEquals("pavel@gmail.com", user2.getEmail());
        User user3 = new User("masha@gmail.com", "123456789");
        userService.saveUser(user3);
        Assertions.assertEquals("masha@gmail.com", user3.getEmail());
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

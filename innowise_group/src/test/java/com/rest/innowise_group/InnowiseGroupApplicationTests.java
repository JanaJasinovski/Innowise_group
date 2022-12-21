package com.rest.innowise_group;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.JwtInterface;
import com.rest.innowise_group.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class InnowiseGroupApplicationTests {

    private UserService userService;
    private JwtInterface jwtInterface;

    @Autowired
    public InnowiseGroupApplicationTests(UserService userService, JwtInterface jwtInterface) {
        this.userService = userService;
        this.jwtInterface = jwtInterface;
    }

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

    // Токены постоянно меняются, я написала только те, которые получила через Postman, но сейчас они другие и тест не пройдёт
    @Test
    void checkUserTest() {
        User user1 = new User("jana@gmail.com", "2301003120701");
		Assertions.assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbnRvbkBnbWFpbC5jb20iLCJpYXQiOjE2NzE2MTU1NDV9.GN1unKxselUanzwIWEdSuWLJoAtFsScUUjrt_zsMyr0", jwtInterface.generateToken(user1));
        User user2 = new User("pavel@gmail.com", "123456789");
		Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjcxNDcyOTMwLCJleHAiOjE2NzIwNzc3MzB9._iD44u7_v4mTF4v8aNzG1kSHVIBfe_0RZ0iQSW6Z5UP2Qd-SdN4DkPQoFpkMOvoCaacHfisOZx596d-XRWp2pA", jwtInterface.generateToken(user2));
        User user3 = new User("yulia@gmail.com", "123456789");
		Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjcxNDczMDI1LCJleHAiOjE2NzIwNzc4MjV9.A3PYFip7mBO1E28fjiq79ZGmE2h2huBSalGxyUMoG1ZNDYJ5jrIkRIBQdqcbZ0sobsdiNBfUCN5y0Z5oLYog4A", jwtInterface.generateToken(user3));
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

    @Test
    void getEmailByTokenTest() {
        User user1 = new User("kate@gmail.com", "123456789");
        User user2 = new User("pavel@gmail.com", "123456789");
        User user3 = new User("masha@gmail.com", "123456789");
        List<String> usersEmail =  new ArrayList(Arrays.asList(user1.getEmail(), user2.getEmail(), user3.getEmail()));

        Assertions.assertEquals(usersEmail, userService.getEmailsIfTokensEquals());
    }
}

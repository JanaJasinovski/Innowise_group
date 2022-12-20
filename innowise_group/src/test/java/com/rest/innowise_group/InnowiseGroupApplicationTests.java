package com.rest.innowise_group;

import com.rest.innowise_group.dto.UserDto;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class InnowiseGroupApplicationTests {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public InnowiseGroupApplicationTests(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    void saveUserTest() {
        UserDto user1 = new UserDto("kate@gmail.com", "123456789");
        userService.saveUser(user1);
        Assertions.assertEquals("kate@gmail.com", user1.getEmail());
        UserDto user2 = new UserDto("pavel@gmail.com", "123456789");
        userService.saveUser(user2);
        Assertions.assertEquals("pavel@gmail.com", user2.getEmail());
        UserDto user3 = new UserDto("masha@gmail.com", "123456789");
        userService.saveUser(user3);
        Assertions.assertEquals("masha@gmail.com", user3.getEmail());
    }

    // Токены постоянно меняются, я написала только те, которые получила через Postman, но сейчас они другие и тест не пройдёт
    @Test
    void checkUserTestTest() {
		UserDto user1 = new UserDto("jana@gmail.com", "2301003120701");
		Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjcxNDczMjQ0LCJleHAiOjE2NzIwNzgwNDR9.ncffhbovQf0M1tzoeXgwr10l_Apb7DKqghHkWxo2vM_GiYj7C1nAEsnpi6stCd80Em3Zva35QAzJifa8z9OnZQ", userService.checkUser(user1).getAccessToken());
		UserDto user2 = new UserDto("denis@gmail.com", "123456789");
		Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzIiwiaWF0IjoxNjcxNDcyOTMwLCJleHAiOjE2NzIwNzc3MzB9._iD44u7_v4mTF4v8aNzG1kSHVIBfe_0RZ0iQSW6Z5UP2Qd-SdN4DkPQoFpkMOvoCaacHfisOZx596d-XRWp2pA", userService.checkUser(user2).getAccessToken());
		UserDto user3 = new UserDto("yulia@gmail.com", "123456789");
		Assertions.assertEquals("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNjcxNDczMDI1LCJleHAiOjE2NzIwNzc4MjV9.A3PYFip7mBO1E28fjiq79ZGmE2h2huBSalGxyUMoG1ZNDYJ5jrIkRIBQdqcbZ0sobsdiNBfUCN5y0Z5oLYog4A", userService.checkUser(user3).getAccessToken());
    }

    @Test
    void getUserByEmailTest() {
        User user1 = userService.getByEmail("jana@gmail.com");
        Assertions.assertEquals("jana@gmail.com", user1.getEmail());
        User user2 = userService.getByEmail("denis@gmail.com");
        Assertions.assertEquals("denis@gmail.com", user2.getEmail());
        User user3 = userService.getByEmail("yulia@gmail.com");
        Assertions.assertEquals("yulia@gmail.com", user3.getEmail());
    }

    @Test
    void getEmailByTokenTest() {
        List<UserDto> userDtos = new ArrayList<>();
        UserDto user1 = new UserDto("kate@gmail.com", "123456789");
        UserDto user2 = new UserDto("pavel@gmail.com", "123456789");
        UserDto user3 = new UserDto("masha@gmail.com", "123456789");
        userDtos.add(user1);
        userDtos.add(user2);
        userDtos.add(user3);

        Assertions.assertEquals("kate@gmail.com", userService.getEmailByToken(userDtos));
    }

    @Test
    void passwordEncoderTest() {
        Assertions.assertEquals("$2a$10$eKBOeUgkhnlFztntUjiWf.ctOkGVNI95MZjxumr3kzcXiXzflwcNC", passwordEncoder.encode("mypassword"));
    }
}

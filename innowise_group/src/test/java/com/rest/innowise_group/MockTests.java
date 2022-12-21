package com.rest.innowise_group;

import com.rest.innowise_group.dto.ErrorDto;
import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.repository.UserRepository;
import com.rest.innowise_group.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class MockTests {

    List<User> users = new ArrayList<>();

    @Autowired
    public UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void saveUserTest() {
        when(this.userRepository.findAll()).thenReturn(this.users);
        when(this.userRepository.getByEmail(anyString())).thenAnswer(invocation ->
                this.users.stream().filter(user ->
                        user.getEmail().equals(invocation.getArgument(0))).findFirst());
        when(this.userRepository.save(any(User.class))).thenAnswer(invocation -> {
            final User saveUser = invocation.getArgument(0);
            saveUser.setId((long) this.users.size());
            this.users.add(saveUser);
            return invocation.getArgument(0);
        });
    }

    @Test
    void exceptionTest() {
        final String message = "Sorry, request is forbidden";
        final ErrorDto error = new ErrorDto(403, message);
        Assertions.assertEquals(403, error.getCode());
    }

    @Test
    void databaseNotEmptyTest() {
        userRepository.findAll()
                .forEach(user -> {
                    Assertions.assertNotNull(user.getId());
                    Assertions.assertNotNull(user.getEmail());
                    Assertions.assertNotNull(user.getPassword());
                });
    }

    @Test
    void EmailDuplicateExceptionTest() {
        EmailDuplicateException cause = Mockito.mock(EmailDuplicateException.class);
        when(cause.getCause()).thenReturn(cause);
    }

}

package com.rest.innowise_group.services;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.repository.UserRepository;
import com.rest.innowise_group.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldSavedUserSuccessFully() {
        final User user = new User( "wergerg@mail.com","23234234");

        given(userRepository.getByEmail(user.getEmail())).willReturn(null);
        given(userRepository.save(user)).willAnswer(invocation -> invocation.getArgument(0));

        boolean isSaved = userService.saveUser(user);

        assertThat(isSaved).isTrue();

        verify(userRepository).save(any(User.class));

    }

    @Test
    void shouldReturnFindAll() {
        List<User> datas = new ArrayList();
        datas.add(new User( "ten@mail.com","teten"));
        datas.add(new User("ten@mail.com","teten"));
        datas.add(new User("ten@mail.com","teten"));

        given(userRepository.findAll()).willReturn(datas);

        List<User> expected = userService.getAll();

        assertEquals(expected, datas);
    }

    @Test
    void findUserByEmail(){
        final String email = "wergerg@mail.com";
        final User user = new User( "wergerg@mail.com","23234234");

        given(userRepository.getByEmail(email)).willReturn(null);

        final Optional<User> expected  = Optional.ofNullable(userService.getUserByEmail(email));

        assertThat(expected).isNotNull();
    }

}
package com.rest.innowise_group.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.model.UserRequest;
import com.rest.innowise_group.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;

    @BeforeEach
    void saveUser() {
        this.userList = new ArrayList<>();
        this.userList.add(new User("user1@gmail.com","1234567"));
        this.userList.add(new User("user2@gmail.com", "123456789"));
        this.userList.add(new User("user3@gmail.com", "123456710"));
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        given(userService.getAll()).willReturn(userList);

        this.mockMvc.perform(get("/users/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.saveUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User("newuser1@gmail.com", "123456789");

        this.mockMvc.perform(post("/users/credentials")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());

    }

    @Test
    void shouldCheckUser() throws Exception {
        given(userService.saveUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User("jantschick@gmail.com", "2301003120701");

        this.mockMvc.perform(post("/users/credentials/verify")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId", is(user.getId())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    void shouldgetAllEmailsByToken() throws Exception {
        this.mockMvc.perform(get("/users/token/RandomSecretKey12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }
}

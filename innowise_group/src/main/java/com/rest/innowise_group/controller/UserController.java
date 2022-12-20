package com.rest.innowise_group.controller;

import com.rest.innowise_group.dto.JwtAuthenticationResponse;
import com.rest.innowise_group.dto.UserDto;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
@Api(tags = "users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/credentials")
    @ResponseStatus(OK)
    public void saveUser(@Valid @RequestBody UserDto user) {
        userService.saveUser(user);
    }

    @PostMapping("/credentials/verify")
    @ResponseStatus(OK)
    public JwtAuthenticationResponse checkUser(@Valid @RequestBody UserDto user) {
        return userService.checkUser(user);
    }

    @GetMapping("/getUser")
    @ResponseStatus(OK)
    public User getUser(String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/getAllUsers")
    @ResponseStatus(OK)
    public void getAllUsers() {
        userService.getAllUsers();
    }

    @GetMapping("/token")
    @ResponseStatus(OK)
    public String getEmailByToken(List<UserDto> users) {
        return userService.getEmailByToken(users);
    }
}

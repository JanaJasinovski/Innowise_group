package com.rest.innowise_group.controller;

import com.rest.innowise_group.model.User;
import com.rest.innowise_group.service.JwtInterface;
import com.rest.innowise_group.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtInterface jwtInterface;

    @Autowired
    public UserController(UserService userService, JwtInterface jwtInterface) {
        this.userService = userService;
        this.jwtInterface = jwtInterface;
    }

    @PostMapping("/credentials")
    @ResponseStatus(OK)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/credentials/verify")
    @ResponseStatus(OK)
    public ResponseEntity<?> checkUser(@RequestBody User user) {
        User userData = userService.getUserByEmail(user.getEmail());
        return new ResponseEntity<>(jwtInterface.generateToken(user), OK);
    }

    @GetMapping("/token")
    @ResponseStatus(OK)
    public List<String> getEmailByToken(List<User> users) {
        return userService.getEmailsIfTokensEquals(users);
    }
}

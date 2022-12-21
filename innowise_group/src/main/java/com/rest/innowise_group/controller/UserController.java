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

    @Autowired
    private UserService userService;

    @Autowired
    private JwtInterface jwtInterface;

    @PostMapping("/credentials")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/credentials/verify")
    public ResponseEntity<?> checkUser(@RequestBody User user) {
        return new ResponseEntity<>(jwtInterface.generateToken(user), OK);
    }

    @GetMapping("/token")
    public ResponseEntity<?> getEmailByToken() {
        return new ResponseEntity<>(userService.getEmailsIfTokensEquals(), OK);
    }

    @GetMapping(value = "/getAll")
    public List<User> getAllEmployees() {
        return userService.getAll();
    }
}

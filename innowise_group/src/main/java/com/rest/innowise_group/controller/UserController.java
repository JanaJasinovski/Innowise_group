package com.rest.innowise_group.controller;

import com.rest.innowise_group.exception.EmailDuplicateException;
import com.rest.innowise_group.exception.ServerErrorException;
import com.rest.innowise_group.exception.TokenIsNotValid;
import com.rest.innowise_group.exception.UserNotFoundException;
import com.rest.innowise_group.model.PropertyConfig;
import com.rest.innowise_group.model.User;
import com.rest.innowise_group.model.UserRequest;
import com.rest.innowise_group.model.UserResponse;
import com.rest.innowise_group.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final GlobalExceptionHandler globalExceptionHandler;
    private final UserService userService;
    private final PropertyConfig propertyConfig;


    @PostMapping("/credentials")
    @ExceptionHandler({EmailDuplicateException.class, ServerErrorException.class})
    public ResponseEntity<?> saveUser(@RequestBody UserRequest userRequest) {
        User user = new User(userRequest.getEmail(), userRequest.getPassword());
        boolean isSaved = userService.saveUser(user);

        if (!isSaved) {
            EmailDuplicateException emailDuplicateException = new EmailDuplicateException(propertyConfig.getProperty("email_duplicate"));
            return globalExceptionHandler.handleEmailDuplicateException(emailDuplicateException, "email_duplicate");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/credentials/verify")
    @ExceptionHandler({UserNotFoundException.class, ServerErrorException.class})
    public ResponseEntity<?> checkUser(@RequestBody UserRequest userRequest) {

        UserResponse userResponse = userService.isUserExist(userRequest);

        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            UserNotFoundException emailDuplicateException = new UserNotFoundException(propertyConfig.getProperty("user_not_found"));
            return globalExceptionHandler.handleEmailDuplicateException(emailDuplicateException, "user_not_found");
        }
    }

    @GetMapping("/{token}")
    @ExceptionHandler({TokenIsNotValid.class, ServerErrorException.class})
    public ResponseEntity<?> getAllEmailsByToken(@PathVariable String token) {
        List<String> emails = userService.getEmailsIfTokensEquals(token);

        if (emails.size() > 0) {
            return ResponseEntity.ok(emails.toArray());
        } else {
            TokenIsNotValid emailDuplicateException = new TokenIsNotValid(propertyConfig.getProperty("token_is_not_valid"));
            return globalExceptionHandler.handleEmailDuplicateException(emailDuplicateException, "token_is_not_valid");
        }
    }

    @GetMapping(value = "/getAll")
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}

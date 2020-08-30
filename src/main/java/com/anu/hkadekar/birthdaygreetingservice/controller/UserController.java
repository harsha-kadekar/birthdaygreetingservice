package com.anu.hkadekar.birthdaygreetingservice.controller;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.service.ResourceAlreadyExistException;
import com.anu.hkadekar.birthdaygreetingservice.service.ResourceDoesNotExistException;
import com.anu.hkadekar.birthdaygreetingservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) throws ResponseStatusException {
        try {
            if (user.getUserId() != null) {
                return userService.updateUser(user);
            } else {
                return userService.addUser(user);
            }
        } catch (ResourceAlreadyExistException exp) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exp.getMessage());
        } catch (ResourceDoesNotExistException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    private UUID getUserId(String userInfo) {
        UUID userId = null;

        try {
            userId = UUID.fromString(userInfo);
        } catch (IllegalArgumentException exp) {
            log.debug(String.format("Passed in userinfo - %s is not an UUID", userInfo));
        }

        return userId;
    }

    private User getUserBasedOnInfo(String userInfo) throws ResponseStatusException{
        UUID userId = getUserId(userInfo);
        User user = null;

        if(userId != null) {
            user = userService.findUserById(userId);
            if(user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with user Id %s is not found", userId.toString()));
            }
        } else {
            user = userService.findUserByEmail(userInfo);
            if(user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User with email %s is not found", userInfo));
            }
        }

        return user;
    }

    @GetMapping(value = "/user/{userInfo}")
    public User getUser(@PathVariable String userInfo) throws ResponseStatusException {
        return getUserBasedOnInfo(userInfo);
    }

    @DeleteMapping(value = "/user/{userInfo}")
    public void deleteUser(@PathVariable String userInfo) throws ResponseStatusException {
        User user = getUserBasedOnInfo(userInfo);
        userService.removeUser(user);

    }


}

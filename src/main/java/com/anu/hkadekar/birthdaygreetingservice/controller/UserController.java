package com.anu.hkadekar.birthdaygreetingservice.controller;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.service.UserService;
import com.anu.hkadekar.birthdaygreetingservice.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping(value = "/user")
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }



}

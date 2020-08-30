package com.anu.hkadekar.birthdaygreetingservice.service;

import com.anu.hkadekar.birthdaygreetingservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    public User addUser(User user);
    public User updateUser(User user);
    public User findUserByEmail(String email);
    public User findUserById(UUID uuid);
    public void removeUser(User user);
    public List<User> getAllUsers();

}

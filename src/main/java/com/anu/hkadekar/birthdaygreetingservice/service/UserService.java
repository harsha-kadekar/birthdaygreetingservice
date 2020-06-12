package com.anu.hkadekar.birthdaygreetingservice.service;

import com.anu.hkadekar.birthdaygreetingservice.model.User;
import com.anu.hkadekar.birthdaygreetingservice.repository.FileUserRepository;
import com.anu.hkadekar.birthdaygreetingservice.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UserService {

    private UserRepository userRepository = null;

    public UserService(){
        userRepository = FileUserRepository.getInstance();
    }

    public void addUser(User user) {
        User existingUser = findUserByEmail(user.getEmail());
        if(existingUser != null) {
            user.setUserId(existingUser.getUserId());
            userRepository.updateUsers(Arrays.asList(user));
        } else {
            user.setUserId(UUID.randomUUID());
            userRepository.addUsers(Arrays.asList(user));
        }

    }

    public void removeUser(User user) {

    }

    public User findUserById(UUID uuid){
        return null;
    }

    public User findUserByEmail(String email){
        List<User> users = userRepository.getUsersByEmail(Arrays.asList(email));
        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> getAllUsers() {
        return null;
    }

}

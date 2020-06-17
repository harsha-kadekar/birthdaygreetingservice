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

    public void addUser(User user) throws ResourceAlreadyExistException {
        User existingUser = findUserByEmail(user.getEmail());
        if (existingUser != null) {
            throw new ResourceAlreadyExistException(String.format("User with email %s already exists", user.getEmail()));
        } else {
            user.setUserId(UUID.randomUUID());
            userRepository.addUsers(Arrays.asList(user));
        }
    }

    public User findUserByEmail(String email){
        List<User> users = userRepository.getUsersByEmail(Arrays.asList(email));
        return users.size() > 0 ? users.get(0) : null;
    }

    public void removeUser(User user) {
        userRepository.removeUsers(Arrays.asList(user));
    }

    public User findUserById(UUID uuid){
        List<User> users = userRepository.getUsersById(Arrays.asList(uuid));
        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public void updateUser(User user) {

    }

}

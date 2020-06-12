package com.anu.hkadekar.birthdaygreetingservice.repository;

import com.anu.hkadekar.birthdaygreetingservice.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    void addUsers(List<User> users);
    void removeUsers(List<User> users);
    void updateUsers(List<User> users);
    List<User> getAllUsers();
    List<User> getUsersByEmail(List<String> emails);
    List<User> getUsersById(List<UUID> uuids);
}

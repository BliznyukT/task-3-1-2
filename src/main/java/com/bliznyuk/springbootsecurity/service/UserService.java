package com.bliznyuk.springbootsecurity.service;


import com.bliznyuk.springbootsecurity.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void deleteUserById(long id);
    User getUserById(long id);
    List<User> getUsers();
}

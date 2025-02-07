package com.example.dao;

import com.example.model.User;

import java.util.List;

public interface UserDAO {

    boolean signUp(User user);

    boolean login(String username, String password);

    boolean isUsernameOrEmailExists(String username, String email);

    int getUserIdByUsername(String username);

    List<User> getAllUsers(int userId);

	User getUserById(int userId);
}

package com.example.service;

import com.example.dao.UserDAO;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public boolean signUp(User user) {
        return userDAO.signUp(user);
    }

    public boolean login(String username, String password) {
        return userDAO.login(username, password);
    }

    public boolean isUsernameOrEmailExists(String username, String email) {
        return userDAO.isUsernameOrEmailExists(username, email);
    }

    public int getUserIdByUsername(String username) {
        return userDAO.getUserIdByUsername(username);
    }

    public List<User> getAllUsers(int userId) {
        return userDAO.getAllUsers(userId);
    }
    
    public User getUserbyId(int userId){
    	return userDAO.getUserById(userId);
    }
}

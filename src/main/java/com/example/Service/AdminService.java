package com.example.service;

import com.example.dao.AdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminService {

    @Autowired
    private AdminDAO adminDAO;

    public boolean login(String username, String password) {
        return adminDAO.login(username, password);
    }
    
  public boolean removeUser(int userId) {
  return adminDAO.removeUser(userId);
}


  public boolean removePost(int postId) {
      return adminDAO.removePost(postId);
  }
}

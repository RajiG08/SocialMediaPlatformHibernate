package com.example.dao;

import com.example.model.Admin;

public interface AdminDAO {
    boolean login(String username, String password);

	boolean removePost(int postId);

	boolean removeUser(int userId);
}


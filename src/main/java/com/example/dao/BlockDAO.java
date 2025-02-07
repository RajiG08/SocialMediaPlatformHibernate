package com.example.dao;

import com.example.model.Block;

public interface BlockDAO {
    boolean blockUser(int userId, int friendId);
    boolean unblockUser(int userId, int friendId);
}


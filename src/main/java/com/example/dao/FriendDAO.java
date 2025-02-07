package com.example.dao;

import com.example.model.User;

import java.util.List;

public interface FriendDAO {
    List<User> getNonFriends(int userId);

    boolean addFriend(int userId, int friendId);

    List<User> getFriends(int userId);

   
    boolean removeFriend(int userId, int friendId);

    
}

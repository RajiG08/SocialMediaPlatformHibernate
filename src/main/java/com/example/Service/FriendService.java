package com.example.service;

import com.example.dao.FriendDAO;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDAO friendDAO;

    public List<User> getNonFriends(int userId) {
        return friendDAO.getNonFriends(userId);
    }

    public boolean addFriend(int userId, int friendId) {
        return friendDAO.addFriend(userId, friendId);
    }

    public List<User> getFriends(int userId) {
        return friendDAO.getFriends(userId);
    }




    public boolean removeFriend(int userId, int friendId) {
        return friendDAO.removeFriend(userId, friendId);
    }


}

package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.FriendDAO;
import com.example.model.User;
import com.example.model.Block;
import com.example.model.Friend;

@Repository
@Transactional
public class FriendDAOImpl implements FriendDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final System.Logger logger = System.getLogger(FriendDAOImpl.class.getName());

    @Override
    public List<User> getNonFriends(int userId) {
        List<User> users = new ArrayList<>();
        String hql = "FROM User u WHERE u.userId != :userId AND u.userId NOT IN " +
                     "(SELECT f.friendUser.userId FROM Friend f WHERE f.user.userId = :userId) " +
                     "AND u.userId NOT IN " +
                     "(SELECT b.friend.userId FROM Block b WHERE b.user.userId = :userId) " +
                     "AND u.userId NOT IN " +
                     "(SELECT b.user.userId FROM Block b WHERE b.friend.userId = :userId)";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userId", userId);
            users = query.getResultList();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Failed to perform operation: {0}", e.getMessage());
        }
        logger.log(System.Logger.Level.INFO, "Number of non-friend users: {0}", users.size());
        return users;
    }

    @Override
    public boolean addFriend(int userId, int friendId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, userId);
            if (user == null) {
                return false;
            }
            User friendUser = session.get(User.class, friendId);
            if (friendUser != null) {
                Friend friend = new Friend(user, friendUser);
                session.save(friend);
                return true;
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.INFO, "Failed to add friend: {0}", e.getMessage());
        }
        return false;
    }


    @Override
    public List<User> getFriends(int userId) {
        List<User> friends = new ArrayList<>();
        String hql = "SELECT f.friendUser FROM Friend f WHERE f.user.userId = :userId AND f.friendUser.userId NOT IN " +
                     "(SELECT b.friend.userId FROM Block b WHERE b.user.userId = :userId) " +
                     "AND f.friendUser.userId NOT IN " +
                     "(SELECT b.user.userId FROM Block b WHERE b.friend.userId = :userId)";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userId", userId);
            friends = query.getResultList();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Failed to get friends: {0}", e.getMessage());
        }
        logger.log(System.Logger.Level.INFO, "Number of friends: {0}", friends.size());
        return friends;
    }


    @Override
    public boolean removeFriend(int userId, int friendId) {
        String hql = "DELETE FROM Friend WHERE user.userId = :userId AND friendUser.userId = :friendId";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<?> query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("friendId", friendId);
            int rowsAffected = query.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Failed to remove friend: {0}", e.getMessage());
        }
        return false;
    }

    // Other methods remain unchanged
}


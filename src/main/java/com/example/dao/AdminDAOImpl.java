package com.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class AdminDAOImpl implements AdminDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final System.Logger logger = System.getLogger(AdminDAOImpl.class.getName());

    @Override
    public boolean login(String username, String password) {
        String hardcodedUsername = "admin";
        String hardcodedPassword = "pwd";
        return (username.equals(hardcodedUsername) && password.equals(hardcodedPassword));
    }

    @Override
    public boolean removePost(int postId) {
        String hql = "DELETE FROM Post WHERE postId = :postId";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<?> query = session.createQuery(hql);
            query.setParameter("postId", postId);
            int rowsAffected = query.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error removing post: {0}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeUser(int userId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            
            // Delete Friends
            String deleteFriendsQuery = "DELETE FROM Friend WHERE user.userId = :userId OR friendUser.userId = :userId";
            Query<?> friendsQuery = session.createQuery(deleteFriendsQuery);
            friendsQuery.setParameter("userId", userId);
            friendsQuery.executeUpdate();

            // Delete other related entities
            String[] deleteQueries = {
                "DELETE FROM Block WHERE user.userId = :userId OR friendUser.userId = :userId",
                "DELETE FROM Message WHERE sender.userId = :userId OR recipient.userId = :userId",
                "DELETE FROM Post WHERE user.userId = :userId",
                "DELETE FROM User WHERE userId = :userId"
            };

            for (String query : deleteQueries) {
                Query<?> deleteQuery = session.createQuery(query);
                deleteQuery.setParameter("userId", userId);
                deleteQuery.executeUpdate();
            }

            return true;
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error removing user: {0}", e.getMessage());
            return false;
        }
    }
}


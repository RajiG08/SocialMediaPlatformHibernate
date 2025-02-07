package com.example.dao;

import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean signUp(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean login(String username, String password) {
        String hql = "SELECT COUNT(*) FROM User WHERE username = :username AND password = :password";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            Long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isUsernameOrEmailExists(String username, String email) {
        String hql = "SELECT COUNT(*) FROM User WHERE username = :username OR email = :email";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("username", username);
            query.setParameter("email", email);
            Long count = query.uniqueResult();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int getUserIdByUsername(String username) {
        String hql = "SELECT userId FROM User WHERE username = :username";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Integer> query = session.createQuery(hql, Integer.class);
            query.setParameter("username", username);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<User> getAllUsers(int userId) {
        List<User> users = new ArrayList<>();
        String hql = "FROM User u WHERE u.userId != :userId AND u.userId NOT IN (SELECT b.friend.userId FROM Block b WHERE b.user.userId = :userId)";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("userId", userId);
            users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    
    @Override
    public User getUserById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, userId);
    }
}

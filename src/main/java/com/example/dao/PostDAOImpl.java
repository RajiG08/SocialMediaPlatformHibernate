package com.example.dao;

import com.example.model.Post;
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
public class PostDAOImpl implements PostDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public boolean addPost(Post post) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(post);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Post> getAllPost(int userId) {
        List<Post> feed = new ArrayList<>();
        String hql = "FROM Post p WHERE p.user.userId != :userId AND p.user.userId NOT IN (SELECT b.friend.userId FROM Block b WHERE b.user.userId = :userId) AND p.user.userId NOT IN (SELECT b.user.userId FROM Block b WHERE b.friend.userId = :userId)";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Post> query = session.createQuery(hql, Post.class);
            query.setParameter("userId", userId);
            feed = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feed;
    }
   
    @Override
    public void updatePost(Post post) {
        Session session = sessionFactory.getCurrentSession();
        session.update(post);
    }
    
    @Override
    public List<Post> getUserPosts(int userId) {
        List<Post> posts = new ArrayList<>();
        String hql = "FROM Post p WHERE p.user.userId = :userId AND p.user.userId NOT IN (SELECT b.friend.userId FROM Block b WHERE b.user.userId = :userId)";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<Post> query = session.createQuery(hql, Post.class);
            query.setParameter("userId", userId);
            posts = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }


   
   


}

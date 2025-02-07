package com.example.service;

import com.example.dao.PostDAO;
import com.example.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostDAO postDAO;

    public boolean addPost(Post post) {
        return postDAO.addPost(post);
    }

    public List<Post> getUserPosts(int userId) {
        return postDAO.getUserPosts(userId);
    }
    
    public void updatePost(Post post) {
        postDAO.updatePost(post);
    }

    public List<Post> getAllPost(int userId) {
        return postDAO.getAllPost(userId);
    }
}

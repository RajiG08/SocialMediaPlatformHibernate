package com.example.dao;

import com.example.model.Post;

import java.util.List;

public interface PostDAO {

    boolean addPost(Post post);

    List<Post> getUserPosts(int userId);

    List<Post> getAllPost(int userId);

	

	void updatePost(Post post);
}

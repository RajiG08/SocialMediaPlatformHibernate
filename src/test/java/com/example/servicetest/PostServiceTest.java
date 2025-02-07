package com.example.servicetest;


import com.example.dao.PostDAO;
import com.example.model.Post;
import com.example.service.PostService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @Mock
    private PostDAO postDAO;

    @InjectMocks
    private PostService postService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testAddPost() {
        // Setup mock behavior
        Post post = new Post();
        when(postDAO.addPost(post)).thenReturn(true);

        // Call the service method
        boolean result = postService.addPost(post);

        // Verify
        assertTrue(result);
        verify(postDAO, times(1)).addPost(post);
    }

    @Test
     void testAddPostFailure() {
        // Setup mock behavior
        Post post = new Post();
        when(postDAO.addPost(post)).thenReturn(false);

        // Call the service method
        boolean result = postService.addPost(post);

        // Verify
        assertFalse(result);
        verify(postDAO, times(1)).addPost(post);
    }

    @Test
     void testGetUserPosts() {
        // Setup mock behavior
        int userId = 1;
        List<Post> expectedPosts = new ArrayList<>();
        when(postDAO.getUserPosts(userId)).thenReturn(expectedPosts);

        // Call the service method
        List<Post> actualPosts = postService.getUserPosts(userId);

        // Verify
        assertEquals(expectedPosts, actualPosts);
        verify(postDAO, times(1)).getUserPosts(userId);
    }

    @Test
     void testGetUserPostsEmpty() {
        // Setup mock behavior
        int userId = 1;
        List<Post> expectedPosts = new ArrayList<>();
        when(postDAO.getUserPosts(userId)).thenReturn(expectedPosts);

        // Call the service method
        List<Post> actualPosts = postService.getUserPosts(userId);

        // Verify
        assertTrue(actualPosts.isEmpty());
        verify(postDAO, times(1)).getUserPosts(userId);
    }

    @Test
     void testUpdatePost() {
        // Setup mock behavior
        Post post = new Post();

        // Call the service method
        postService.updatePost(post);

        // Verify
        verify(postDAO, times(1)).updatePost(post);
    }

    @Test
     void testGetAllPost() {
        // Setup mock behavior
        int userId = 1;
        List<Post> expectedPosts = new ArrayList<>();
        when(postDAO.getAllPost(userId)).thenReturn(expectedPosts);

        // Call the service method
        List<Post> actualPosts = postService.getAllPost(userId);

        // Verify
        assertEquals(expectedPosts, actualPosts);
        verify(postDAO, times(1)).getAllPost(userId);
    }

    @Test
     void testGetAllPostEmpty() {
        // Setup mock behavior
        int userId = 1;
        List<Post> expectedPosts = new ArrayList<>();
        when(postDAO.getAllPost(userId)).thenReturn(expectedPosts);

        // Call the service method
        List<Post> actualPosts = postService.getAllPost(userId);

        // Verify
        assertTrue(actualPosts.isEmpty());
        verify(postDAO, times(1)).getAllPost(userId);
    }
}

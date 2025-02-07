package com.example.modeltest;

import org.junit.jupiter.api.Test;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class CommentTest {

    @Test
     void testDefaultConstructor() {
        Comment comment = new Comment();
        assertNotNull(comment);
        assertNull(comment.getPost());
        assertNull(comment.getUser());
        assertNull(comment.getCommentText());
    }

    @Test
     void testParameterizedConstructor() {
        Post post = new Post();
        User user = new User("user1", "User One", "password1", "user1@example.com");
        String commentText = "This is a test comment.";

        Comment comment = new Comment(post, user, commentText);

        assertNotNull(comment);
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(commentText, comment.getCommentText());
    }

    @Test
     void testGettersAndSetters() {
        Post post = new Post();
        User user = new User("user1", "User One", "password1", "user1@example.com");
        String commentText = "Another test comment.";

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setCommentText(commentText);

        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(commentText, comment.getCommentText());
    }
}

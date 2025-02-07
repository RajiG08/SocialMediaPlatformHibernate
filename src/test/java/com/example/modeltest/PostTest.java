package com.example.modeltest;

import com.example.model.Comment;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import com.example.model.Comment;
import com.example.model.Post;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class PostTest {

    @Test
     void testDefaultConstructor() {
        Post post = new Post();
        assertNotNull(post);
        assertEquals(0, post.getPostId());
        assertNull(post.getUser());
        assertNull(post.getPostText());
        assertEquals(0, post.getLikesCount());
        assertEquals(0, post.getCommentCount());
        assertNull(post.getComments());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User("user1", "User One", "password1", "user1@example.com");

        Post post = new Post();
        post.setPostId(1);
        post.setUser(user);
        post.setPostText("This is a test post");
        post.setLikesCount(10);
        post.setCommentCount(5);

        assertEquals(1, post.getPostId());
        assertEquals(user, post.getUser());
        assertEquals("This is a test post", post.getPostText());
        assertEquals(10, post.getLikesCount());
        assertEquals(5, post.getCommentCount());
    }

    @Test
     void testCommentsRelationship() {
        User user = new User("user1", "User One", "password1", "user1@example.com");
        Post post = new Post();
        post.setUser(user);

        Comment comment1 = new Comment(post, user, "Comment 1");
        Comment comment2 = new Comment(post, user, "Comment 2");

        List<Comment> comments = new ArrayList<>();
        comments.add(comment1);
        comments.add(comment2);

        post.setComments(comments);

        assertEquals(2, post.getComments().size());
        assertTrue(post.getComments().contains(comment1));
        assertTrue(post.getComments().contains(comment2));
    }
}

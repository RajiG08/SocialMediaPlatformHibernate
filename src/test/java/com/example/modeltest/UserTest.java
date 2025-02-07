package com.example.modeltest;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import com.example.model.Friend;
import com.example.model.Post;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserTest {

    @Test
     void testDefaultConstructor() {
        User user = new User();
        assertNotNull(user);
        assertEquals(0, user.getUserId());
        assertNull(user.getUsername());
        assertNull(user.getName());
        assertNull(user.getPassword());
        assertNull(user.getEmail());
        assertNull(user.getPrivacyStatus());
        assertNull(user.getPosts());
        assertNull(user.getFriends());
        assertNull(user.getFriendOf());
    }

    @Test
     void testGettersAndSetters() {
        User user = new User();
        user.setUserId(1);
        user.setUsername("user1");
        user.setName("User One");
        user.setPassword("password1");
        user.setEmail("user1@example.com");
        user.setPrivacyStatus("public");

        assertEquals(1, user.getUserId());
        assertEquals("user1", user.getUsername());
        assertEquals("User One", user.getName());
        assertEquals("password1", user.getPassword());
        assertEquals("user1@example.com", user.getEmail());
        assertEquals("public", user.getPrivacyStatus());
    }

    @Test
     void testPostsRelationship() {
        User user = new User();
        Post post1 = new Post();
        Post post2 = new Post();
        List<Post> posts = new ArrayList<>();
        posts.add(post1);
        posts.add(post2);

        user.setPosts(posts);

        assertEquals(2, user.getPosts().size());
        assertTrue(user.getPosts().contains(post1));
        assertTrue(user.getPosts().contains(post2));
    }

    @Test
     void testFriendsRelationship() {
        User user = new User();
        Friend friend1 = new Friend();
        Friend friend2 = new Friend();
        List<Friend> friends = new ArrayList<>();
        friends.add(friend1);
        friends.add(friend2);

        user.setFriends(friends);

        assertEquals(2, user.getFriends().size());
        assertTrue(user.getFriends().contains(friend1));
        assertTrue(user.getFriends().contains(friend2));
    }

    @Test
     void testFriendOfRelationship() {
        User user = new User();
        Friend friendOf1 = new Friend();
        Friend friendOf2 = new Friend();
        List<Friend> friendOf = new ArrayList<>();
        friendOf.add(friendOf1);
        friendOf.add(friendOf2);

        user.setFriendOf(friendOf);

        assertEquals(2, user.getFriendOf().size());
        assertTrue(user.getFriendOf().contains(friendOf1));
        assertTrue(user.getFriendOf().contains(friendOf2));
    }
}

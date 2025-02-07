package com.example.modeltest;

import org.junit.jupiter.api.Test;

import com.example.model.Friend;
import com.example.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class FriendTest {

    @Test
     void testDefaultConstructor() {
        Friend friend = new Friend();
        assertNotNull(friend);
        assertNull(friend.getUser());
        assertNull(friend.getFriendUser());
    }

    @Test
     void testParameterizedConstructor() {
        User user = new User("user1", "User One", "password1", "user1@example.com");
        User friendUser = new User("user2", "User Two", "password2", "user2@example.com");

        Friend friend = new Friend(user, friendUser);

        assertNotNull(friend);
        assertEquals(user, friend.getUser());
        assertEquals(friendUser, friend.getFriendUser());
    }

    @Test
     void testGettersAndSetters() {
        User user = new User("user1", "User One", "password1", "user1@example.com");
        User friendUser = new User("user2", "User Two", "password2", "user2@example.com");

        Friend friend = new Friend();
        friend.setUser(user);
        friend.setFriendUser(friendUser);

        assertEquals(user, friend.getUser());
        assertEquals(friendUser, friend.getFriendUser());
    }
}

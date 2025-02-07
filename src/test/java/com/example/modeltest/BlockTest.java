package com.example.modeltest;

import org.junit.jupiter.api.Test;

import com.example.model.Block;
import com.example.model.User;

import static org.junit.jupiter.api.Assertions.*;

public class BlockTest {

    @Test
     void testDefaultConstructor() {
        Block block = new Block();
        assertNotNull(block);
        assertNull(block.getUser());
        assertNull(block.getFriend());
    }

    @Test
     void testParameterizedConstructor() {
        User user = new User("user1", "User One", "password1", "user1@example.com");
        User friend = new User("user2", "User Two", "password2", "user2@example.com");

        Block block = new Block(user, friend);

        assertNotNull(block);
        assertEquals(user, block.getUser());
        assertEquals(friend, block.getFriend());
    }

    @Test
    public void testGettersAndSetters() {
        User user = new User("user1", "User One", "password1", "user1@example.com");
        User friend = new User("user2", "User Two", "password2", "user2@example.com");

        Block block = new Block();
        block.setUser(user);
        block.setFriend(friend);

        assertEquals(user, block.getUser());
        assertEquals(friend, block.getFriend());
    }
}

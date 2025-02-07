package com.example.servicetest;

import com.example.dao.FriendDAO;
import com.example.model.User;
import com.example.service.FriendService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FriendServiceTest {

    @Mock
    private FriendDAO friendDAO;

    @InjectMocks
    private FriendService friendService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testGetNonFriends() {
        // Setup mock behavior
        int userId = 1;
        List<User> expectedNonFriends = new ArrayList<>();
        when(friendDAO.getNonFriends(userId)).thenReturn(expectedNonFriends);

        // Call the service method
        List<User> actualNonFriends = friendService.getNonFriends(userId);

        // Verify
        assertEquals(expectedNonFriends, actualNonFriends);
        verify(friendDAO, times(1)).getNonFriends(userId);
    }

    @Test
     void testAddFriend() {
        // Setup mock behavior
        int userId = 1;
        int friendId = 2;
        when(friendDAO.addFriend(userId, friendId)).thenReturn(true);

        // Call the service method
        boolean added = friendService.addFriend(userId, friendId);

        // Verify
        assertTrue(added);
        verify(friendDAO, times(1)).addFriend(userId, friendId);
    }

    @Test
     void testGetFriends() {
        // Setup mock behavior
        int userId = 1;
        List<User> expectedFriends = new ArrayList<>();
        when(friendDAO.getFriends(userId)).thenReturn(expectedFriends);

        // Call the service method
        List<User> actualFriends = friendService.getFriends(userId);

        // Verify
        assertEquals(expectedFriends, actualFriends);
        verify(friendDAO, times(1)).getFriends(userId);
    }

    @Test
     void testRemoveFriend() {
        // Setup mock behavior
        int userId = 1;
        int friendId = 2;
        when(friendDAO.removeFriend(userId, friendId)).thenReturn(true);

        // Call the service method
        boolean removed = friendService.removeFriend(userId, friendId);

        // Verify
        assertTrue(removed);
        verify(friendDAO, times(1)).removeFriend(userId, friendId);
    }
}

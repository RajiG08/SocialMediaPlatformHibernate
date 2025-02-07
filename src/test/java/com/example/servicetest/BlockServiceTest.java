
package com.example.servicetest;

import com.example.dao.BlockDAO;
import com.example.service.BlockService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BlockServiceTest {

    @Mock
    private BlockDAO blockDAO;

    @InjectMocks
    private BlockService blockService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testBlockUser_Success() {
        // Mocking behavior of blockDAO
        int userId = 1;
        int friendId = 2;
        when(blockDAO.blockUser(userId, friendId)).thenReturn(true);

        // Call the service method
        boolean result = blockService.blockUser(userId, friendId);

        // Verify the result
        assertTrue(result);

        // Verify that blockUser method in blockDAO was called exactly once with correct arguments
        verify(blockDAO, times(1)).blockUser(userId, friendId);
    }

    @Test
     void testBlockUser_Failure() {
        // Mocking behavior of blockDAO
        int userId = 1;
        int friendId = 2;
        when(blockDAO.blockUser(userId, friendId)).thenReturn(false);

        // Call the service method
        boolean result = blockService.blockUser(userId, friendId);

        // Verify the result
        assertFalse(result);

        // Verify that blockUser method in blockDAO was called exactly once with correct arguments
        verify(blockDAO, times(1)).blockUser(userId, friendId);
    }

    @Test
     void testUnblockUser_Success() {
        // Mocking behavior of blockDAO
        int userId = 1;
        int friendId = 2;
        when(blockDAO.unblockUser(userId, friendId)).thenReturn(true);

        // Call the service method
        boolean result = blockService.unblockUser(userId, friendId);

        // Verify the result
        assertTrue(result);

        // Verify that unblockUser method in blockDAO was called exactly once with correct arguments
        verify(blockDAO, times(1)).unblockUser(userId, friendId);
    }

    @Test
     void testUnblockUser_Failure() {
        // Mocking behavior of blockDAO
        int userId = 1;
        int friendId = 2;
        when(blockDAO.unblockUser(userId, friendId)).thenReturn(false);

        // Call the service method
        boolean result = blockService.unblockUser(userId, friendId);

        // Verify the result
        assertFalse(result);

        // Verify that unblockUser method in blockDAO was called exactly once with correct arguments
        verify(blockDAO, times(1)).unblockUser(userId, friendId);
    }
}

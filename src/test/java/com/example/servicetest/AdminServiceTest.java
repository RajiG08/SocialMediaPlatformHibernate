package com.example.servicetest;

import com.example.dao.AdminDAO;
import com.example.service.AdminService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class AdminServiceTest {

    @Mock
    private AdminDAO adminDAO;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
        reset(adminDAO);
    }

    @Test
    void testLoginSuccess() {
        // Mock data
        String username = "admin";
        String password = "pwd";
        when(adminDAO.login(username, password)).thenReturn(true);

        // Test login method in service
        assertTrue(adminService.login(username, password));

        // Verify interaction
        verify(adminDAO).login(username, password);
    }

    @Test
    void testLoginFailure() {
        // Mock data
        String username = "admin";
        String password = "wrongPwd";
        when(adminDAO.login(username, password)).thenReturn(false);

        // Test login method in service
        assertFalse(adminService.login(username, password));

        // Verify interaction
        verify(adminDAO).login(username, password);
    }

    @Test
    void testRemovePostSuccess() {
        // Mock data
        int postId = 1;
        when(adminDAO.removePost(postId)).thenReturn(true);

        // Test removePost method in service
        assertTrue(adminService.removePost(postId));

        // Verify interaction
        verify(adminDAO).removePost(postId);
    }

    @Test
    void testRemovePostFailure() {
        // Mock data
        int postId = 2;
        when(adminDAO.removePost(postId)).thenReturn(false);

        // Test removePost method in service
        assertFalse(adminService.removePost(postId));

        // Verify interaction
        verify(adminDAO).removePost(postId);
    }

    @Test
    void testRemoveUserSuccess() {
        // Mock data
        int userId = 1;
        when(adminDAO.removeUser(userId)).thenReturn(true);

        // Test removeUser method in service
        assertTrue(adminService.removeUser(userId));

        // Verify interaction
        verify(adminDAO).removeUser(userId);
    }

    @Test
    void testRemoveUserFailure() {
        // Mock data
        int userId = 2;
        when(adminDAO.removeUser(userId)).thenReturn(false);

        // Test removeUser method in service
        assertFalse(adminService.removeUser(userId));

        // Verify interaction
        verify(adminDAO).removeUser(userId);
    }
}

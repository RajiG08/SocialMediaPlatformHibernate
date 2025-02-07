package com.example.servicetest;



import com.example.dao.UserDAO;
import com.example.model.User;
import com.example.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSignUp() {
        // Setup mock behavior
        User user = new User();
        when(userDAO.signUp(user)).thenReturn(true);

        // Call the service method
        boolean result = userService.signUp(user);

        // Verify
        assertTrue(result);
        verify(userDAO, times(1)).signUp(user);
    }

    @Test
     void testSignUpFailure() {
        // Setup mock behavior
        User user = new User();
        when(userDAO.signUp(user)).thenReturn(false);

        // Call the service method
        boolean result = userService.signUp(user);

        // Verify
        assertFalse(result);
        verify(userDAO, times(1)).signUp(user);
    }

    @Test
     void testLogin() {
        // Setup mock behavior
        String username = "testuser";
        String password = "testpass";
        when(userDAO.login(username, password)).thenReturn(true);

        // Call the service method
        boolean result = userService.login(username, password);

        // Verify
        assertTrue(result);
        verify(userDAO, times(1)).login(username, password);
    }

    @Test
     void testLoginFailure() {
        // Setup mock behavior
        String username = "testuser";
        String password = "wrongpass";
        when(userDAO.login(username, password)).thenReturn(false);

        // Call the service method
        boolean result = userService.login(username, password);

        // Verify
        assertFalse(result);
        verify(userDAO, times(1)).login(username, password);
    }

    @Test
     void testIsUsernameOrEmailExists() {
        // Setup mock behavior
        String username = "testuser";
        String email = "test@example.com";
        when(userDAO.isUsernameOrEmailExists(username, email)).thenReturn(true);

        // Call the service method
        boolean result = userService.isUsernameOrEmailExists(username, email);

        // Verify
        assertTrue(result);
        verify(userDAO, times(1)).isUsernameOrEmailExists(username, email);
    }

    @Test
     void testIsUsernameOrEmailExistsNotFound() {
        // Setup mock behavior
        String username = "nonexistentuser";
        String email = "nonexistent@example.com";
        when(userDAO.isUsernameOrEmailExists(username, email)).thenReturn(false);

        // Call the service method
        boolean result = userService.isUsernameOrEmailExists(username, email);

        // Verify
        assertFalse(result);
        verify(userDAO, times(1)).isUsernameOrEmailExists(username, email);
    }

    @Test
     void testGetUserIdByUsername() {
        // Setup mock behavior
        String username = "testuser";
        int expectedUserId = 1;
        when(userDAO.getUserIdByUsername(username)).thenReturn(expectedUserId);

        // Call the service method
        int actualUserId = userService.getUserIdByUsername(username);

        // Verify
        assertEquals(expectedUserId, actualUserId);
        verify(userDAO, times(1)).getUserIdByUsername(username);
    }

    @Test
     void testGetAllUsers() {
        // Setup mock behavior
        int userId = 1;
        List<User> expectedUsers = new ArrayList<>();
        when(userDAO.getAllUsers(userId)).thenReturn(expectedUsers);

        // Call the service method
        List<User> actualUsers = userService.getAllUsers(userId);

        // Verify
        assertEquals(expectedUsers, actualUsers);
        verify(userDAO, times(1)).getAllUsers(userId);
    }

    @Test
     void testGetUserById() {
        // Setup mock behavior
        int userId = 1;
        User expectedUser = new User();
        when(userDAO.getUserById(userId)).thenReturn(expectedUser);

        // Call the service method
        User actualUser = userService.getUserbyId(userId);

        // Verify
        assertEquals(expectedUser, actualUser);
        verify(userDAO, times(1)).getUserById(userId);
    }
}

package com.example.modeltest;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.model.Admin;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    private Admin admin;

    @BeforeEach
    public void setUp() {
        admin = new Admin();
    }

    @Test
     void testGetAndSetAdminId() {
        int adminId = 1;
        admin.setAdminId(adminId);
        assertEquals(adminId, admin.getAdminId());
    }

    @Test
     void testGetAndSetUsername() {
        String username = "adminUser";
        admin.setUsername(username);
        assertEquals(username, admin.getUsername());
    }

    @Test
     void testGetAndSetName() {
        String name = "Admin Name";
        admin.setName(name);
        assertEquals(name, admin.getName());
    }

    @Test
     void testGetAndSetPassword() {
        String password = "adminPass";
        admin.setPassword(password);
        assertEquals(password, admin.getPassword());
    }

    @Test
     void testGetAndSetEmail() {
        String email = "admin@example.com";
        admin.setEmail(email);
        assertEquals(email, admin.getEmail());
    }
}

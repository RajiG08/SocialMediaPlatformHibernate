package com.example.daotest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dao.UserDAOImpl;
import com.example.model.User;

@ExtendWith(MockitoExtension.class)
public class UserDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Long> longQuery;

    @Mock
    private Query<Integer> intQuery;

    @Mock
    private Query<User> userQuery;

    @InjectMocks
    private UserDAOImpl userDAO;

    @BeforeEach
     void setUp() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
     void testSignUp() {
        User user = new User();
        when(session.save(user)).thenReturn(1);

        boolean result = userDAO.signUp(user);
        assertTrue(result);
        verify(session).save(user);
    }

    @Test
     void testSignUpException() {
        User user = new User();
        doThrow(RuntimeException.class).when(session).save(user);

        boolean result = userDAO.signUp(user);
        assertFalse(result);
    }

    @Test
     void testLogin() {
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("username", "user")).thenReturn(longQuery);
        when(longQuery.setParameter("password", "pass")).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(1L);

        boolean result = userDAO.login("user", "pass");
        assertTrue(result);
        verify(session).createQuery(anyString(), eq(Long.class));
        verify(longQuery).setParameter("username", "user");
        verify(longQuery).setParameter("password", "pass");
        verify(longQuery).uniqueResult();
    }

    @Test
     void testLoginException() {
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(anyString(), anyString())).thenThrow(RuntimeException.class);

        boolean result = userDAO.login("user", "pass");
        assertFalse(result);
    }

    @Test
     void testIsUsernameOrEmailExists() {
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter("username", "user")).thenReturn(longQuery);
        when(longQuery.setParameter("email", "email@example.com")).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(1L);

        boolean result = userDAO.isUsernameOrEmailExists("user", "email@example.com");
        assertTrue(result);
        verify(session).createQuery(anyString(), eq(Long.class));
        verify(longQuery).setParameter("username", "user");
        verify(longQuery).setParameter("email", "email@example.com");
        verify(longQuery).uniqueResult();
    }

    @Test
    public void testIsUsernameOrEmailExistsException() {
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(anyString(), anyString())).thenThrow(RuntimeException.class);

        boolean result = userDAO.isUsernameOrEmailExists("user", "email@example.com");
        assertFalse(result);
    }

    @Test
     void testGetUserIdByUsername() {
        when(session.createQuery(anyString(), eq(Integer.class))).thenReturn(intQuery);
        when(intQuery.setParameter("username", "user")).thenReturn(intQuery);
        when(intQuery.uniqueResult()).thenReturn(1);

        int userId = userDAO.getUserIdByUsername("user");
        assertEquals(1, userId);
        verify(session).createQuery(anyString(), eq(Integer.class));
        verify(intQuery).setParameter("username", "user");
        verify(intQuery).uniqueResult();
    }

    @Test
     void testGetUserIdByUsernameException() {
        when(session.createQuery(anyString(), eq(Integer.class))).thenReturn(intQuery);
        when(intQuery.setParameter(anyString(), anyString())).thenThrow(RuntimeException.class);

        int userId = userDAO.getUserIdByUsername("user");
        assertEquals(-1, userId);
    }

    @Test
     void testGetAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter("userId", 1)).thenReturn(userQuery);
        when(userQuery.getResultList()).thenReturn(expectedUsers);

        List<User> actualUsers = userDAO.getAllUsers(1);
        assertEquals(expectedUsers, actualUsers);
        verify(session).createQuery(anyString(), eq(User.class));
        verify(userQuery).setParameter("userId", 1);
        verify(userQuery).getResultList();
    }

    @Test
     void testGetAllUsersException() {
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter(anyString(), anyInt())).thenThrow(RuntimeException.class);

        List<User> actualUsers = userDAO.getAllUsers(1);
        assertTrue(actualUsers.isEmpty());
    }

    @Test
     void testGetUserById() {
        User expectedUser = new User();
        when(session.get(User.class, 1)).thenReturn(expectedUser);

        User actualUser = userDAO.getUserById(1);
        assertEquals(expectedUser, actualUser);
        verify(session).get(User.class, 1);
    }

    @Test
     void testGetUserByIdException() {
        doThrow(RuntimeException.class).when(session).get(User.class, 1);

        assertThrows(RuntimeException.class, () -> {
            userDAO.getUserById(1);
        });
    }
}

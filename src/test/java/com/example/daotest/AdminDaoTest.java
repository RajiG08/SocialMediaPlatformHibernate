package com.example.daotest;

import com.example.dao.AdminDAOImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AdminDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<?> query;

    @InjectMocks
    private AdminDAOImpl adminDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock sessionFactory behavior
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.createQuery(anyString())).thenReturn(query);
    }

    @AfterEach
    void tearDown() {
        reset(sessionFactory, session, query);
    }

    @Test
    void testLoginSuccess() {
        // Mock data
        String username = "admin";
        String password = "pwd";

        // Test login method
        assertTrue(adminDAO.login(username, password));
    }

    @Test
    void testLoginFailure() {
        // Mock data
        String username = "admin";
        String password = "wrongPwd";

        // Test login method
        assertFalse(adminDAO.login(username, password));
    }

    @Test
    void testRemovePostSuccess() {
        // Mock data
        int postId = 1;
        when(query.executeUpdate()).thenReturn(1);

        // Test removePost method
        assertTrue(adminDAO.removePost(postId));

        // Verify interactions
        verify(session).createQuery(anyString());
        verify(query).setParameter(eq("postId"), eq(postId));
        verify(query).executeUpdate();
    }

    @Test
    void testRemovePostFailure() {
        // Mock data
        int postId = 2;
        when(query.executeUpdate()).thenReturn(0);

        // Test removePost method
        assertFalse(adminDAO.removePost(postId));

        // Verify interactions
        verify(session).createQuery(anyString());
        verify(query).setParameter(eq("postId"), eq(postId));
        verify(query).executeUpdate();
    }

    @Test
    void testRemoveUserSuccess() {
        // Mock data
        int userId = 1;

        // Mock queries
        Query<?> friendsQueryMock = mock(Query.class);
        Query<?> blockQueryMock = mock(Query.class);
        Query<?> messageQueryMock = mock(Query.class);
        Query<?> postQueryMock = mock(Query.class);
        Query<?> userQueryMock = mock(Query.class);

        when(session.createQuery("DELETE FROM Friend WHERE user.userId = :userId OR friendUser.userId = :userId")).thenReturn(friendsQueryMock);
        when(session.createQuery("DELETE FROM Block WHERE user.userId = :userId OR friendUser.userId = :userId")).thenReturn(blockQueryMock);
        when(session.createQuery("DELETE FROM Message WHERE sender.userId = :userId OR recipient.userId = :userId")).thenReturn(messageQueryMock);
        when(session.createQuery("DELETE FROM Post WHERE user.userId = :userId")).thenReturn(postQueryMock);
        when(session.createQuery("DELETE FROM User WHERE userId = :userId")).thenReturn(userQueryMock);

        when(friendsQueryMock.executeUpdate()).thenReturn(1);
        when(blockQueryMock.executeUpdate()).thenReturn(1);
        when(messageQueryMock.executeUpdate()).thenReturn(1);
        when(postQueryMock.executeUpdate()).thenReturn(1);
        when(userQueryMock.executeUpdate()).thenReturn(1);

        // Test removeUser method
        assertTrue(adminDAO.removeUser(userId));

        // Verify interactions
        verify(session, times(5)).createQuery(anyString());
        verify(friendsQueryMock).setParameter("userId", userId);
        verify(blockQueryMock).setParameter("userId", userId);
        verify(messageQueryMock).setParameter("userId", userId);
        verify(postQueryMock).setParameter("userId", userId);
        verify(userQueryMock).setParameter("userId", userId);

        verify(friendsQueryMock).executeUpdate();
        verify(blockQueryMock).executeUpdate();
        verify(messageQueryMock).executeUpdate();
        verify(postQueryMock).executeUpdate();
        verify(userQueryMock).executeUpdate();
    }

}

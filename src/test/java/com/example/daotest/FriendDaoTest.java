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

import com.example.model.User;
import com.example.dao.FriendDAOImpl;
import com.example.model.Friend;

@ExtendWith(MockitoExtension.class)
public class FriendDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<User> userQuery;

    @InjectMocks
    private FriendDAOImpl friendDAO;

    @BeforeEach
     void setUp() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
     void testGetNonFriends() {
        List<User> expectedUsers = new ArrayList<>();
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter(anyString(), anyInt())).thenReturn(userQuery);
        when(userQuery.getResultList()).thenReturn(expectedUsers);

        List<User> actualUsers = friendDAO.getNonFriends(1);
        assertEquals(expectedUsers, actualUsers);
        verify(session).createQuery(anyString(), eq(User.class));
        verify(userQuery).setParameter("userId", 1);
        verify(userQuery).getResultList();
    }

    @Test
     void testAddFriend() {
        User user = new User();
        User friendUser = new User();
        when(session.get(User.class, 1)).thenReturn(user);
        when(session.get(User.class, 2)).thenReturn(friendUser);

        boolean result = friendDAO.addFriend(1, 2);
        assertTrue(result);
        verify(session).get(User.class, 1);
        verify(session).get(User.class, 2);
        verify(session).save(any(Friend.class));
    }

    @Test
    void testAddFriendUserNotFound() {
        when(session.get(User.class, 1)).thenReturn(null);

        boolean result = friendDAO.addFriend(1, 2);
        assertFalse(result);
        verify(session).get(User.class, 1);
        verify(session, never()).get(User.class, 2);
        verify(session, never()).save(any(Friend.class));
    }

    @Test
     void testGetFriends() {
        List<User> expectedFriends = new ArrayList<>();
        when(session.createQuery(anyString(), eq(User.class))).thenReturn(userQuery);
        when(userQuery.setParameter(anyString(), anyInt())).thenReturn(userQuery);
        when(userQuery.getResultList()).thenReturn(expectedFriends);

        List<User> actualFriends = friendDAO.getFriends(1);
        assertEquals(expectedFriends, actualFriends);
        verify(session).createQuery(anyString(), eq(User.class));
        verify(userQuery).setParameter("userId", 1);
        verify(userQuery).getResultList();
    }

    @Test
     void testRemoveFriend() {
        @SuppressWarnings("unchecked")
        Query<Object> query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = friendDAO.removeFriend(1, 2);
        assertTrue(result);
        verify(query).setParameter("userId", 1);
        verify(query).setParameter("friendId", 2);
        verify(query).executeUpdate();
    }

    @Test
     void testRemoveFriendNotExists() {
        @SuppressWarnings("unchecked")
        Query<Object> query = mock(Query.class);
        when(session.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyInt())).thenReturn(query);
        when(query.executeUpdate()).thenReturn(0);

        boolean result = friendDAO.removeFriend(1, 2);
        assertFalse(result);
        verify(query).setParameter("userId", 1);
        verify(query).setParameter("friendId", 2);
        verify(query).executeUpdate();
    }
}

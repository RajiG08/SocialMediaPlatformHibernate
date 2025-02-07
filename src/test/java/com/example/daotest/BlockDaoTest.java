package com.example.daotest;

import com.example.dao.BlockDAOImpl;
import com.example.model.Block;
import com.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class BlockDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<?> query;

    @InjectMocks
    private BlockDAOImpl blockDAO;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testBlockUserSuccess() {
        User user = new User();
        User friend = new User();
        when(session.get(User.class, 1)).thenReturn(user);
        when(session.get(User.class, 2)).thenReturn(friend);

        boolean result = blockDAO.blockUser(1, 2);
        assertTrue(result);

        verify(session, times(1)).get(User.class, 1);
        verify(session, times(1)).get(User.class, 2);
        verify(session, times(1)).save(any(Block.class));
    }

    @Test
     void testBlockUserFailWhenUserIsNull() {
        when(session.get(User.class, 1)).thenReturn(null);

        boolean result = blockDAO.blockUser(1, 2);
        assertFalse(result);

        verify(session, times(1)).get(User.class, 1);
        verify(session, never()).get(User.class, 2);
        verify(session, never()).save(any(Block.class));
    }

    @Test
     void testBlockUserFailWhenFriendIsNull() {
        User user = new User();
        when(session.get(User.class, 1)).thenReturn(user);
        when(session.get(User.class, 2)).thenReturn(null);

        boolean result = blockDAO.blockUser(1, 2);
        assertFalse(result);

        verify(session, times(1)).get(User.class, 1);
        verify(session, times(1)).get(User.class, 2);
        verify(session, never()).save(any(Block.class));
    }

    @Test
     void testUnblockUserSuccess() {
        when(session.createQuery(anyString())).thenReturn((Query) query);
        when(query.setParameter(anyString(), anyInt())).thenReturn((Query) query);
        when(query.executeUpdate()).thenReturn(1);

        boolean result = blockDAO.unblockUser(1, 2);
        assertTrue(result);

        verify(session, times(1)).createQuery(anyString());
        verify(query, times(2)).setParameter(anyString(), anyInt());
        verify(query, times(1)).executeUpdate();
    }

    @Test
   void testUnblockUserFail() {
        when(session.createQuery(anyString())).thenReturn((Query) query);
        when(query.setParameter(anyString(), anyInt())).thenReturn((Query) query);
        when(query.executeUpdate()).thenReturn(0);

        boolean result = blockDAO.unblockUser(1, 2);
        assertFalse(result);

        verify(session, times(1)).createQuery(anyString());
        verify(query, times(2)).setParameter(anyString(), anyInt());
        verify(query, times(1)).executeUpdate();
    }

    @Test
    void testUnblockUserException() {
        when(session.createQuery(anyString())).thenThrow(new RuntimeException("Exception"));

        boolean result = blockDAO.unblockUser(1, 2);
        assertFalse(result);

        verify(session, times(1)).createQuery(anyString());
    }
}

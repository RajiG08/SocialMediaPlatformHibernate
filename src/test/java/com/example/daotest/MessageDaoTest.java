
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

import com.example.dao.MessageDAOImpl;
import com.example.model.Message;
import com.example.model.User;

@ExtendWith(MockitoExtension.class)
public class MessageDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Long> longQuery;

    @Mock
    private Query<String> stringQuery;

    @InjectMocks
    private MessageDAOImpl messageDAO;

    @BeforeEach
     void setUp() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
     void testSendMessage() {
        User sender = new User();
        User recipient = new User();

        when(session.get(User.class, 1)).thenReturn(sender);
        when(session.get(User.class, 2)).thenReturn(recipient);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(anyString(), anyInt())).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(1L).thenReturn(0L);

        boolean result = messageDAO.sendMessage(1, 2, "Hello");
        assertTrue(result);
        verify(session).save(any(Message.class));
    }

    @Test
     void testSendMessageRecipientNotFriend() {
        User sender = new User();
        User recipient = new User();

        when(session.get(User.class, 1)).thenReturn(sender);
        when(session.get(User.class, 2)).thenReturn(recipient);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(anyString(), anyInt())).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(0L);

        boolean result = messageDAO.sendMessage(1, 2, "Hello");
        assertFalse(result);
        verify(session, never()).save(any(Message.class));
    }

    @Test
     void testSendMessageRecipientBlocked() {
        User sender = new User();
        User recipient = new User();

        when(session.get(User.class, 1)).thenReturn(sender);
        when(session.get(User.class, 2)).thenReturn(recipient);
        when(session.createQuery(anyString(), eq(Long.class))).thenReturn(longQuery);
        when(longQuery.setParameter(anyString(), anyInt())).thenReturn(longQuery);
        when(longQuery.uniqueResult()).thenReturn(1L).thenReturn(1L);

        boolean result = messageDAO.sendMessage(1, 2, "Hello");
        assertFalse(result);
        verify(session, never()).save(any(Message.class));
    }

    @Test
     void testSendMessageSenderNotFound() {
        when(session.get(User.class, 1)).thenReturn(null);

        boolean result = messageDAO.sendMessage(1, 2, "Hello");
        assertFalse(result);
        verify(session, never()).save(any(Message.class));
    }

    @Test
     void testSendMessageRecipientNotFound() {
        User sender = new User();
        when(session.get(User.class, 1)).thenReturn(sender);
        when(session.get(User.class, 2)).thenReturn(null);

        boolean result = messageDAO.sendMessage(1, 2, "Hello");
        assertFalse(result);
        verify(session, never()).save(any(Message.class));
    }

    @Test
     void testGetMessages() {
        List<String> expectedMessages = new ArrayList<>();
        when(session.createQuery(anyString(), eq(String.class))).thenReturn(stringQuery);
        when(stringQuery.setParameter(anyString(), anyInt())).thenReturn(stringQuery);
        when(stringQuery.getResultList()).thenReturn(expectedMessages);

        List<String> actualMessages = messageDAO.getMessages(1);
        assertEquals(expectedMessages, actualMessages);
        verify(session).createQuery(anyString(), eq(String.class));
        verify(stringQuery).setParameter("userId", 1);
        verify(stringQuery).getResultList();
    }

    @Test
     void testGetMessagesError() {
        when(session.createQuery(anyString(), eq(String.class))).thenReturn(stringQuery);
        when(stringQuery.setParameter(anyString(), anyInt())).thenThrow(RuntimeException.class);

        List<String> actualMessages = messageDAO.getMessages(1);
        assertTrue(actualMessages.isEmpty());
    }
}

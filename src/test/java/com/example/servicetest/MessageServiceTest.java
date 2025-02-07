package com.example.servicetest;


import com.example.dao.MessageDAO;
import com.example.service.MessageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageDAO messageDAO;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSendMessage() {
        // Setup mock behavior
        int senderId = 1;
        int recipientId = 2;
        String messageText = "Hello, World!";
        when(messageDAO.sendMessage(senderId, recipientId, messageText)).thenReturn(true);

        // Call the service method
        boolean sent = messageService.sendMessage(senderId, recipientId, messageText);

        // Verify
        assertTrue(sent);
        verify(messageDAO, times(1)).sendMessage(senderId, recipientId, messageText);
    }

    @Test
     void testSendMessageFailure() {
        // Setup mock behavior
        int senderId = 1;
        int recipientId = 2;
        String messageText = "Hello, World!";
        when(messageDAO.sendMessage(senderId, recipientId, messageText)).thenReturn(false);

        // Call the service method
        boolean sent = messageService.sendMessage(senderId, recipientId, messageText);

        // Verify
        assertFalse(sent);
        verify(messageDAO, times(1)).sendMessage(senderId, recipientId, messageText);
    }

    @Test
     void testGetMessages() {
        // Setup mock behavior
        int userId = 1;
        List<String> expectedMessages = new ArrayList<>();
        expectedMessages.add("Message 1");
        expectedMessages.add("Message 2");
        when(messageDAO.getMessages(userId)).thenReturn(expectedMessages);

        // Call the service method
        List<String> actualMessages = messageService.getMessages(userId);

        // Verify
        assertEquals(expectedMessages, actualMessages);
        verify(messageDAO, times(1)).getMessages(userId);
    }

    @Test
     void testGetMessagesEmpty() {
        // Setup mock behavior
        int userId = 1;
        List<String> expectedMessages = new ArrayList<>();
        when(messageDAO.getMessages(userId)).thenReturn(expectedMessages);

        // Call the service method
        List<String> actualMessages = messageService.getMessages(userId);

        // Verify
        assertTrue(actualMessages.isEmpty());
        verify(messageDAO, times(1)).getMessages(userId);
    }
}

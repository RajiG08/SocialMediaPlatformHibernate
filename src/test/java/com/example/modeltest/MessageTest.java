package com.example.modeltest;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import com.example.model.Message;
import com.example.model.User;

public class MessageTest {

    @Test
     void testDefaultConstructor() {
        Message message = new Message();
        assertNotNull(message);
        assertEquals(0, message.getMessageId());
        assertNull(message.getSender());
        assertNull(message.getRecipient());
        assertNull(message.getMessageText());
    }

    @Test
     void testParameterizedConstructor() {
        User sender = new User("sender1", "Sender One", "password1", "sender1@example.com");
        User recipient = new User("recipient1", "Recipient One", "password2", "recipient1@example.com");
        String messageText = "Hello, World!";
        
        Message message = new Message(sender, recipient, messageText);

        assertNotNull(message);
        assertEquals(sender, message.getSender());
        assertEquals(recipient, message.getRecipient());
        assertEquals(messageText, message.getMessageText());
    }

    @Test
     void testGettersAndSetters() {
        Message message = new Message();
        User sender = new User("sender1", "Sender One", "password1", "sender1@example.com");
        User recipient = new User("recipient1", "Recipient One", "password2", "recipient1@example.com");
        String messageText = "Hello, World!";

        message.setMessageId(1);
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setMessageText(messageText);

        assertEquals(1, message.getMessageId());
        assertEquals(sender, message.getSender());
        assertEquals(recipient, message.getRecipient());
        assertEquals(messageText, message.getMessageText());
    }
}

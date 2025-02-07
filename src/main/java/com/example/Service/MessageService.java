package com.example.service;

import com.example.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired
    private MessageDAO messageDAO;

    public boolean sendMessage(int senderId, int recipientId, String messageText) {
        return messageDAO.sendMessage(senderId, recipientId, messageText);
    }

    public List<String> getMessages(int userId) {
        return messageDAO.getMessages(userId);
    }
}

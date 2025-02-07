package com.example.dao;

import java.util.List;

public interface MessageDAO {

    boolean sendMessage(int senderId, int recipientId, String messageText);

    List<String> getMessages(int userId);
}

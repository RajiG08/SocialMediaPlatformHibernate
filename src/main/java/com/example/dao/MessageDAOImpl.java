package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.MessageDAO;
import com.example.model.Message;
import com.example.model.User;

@Repository
@Transactional
public class MessageDAOImpl implements MessageDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final System.Logger logger = System.getLogger(MessageDAOImpl.class.getName());

    @Override
    public boolean sendMessage(int senderId, int recipientId, String messageText) {
        try {
            Session session = sessionFactory.getCurrentSession();
            User sender = session.get(User.class, senderId);
            User recipient = session.get(User.class, recipientId);

            if (sender != null && recipient != null) {
                // Check if the recipient is a friend
                String friendHql = "SELECT COUNT(*) FROM Friend f WHERE f.user.userId = :senderId AND f.friendUser.userId = :recipientId";
                Query<Long> friendQuery = session.createQuery(friendHql, Long.class);
                friendQuery.setParameter("senderId", senderId);
                friendQuery.setParameter("recipientId", recipientId);
                Long friendCount = friendQuery.uniqueResult();

                if (friendCount > 0) {
                    // Check if the recipient is blocked by the sender or vice versa
                    String blockHql = "SELECT COUNT(*) FROM Block b WHERE (b.user.userId = :senderId AND b.friend.userId = :recipientId) " +
                                      "OR (b.user.userId = :recipientId AND b.friend.userId = :senderId)";
                    Query<Long> blockQuery = session.createQuery(blockHql, Long.class);
                    blockQuery.setParameter("senderId", senderId);
                    blockQuery.setParameter("recipientId", recipientId);
                    Long blockCount = blockQuery.uniqueResult();

                    if (blockCount == 0) {
                        // Neither user has blocked the other, send the message
                        Message message = new Message(sender, recipient, messageText);
                        session.save(message);
                        return true;
                    } else {
                        logger.log(System.Logger.Level.INFO, "Message cannot be sent as the recipient is blocked.");
                    }
                } else {
                    logger.log(System.Logger.Level.INFO, "Follow to message.");
                }
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error sending message: {0}", e.getMessage());
        }
        return false;
    }


    @Override
    public List<String> getMessages(int userId) {
        List<String> messages = new ArrayList<>();
        String hql = "SELECT CONCAT('From: ', m.sender.username, ' - ', m.messageText) FROM Message m WHERE m.recipient.userId = :userId";
        try {
            Session session = sessionFactory.getCurrentSession();
            Query<String> query = session.createQuery(hql, String.class);
            query.setParameter("userId", userId);
            messages = query.getResultList();
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error fetching messages: {0}", e.getMessage());
        }
        return messages;
    }
}

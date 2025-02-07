package com.example.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Block;
import com.example.model.User;

@Repository
@Transactional
public class BlockDAOImpl implements BlockDAO {

    @Autowired
    private SessionFactory sessionFactory;

    private static final System.Logger logger = System.getLogger(BlockDAOImpl.class.getName());

    @Override
    public boolean blockUser(int userId, int friendId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, userId);
            if (user == null) {
                return false;
            }
            User friend = session.get(User.class, friendId);
            if (friend != null) {
                Block block = new Block(user, friend);
                session.save(block);
                return true;
            }
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error blocking user: {0}", e.getMessage());
        }
        return false;
    }

    @Override
    public boolean unblockUser(int userId, int friendId) {
        try {
            Session session = sessionFactory.getCurrentSession();
            String hql = "DELETE FROM Block WHERE user.userId = :userId AND friend.userId = :friendId";
            Query<?> query = session.createQuery(hql);
            query.setParameter("userId", userId);
            query.setParameter("friendId", friendId);
            int result = query.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            logger.log(System.Logger.Level.ERROR, "Error unblocking user: {0}", e.getMessage());
        }
        return false;
    }
}


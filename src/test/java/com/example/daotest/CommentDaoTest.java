
package com.example.daotest;

import com.example.dao.CommentDAOImpl;
import com.example.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class CommentDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private CommentDAOImpl commentDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        commentDAO.saveComment(comment);
        verify(session, times(1)).save(comment);
    }

    @Test
    void testSaveCommentWithException() {
        Comment comment = new Comment();
        doThrow(new RuntimeException("Exception")).when(session).save(comment);
        try {
            commentDAO.saveComment(comment);
        } catch (Exception e) {
            // Exception expected
        }
        verify(session, times(1)).save(comment);
    }
}

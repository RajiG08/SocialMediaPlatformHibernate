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

import com.example.dao.PostDAOImpl;
import com.example.model.Post;

@ExtendWith(MockitoExtension.class)
public class PostDaoTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<Post> postQuery;

    @InjectMocks
    private PostDAOImpl postDAO;

    @BeforeEach
     void setUp() {
        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
     void testAddPost() {
        Post post = new Post();
        when(session.save(post)).thenReturn(1);

        boolean result = postDAO.addPost(post);
        assertTrue(result);
        verify(session).save(post);
    }

    @Test
     void testAddPostException() {
        Post post = new Post();
        doThrow(RuntimeException.class).when(session).save(post);

        boolean result = postDAO.addPost(post);
        assertFalse(result);
    }

    @Test
     void testGetAllPost() {
        List<Post> expectedPosts = new ArrayList<>();
        when(session.createQuery(anyString(), eq(Post.class))).thenReturn(postQuery);
        when(postQuery.setParameter(anyString(), anyInt())).thenReturn(postQuery);
        when(postQuery.getResultList()).thenReturn(expectedPosts);

        List<Post> actualPosts = postDAO.getAllPost(1);
        assertEquals(expectedPosts, actualPosts);
        verify(session).createQuery(anyString(), eq(Post.class));
        verify(postQuery).setParameter("userId", 1);
        verify(postQuery).getResultList();
    }

    @Test
     void testGetAllPostException() {
        when(session.createQuery(anyString(), eq(Post.class))).thenReturn(postQuery);
        when(postQuery.setParameter(anyString(), anyInt())).thenThrow(RuntimeException.class);

        List<Post> actualPosts = postDAO.getAllPost(1);
        assertTrue(actualPosts.isEmpty());
    }

    @Test
     void testUpdatePost() {
        Post post = new Post();
        doNothing().when(session).update(post);

        postDAO.updatePost(post);
        verify(session).update(post);
    }

    @Test
     void testGetUserPosts() {
        List<Post> expectedPosts = new ArrayList<>();
        when(session.createQuery(anyString(), eq(Post.class))).thenReturn(postQuery);
        when(postQuery.setParameter(anyString(), anyInt())).thenReturn(postQuery);
        when(postQuery.getResultList()).thenReturn(expectedPosts);

        List<Post> actualPosts = postDAO.getUserPosts(1);
        assertEquals(expectedPosts, actualPosts);
        verify(session).createQuery(anyString(), eq(Post.class));
        verify(postQuery).setParameter("userId", 1);
        verify(postQuery).getResultList();
    }

    @Test
     void testGetUserPostsException() {
        when(session.createQuery(anyString(), eq(Post.class))).thenReturn(postQuery);
        when(postQuery.setParameter(anyString(), anyInt())).thenThrow(RuntimeException.class);

        List<Post> actualPosts = postDAO.getUserPosts(1);
        assertTrue(actualPosts.isEmpty());
    }
}

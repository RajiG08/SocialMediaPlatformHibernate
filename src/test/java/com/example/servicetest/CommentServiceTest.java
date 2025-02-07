package com.example.servicetest;


import com.example.dao.CommentDAO;
import com.example.model.Comment;
import com.example.service.CommentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSaveComment() {
        // Create a sample comment
        Comment comment = new Comment();
        comment.setCommentId(1);  // Adjust as per your Comment model

        // Mock the behavior of commentDAO.saveComment()
        doNothing().when(commentDAO).saveComment(comment);

        // Call the service method
        commentService.saveComment(comment);

        // Verify that saveComment method in commentDAO was called exactly once with correct argument
        verify(commentDAO, times(1)).saveComment(comment);
    }
}

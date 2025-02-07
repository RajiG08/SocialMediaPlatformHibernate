package com.example.service;

import com.example.dao.BlockDAO;
import com.example.dao.CommentDAO;
import com.example.model.Block;
import com.example.model.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {

    
	@Autowired
    private CommentDAO commentDAO;

   

    
    @Transactional
    public void saveComment(Comment comment) {
         commentDAO.saveComment(comment);
    }
}

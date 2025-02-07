package com.example.service;

import com.example.dao.BlockDAO;
import com.example.model.Block;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BlockService {

    
	@Autowired
    private BlockDAO blockDAO;

   
    public boolean blockUser(int userId, int friendId) {
        return blockDAO.blockUser(userId, friendId);
    }

    
    public boolean unblockUser(int userId, int friendId) {
        return blockDAO.unblockUser(userId, friendId);
    }
}

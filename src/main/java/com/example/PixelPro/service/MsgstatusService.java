package com.example.PixelPro.service;

import com.example.PixelPro.repository.MsgstatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsgstatusService {
    @Autowired
    MsgstatusRepository msgstatusrepository;

    public int getUnreadCount(int cnum, int cpnum) {
        int count = msgstatusrepository.countUnread(cnum, cpnum);
        return count;
    }
}

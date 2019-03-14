package com.example.txmessage.service.impl;

import com.example.txmessage.dao.MessageDao;
import com.example.txmessage.entity.Message;
import com.example.txmessage.enums.MessageStatusEnum;
import com.example.txmessage.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {

    @Resource(name = "messageDao")
    private MessageDao messageDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String add(Message message) {
        return messageDao.save(message).getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(String id, MessageStatusEnum statusEnum) {

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setDead(String messageID) {

    }
}

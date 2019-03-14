package com.example.txmessage.service;


import com.example.txmessage.entity.Message;
import com.example.txmessage.enums.MessageStatusEnum;

/**
 * @author Administrator
 */
public interface MessageService {

    /**
     * 保存消息
     * @param message
     * @return
     */
    String add(Message message);

    void update(String id, MessageStatusEnum statusEnum);

    /**
     * 设置该消息死亡
     * @param messageID
     */
    void setDead(String messageID);
}

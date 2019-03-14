package com.example.txuser1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.txmessage.entity.Message;
import com.example.txmessage.service.MessageService;
import com.example.txuser1.dao.UserADao;
import com.example.txuser1.entity.UserA;
import com.example.txuser1.service.UserAService;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service(value = "userAService")
public class UserAServiceImpl implements UserAService {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserADao userADao;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String add(UserA userA) {

            UserA userA1 = userADao.save(userA);
            String userJson = JSONObject.toJSONString(userA);
            Message message = Message.builder().id(UUID.randomUUID().toString()).message(userJson).build();
            messageService.add(message);

            //消息持久化
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            org.springframework.amqp.core.Message messagemq = new org.springframework.amqp.core.Message(userJson.getBytes(),messageProperties);
            //收集发送者的信息，异步confirm 可以使用
            CorrelationData correlationData  = new CorrelationData();
            correlationData.setId(userA1.getId());

            //消息发送
            rabbitTemplate.convertAndSend("txDirectExchange","txx",messagemq,correlationData);
            return userA1.getId();
    }
}

package com.example.txuser2.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.txmessage.enums.MessageStatusEnum;
import com.example.txmessage.service.MessageService;
import com.example.txuser2.dao.UserBDao;
import com.example.txuser2.entity.UserB;
import com.example.txuser2.service.UserBService;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service(value = "userBService")
public class UserBServiceImpl implements UserBService {

    @Autowired
    private UserBDao userBDao;

    @Autowired
    private MessageService messageService;


    @Override
    public String add(UserB userB) {
        UserB save = userBDao.save(userB);
        return save.getId();
    }

    @RabbitListener(queues = "txQueue")
    @RabbitHandler
    public void saveUserBFromMq(Channel channel, Message message) throws IOException {
//        String messageId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        String messageID = JSONObject.parseObject(new String(message.getBody())).getString("id");
        try {
            channel.basicQos(1);
            byte[] messageBody = message.getBody();
            String messageJson = new String(messageBody);
            UserB userB = (UserB) JSONObject.parse(messageJson);
            //TODO 重复消息幂等处理
//            String add = this.add(userB);
            //更新消息表状态，已消费
//            messageService.update(messageID, MessageStatusEnum.USED);
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e) {
            //无论是否拒绝消息成功，
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
        }finally {
            //处理消息异常,拒绝接受消息，消息进入死信队列
//            messageService.setDead(messageID);
        }


    }
}

package com.example.txuser1.rabbitmq;

import com.alibaba.fastjson.JSONObject;
import com.example.txmessage.enums.MessageStatusEnum;
import com.example.txmessage.service.MessageService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 配置生产者消息确认
 * @author Administrator
 */
@Component
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    @Autowired
    private MessageService messageService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 消息是否到达exchange 回调
     * 可能由于数据库异常，更改数据库表状态失败，消费者幂等
     * 也可能由于网络原因无法收到 broker 下发的ack
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //获取生产者传递过来的消息ID
        String id = correlationData.getId();
        if (ack) {
            //当消息成功到达 exchange 时，可认为消息已经发送成功，设置消息为待消费 。这里的ack 机制 可能会由于网络因素无法获得
            //解决ack 由于网络问题导致无法收到：定时重发，消费者幂等
            messageService.update(id, MessageStatusEnum.WAIT_USE);
        }
    }

    /**
     * 消息由exchange 投递到 队列失败时，返回消息，回调
     * 这里如果数据库异常，就无法修改消息状态，就有可能导致 消息丢失
     * 方案1 ：异常处理，重发消息
     * 方案2 ： 将消息存放到内存集合中（单例），定时重发
     * 方案3 ：如果数据库发生错误短时间内无法修复，可以1和2 结合使用
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        //消息投递到queue 失败时，获取失败的消息
        //获取消息ID（指消息表中的ID）
//        String messageID = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        String messageID = JSONObject.parseObject(new String(message.getBody())).getString("id");

        try{
            //将消息设置为待发送
            messageService.update(messageID, MessageStatusEnum.WAIT_SEND);
        }catch (Exception e) {
            //如果更新消息状态发生错误，可以重发消息
            rabbitTemplate.convertAndSend("txDirectExchange","tx",message);
        }

    }
}

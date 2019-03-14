package com.example.txmessage.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tx_message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable{

    @Id
    private String id;

    /**
     * 消息
     */
    private String message;

    /**
     * 消息状态
     * 1 待发送
     * 2 待消费
     * 3 已消费
     */
    private int status;

    /**
     * 标记消息是否已经死亡
     */
    private boolean dead;

    /**
     * 重发次数
     */
    private int retry;

    /**
     * 生成者
     */
    private String producer;

    /**
     * 消费者
     */
    private String consumer;

    /**
     * 队列
     */
    private String queue;

    /**
     * 死信队列
     */
    private String deadQueue;

    /**
     * exchange
     */
    private String exchange;

    /**
     * 死信交换机
     */
    private String deadExchange;

    /**
     * 路由关键字
     */
    private String routingKey;

    /**
     * 死信队列路由关键字
     */
    private String deadRoutingKey;




}

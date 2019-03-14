package com.example.txuser1.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * rabbit mq 配置
 * @author Administrator
 */
@Configuration
public class RabbitmqConfig {



    /**
     * 死信队列
     * @return
     */
    @Bean
    public Queue deadQueue() {
        return new Queue("deadQueue");
    }


    /**
     * 创建直连交换机
     * @return
     */
    @Bean
    public DirectExchange deadtxDirectExchange() {
        return new DirectExchange("deadtxDirectExchange");
    }




    /**
     * 设置routing key 绑定exchange 和 queue
     * @param deadQueue
     * @param deadtxDirectExchange
     * @return
     */
    @Bean
    public Binding deadBinding(Queue deadQueue,
                               DirectExchange deadtxDirectExchange) {
        return BindingBuilder.bind(deadQueue).to(deadtxDirectExchange).with("deadQueue");
    }



    /**
     * 创建普通队列，存储tx 消息
     * @return
     */
    @Bean
    public Queue txQueue() {
        Map<String, Object> args = new ConcurrentHashMap<>();
        //设置死信交换机和死信路由
        args.put("x-dead-letter-exchange","deadtxDirectExchange");
        args.put("x-dead-letter-routing-key","deadQueue");
        return new Queue("txQueue",true,false,false, args);
    }

    @Bean
    public DirectExchange txDirectExchange() {
        return new DirectExchange("txDirectExchange");
    }

    @Bean
    public Binding txBinding(Queue txQueue,
                             DirectExchange txDirectExchange) {
        return BindingBuilder.bind(txQueue).to(txDirectExchange).with("tx");
    }
}

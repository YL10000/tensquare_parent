package com.tensquare.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout_queue_02")
public class FanoutConsumer02 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("fanout_queue_02收到消息："+msg);
    }
}

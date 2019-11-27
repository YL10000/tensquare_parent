package com.tensquare.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout_queue_01")
public class FanoutConsumer01 {

    @RabbitHandler
    public void getMsg(String msg){
        System.out.println("fanout_queue_01收到消息："+msg);
    }
}

package com.tensquare.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/*@Component
@RabbitListener(queues = "direct_mode_queue_01")*/
public class Consumer01 {

    @RabbitHandler()
    public void getMsg(String msg){
        System.out.println("直接模式消费消息："+msg);
    }
}

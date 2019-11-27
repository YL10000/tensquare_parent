package com.tensquare.rabbitmq;

import com.tensquare.rabbitmq.config.MQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {

    @RabbitListener(queues = MQConfig.direct_queue)
    public void getDirectMessage(String msg){
        System.out.println("直接模式接收消息："+msg);
    }

    @RabbitListener(queues = MQConfig.fanout_queue_01)
    public void getFanoutMsg01(String msg){
        System.out.println("分裂模式接收消息01："+msg);
    }

    @RabbitListener(queues = MQConfig.fanout_queue_02)
    public void getFanoutMsg02(String msg){
        System.out.println("分裂模式接收消息02："+msg);
    }

    @RabbitListener(queues = MQConfig.topic_queue_01)
    public void getTopicMsg01(String msg){
        System.out.println("主题模式接收消息01："+msg);
    }

    @RabbitListener(queues = MQConfig.topic_queue_02)
    public void getTopicMsg02(String msg){
        System.out.println("主题模式接收消息02："+msg);
    }

    //以字节数组进行接收消息
    @RabbitListener(queues = MQConfig.headers_queue_01)
    public void getHeadersMsg01(byte[] msg){
        System.out.println("headers模式接收消息01："+new String(msg));
    }

    //以字节数组进行接收消息
    @RabbitListener(queues = MQConfig.headers_queue_02)
    public void getHeadersMsg02(byte[] msg){
        System.out.println("headers模式接收消息02："+new String(msg));
    }
}

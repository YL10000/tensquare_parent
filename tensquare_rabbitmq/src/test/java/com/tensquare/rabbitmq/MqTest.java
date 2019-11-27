package com.tensquare.rabbitmq;

import com.tensquare.rabbitmq.config.MQConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
public class MqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMessage(){
        rabbitTemplate.convertAndSend(MQConfig.direct_queue,"直接模式测试");
    }

    //添加了routingKey也没有作用
    @Test
    public void sendFanoutMessage(){
        rabbitTemplate.convertAndSend(MQConfig.fanout_exchange,"","分裂模式发送消息");
    }

    @Test
    public void sendTopicMessage(){
        rabbitTemplate.convertAndSend(MQConfig.topic_exchange,"good.abc","主题模式发送消息good.abc");
        //主题模式接收消息01：主题模式发送消息good.abc

        rabbitTemplate.convertAndSend(MQConfig.topic_exchange,"good.log","主题模式发送消息good.log");
        //主题模式接收消息01：主题模式发送消息good.log
        //主题模式接收消息02：主题模式发送消息good.log

        rabbitTemplate.convertAndSend(MQConfig.topic_exchange,"abc.log","主题模式发送消息abc.log");
        //主题模式接收消息02：主题模式发送消息abc.log
    }

    @Test
    public void sendHeadersMessage(){
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setHeader("key1","value1");
        messageProperties.setHeader("key2","value2");
        System.out.println(messageProperties);
        Message message=new Message((messageProperties.getHeaders().toString()).getBytes(),messageProperties);
        rabbitTemplate.convertAndSend(MQConfig.headers_exchange,"",message);
    }
}

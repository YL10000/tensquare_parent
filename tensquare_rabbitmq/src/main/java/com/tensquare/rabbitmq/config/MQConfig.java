package com.tensquare.rabbitmq.config;
import	java.util.HashMap;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
public class MQConfig {

    //直接模式的消费队列
    public static final String direct_queue="direct_queue";

    //分裂模式的交换机
    public static final String fanout_exchange ="fanout_exchange";

    //分裂模式消费队列01
    public static final String fanout_queue_01="fanout_queue_01";

    //分裂模式消费队列02
    public static final String fanout_queue_02="fanout_queue_02";


    //主题模式的交换机
    public static final String topic_exchange ="topic_exchange";

    //主题模式消费队列01
    public static final String topic_queue_01="topic_queue_01";

    //主题模式消费队列01的routing key
    public static final String topic_queue_01_routing_key="good.#";

    //主题模式消费队列02
    public static final String topic_queue_02="topic_queue_02";

    //主题模式消费队列02的routing key
    public static final String topic_queue_02_routing_key="#.log";



    //headers模式的交换机
    public static final String headers_exchange ="headers_exchange";

    //headers模式消费队列01
    public static final String headers_queue_01="headers_queue_01";

    //headers模式消费队列02
    public static final String headers_queue_02="headers_queue_02";

    //配置直接模式的消息队列
    @Bean
    public Queue queue(){
        return new Queue(MQConfig.direct_queue,true);
    }

    //配置分裂模式的交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return  new FanoutExchange(MQConfig.fanout_exchange,true,false);
    }

    //配置分裂模式消息队列01
    @Bean
    public Queue fanoutQueue01(){
        return new Queue(MQConfig.fanout_queue_01,true);
    }

    //配置分裂模式消息队列02
    @Bean
    public Queue fanoutQueue02(){
        return new Queue(MQConfig.fanout_queue_02,true);
    }

    //将分裂模式的消费队列01绑定到分裂模式的交换机上
    @Bean
    public Binding fanoutBind01(){
        return BindingBuilder.bind(fanoutQueue01()).to(fanoutExchange());
    }

    //将分裂模式的消费队列02绑定到分裂模式的交换机上
    @Bean
    public Binding fanoutBind02(){
        return BindingBuilder.bind(fanoutQueue02()).to(fanoutExchange());
    }


    //配置主题模式的交换机
    @Bean
    public TopicExchange topicExchange() {
        return  new TopicExchange(MQConfig.topic_exchange,true,false);
    }

    //配置主题模式消息队列01
    @Bean
    public Queue topicQueue01(){
        return new Queue(MQConfig.topic_queue_01,true);
    }

    //配置主题模式消息队列02
    @Bean
    public Queue topicQueue02(){
        return new Queue(MQConfig.topic_queue_02,true);
    }

    //将主题模式的消费队列01绑定到主题模式的交换机上
    @Bean
    public Binding topicBind01(){
        return BindingBuilder.bind(topicQueue01()).to(topicExchange()).with(MQConfig.topic_queue_01_routing_key);
    }

    //将主题模式的消费队列02绑定到主题模式的交换机上
    @Bean
    public Binding topicBind02(){
        return BindingBuilder.bind(topicQueue02()).to(topicExchange()).with(MQConfig.topic_queue_02_routing_key);
    }



    //配置headers模式的交换机
    @Bean
    public HeadersExchange headersExchange() {
        return  new HeadersExchange(MQConfig.headers_exchange,true,false);
    }

    //配置headers模式消息队列01
    @Bean
    public Queue headersQueue01(){
        return new Queue(MQConfig.headers_queue_01,true);
    }

    //配置headers模式消息队列02
    @Bean
    public Queue headersQueue02(){
        return new Queue(MQConfig.headers_queue_02,true);
    }

    //将headers模式的消费队列01绑定到headers模式的交换机上
    @Bean
    public Binding headersBind01(){
        return BindingBuilder.bind(headersQueue01()).to(headersExchange()).whereAll(MQConfig.headers).match();
    }

    //将headers模式的消费队列02绑定到headers模式的交换机上
    @Bean
    public Binding headersBind02(){
        return BindingBuilder.bind(headersQueue02()).to(headersExchange()).whereAny(MQConfig.headers).match();
    }

    public static Map<String,Object> headers=new HashMap<String,Object>() {{
        put("key1","value1");
        put("key2","value2");
    }};
}

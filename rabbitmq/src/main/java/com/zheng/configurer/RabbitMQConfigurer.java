package com.zheng.configurer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhenghui
 * @date 2018/4/11
 * 描述：配置队列
 * Queue 队列
 * Exchange 交换机
 */
@Configuration
public class RabbitMQConfigurer {
    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.port}")
    private int port;
    @Value("${spring.rabbitmq.username}")
    private String userName;
    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host, port);
        cachingConnectionFactory.setUsername(userName);
        cachingConnectionFactory.setPassword(password);
        cachingConnectionFactory.setVirtualHost("/");
        cachingConnectionFactory.setPublisherConfirms(true);
        return cachingConnectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        return rabbitTemplate;
    }

    //取代directQueue
    @Bean
    public Queue queue() {
        return new Queue("hello");
    }

    //配置默认的交换机模式
    @Bean
    public Queue directQueue() {
        return new Queue("direct");
    }

    @Bean
    public Queue topicQueue() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topic.message.*");
    }

    @Bean
    public Queue topicQueue3() {
        return new Queue("#topic.message");
    }

    @Bean
    public Queue fanoutQueue() {
        return new Queue("fanout");
    }

    //直连交换机：完全匹配
    @Bean
    public DirectExchange directExchange() {

        return new DirectExchange("directExchange");
    }

    //主题交换机：通配符匹配
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    //广播交换机：速度最快，广播消息
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    //绑定直连交换机 一般用queue()替代
    @Bean
    public Binding bindingDirectExchange(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }

    //绑定主题交换机
    @Bean
    public Binding bindingTopicExchange(Queue topicQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(topicQueue).to(topicExchange).with("topic.message");
    }

    //绑定广播交换机
    @Bean
    public Binding bindingFanoutExchange(Queue fanoutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanoutQueue).to(fanoutExchange);
    }


    /**
     * 接受消息的监听，这个监听会接受消息队列的消息
     * 针对消费者配置
     *
     * @return
     */
    @Bean
    @Autowired
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue());
        container.setQueues(topicQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("手动收到消息 : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
            }
        });
        return container;
    }
}

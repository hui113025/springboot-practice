package zheng;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhenghui on 2018/1/29.
 */
public class TestExample extends Tester{
    private final Logger logger = Logger.getLogger(getClass());
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendHello() {

        String context = "此消息在，默认的交换机模式队列下，有 helloReceiver 可以收到";

        String routeKey = "hello";

        context = "routeKey:" + routeKey + ",context:" + context;

        logger.info("sendHello : " + context);

        rabbitTemplate.convertAndSend(routeKey, context);
    }

    @Test
    public void sendDirect(){
        String context = "此消息在，默认的交换机模式队列下，有 DirectReceiver 可以收到";

        String routeKey = "direct";

        String exchange = "directExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        logger.info("sendDirect : " + context);

        // 推荐使用 sendHello（） 方法写法，这种方式在 Direct Exchange 多此一举，没必要这样写
        rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }

    @Test
    public void sendTopic(){
        String context = "此消息在，配置转发消息模式队列下， 有 TopicReceiver可以收到";

        String routeKey = "topic.message";

        String exchange = "topicExchange";

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        logger.info("sendTopic : " + context);

        rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }

    @Test
    public void sendFanout(){
        String context = "此消息在，广播模式或者订阅模式队列下，有 FanoutReceiver 可以收到";

        String routeKey = "fanout";

        String exchange = "fanoutExchange";

        logger.info("sendFanout : " + context);

        context = "context:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
}

package com.zheng.receiver;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by zhenghui on 2018/11/2.
 * 直接交换机 监听队列
 */
@Component
@RabbitListener(queues = "direct")
public class DirectReceiver {
    private final Logger logger = Logger.getLogger(getClass());

    @RabbitHandler
    public void process(String message) {
        logger.info("接收者 DirectReceiver," + message);
    }

}

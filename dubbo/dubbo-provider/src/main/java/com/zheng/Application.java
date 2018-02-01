package com.zheng;


import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

import java.util.concurrent.CountDownLatch;

/**
 * Created by  on 2017/6/13.
 */
@SpringBootApplication
@ImportResource({"classpath:dubbo.xml"})
public class Application extends SpringBootServletInitializer {
    private static final Logger logger = Logger.getLogger(Application.class);

    //并发 计数器 计数到达零之前，await 方法会一直受阻塞
    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        logger.info("项目启动!");
        CountDownLatch closeLatch = ctx.getBean(CountDownLatch.class);
        closeLatch.await();
    }
}

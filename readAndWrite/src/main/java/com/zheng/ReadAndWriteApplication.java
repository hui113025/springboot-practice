package com.zheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@Configuration
@EnableDiscoveryClient
@ImportResource("classpath:/spring/spring-core.xml")
public class ReadAndWriteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadAndWriteApplication.class, args);
    }

}

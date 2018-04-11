package com.zheng.configurer;

import com.zheng.service.HelloService;
import com.zheng.service.HelloServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.xml.ws.Endpoint;

/**
 * Created by zhenghui on 2018/4/11.
 */
@Configuration
public class CxfConfigurer {
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new CXFServlet(), "/soap/*");
        bean.setLoadOnStartup(0);
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }

    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    @Bean
    public HelloService hellowService() {
        return new HelloServiceImpl();
    }

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), hellowService());
        endpoint.publish("/hello");
        return endpoint;
    }
}

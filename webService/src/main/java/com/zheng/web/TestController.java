package com.zheng.web;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 李恒名 on 2017/7/21.
 *
 * 运维要求
 */
@Controller
public class TestController {
    private final Logger logger = LoggerFactory.getLogger(TestController.class);


    @ResponseBody
    @RequestMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        response.getWriter().write("ok");
    }


    @ResponseBody
    @RequestMapping("/testSoap")
    public void test22(HttpServletResponse response) throws IOException {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient("http://localhost:8080/soap/hello?wsdl");
        Object[] objects = new Object[0];
        try {
            objects = client.invoke("sayHi", "zhang");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //输出调用结果
        System.out.println(objects[0].getClass());
        System.out.println(objects[0].toString());
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.getWriter().write(objects[0].toString());
    }

}

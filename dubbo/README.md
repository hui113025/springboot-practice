# ##SpringBoot + Dubbo
## 本地安装zookeeper，下载地址：https://mirrors.tuna.tsinghua.edu.cn/apache/zookeeper/?C=N;O=A
## 访问 http://localhost:82/dubbo/add?a=1&b=2
## consumer(消费者)、provider(服务者) 都需映入api包
## api提供服务的接口 作为桥梁
## dubbo-provider 配置注册中心服务地址 配置dubbo协议 声明需要暴露的服务接口（demoService），并把具体服务注入bean
## dubbo-consumer 配置注册中心服务地址 引用服务（DemoService）
## dubbo监控中心 下载dubbo-admin-2.5.4.war 访问地址：http://localhost:8080/ 默认账号：root/root

参考：
    阿里dubbo项目地址： https://github.com/alibaba/dubbo
    ZooKeeper官网为：http://zookeeper.apache.org/
    Dubbo官网为：http://dubbo.io/
    Zookeeper在线API速查：http://zookeeper.apache.org/doc/r3.4.6/api/index.html
    Zookeeper学习：http://www.cnblogs.com/sunddenly/category/620563.html
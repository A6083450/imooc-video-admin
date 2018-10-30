package com.imooc.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * zookeeper 放入spring容器, 项目启动加载的时候就建立和zk的连接
 */
@Component
public class ZookeeperConfig {

    private Logger log = LoggerFactory.getLogger(ZKCurator.class);

    @Autowired
    @Qualifier("retryPolicy")
    private RetryPolicy retry;

    //创建重连策略
    @Bean("retryPolicy")
    public RetryPolicy retryPolicy(){
        //创建重连策略类;第一个参数的意思是: 每次重连的等待时间(单位: 毫秒), 第二个参数的意思是: 重连的次数
        RetryPolicy retry = new ExponentialBackoffRetry(1000,5);
        return retry;
    }

    //创建zookeeper客户端
    @Bean
    public CuratorFramework curatorFramework(){
        // 连接zookeeper;
        // 第一个参数意思是: 连接的服务器地址加端口号,如果要连接多个服务器则以逗号隔开
        // 第二个参数意思是: 会话连接超时时间(单位:毫秒)
        // 第三个参数意思是: 服务器连接超时时间(单位:毫秒)
        /*第一种连接方式
        CuratorFramework client = CuratorFrameworkFactory.newClient("192.168.56.105:2181",
                10000, 10000, retry);
        client.start();
        client.usingNamespace("admin");*/
        //第二种连接方式
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("192.168.56.105:2181")
                .sessionTimeoutMs(10000).connectionTimeoutMs(10000).retryPolicy(retry).namespace("admin").build();
        return client;
    }

    @Bean(initMethod = "init")
    public ZKCurator zkCurator(){
        return new ZKCurator();
    }
}

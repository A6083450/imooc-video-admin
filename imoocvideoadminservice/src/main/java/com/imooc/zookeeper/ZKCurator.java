package com.imooc.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author erpljq
 * @date 2018/10/11
 */
public class ZKCurator {

    //zk客户端
    @Autowired
    private CuratorFramework client;
    private Logger log = LoggerFactory.getLogger(ZKCurator.class);

    public void init(){
        client.start();
        //判断在admin命名空间下是否有bgm节点 如: /admin/bgm
        try {
            if (client.checkExists().forPath("/bgm") == null){
                //没有这个节点就创建create();
                // creatingParentsIfNeeded()表示递归创建,不管多级文件夹还是单个文件夹
                /**
                 * withMode() 节点的类型, CreateMode.PERSISTENT=持久的
                 * 对于zk来讲,有两种类型的节点:
                 * 持久节点: 当你创建一个节点的时候, 这个节点就永远存在, 除非你手动删除
                 * 临时节点: 你创建一个节点之后, 会话断开、服务器暂停等,会自动删除,当然也可以手动删除
                 */
                // withACL() 默认的权限
                client.create()  //创建节点
                        .creatingParentsIfNeeded()      //递归创建,不管多级文件夹还是单个文件夹
                        .withMode(CreateMode.PERSISTENT)    //节点类型: 持久节点
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)  // acl: 匿名权限
                        .forPath("/bgm");      //操作哪个节点
                log.info("zookeeper初始化成功...");
                //log.info("zookeeper服务器状态: {}", client.isStarted());
            }
        } catch (Exception e) {
            log.error("zookeeper客户端连接, 初始化错误...");
            e.printStackTrace();
        }
    }

    /**
     * 增加或者删除bgm, 向zk-server创建子节点, 供小程序后端监听
     * @param bgmId
     * @param operObj
     */
    public void sendBgmOperator(String bgmId, String operObj){
        try {
            client.create()  //创建节点
                    .creatingParentsIfNeeded()      //递归创建,不管多个节点还是单个节点
                    .withMode(CreateMode.PERSISTENT)    //节点类型: 持久节点
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)  // acl: 匿名权限
                    .forPath("/bgm/" + bgmId, operObj.getBytes());      //创建节点路径, 并且加值
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

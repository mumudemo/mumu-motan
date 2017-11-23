package com.lovecws.mumu.rpc.motan.service.impl;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MotanZookeeperServer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring-motan-zookeeper.xml");
        context.start();
        System.out.println("rpc zookeeper server starting.....");
        //心跳检测
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        while (true){
            Thread.yield();
        }
    }
}

package com.lovecws.mumu.rpc.motan.service.impl;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MotanLocalServer {

    public static void main(String[] args){
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring-motan-local.xml");
        context.start();
        System.out.println("rpc local server starting.....");
        while (true){
            Thread.yield();
        }
    }
}

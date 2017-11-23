package com.lovecws.mumu.rpc.motan.client;

import com.lovecws.mumu.rpc.motan.service.FooService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MotanLocalClient {

    public static void main(String[] args){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-motan.xml");
        FooService service = (FooService) ctx.getBean(FooService.class);
        for (int i=0;i<10000;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+service.hello("motan"));
                }
            }).start();
        }
    }
}
package com.lovecws.mumu.rpc.motan.client;

import com.lovecws.mumu.rpc.motan.service.FooService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MotanBenchmark {

    @Test
    public void testEmptyString(){
        run(100,10000,"");
    }
    @Test
    public void testString(){
        run(100,1000,"sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssaaaaaaaaaaaaaaaaaaaaaaaaaaaawwwwwwwwwwwwwwwwwwwwwwweeeeeeeeeeee");
    }
    public void run(int clientCount,int connectCount,String message){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring-motan.xml");
        FooService service = (FooService) ctx.getBean(FooService.class);

        CountDownLatch countDownLatch=new CountDownLatch(clientCount);
        CyclicBarrier cyclicBarrier=new CyclicBarrier(connectCount, new Runnable() {
            @Override
            public void run() {
                System.out.println("开始执行....");
            }
        });

        long startTime=System.currentTimeMillis();
        for (int i=0;i<clientCount;i++){
            new MotanThread(countDownLatch,cyclicBarrier,service,message).start();
        }
        try {
            countDownLatch.await();
            long endTime=System.currentTimeMillis();
            System.out.println("消费时间:"+(endTime-startTime)+"ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

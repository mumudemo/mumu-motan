package com.lovecws.mumu.rpc.motan.client;

import com.lovecws.mumu.rpc.motan.service.FooService;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MotanThread extends Thread{

    private CountDownLatch countDownLatch;
    private CyclicBarrier cyclicBarrier;
    private FooService fooService;
    private String message;

    public MotanThread(CountDownLatch countDownLatch, CyclicBarrier cyclicBarrier,FooService fooService, String message){
        this.countDownLatch=countDownLatch;
        this.cyclicBarrier=cyclicBarrier;
        this.fooService=fooService;
        this.message=message;
    }

    @Override
    public void run() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        String hello = fooService.hello(message);
        System.out.println(hello);
        countDownLatch.countDown();
    }
}

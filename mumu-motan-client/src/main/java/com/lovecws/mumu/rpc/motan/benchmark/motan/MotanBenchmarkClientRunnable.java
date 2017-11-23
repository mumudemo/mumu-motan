package com.lovecws.mumu.rpc.motan.benchmark.motan;

import com.lovecws.mumu.rpc.motan.benchmark.client.AbstractBenchmarkClientRunnable;
import com.lovecws.mumu.rpc.motan.benchmark.service.BenchmarkService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MotanBenchmarkClientRunnable extends AbstractBenchmarkClientRunnable {

    public MotanBenchmarkClientRunnable(BenchmarkService benchmarkService, String params, CyclicBarrier barrier, CountDownLatch latch, long startTime, long endTime) {
        super(benchmarkService, barrier, latch, startTime, endTime);
    }

    private static String SEND_MESSAGE_EMPTY="";//空字符串
    private static String SEND_MESSAGE_1k=null;//1k字符串
    private static String SEND_MESSAGE_5k=null;//5k字符串
    static {
        StringBuilder builder_5k=new StringBuilder();
        while (builder_5k.toString().getBytes().length<1024*5){
            builder_5k.append("lovecws");
        }
        SEND_MESSAGE_5k=builder_5k.toString();

        StringBuilder builder_1k=new StringBuilder();
        while (builder_1k.toString().getBytes().length<1024){
            builder_1k.append("lovecws");
        }
        SEND_MESSAGE_1k=builder_1k.toString();
    }
    @Override
    protected Object call(BenchmarkService benchmarkService) {
        try {
            String hello = ((MotanBenchmarkServiceImpl) benchmarkService).getFooService().hello(SEND_MESSAGE_EMPTY);
            return hello;
            //return benchmarkService.execute("hello",new Class[]{String.class},new Object[]{"lovecws"});
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}

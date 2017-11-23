/*
 *  Copyright 2009-2016 Weibo, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.lovecws.mumu.rpc.motan.benchmark.motan;

import com.lovecws.mumu.rpc.motan.benchmark.client.AbstractBenchmarkClient;
import com.lovecws.mumu.rpc.motan.benchmark.client.ClientRunnable;
import com.lovecws.mumu.rpc.motan.benchmark.service.BenchmarkService;
import com.lovecws.mumu.rpc.motan.service.FooService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class MotanBenchmarkClient extends AbstractBenchmarkClient {

    static Properties properties = new Properties();
    /**
     * 并发的Runable线程，是否使用相同的client进行调用。
     * true：并发线程只使用一个client（bean实例）调用服务。
     * false: 每个并发线程使用不同的Client调用服务
     */
    private static BenchmarkService benchmarkService;
    private static boolean isMultiClient;

    public static void main(String[] args) {
        int concurrents = 100;
        int runtime = 60;//运行时间必须要大于预热时间 默认预热时间为30秒
        if(runtime<AbstractBenchmarkClient.WARMUPTIME){
            throw new IllegalArgumentException("总运行时间不能小于程序预热时间【30s】");
        }

        String classname = MotanBenchmarkClientRunnable.class.getName();
        String params = null;
        isMultiClient = false;

        ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-motan-local.xml");
        FooService fooService = applicationContext.getBean(FooService.class);
        benchmarkService=new MotanBenchmarkServiceImpl(fooService);

        new MotanBenchmarkClient().start(concurrents, runtime, classname, params);
    }

    @Override
    public ClientRunnable getClientRunnable(String classname, String params, CyclicBarrier barrier,
                                            CountDownLatch latch, long startTime, long endTime) {
        BenchmarkService service=null;
        if (isMultiClient) {
            //service=new MotanBenchmarkServiceImpl();
        } else {
            service = benchmarkService;
        }

        Class[] parameterTypes = new Class[]{BenchmarkService.class, String.class, CyclicBarrier.class,
                CountDownLatch.class, long.class, long.class};
        Object[] parameters = new Object[]{service, params, barrier, latch, startTime, endTime};

        ClientRunnable clientRunnable = null;
        try {
            clientRunnable = (ClientRunnable) (Class.forName(classname).getConstructor(parameterTypes).newInstance(parameters));
        } catch (InstantiationException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException |InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return clientRunnable;
    }
}

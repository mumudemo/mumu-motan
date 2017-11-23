package com.lovecws.mumu.rpc.motan.benchmark.motan;

import com.lovecws.mumu.rpc.motan.benchmark.service.BenchmarkService;
import com.lovecws.mumu.rpc.motan.service.FooService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MotanBenchmarkServiceImpl implements BenchmarkService{

    private FooService fooService;

    public MotanBenchmarkServiceImpl(FooService fooService){
        this.fooService=fooService;
    }

    public Object execute(String name, Class[] parameterTypes,Object[] parameters){
        try {
            Method method = fooService.getClass().getMethod(name, parameterTypes);
            method.setAccessible(true);
            return method.invoke(fooService,parameters);
        } catch (NoSuchMethodException|IllegalAccessException|InvocationTargetException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public FooService getFooService() {
        return fooService;
    }

    public void setFooService(FooService fooService) {
        this.fooService = fooService;
    }
}

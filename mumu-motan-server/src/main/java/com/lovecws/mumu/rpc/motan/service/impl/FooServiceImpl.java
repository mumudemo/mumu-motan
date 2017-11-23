package com.lovecws.mumu.rpc.motan.service.impl;

import com.lovecws.mumu.rpc.motan.service.FooService;

public class FooServiceImpl implements FooService{
    @Override
    public String hello(String name) {
        System.out.println(name + " invoked rpc service");
        return "hello " + name;
    }
}

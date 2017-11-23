package com.lovecws.mumu.rpc.motan.service.impl;

import com.lovecws.mumu.rpc.motan.service.HelloWorldService;

public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String print(String name) {
        System.out.println(name + " invoked rpc service [helloworld]");
        return name;
    }
}

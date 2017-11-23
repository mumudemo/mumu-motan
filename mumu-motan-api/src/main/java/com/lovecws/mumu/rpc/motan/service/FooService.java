package com.lovecws.mumu.rpc.motan.service;

import com.weibo.api.motan.transport.async.MotanAsync;

@MotanAsync
public interface FooService {
    public String hello(String name);
}

package com.lovecws.mumu.rpc.motan.benchmark.service;

public interface BenchmarkService {

    public Object execute(String name, Class[] parameterTypes,Object[] parameters);
}

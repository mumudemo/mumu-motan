package com.lovecws.mumu.rpc.motan.client;

import com.lovecws.mumu.rpc.motan.serialize.JDKSerialization;
import com.lovecws.mumu.rpc.motan.service.FooService;
import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RefererConfig;
import com.weibo.api.motan.config.RegistryConfig;

public class MotanSimpleClient {

    public static void main(String[] args){
        RefererConfig<FooService> refererConfig=new RefererConfig<FooService>();

        //protocol
        ProtocolConfig protocolConfig=new ProtocolConfig();
        protocolConfig.setId("motan");
        protocolConfig.setName("motan");

        //ExtensionLoader<JDKSerialization> extensionLoader = ExtensionLoader.initExtensionLoader(JDKSerialization.class);
        //extensionLoader.addExtensionClass(JDKSerialization.class);

        //protocolConfig.setSerialization("jdk");
        refererConfig.setProtocol(protocolConfig);

        //registry
        RegistryConfig registryConfig=new RegistryConfig();
        registryConfig.setCheck("false");
        registryConfig.setRegProtocol("local");
        refererConfig.setRegistry(registryConfig);

        refererConfig.setInterface(FooService.class);
        refererConfig.setRequestTimeout(2000);
        refererConfig.setDirectUrl("localhost:8002");
        refererConfig.setGroup("default_rpc");
        refererConfig.setModule("mumu-rpc-motan123");
        FooService fooService = refererConfig.getRef();
        String simple_motan_service = fooService.hello("simple motan service");
        System.out.println(simple_motan_service);
        refererConfig.destroy();
        System.exit(1);
    }
}

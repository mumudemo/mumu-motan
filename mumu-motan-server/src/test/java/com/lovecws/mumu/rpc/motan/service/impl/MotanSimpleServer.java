package com.lovecws.mumu.rpc.motan.service.impl;

import com.lovecws.mumu.rpc.motan.serialize.JDKSerialization;
import com.lovecws.mumu.rpc.motan.service.FooService;
import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RegistryConfig;
import com.weibo.api.motan.config.ServiceConfig;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.weibo.api.motan.core.extension.ExtensionLoader;
import com.weibo.api.motan.util.MotanSwitcherUtil;

public class MotanSimpleServer {

    public static void main(String[] args){
        ServiceConfig<FooService> serviceConfig=new ServiceConfig<FooService>();
        //注册中心
        RegistryConfig registryConfig=new RegistryConfig();
        registryConfig.setRegProtocol("local");
        registryConfig.setCheck("false");
        serviceConfig.setRegistry(registryConfig);

        //rpc协议
        ProtocolConfig protocol = new ProtocolConfig();
        protocol.setId("motan");
        protocol.setName("motan");
        //ExtensionLoader<JDKSerialization> extensionLoader = ExtensionLoader.initExtensionLoader(JDKSerialization.class);
        //extensionLoader.addExtensionClass(JDKSerialization.class);
        //System.out.println(extensionLoader);

        //protocol.setSerialization("jdk");
        serviceConfig.setProtocol(protocol);

        //server config
        serviceConfig.setInterface(FooService.class);
        serviceConfig.setRef(new FooServiceImpl());
        serviceConfig.setExport("motan:8002");
        serviceConfig.setGroup("default_rpc");
        serviceConfig.setModule("mumu-rpc-motan");

        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);
        serviceConfig.export();
        System.out.println("server starting....");
    }
}

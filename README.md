# mumu-rpc-motan
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://github.com/babymm/mumu-motan/blob/master/LICENSE) [![Maven Central](https://img.shields.io/maven-central/v/com.weibo/motan.svg?label=Maven%20Central)](https://mvnrepository.com/search?q=motan) [![Build Status](https://img.shields.io/travis/weibocom/motan/master.svg?label=Build)](https://github.com/babymm/mumu-motan) [![OpenTracing-1.0 Badge](https://img.shields.io/badge/OpenTracing--1.0-enabled-blue.svg)](http://opentracing.io)
>[mumu-motan](https://github.com/babymm/mumu-motan)是一个以[weibo montan](https://github.com/weibocom/motan)为基础的测试程序，了解motan rpc架构设计和编程思想。同时也是想要多了解一些rpc框架，为项目做好rpc技术选型的准备。

# 项目简述
> 周五夜晚登陆[github](https://github.com)闲来无事，偶然看到有个项目使用到了motan rpc框架，碰巧自己也一直在寻找一款比较适合本公司的rpc框架，所以抱着试试的心态，查看了一些相关的文档，不查不知道，一查吓一跳，motan尽然是新浪微博开源出来的一款内部使用的rpc调用框架，文中详细的描述了motan怎么好，怎么性能牛叉的，所以跃跃欲试的研究了几天，所以才有了这个测试项目。
> 

# 项目架构
> mumu-motan项目一共分为三个模块，接口模板、服务端模块、客户端模块。
> 1. 接口模块主要定义一些接口，将接口单独抽取出来成为一个模快，主要是为了rpc调用。接口包括普通接口和异步调用接口，异步调用接口也就是在接口上添加@MotanAsync注解，然后在项目的pom中配置build-helper-maven-plugin插件，使用该插件生成异步调用的接口。至于具体的业务逻辑，请查看源码。
> 2. 服务端模块包含各种方式开启rpc服务。
   > - 无注册中心模式，仅仅包含一些必要的配置信息，其他的全是使用motan默认值。
   > - consul注册中心模式，以consul为注册中心，将服务注册到consul注册中心，然后客户端订阅注册中心。
   > - zookeeper注册中心模式,将服务注册到zookeeper注册中心，然后客户端订阅zookeeper注册中心，从而获取服务。
   > - 手动模式，手动编写代码开启服务，首先生成ServerConfig对象，然后初始化注册中心RegistryConfig（无注册中心使用local模式），然后生成rpc协议ProtocolConfig,最后将服务export暴露出来，供客户端调用。
> 3. 客户端模块主要实现rpc方法的调用。其中无注册中心模式使用redirectUrl来指定服务器的地址和端口号，consul注册中心模式和zookeeper注册中心模式不必需要知道服务的地址信息，只需要知道注册中心即可。至于到底使用哪个服务来调用，这个可以通过在客户端配置负载均衡来配置。

# motan架构
Motan中分为服务提供方(RPC Server)，服务调用方(RPC Client)和服务注册中心(Registry)三个角色。
- Server提供服务，向Registry注册自身服务，并向注册中心定期发送心跳汇报状态；
- Client使用服务，需要向注册中心订阅RPC服务，Client根据Registry返回的服务列表，与具体的Sever建立连接，并进行RPC调用。
- 当Server发生变更时，Registry会同步变更，Client感知后会对本地的服务列表作相应调整。

三者的交互关系如下图：

![motan关系图](https://github.com/weibocom/motan/wiki/media/14612349319195.jpg)

Motan框架中主要有register、transport、serialize、protocol几个功能模块，各个功能模块都支持通过SPI进行扩展，各模块的交互如下图所示：

![模块介绍图](https://github.com/weibocom/motan/wiki/media/14612352579675.jpg)

**摘录自[weibo motan](https://github.com/weibocom/motan/wiki/zh_userguide#%E5%9F%BA%E6%9C%AC%E4%BB%8B%E7%BB%8D)**

# 测试
Server测试结果：

请求空包：单Server极限TPS：18W

请求1KString：单Server极限TPS：8.4W

请求5KString：单Server极限TPS：2W

Client测试结果：

对比图：

![测试性能对比统计图](https://github.com/weibocom/motan/wiki/media/14614085719511.jpg)

# 个人观点
## 优点
   - motan轻量，代码逻辑简单，集成耦合度低，可以无缝的接入到项目中去，而不必对项目做其他的改动。
   - 客户端支持集群、负载均衡（activeWeight、configurableWeight、consistent、localFirst、random、roundrobin），避免单机宕机之后，服务不可用的问题。
   - rpc异常处理的比较好，当服务端调用发生异常的时候，会在客户端进行抛出，方便客户端进行bug追踪。
## 缺点
   - 异步调用集成方式比较麻烦，必须要使用maven的build-helper-maven-plugin插件来自动生成异步调用的接口。


 > 以上观点纯属个人看法，如有不同，欢迎指正。

 > 联系方式

 > email:<babymm@aliyun.com>

 > github:[https://github.com/babymm](https://github.com/babymm)
 > 

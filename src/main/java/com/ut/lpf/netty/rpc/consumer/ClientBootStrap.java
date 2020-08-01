package com.ut.lpf.netty.rpc.consumer;

import com.ut.lpf.netty.rpc.netty.NettyClient;
import com.ut.lpf.netty.rpc.publicInter.HelloService;

public class ClientBootStrap {
    private  static final  String providerName="hello#";
    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
       HelloService service = (HelloService) nettyClient.getBean(HelloService.class, providerName);
        String res = service.hello("我是liqiqi");
        System.out.println("服务器调用的结果res="+res);
    }
}

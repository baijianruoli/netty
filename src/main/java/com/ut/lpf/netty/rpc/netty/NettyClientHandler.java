package com.ut.lpf.netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

import java.util.concurrent.Callable;
@Data
public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String pars;
    //被代理对象调用，发送数据给服务器，wait-》等待唤醒-》返回结果
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(1);
        context=ctx;
        if(context==null)
            System.out.println("!");
        else
            System.out.println("?");
    }
    @Override
    public  synchronized Object call() throws Exception {
        System.out.println(2);
        if(context==null)
            System.out.println("!");
        else
            System.out.println("?");
        context.writeAndFlush(pars);
        wait();
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }
    //notify唤醒等待的线程
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       result=msg.toString();
       notify();
    }


}

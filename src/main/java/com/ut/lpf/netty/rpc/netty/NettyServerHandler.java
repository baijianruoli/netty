package com.ut.lpf.netty.rpc.netty;

import com.ut.lpf.netty.rpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg="+msg);
        //自定义一个协议
        if(msg.toString().startsWith("hello"))
        {
           String result= new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
           ctx.writeAndFlush(result);
        }

    }
}

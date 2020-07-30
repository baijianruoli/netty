package com.ut.lpf.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private static int count=0;


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        byte[] content = messageProtocol.getContent();
        System.out.println("服务器接收信息如下"+new String(content,Charset.forName("utf-8")));
        System.out.println(++count);
        String response=UUID.randomUUID().toString();
        int relen=response.getBytes("utf-8").length;
        MessageProtocol res = new MessageProtocol();
        res.setLen(relen);
        res.setContent(response.getBytes("utf-8"));
        channelHandlerContext.writeAndFlush(res);
    }
}

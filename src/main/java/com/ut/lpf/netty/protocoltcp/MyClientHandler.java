package com.ut.lpf.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count=0;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for(int i=0;i<50;i++)
        {
           String msg="吃火锅，嘻嘻";
            byte[] bytes = msg.getBytes(Charset.forName("utf-8"));

            int length=msg.getBytes(Charset.forName("utf-8")).length;
            System.out.println(bytes.length+" --- "+length);
            MessageProtocol messageProtocol = new MessageProtocol();
            messageProtocol.setLen(length);
            messageProtocol.setContent(bytes);
            ctx.writeAndFlush(messageProtocol);

        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageProtocol messageProtocol) throws Exception {
        int len = messageProtocol.getLen();
        System.out.println(new String(messageProtocol.getContent()));
    }
}

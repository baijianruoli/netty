package com.ut.lpf.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

//自定义Handler 要继承netty
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //编码
        ctx.channel().eventLoop().execute(new Thread(()->{
            try{
                Thread.sleep(10*1000);
                ctx.writeAndFlush(Unpooled.copiedBuffer("我被阻塞了",CharsetUtil.UTF_8));
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }));
       ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端",CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       ctx.close();
    }

    /**
     *
     * @param ctx 上下文对象爆红管道 pipeline 通道channel 地址
     * @param msg  客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx ="+ctx);
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息是："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址"+ctx.channel().remoteAddress());
    }
}

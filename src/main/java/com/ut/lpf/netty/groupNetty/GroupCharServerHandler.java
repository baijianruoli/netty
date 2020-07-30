package com.ut.lpf.netty.groupNetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupCharServerHandler extends SimpleChannelInboundHandler<String> {
    public static Map<String,Channel> channelMap = new HashMap<>();
    public static List<Channel> channelList=new ArrayList<>();
     //定义一个channel组，关了所有的channel
    //GlobalEventExecutro.INSTANCE 全局事件执行器，单例
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
                 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel channel = channelHandlerContext.channel();
         if(s.contains(":"))
         {
             String[] split = s.split(":");
             Channel channel1 = channelMap.get(split[0]);
             channel1.writeAndFlush("[客户端]"+channel.remoteAddress()+" 发送了私聊消息:"+s);
         }
         else
         {
             channelGroup.forEach(item->{
                 if(channel!=item)
                 {
                     item.writeAndFlush("[客户端]"+channel.remoteAddress()+" 发送了消息:"+s+"\n");
                 }
                 else
                 {
                     item.writeAndFlush("[自己]发送了消息"+s+"\n");
                 }
             });
         }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 离开");
        System.out.println("当前channelGroup size"+ channelGroup.size());
    }

    //channle处于非活动状态，提示xx下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"离线了");
    }

    //channle处于活动状态，提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress()+"上线了");
        super.channelActive(ctx);
    }

    //handlerAdded 表示连接建立 一旦连接 第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 加入聊天\n");
        channelGroup.add(channel);
        System.out.println(channel.id().toString());
        channelMap.put(channel.id().toString(),channel);
    }
}

package com.ut.lpf.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.channels.ServerSocketChannel;

public class TestServer {
    public static void main(String[] args) {
        EventLoopGroup boss=new NioEventLoopGroup(1);
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try{

            ServerBootstrap serverSocketChannel =new ServerBootstrap();
            serverSocketChannel.group(boss,workerGroup).channel(NioServerSocketChannel.class).childHandler(new TestServerImpl());
            ChannelFuture sync = serverSocketChannel.bind(7000).sync();
            sync.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println(2);
        } finally {
            boss.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

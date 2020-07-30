package com.ut.lpf.netty.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try
        {


        bootstrap.group(eventExecutors)
                 .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyClientHandler());
                    }
                });
        System.out.println("客户端 ok...");
        //netty异步机制
        ChannelFuture channelFuture = bootstrap.connect("localhost", 6669).sync();
        channelFuture.channel().closeFuture().sync();
        }
        finally {
            eventExecutors.shutdownGracefully();
        }
    }
}

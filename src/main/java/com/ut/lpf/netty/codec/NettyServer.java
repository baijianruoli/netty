package com.ut.lpf.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 1.创建两个线程组
         * 2 boss处理连接请求，业务交给worker
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建服务器的启动对象，参数
        try
        {


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                 .childHandler(new ChannelInitializer<SocketChannel>() {
                     //给pipechannel配置处理器
                     @Override
                     protected void initChannel(SocketChannel ch) throws  Exception
                     {
                         ch.pipeline().addLast(new NettyServerHandler());
                     }
                 });

        System.out.println("服务器 is ready");
        //绑定端口，同步处理,启动服务器
        ChannelFuture cf = serverBootstrap.bind(6669).sync();

        //对关闭通道监听
        cf.channel().closeFuture().sync();
        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}

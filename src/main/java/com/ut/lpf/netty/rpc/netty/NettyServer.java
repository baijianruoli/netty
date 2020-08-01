package com.ut.lpf.netty.rpc.netty;

import com.ut.lpf.netty.rpc.provider.ServerBootStrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {
    public static void startServer(String hostName,int port)
    {
        startServer0(hostName,port);
    }
  private static void startServer0(String hostname,int port)
  {
      NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
      NioEventLoopGroup workGroup = new NioEventLoopGroup();
      try{
          ServerBootstrap serverBootstrap = new ServerBootstrap();
          serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                  .childHandler(new ChannelInitializer<SocketChannel>() {
                      @Override
                      protected void initChannel(SocketChannel socketChannel) throws Exception {
                          ChannelPipeline pipeline = socketChannel.pipeline();
                          pipeline.addLast(new StringDecoder());
                          pipeline.addLast(new StringEncoder());
                          pipeline.addLast(new NettyServerHandler());
                      }
                  });
          ChannelFuture sync = serverBootstrap.bind(hostname, port).sync();
          System.out.println("服务器启动");
         sync.channel().closeFuture().sync();
      }catch (Exception e)
      {

      }finally {
         bossGroup.shutdownGracefully();
         workGroup.shutdownGracefully();
      }
  }
}

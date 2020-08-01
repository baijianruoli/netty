package com.ut.lpf.netty.rpc.netty;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {
    private  static ExecutorService executor=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private  static NettyClientHandler handler;

    public  Object getBean(final  Class<?> serviceClass,final String providerName)
    {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class<?>[]{serviceClass}, (proxy, method, args) -> {
            if(handler==null)
            {
                initClient();
            }
            handler.setPars(providerName + args[0]);
            return executor.submit(handler).get();
        });
    }
    private  static void initClient() throws InterruptedException {
       handler = new NettyClientHandler();
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(handler);
                    }
                });
        ChannelFuture localhost;
        localhost = bootstrap.connect("localhost", 8989).sync();

    }
}

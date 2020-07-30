package com.ut.lpf.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerImpl extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel)  {
        try
        {
            ChannelPipeline pipeline = socketChannel.pipeline();
            pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
            pipeline.addLast("MyTestHttpServerHandler",new TestHttpServerHandler());
        }
        catch (Exception e)
        {
            System.out.println(3);
        }

    }
}

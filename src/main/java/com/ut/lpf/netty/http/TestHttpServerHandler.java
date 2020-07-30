package com.ut.lpf.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

//HttoObject 客户端和服务端通信的数据封装成HttpObject
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(4);
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HttpObject httpObject)  {
        if( httpObject instanceof HttpRequest)
        {
            try {
                System.out.println("类型：= "+httpObject.getClass());
                System.out.println("客户端地址 :"+channelHandlerContext.channel().remoteAddress() );
                HttpRequest httpRequest=(HttpRequest) httpObject;
                URI uri = new URI(httpRequest.uri());
                if("/favicon.ico".equals(uri.getPath()))
                {
                    System.out.println("请求了ico");
                    return ;
                }
                ByteBuf content = Unpooled.copiedBuffer("hello 我是服务器", CharsetUtil.UTF_8);
                DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
                defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
                defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
                channelHandlerContext.writeAndFlush(defaultFullHttpResponse);
            }catch (Exception e)
            {
                System.out.println(1);
            }

        }
    }
}

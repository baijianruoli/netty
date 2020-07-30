package com.ut.lpf.netty.inandoutHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param channelHandlerContext 上下文
     * @param byteBuf  入站buf
     * @param list 集合,将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println(byteBuf.readableBytes());
        if(byteBuf.readableBytes()>=8)
            list.add(byteBuf.readLong());
    }
}

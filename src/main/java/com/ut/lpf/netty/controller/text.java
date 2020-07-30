package com.ut.lpf.netty.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class text {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("www.baidu.com", 80));
       /* socketChannel.configureBlocking(false);*/
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("test end!");
    }
}

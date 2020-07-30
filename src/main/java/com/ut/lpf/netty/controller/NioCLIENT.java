package com.ut.lpf.netty.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioCLIENT {
    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", 6666);
        if(!socketChannel.connect(inetSocketAddress))
        {
            while(!socketChannel.finishConnect())
            {
                System.out.println("因为连接需要时间，客户端不会阻塞");
            }
        }
        Thread.sleep(5000);
        String msg="hello liqiqi呜呜";
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(buffer);
        Thread.sleep(50000);
    }
}


















package com.ut.lpf.netty.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ScatterText {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel open = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        open.socket().bind(inetSocketAddress);

        ByteBuffer allocate = ByteBuffer.allocate(1024);
        System.out.println("wait");
        SocketChannel accept = open.accept();
        System.out.println("link");
        while(true)
        {
            System.out.println("等待read....");
            int read = accept.read(allocate);
            System.out.println("读取 "+read);
            System.out.println(new String(allocate.array()));
            allocate.flip();

            accept.write(allocate);
            allocate.clear();
        }

    }
}

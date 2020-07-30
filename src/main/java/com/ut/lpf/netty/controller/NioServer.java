package com.ut.lpf.netty.controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException {
        //开启ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //开启Selector
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //使用非阻塞ServerSocketChannel
        serverSocketChannel.configureBlocking(false);
        //ServerSocketChannel注册selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true)
        {
            int sum=selector.select(1000);
            System.out.println("主机连接数量: "+sum);
            if(sum==0)
            {
                System.out.println("服务器等待1秒，无连接");
                continue;
            }
            //Selector获得Selection的key集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //获取迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                if(key.isAcceptable())//连接
                {
                    //ServerSocketChannel连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println(socketChannel.hashCode());
                    //SocketChannel分配ByteBuffer，并且注册到Selector
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if(key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();
                    try {
                        ByteBuffer attachment = (ByteBuffer)key.attachment();
                        channel.read(attachment);
                        System.out.println("from 客户端："+new String(attachment.array()));
                    }catch (IOException e)
                    {
                        System.out.println(e.getMessage());
                        key.cancel();
                        if(channel!=null)
                            channel.close();
                    }

                }
                iterator.remove();
            }

        }


    }
}






















package com.ut.lpf.netty.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class FileChannel {
    public static void main(String[] args) throws IOException {
        String msg="hello 我说niopp";
        FileOutputStream fileOutputStream=new FileOutputStream("d:/nio.txt");
        java.nio.channels.FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer byteBuffer=ByteBuffer.allocate(1024);
        byteBuffer.put(msg.getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
        fileOutputStream.close();

    }
}

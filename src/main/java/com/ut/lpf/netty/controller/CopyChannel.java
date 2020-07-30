package com.ut.lpf.netty.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyChannel {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("d:/2.txt");
        FileInputStream fileInputStream = new FileInputStream("d:/nio.txt");
        FileChannel channel1 = fileInputStream.getChannel();
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer allocate = ByteBuffer.allocate(1024);
       channel1.read(allocate);
       allocate.flip();
       channel.write(allocate);



    }
}

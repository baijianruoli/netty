package com.ut.lpf.netty.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ImgChannel {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("d:/1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("d:/2.jpg");
        FileChannel channel = fileInputStream.getChannel();
        FileChannel channel1 = fileOutputStream.getChannel();
        channel1.transferFrom(channel,0,channel.size());
    }
}

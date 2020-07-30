package com.ut.lpf.netty.controller;

import lombok.val;

import java.nio.IntBuffer;

public class BufferController {
    public static void main(String[] args) {
      val intBuffer = IntBuffer.allocate(5);
        for(int i=1;i<=5;i++)
            intBuffer.put(i*2);
        intBuffer.flip();

        while(intBuffer.hasRemaining())
        {
            System.out.println(intBuffer.get());
        }
    }
}

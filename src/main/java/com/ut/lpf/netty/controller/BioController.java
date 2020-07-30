package com.ut.lpf.netty.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class BioController {
    public static void main(String[] args) throws IOException {
        ServerSocket socket=new ServerSocket(6666);
        ExecutorService executorService = Executors.newCachedThreadPool();


        while (true)
        {
            Socket accept = socket.accept();
         executorService.execute( new Thread(() -> {
             try {

                 System.out.println(Thread.currentThread().getId()+" "+Thread.currentThread().getName());
                 InputStream inputStream = accept.getInputStream();
                 while(true)
                 {
                     byte b[]=new byte[1024];
                     System.out.println("read....");
                     int read = inputStream.read(b);
                     System.out.println(read);
                     if(read!=-1)
                         System.out.println(new String(b));
                     else
                         break;
                 }

             } catch (IOException e) {
                 e.printStackTrace();
             }

         }));

        }
    }

}

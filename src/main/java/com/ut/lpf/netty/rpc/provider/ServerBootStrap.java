package com.ut.lpf.netty.rpc.provider;

import com.ut.lpf.netty.rpc.netty.NettyServer;

public class ServerBootStrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost",8989);
    }
}

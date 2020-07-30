package com.ut.lpf.netty.groupNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class GroupChatClient {
    private final  String host;
    private final int port;
    public GroupChatClient(String host,int port)
    {
        this.host=host;
        this.port=port;
    }
    public  void run()  {
        NioEventLoopGroup group=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        try
        {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline=socketChannel.pipeline();
                            pipeline.addLast("decoder",new StringDecoder());
                            pipeline.addLast("encoder",new StringEncoder());
                            pipeline.addLast(new GroupChatClientHandler());
                        }
                    });
            ChannelFuture sync = bootstrap.connect(host, port).sync();
            Channel channel=sync.channel();
            System.out.println(sync.channel().localAddress());
            Scanner scanner = new Scanner(System.in);
            while(scanner.hasNextLine())
            {
                String msg=scanner.nextLine();
                channel.writeAndFlush(msg+"\n");

            }
            
        }catch ( Exception e)
        {
            group.shutdownGracefully();
        }


    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient("localhost",8000);
        groupChatClient.run();
    }

}

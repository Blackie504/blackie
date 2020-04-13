package com.example.basic.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * USER     DATE      VERSION
 * yang    2020/4/7     V1.0.0
 **/
public class UdpAnswerSide {
    public final static String ANSWER = "古诗来了：";

    public void run(int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                        .channel(NioDatagramChannel.class)
                        .handler(new AnswerHandler());
            ChannelFuture future = bootstrap.bind(port).sync();
            System.out.println("应答服务已启动.....");
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8090;
        new UdpAnswerSide().run(port);
    }
}

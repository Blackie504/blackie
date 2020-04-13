package com.example.basic.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * USER     DATE      VERSION
 * yang    2020/4/7     V1.0.0
 **/
public class UdpQuestionSide {

    public final static String QUESTION = "请告诉我一句古诗：";

    public void run(int port) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup(); //建立group
        try{
            Bootstrap b = new Bootstrap(); //建立bootstrap
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .handler(new QuestionHanler());
            Channel channel = b.bind(0).sync().channel();
            channel.writeAndFlush(new DatagramPacket(
                    Unpooled.copiedBuffer(QUESTION, CharsetUtil.UTF_8),
                    new InetSocketAddress("127.0.0.1", port)
            ));
            if (!channel.closeFuture().await(15000)) {
                System.out.println("查询超时");
            }
        }catch (Exception e){
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8090;
        new UdpQuestionSide().run(port);
    }
}

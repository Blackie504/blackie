package com.example.basic.server;

import com.example.basic.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * USER     DATE      VERSION
 * yang    2020/3/27     V1.0.0
 **/
public class EchoServcer {

    private final int port;

    public EchoServcer(int port){
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        EchoServcer servcer = new EchoServcer(9999);
        System.out.println("服务器启动:");
        servcer.start();
        System.out.println("服务器关闭");
    }

    public void start() throws InterruptedException {
        final EchoServerHandler handler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(handler);
                    }
                });
        ChannelFuture future = bootstrap.bind().sync();
        future.channel().closeFuture().sync();
    }
}

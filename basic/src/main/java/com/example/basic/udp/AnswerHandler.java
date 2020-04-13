package com.example.basic.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * USER     DATE      VERSION
 * yang    2020/4/7     V1.0.0
 **/
public class AnswerHandler extends
        SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {
            "只要功夫深，铁棒磨成针。",
            "旧时王谢堂前燕,飞入寻常百姓家。",
            "洛阳亲友如相问，一片冰心在玉壶。",
            "一寸光阴一寸金，寸金难买寸光阴。",
            "老骥伏枥，志在千里，烈士暮年，壮心不已" };
    private static Random r = new Random();
    private String nextQuote(){
        return DICTIONARY[r.nextInt(DICTIONARY.length-1)];
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket data) throws Exception {
        String request = data.content().toString(CharsetUtil.UTF_8);
        System.out.println(request);
        ctx.writeAndFlush(new DatagramPacket(
                Unpooled.copiedBuffer(
                        UdpAnswerSide.ANSWER + nextQuote(),
                        CharsetUtil.UTF_8
                ), data.sender()
        ));
    }
}

package com.sun.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/22 13:45
 */
public class MyClientHandler extends SimpleChannelInboundHandler<Long> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {

    }

    //重写 channelActibe 发送数据

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("MyClientHandler 发送数据");
        ctx.writeAndFlush(123456L); //发送的是一个long
    }
}

package com.sun.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/21 11:11
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //入站的handler进行解码 MyByteToLongDecoder
        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyServerHandler());
    }
}

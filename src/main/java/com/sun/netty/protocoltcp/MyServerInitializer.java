package com.sun.netty.protocoltcp;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/22 15:33
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyMessageDecoder());
        pipeline.addLast(new MyMessageEncoder());
        pipeline.addLast(new MyServerHandler());
    }
}

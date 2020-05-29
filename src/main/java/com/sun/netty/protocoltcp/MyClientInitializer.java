package com.sun.netty.protocoltcp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/22 15:32
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new MyMessageEncoder()); //编码器
        pipeline.addLast(new MyMessageDecoder()); //解码器
        pipeline.addLast(new MyClientHandler());
    }
}

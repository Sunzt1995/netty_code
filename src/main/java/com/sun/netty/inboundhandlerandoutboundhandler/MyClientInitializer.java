package com.sun.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/21 11:22
 */
public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        //加入一个出站的handler , 对数据进行一个编码
        pipeline.addLast(new MyLongToByteEncoder());

        //加入自定义的handler , 处理业务逻辑
        pipeline.addLast(new MyClientHandler());

    }
}

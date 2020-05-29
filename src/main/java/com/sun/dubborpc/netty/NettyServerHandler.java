package com.sun.dubborpc.netty;

import com.sun.dubborpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/24 21:39
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //获取客户端发送的消息 , 并调用服务
        System.out.println("msg=" + msg);
        //客户端在调用服务器的api时 , 我们需要定义一个协议
        //比如 , 我们要求每次发消息时都必须以某个字符串开头 "HelloService#hello#"
        if (msg.toString().startsWith("HelloService#hello#")) {
            String result = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

package com.sun.netty.codec;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/16 21:18
 *
 * 说明:
 * 1. 自定义一个Handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 2. 这时 , 自定义的Handler , 才能称为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件(这里可以读取客户端发送的信息)
     * @param ctx 上下文对象 , 含有 管道pipeline , 通道channel , 地址
     * @param msg 客户端发送的数据 , 默认是Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //读取从客户端发送的 Student
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("客户端发送的数据 id = " + student.getId() + " , 名字 = " + student.getName());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        //将数据写入到缓存 , 并刷新
        //一般讲 , 对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello , 客户端~" , CharsetUtil.UTF_8));
    }

    /**
     * 处理异常 , 一般是需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

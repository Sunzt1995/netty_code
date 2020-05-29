package com.sun.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
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

        //比如 , 这里有一个非常耗费时间的业务 -->异步执行 --> 提交到该 channel对应的 NioEventLoop 的 taskQueue中
//        Thread.sleep(10 * 1000);

        //解决方案 1. 用户程序自定义的普通任务 -> 提交到 NioEventLoop 的 taskQueue
        ctx.channel().eventLoop().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello , 客户端2~" , CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        });

        // 2. 用户自定义定时任务  -> 该任务是提交到 scheduleTaskQueue中
        ctx.channel().eventLoop().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello , 客户端3~" , CharsetUtil.UTF_8));
                } catch (InterruptedException e) {
                    System.out.println("发生异常" + e.getMessage());
                }
            }
        } , 5 * 1000 , TimeUnit.SECONDS);

        System.out.println("go on ...");

//        System.out.println("server ctx =" + ctx);
//        System.out.println("看看channel和pipeline的关系");
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline(); //本质是一个双向链表 , 出站入站
//        //将 msg 转成一个 ByteBuf
//        // ByteBuf 是 Netty提供的 , 不是 NIO 的ByteBuffer
//        ByteBuf buf = (ByteBuf) msg;
//        System.out.println("客户端发送消息是 : " + buf.toString(CharsetUtil.UTF_8));
//        System.out.println("客户端地址是 : " + ctx.channel().remoteAddress());
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

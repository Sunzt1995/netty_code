package com.sun.netty.codec2;

import com.sun.netty.codec.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/16 21:18
 *
 * 说明:
 * 1. 自定义一个Handler需要继承netty规定好的某个HandlerAdapter(规范)
 * 2. 这时 , 自定义的Handler , 才能称为一个handler
 */
//public class NettyServerHandler extends ChannelInboundHandlerAdapter {
public class NettyServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    /**
     * 读取数据事件(这里可以读取客户端发送的信息)
     * @param ctx 上下文对象 , 含有 管道pipeline , 通道channel , 地址
     * @param msg 客户端发送的数据 , 默认是Object
     * @throws Exception
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

        //根据 dataType显示不同的信息
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        if(dataType == MyDataInfo.MyMessage.DataType.StudentType) {

            MyDataInfo.Student student = msg.getStudent();
            System.out.println("学生 = " + student.getId() + "--" + student.getName());

        } else if (dataType == MyDataInfo.MyMessage.DataType.WorkerType) {

            MyDataInfo.Worker worker = msg.getWorker();
            System.out.println("工人 = " + worker.getName() + "--" + worker.getAge());

        } else {
            System.out.println("传输类型不正确");
        }

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

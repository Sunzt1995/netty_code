package com.sun.netty.codec2;

import com.sun.netty.codec.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/16 22:02
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 当通道就绪就会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //随机的发送 Student 或者 Worker
        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;

        if(0 == random) { //发送student
            myMessage =  MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType).setStudent(
                    MyDataInfo.Student.newBuilder().setId(5).setName("张飞").build()).build();
        } else { //发送Worker对象
            myMessage =  MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType).setWorker(
                    MyDataInfo.Worker.newBuilder().setAge(18).setName("老李").build()).build();
        }

        ctx.writeAndFlush(myMessage);

    }

    /**
     * 当通道有读取事件时 , 会触发
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        System.out.println("服务器回复的消息: " + buf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址: " +ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

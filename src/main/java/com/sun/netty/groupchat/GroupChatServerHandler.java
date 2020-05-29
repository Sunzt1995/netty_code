package com.sun.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/19 15:40
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler {

    //定义一个channle 组, 管理所有的channel
    //GlobalEventExecutor.INSTANCE 是全局的事件执行器 , 是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // handlerAdded 表示连接建立 , 一旦连接, 第一个被执行
    //将当前channel 加入到 channelGroup
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //将该客户加入聊天的信息推送给其它在线的客户端
        /*
        该方法会将 channelGroup中所有的channel 遍历 , 并发送 消息
        不需要自己遍历
         */
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + "加入聊天" + sdf.format(new Date()) + "\n");
        channelGroup.add(channel);

    }

    //表示 channel 处于活动状态 提示xx上线
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 上线了:)");
    }

    //表示 channel 处于非活动状态 提示xx下线
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 下线了:(");
    }

    //读取数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        //获取当前channel
        Channel channel = ctx.channel();

        //遍历 channelGroup , 根据不同的情况 , 回送不同的消息
        channelGroup.forEach(ch -> {
            if(channel != ch) { //不是当前的channel , 转发消息
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + "发送了消息" + msg + "\n");
            }else { //回显自己发送的消息给自己
                ch.writeAndFlush("[自己]发送了消息" + msg + "\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.close();
    }
}

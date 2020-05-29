package com.sun.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/21 11:12
 */
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     *
     * @param ctx 上下文对象
     * @param in  入站的 ByteBuf
     * @param out List 集合 , 将解码后的数据传给下一个handler
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        //long 占8个字节 , 需要判断有8个字节 才能读取一个long
        if (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}

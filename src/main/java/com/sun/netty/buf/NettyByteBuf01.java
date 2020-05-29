package com.sun.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/18 20:25
 */
public class NettyByteBuf01 {
    public static void main(String[] args) {

        //创建一个ByteBuf
        //1. 创建一个对象 , 该对象包含一个数组arr , 是byte[10]
        //2. 在netty的buffer中 , 不需要使用flip进行反转
        //  底层维护了 readerindex 和 writeindex
        //3. 通过 readerindex 和 writeindex 和 capacity , 将buffer分成三个区域
        //0---readerindex 已经读取的区域
        //readerindex---writeindex 可读的区域
        //writeindex---capacity 可写的区域
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i  = 0 ; i < 10 ; i++) {
            buffer.writeByte(i);
        }

        //输出
        for (int i = 0 ; i < buffer.capacity() ; i++) {
            System.out.println(buffer.getByte(i));
        }
    }
}

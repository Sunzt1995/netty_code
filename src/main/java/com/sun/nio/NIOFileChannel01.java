package com.sun.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/11 21:59
 */
public class NIOFileChannel01 {

    public static void main(String[] args) throws Exception {
        String str = "hello , cims";
        //创建一个输出流 -> channel
        FileOutputStream fileOutputStream = new FileOutputStream("F:\\war\\file01.txt");

        //通过fileOutputStream 获取对应的FileChannel
        //这个fileChannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();

        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //将 str 放入到 byteBuffer
        byteBuffer.put(str.getBytes());

        //对byteBuffer 进行flip
        byteBuffer.flip();

        //将byteBuffer数据写入到 fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}

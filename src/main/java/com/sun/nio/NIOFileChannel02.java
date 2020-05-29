package com.sun.nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/11 22:18
 */
public class NIOFileChannel02 {

    public static void main(String[] args) throws Exception {
        //创建文件的输入流
        File file = new File("F:\\war\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        //将通道的数据读入到 Buffer中
        fileChannel.read(byteBuffer);

        //将byteBuffer字节数据转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }
}

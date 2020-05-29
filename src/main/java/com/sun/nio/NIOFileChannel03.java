package com.sun.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/12 19:39
 */
public class NIOFileChannel03 {

    public static void main(String[] args) throws Exception {

        FileInputStream fileInputStream = new FileInputStream("1.txt");
        FileChannel channel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("2.txt");
        FileChannel channel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while (true) {   //循环读取

            //这里有一个重要的操作 , 一定不要忘了
            byteBuffer.clear();//清空buffer

            int read = channel01.read(byteBuffer);
            if (read == -1) {    //表示读完
                break;
            }
            //将buffer中的数据写入
            byteBuffer.flip();
            channel02.write(byteBuffer);
        }
    }
}

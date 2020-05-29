package com.sun.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/15 13:47
 */
public class GroupChatServer {

    //定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //构造器
    //初始化工作
    public GroupChatServer() {
        try {
            
            //得到选择器
            selector = Selector.open();

            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将 listenChannel 注册到 selector
            listenChannel.register(selector , SelectionKey.OP_ACCEPT);


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try {

            //循环处理
            while (true) {

                int count = selector.select();
                if (count > 0) { //有事件要处理

                    //遍历 selectionKey 集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //取出 selectionKey
                        SelectionKey key = iterator.next();

                        //监听到 accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将该 sc 注册到 selector上
                            sc.register(selector , SelectionKey.OP_READ);
                            //提示
                            System.out.println(sc.getRemoteAddress() + " 上线了 ");
                        }
                        if (key.isReadable()) { // 通道发生read事件 , 即通道是可读的状态
                            //处理读(专门写方法....)
                            readData(key);
                        }

                        //当前的key 删除 , 防止重复操作
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待......");
                }

            }

        }catch (Exception e) {
            e.printStackTrace();

        }finally {
            //发生异常处理....
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {

        //定义一个 SocketChannel
        SocketChannel channel = null;
        try {
            //得到关联的channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            //根据 count 的值做处理
            if(count > 0) {
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端: " + msg);

                //向其它的客户端转发消息 , 专门写一个方法来处理
                sendInfoToOtherClients(msg , channel);

            }

        }catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了 ");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    //转发消息给其它客户(通道)
    private void sendInfoToOtherClients(String msg , SocketChannel self) throws IOException {

        System.out.println("服务器转发消息中....");
        //遍历 所有注册到 selector 上的 SocketChannel , 并排除 self
        for (SelectionKey key : selector.keys()) {

            //通过key 取出对应的 SocketChannel
            Channel targetChannel = key.channel();

            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != self) {
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg 存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer 的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {

        //创建一个服务器对象
        GroupChatServer chatServer = new GroupChatServer();
        chatServer.listen();
    }
}

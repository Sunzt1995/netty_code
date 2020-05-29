package com.sun.dubborpc.provider;

import com.sun.dubborpc.netty.NettyServer;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/24 21:26
 */
//ServerBootstrap 会启动一个服务提供者 , 就是NettyServer
public class ServerBootstrap {

    public static void main(String[] args) {

        //代码...
        NettyServer.startServer("127.0.0.1" , 7000);
    }
}

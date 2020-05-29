package com.sun.dubborpc.provider;

import com.sun.dubborpc.publicinterface.HelloService;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/24 21:22
 */
public class HelloServiceImpl implements HelloService {

    //当有消费方调用该方法时 , 就返回一个结果
    @Override
    public String hello(String mes) {
        System.out.println("收到客户端消息=" + mes);
        //根据mes 返回不同的结果
        if(mes != null) {
            return "你好 , 客户端 , 服务端已经收到消息 [" + mes + "]";
        } else {
            return "你好 , 客户端 , 服务端已经收到消息";
        }
    }
}

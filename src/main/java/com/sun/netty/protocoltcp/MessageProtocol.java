package com.sun.netty.protocoltcp;

/**
 * @author Sunzt
 * @version 1.0
 * @date 2019/12/22 16:39
 */
//协议包
public class MessageProtocol {
    private int len; //关键
    private  byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}

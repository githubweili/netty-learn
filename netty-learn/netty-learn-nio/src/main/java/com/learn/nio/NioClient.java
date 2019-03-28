package com.learn.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by wei.li
 * on 2019/3/27
 */
public class NioClient {

    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 2000));

        while (true){
            ByteBuffer buf = ByteBuffer.allocate(48);
            buf.clear();
            buf.put("hello, i am client".getBytes());
            buf.flip();

            while (buf.hasRemaining()){
                socketChannel.write(buf);
            }

            Thread.sleep(2000);
        }
    }
}

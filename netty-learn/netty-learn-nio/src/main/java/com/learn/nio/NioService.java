package com.learn.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by wei.li
 * on 2019/3/27
 */
public class NioService {


    public static void main(String[] args)throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(2000));
        while (true){
            serverSocketChannel.configureBlocking(false);
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (null != socketChannel){
                ByteBuffer buf = ByteBuffer.allocate(48);
                int num = socketChannel.read(buf);
                while (num != -1){
                    buf.flip();
                    while(buf.hasRemaining()){
                        System.out.print((char) buf.get());
                    }
                    buf.clear();
                }
            }
        }
    }
}

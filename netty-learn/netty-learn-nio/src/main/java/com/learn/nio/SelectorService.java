package com.learn.nio;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wei.li
 * on 2019/3/28
 */
public class SelectorService {

    public static void main(String[] args) throws Exception{
        Selector selector = Selector.open();
        Selector client = Selector.open();


        new Thread(() -> {
            try {
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.socket().bind(new InetSocketAddress(2000));
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (true){
                    if (selector.select(1) == 0) continue;
                    Set selectors = selector.selectedKeys();
                    Iterator key = selectors.iterator();
                    while (key.hasNext()){
                        SelectionKey k = (SelectionKey) key.next();
                        if (k.isAcceptable()){
                            SocketChannel socketChannel = ((ServerSocketChannel) k.channel()).accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(client, SelectionKey.OP_READ);
                            key.remove();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();



        new Thread(() ->{

            try {
                while (true){
                    if (client.select(1) == 0) continue;
                    Set selectors = client.selectedKeys();
                    Iterator key = selectors.iterator();
                    while (key.hasNext()){
                        SelectionKey k = (SelectionKey) key.next();
                        if (k.isReadable()){
                            try {
                                SocketChannel socketChannel = (SocketChannel) k.channel();
                                ByteBuffer buf = ByteBuffer.allocate(48);
                                socketChannel.read(buf);
                                buf.flip();
                                System.out.println(Charset.defaultCharset().newDecoder().decode(buf)
                                        .toString());

                            } catch (IOException e) {
                                key.remove();
                                k.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }
}

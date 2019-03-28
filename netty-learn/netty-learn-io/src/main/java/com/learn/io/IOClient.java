package com.learn.io;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by wei.li
 * on 2019/3/27
 */
public class IOClient {


    public static void main(String[] args) {
        new Thread(() ->{
            try {
                Socket socket = new Socket("127.0.0.1", 1000);
                while (true){
                    socket.getOutputStream().write(("hello, i am client").getBytes());
                    socket.getOutputStream().flush();
                    Thread.sleep(5000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

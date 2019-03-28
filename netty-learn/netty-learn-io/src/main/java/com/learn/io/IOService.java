package com.learn.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wei.li
 * on 2019/3/27
 */
public class IOService{

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(1000);
        new Thread(() -> {
            try {
                //监听并获取连接
                Socket socket = serverSocket.accept();
                byte[] bytes = new byte[1024];
                InputStream inputStream = socket.getInputStream();
                while (true){
                    int num;
                    while ((num = inputStream.read(bytes)) != -1){
                        System.out.println(new String(bytes, 0, num));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}

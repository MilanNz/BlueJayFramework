package com.bluejay.jaynio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class JayNioWebServer extends Thread {
    private HttpListener httpListener;
    private SocketChannel socketChannel;
    private InetSocketAddress socketAddress;

    public JayNioWebServer() {}

    public void setServerHttpListening(String host, int port) {
        this.socketAddress = new InetSocketAddress(host, port);
    }

    public void setHttpListener(HttpListener httpListener) {
        this.httpListener = httpListener;
    }

    public void startServer() throws IOException {
        start();
    }

    public void stopServer() throws IOException {
        interrupt();
        socketChannel.close();
    }

    public boolean isStarted() {
        return socketChannel != null && socketChannel.isConnected();
    }

    @Override
    public void run() {
        try {
            this.socketChannel = SocketChannel.open(socketAddress);
            ByteBuffer buf = ByteBuffer.allocate(48);
            while (isInterrupted()) {
                try {
                    int bytesRead = socketChannel.read(buf);
                    System.out.println(new String(buf.array()));

                    if (httpListener != null) {
                        httpListener.handleRequest();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

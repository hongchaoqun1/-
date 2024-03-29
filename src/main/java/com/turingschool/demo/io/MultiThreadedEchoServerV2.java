package com.turingschool.demo.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MultiThreadedEchoServerV2 {
	private int port;

	public MultiThreadedEchoServerV2(int port) {
		this.port = port;
	}

	public void startServer() {
		ServerSocket echoServer = null;
		Executor executor = Executors.newFixedThreadPool(5);
		int i = 0;
		System.out.println("服务器在端口[" + this.port + "]等待客户请求......");
		try {
			echoServer = new ServerSocket(8080);
			while (true) {
				Socket clientRequest = echoServer.accept();
				executor.execute(new ThreadedServerHandler(clientRequest, i++));
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws IOException {
		new MultiThreadedEchoServerV2(8080).startServer();

	}
}

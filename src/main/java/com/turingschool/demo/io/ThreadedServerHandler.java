package com.turingschool.demo.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadedServerHandler implements Runnable {
	Socket clientSocket = null;
	int clientNo = 0;

	ThreadedServerHandler(Socket socket, int i) {
		if (socket != null) {
			clientSocket = socket;
			clientNo = i;
			System.out.println("创建线程为[" + clientNo + "]号客户服务...");
		}
	}

	@Override
	public void run() {
		PrintStream os = null;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
			String inputLine;
			while ((inputLine = in.readLine()) != null) {

				// 输入'Quit'退出
				if (inputLine.equals("Quit")) {
					System.out.println("关闭与客户端[" + clientNo + "]......" + clientNo);
					os.close();
					in.close();
					clientSocket.close();
					break;
				} else {
					System.out.println("来自客户端[" + clientNo + "]的输入： [" + inputLine + "]！");
					os.println("来自服务器端的响应：" + inputLine);
				}
			}
		} catch (IOException e) {
			System.out.println("Stream closed");
		}
	}
}

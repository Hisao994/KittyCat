package com.kittycat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class KittyCat {

	public static void main(String[] args) {
		KittyCat kc = new KittyCat();
		kc.startServer();
	}

	public void startServer() {
		System.out.println("服务器准备启动");

		int port = getServerPort();// TODO：在这里加入一个xml解析模块，读取server，xml，读取port信息

		boolean flag = true;
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(port);
			System.out.println("服务器启动成功，监听：" + ss.getLocalPort() + "端口");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(port + "被占用，服务器启动失败");
			// TODO:自动选定另一个tcp空闲的端口
			System.exit(0);
		}

		while (flag) {
			Socket s = null;
			try {
				s = ss.accept();
				// 启动线程来处理这个客户端的请求和响应
				System.out.println("客户端" + s.getLocalPort() + "链接上来了...");
				ServerService service = new ServerService(s);
				Thread t = new Thread(service);
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("客户端" + s.getRemoteSocketAddress() + "已掉线...");
			}
		}
	}

	private int getServerPort() {
		return 7777;
	}

}

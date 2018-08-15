package com.kittycat.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public class ServerService implements Runnable {

	private Socket s;
	private InputStream ips;
	private OutputStream ops;

	public ServerService(Socket s) {
		this.s = s;

	}

	@Override
	public void run() {
		try {
			this.ips = s.getInputStream();
			this.ops = s.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("客户端" + this.s.getRemoteSocketAddress() + "掉线...");
			return;
		}

		// http是一个基于请求与响应的协议
		// 1.创建一个请求的对象
		HttpServletRequest request = new HttpServletRequest(this.ips);// 从ips中读取请求头，请求实体

		// request.parseProtocal();// 解析协议 =>url ->存到request对象中的url属性中

		// 2.创建一个相应的对象
		HttpServletResponse response = new HttpServletResponse(request, ops);

		// 3.判断是静态还是动态的请求
		// 如果是静态的请求，创建StaticProcessor

		// 如果是动态的请求，创建DynamicProcessor

		Processor processor = null;
		String uri = request.getRequestURI();
		if (uri.indexOf(".action") > 0 || uri.indexOf(".do") > 0) {
			processor = new DynamicProcessor();
		} else {
			processor = new StaticProcessor();
		}
		processor.process(request, response);

		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

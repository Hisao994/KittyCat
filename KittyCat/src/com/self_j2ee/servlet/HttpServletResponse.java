package com.self_j2ee.servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class HttpServletResponse {
	private OutputStream ops;
	private HttpServletRequest request;

	// 定义一个全局变量，用来表示规范的web项目的文件的位置
	private String webroot = System.getProperty("user.dir") + File.separator + "webapps";

	public HttpServletResponse(HttpServletRequest request, OutputStream ops) {
		this.ops = ops;
		this.request = request;
	}

	public PrintWriter getWriter() {
		PrintWriter out = new PrintWriter(this.ops);
		return out;
	}

	/**
	 * 基本逻辑
	 * 
	 * public void sendRedirect() { // 1.取出request中的url String url =
	 * this.request.getRequestURL();
	 * 
	 * File f = new File(webroot, url); // 2.查看webapps下是否有这个url指代的文件 String
	 * responseProtocal = null; if (f.exists() == false) { //
	 * 3.如果文件不存在，则拼装404响应头，输出 String httpEntity = readFile(new File(webroot,
	 * "404.html")); responseProtocal = gen404(httpEntity.getBytes().length);
	 * responseProtocal = responseProtocal + httpEntity; } else { //
	 * 4.如果文件存在，读取url对应的文件 String httpEntity = readFile(f); //
	 * 判断文件的类型，选用content—type responseProtocal =
	 * gen200(httpEntity.getBytes().length); // 再拼装response头 和实体}
	 * responseProtocal = responseProtocal + httpEntity; } // 5.以流的方式发给客户端 try {
	 * this.ops.write(responseProtocal.getBytes()); this.ops.flush();
	 * 
	 * this.ops.close(); } catch (IOException e) { e.printStackTrace(); } }
	 * */

	public void sendRedirect() {
		int code = 200;
		// 1.取出request中的url
		String uri = this.request.getRequestURI();
		File file = null;
		String fileName = "";
		if (uri.endsWith("/")) {
			fileName = uri + "index.html";
		} else {
			fileName = uri;
		}
		file = new File(webroot, fileName);
		// 2.拼装file，查看是否存在
		if (file.exists() == false) {
			file = new File(webroot, "404.html");
			code = 404;
		}
		// 3.存在，找这个文件，读出来
		if (file.getName().endsWith(".jpg")) {
			send(file, "application/x-jpg", code);
		} else if (file.getName().endsWith(".jpe") || file.getName().endsWith(".jpeg")) {
			send(file, "image/jpeg", code);
		} else if (file.getName().endsWith(".gif")) {
			send(file, "image/gif", code);
		} else if (file.getName().endsWith(".css")) {
			send(file, "text/css", code);
		} else if (file.getName().endsWith(".js")) {
			send(file, "application/x-javascript", code);
		} else if (file.getName().endsWith(".swf")) {
			send(file, "application/x-shockwave-flash", code);
		} else {
			send(file, "text/html", code);
		}

	}

	private void send(File file, String contentType, int code) {
		try {
			String responseHeader = genProtocal(file.length(), contentType, code);
			byte[] bs = readFile(file);

			this.ops.write(responseHeader.getBytes());
			this.ops.flush();
			this.ops.write(bs);
			this.ops.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				this.ops.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String genProtocal(long length, String contentType, int code) {
		String result = "HTTP/1.1 " + code + " OK\r\n";
		result += "Server: KittyCat is the best server\r\n"; // TODO:要改，因为有图片
		result += "Content-Type:" + contentType + "\r\n";
		result += "Content-Length:" + length + "\r\n";
		result += "Date::" + new Date() + "\r\n";
		result += "\r\n";
		return result;
	}

	private byte[] readFile(File file) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte[] bs = new byte[1024];
			int length = -1;
			while ((length = fis.read(bs, 0, bs.length)) != -1) {
				baos.write(bs, 0, length);
				baos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}

}

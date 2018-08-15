package com.self_j2ee.servlet;

//httpServlet 针对http协议
//GET / HTTP/1.1
//method
public abstract class HttpServlet implements Servlet {

	public void init() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
	}

	protected void doHead(HttpServletRequest request, HttpServletResponse response) {
	}

	protected void doTrace(HttpServletRequest request, HttpServletResponse response) {
	}

	// 从request中取出method，判断是get还是post
	public void service(HttpServletRequest request, HttpServletResponse response) {
		String method = request.getMethod();
		if ("GET".equals(method)) {
			doGet(request, response);
		} else if ("HEAD".equals(method)) {
			doHead(request, response);
		} else if ("POST".equals(method)) {
			doPost(request, response);
		}
	}
}

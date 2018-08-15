package com.self_j2ee.servlet;

public interface Servlet {

	public void init();

	public void service(HttpServletRequest request, HttpServletResponse response);
}

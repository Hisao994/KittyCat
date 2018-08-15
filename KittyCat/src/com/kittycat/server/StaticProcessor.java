package com.kittycat.server;

import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public class StaticProcessor implements Processor {

	@Override
	public  void process(HttpServletRequest request, HttpServletResponse response) {
		response.sendRedirect();// 静态处理
	}

}

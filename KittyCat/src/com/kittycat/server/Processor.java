package com.kittycat.server;

import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public interface Processor {

	// 处理请求
	public void process(HttpServletRequest request, HttpServletResponse response);
}

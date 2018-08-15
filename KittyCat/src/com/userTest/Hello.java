package com.userTest;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;

public class Hello extends HttpServlet {

	public Hello() {
		System.out.println("构造方法。。。");
	}

	@Override
	public void init() {
		super.init();
		System.out.println("初始化...");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doGET...");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("doPost...");
	}

}

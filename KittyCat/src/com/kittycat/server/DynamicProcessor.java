package com.kittycat.server;

import com.self_j2ee.servlet.HttpServlet;
import com.self_j2ee.servlet.HttpServletRequest;
import com.self_j2ee.servlet.HttpServletResponse;
import com.self_j2ee.servlet.ServletContext;

public class DynamicProcessor implements Processor {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) {
		// 1.假设请求的地址为 /hello.action ->对应的 是hello这个servlet类

		// 1.从request中取出getRequestURI
		String uri = request.getRequestURI();

		// 2.去掉/ 及action两个部分，得到Hello 就是servlet类名
		String servletName = uri.substring(1, uri.indexOf("."));

		// TODO:优化:考虑一下单例如何实现， ->到map中判断这个servlet类是否已经存在，如果有，则取出这个类
		HttpServlet servlet = null;
		if (ServletContext.servlets.containsKey(servletName) == false) {
			try {
				// 没有，则1.利用反射来加载这个类的字节码 URLClassLoader来加载
				Class c = Class.forName(servletName);
				// 2.利用class实例化 newInstance()-> Servlet初始化
				Object obj = c.newInstance();
				ServletContext.servlets.put(servletName, obj);
				if (obj instanceof HttpServlet) {
					servlet = (HttpServlet) obj;
					servlet.init();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			servlet = (HttpServlet) ServletContext.servlets.get(servletName);
		}
		servlet.service(request, response);
		// 有，则从Map中取出这个实例

		// 3.servlet生命周期 ... 构造方法 -> init() -> service

		// ->根据请求的method不同，调用servlet中的doGet 或doPost

	}
}

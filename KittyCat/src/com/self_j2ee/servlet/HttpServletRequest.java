package com.self_j2ee.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//这个类里的属性，请参考j2ee
public class HttpServletRequest {
	private InputStream ips;

	private String method;
	private String requestURL;
	private String protocal;
	private Hashtable<String, String> headers = new Hashtable<String, String>();

	private String content;

	private Map<String, String> parameter = new Hashtable<String, String>();

	private List<Cookie> cookies = new ArrayList<Cookie>();

	private String jsessionid = null;

	public String getJsessionid() {
		return jsessionid;
	}

	public List<Cookie> getCookies() {
		return cookies;
	}

	public String getParameter(String key) {
		return parameter.get(key);
	}

	public HttpServletRequest(InputStream ips) {
		this.ips = ips;

		// 一次性地读完所有的请求信息
		StringBuffer sb = new StringBuffer();
		int length = -1;
		byte[] bs = new byte[1024 * 1024];
		try {
			length = this.ips.read(bs);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("读取客户端请求异常");
			length = -1;
		}
		// 将bs中的字节数据转为char
		for (int i = 0; i < length; i++) {
			sb.append((char) bs[i]);
		}
		content = sb.toString();
		String[] parts = this.content.split("\r\n\r\n");

		// System.out.println("客户端的请求协议：" + content);

		parseProtocal();
	}

	public HttpSession getSession() {
		HttpSession session = null;
		// 判断jsessionid是否存在，如果有，则取出httpSession返回
		if (jsessionid == null || ServletContext.sessions.containsKey(jsessionid) == false) {
			// 创建一个session
			jsessionid = UUID.randomUUID().toString();
			session = new HttpSession(jsessionid);
			ServletContext.sessions.put(jsessionid, session);
		} else {
			// 没有，则创建一个httpsession存起来，再返回
			session = ServletContext.sessions.get(jsessionid);
		}
		return session;
	}

	//
	public void parseProtocal() {
		// 从ips中取出请求头，请求实体，解析数据，存到属性中
		// TODO:先取出url
		String[] ss = content.split(" ");
		this.method = ss[0];
		this.requestURL = ss[1];
		// System.out.println("  " + this.method + "  " + this.requestURL);

		// TODO： 解析参数 ->存到一个名为parameter的Map<String,String>
		parseParameter();
		// TODO：解析出header存好
		parseHead();
		// TODO:从header中取出cookie
		parseCookie();
		// TODO:从cookie中取出jsessionid
		jsessionid = parseJSessionId();
	}

	private String parseJSessionId() {
		if (cookies != null && cookies.size() > 0) {
			for (Cookie c : cookies) {
				if (c.getName().equals("JSESSIONID")) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	private void parseCookie() {
		if (headers == null || headers.size() <= 0) {
			return;
		}
		// 因為是tomcat服务器，所以cookie中的叫jsessionid
		String cookieValue = headers.get("Cookie");
		if (cookieValue == null || cookieValue.length() <= 0) {
			return;
		}
		String[] cvs = cookieValue.split("; ");
		if (cvs != null && cvs.length > 0) {
			for (String cv : cvs) {
				String[] str = cv.split("=");
				if (str != null && str.length > 0) {
					String key = str[0];
					String value = str[1];
					Cookie c = new Cookie(key, value);
					cookies.add(c);
				}
			}
		}
	}

	/**
	 * 解析请求头
	 */
	private void parseHead() {
		// 将请求头分割
		String[] parts = this.content.split("\r\n\r\n");
		// 将其分割成条
		String[] headerPairs = parts[0].split("\r\n");
		for (int i = 1; i < headerPairs.length; i++) {
			// 请求头为 键:值
			String[] headerPair = headerPairs[i].split(": ");
			headers.put(headerPair[0], headerPair[1]);
		}
	}

	public String getHeader(String name) {
		return headers.get(name);
	}

	// 取参数
	private void parseParameter() {
		int index = this.requestURL.indexOf("?");
		if (index >= 1) {
			String[] pairs = this.requestURL.substring(index + 1).split("&");
			for (String p : pairs) {
				String[] po = p.split("=");
				parameter.put(po[0], po[1]);
			}
		}
		if (this.getMethod().equals("POST")) {
			String[] parts = this.content.split("\r\n\r\n");
			String entity = parts[1];
			String[] pairs = entity.split("&");
			for (String p : pairs) {
				String[] po = p.split("=");
				parameter.put(po[0], po[1]);
			}
		}
	}

	public String getProtocal() {
		return protocal;
	}

	public InputStream getIps() {
		return ips;
	}

	public String getMethod() {
		return method;
	}

	public String getRequestURI() {
		return requestURL;
	}

	public Hashtable<String, String> getHeaders() {
		return headers;
	}

}

package com.self_j2ee.servlet;

import java.util.HashMap;
import java.util.Map;

public class HttpSession {

	private Map<String, Object> map = new HashMap<String, Object>();
	private String id;

	public HttpSession(String id) {
		super();
		this.id = id;
	}

	public Object getAttribute(String name) {
		return map.get(name);
	}

	public String getId() {
		return id;
	}

	public void setAttribute(String name, Object value) {
		this.map.put(name, value);
	}

	public void removeAttribute(String name) {
		this.map.remove(name);
	}
}

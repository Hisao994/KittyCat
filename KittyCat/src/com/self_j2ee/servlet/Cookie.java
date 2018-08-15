package com.self_j2ee.servlet;

import java.io.Serializable;

public class Cookie implements Serializable {

	private String name;
	private String value;
	private String path;
	private String domain;
	private int maxAge;

	@Override
	public String toString() {
		return "Cookie [name=" + name + ", value=" + value + ", path=" + path + ", domain=" + domain + ", maxAge=" + maxAge + "]";
	}

	public Cookie(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}
}

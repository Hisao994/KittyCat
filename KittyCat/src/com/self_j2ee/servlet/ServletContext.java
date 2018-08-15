package com.self_j2ee.servlet;

import java.util.Hashtable;
import java.util.Map;

public class ServletContext {

	public static Map<String, Object> servlets = new Hashtable<String, Object>();

	public static Map<String, HttpSession> sessions = new Hashtable<String, HttpSession>();
}

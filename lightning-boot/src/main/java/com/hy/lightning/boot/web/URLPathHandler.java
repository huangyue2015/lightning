package com.hy.lightning.boot.web;

import com.hy.lightning.boot.util.RequestException;

public class URLPathHandler {
	private String servletName;

	private String method;

	private String path;
	
	private void formaitPath() throws RequestException {
		String paString = path;
		if (paString.startsWith("/"))
			paString = paString.substring(1);
		String[] paths = paString.split("/");
		if (paths == null || paths.length != 2)
			throw new RequestException(300, "/" + path, "请求路径不合法 ,标准格式/service/method");
		servletName = paths[0];
		method = paths[1];
	}
	
	public URLPathHandler(String path) throws RequestException {
		this.path = path;
		formaitPath();
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getServletName() {
		return servletName;
	}

	public String getMethod() {
		return method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}

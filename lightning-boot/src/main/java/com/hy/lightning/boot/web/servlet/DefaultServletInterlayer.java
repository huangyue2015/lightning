package com.hy.lightning.boot.web.servlet;

import javax.servlet.http.HttpServletRequest;

import com.hy.lightning.boot.util.RequestException;

public class DefaultServletInterlayer implements ServletInterlayer{
	
	private ServletRegister servletRegister;
	private DefaultHttpServletRequest request;

	public DefaultServletInterlayer(HttpServletRequest request, ServletRegister servletRegister) {
		this.request = (DefaultHttpServletRequest) request;
		this.servletRegister = servletRegister;
	}

	@Override
	public Object work() throws RequestException {
		return servletRegister.registerService(request);
	}

	public ServletRegister getServletRegister() {
		return servletRegister;
	}


	public void setServletRegister(ServletRegister servletRegister) {
		this.servletRegister = servletRegister;
	}


	public DefaultHttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(DefaultHttpServletRequest request) {
		this.request = request;
	}

}

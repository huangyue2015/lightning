package com.hy.lightning.boot.web.servlet;

import javax.servlet.http.HttpServletRequest;

import com.hy.lightning.boot.util.RequestException;

public interface ServletRegister {
	public Object registerService(HttpServletRequest req) throws RequestException;
}

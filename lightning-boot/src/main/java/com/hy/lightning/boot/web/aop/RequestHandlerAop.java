package com.hy.lightning.boot.web.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.lightning.boot.util.RequestException;

public interface RequestHandlerAop {
	void before(HttpServletRequest req, HttpServletResponse resp) throws RequestException;
	
	void end(Object object) throws RequestException;
}

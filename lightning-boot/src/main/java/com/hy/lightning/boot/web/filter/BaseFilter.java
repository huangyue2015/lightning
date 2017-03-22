package com.hy.lightning.boot.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hy.lightning.boot.web.servlet.DefaultHttpServletReqsponse;
import com.hy.lightning.boot.web.servlet.DefaultHttpServletRequest;


@WebFilter("/*")
public class BaseFilter implements Filter {

	public BaseFilter() {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/javascript;charset=UTF-8");
		ServletRequest defaultServletRequest = new DefaultHttpServletRequest((HttpServletRequest) request);
		ServletResponse defaultServletResponse = new DefaultHttpServletReqsponse((HttpServletResponse) response);
		chain.doFilter(defaultServletRequest, defaultServletResponse);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}

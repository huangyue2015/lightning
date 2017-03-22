package com.hy.lightning.boot.web.servlet;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DefaultHttpServletReqsponse extends HttpServletResponseWrapper
{

	public DefaultHttpServletReqsponse(HttpServletResponse response)
	{
		super(response);
	}

}

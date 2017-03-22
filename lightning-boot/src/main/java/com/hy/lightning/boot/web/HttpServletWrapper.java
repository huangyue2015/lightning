package com.hy.lightning.boot.web;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hy.lightning.boot.util.ObjectMapperWrap;
import com.hy.lightning.boot.util.RequestException;
import com.hy.lightning.boot.web.aop.DefaultRequestHandlerAop;
import com.hy.lightning.boot.web.aop.RequestHandlerAop;
import com.hy.lightning.boot.web.servlet.DefaultServletInterlayer;
import com.hy.lightning.boot.web.servlet.ServletRegister;
import com.hy.lightning.boot.web.servlet.StaticResourcesHandler;

public abstract class HttpServletWrapper extends HttpServlet implements ServletRegister {

	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	private volatile String callback = null;// 是否启用JSOP

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) {
		RequestHandlerAop requestHandlerAop = new DefaultRequestHandlerAop();
		callback = req.getParameter("callback");// JSONP
		if (new StaticResourcesHandler(req, resp).mapping())
			return;// 静态文件过滤
		DefaultServletInterlayer defaultServletInterlayer = new DefaultServletInterlayer(req, this);// 反射处理核心类
		String resultMsg = "";
		try {
			requestHandlerAop.before(req, resp);// aop前期数据处理
			Object resultObj = defaultServletInterlayer.work();
			requestHandlerAop.end(resultObj);// aop后期数据处理
			resultMsg = ObjectMapperWrap.serialize(new PR(1, resultObj, "OK"));
		} catch (RequestException e) {
			logger.error(e.getResultstate() + ":" + e.getResultdesc(), e);
			resultMsg = ObjectMapperWrap.serialize(new PR(e.getResultstate(), e.getResult(), e.getResultdesc()));
		}
		if (callback != null)
			resultMsg = String.format("%s(%s)", callback, resultMsg);
		writeAndFlush(resp, resultMsg.getBytes());
	}

	/**
	 * 向客户端返回数据
	 * 
	 * @param resp
	 * @param message
	 */
	protected void writeAndFlush(HttpServletResponse resp, byte[] data) {
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			out.write(data);
			out.flush();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {}
		}
	}
}

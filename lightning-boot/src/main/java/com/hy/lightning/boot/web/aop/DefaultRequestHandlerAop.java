package com.hy.lightning.boot.web.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.hy.lightning.boot.util.ObjectMapperWrap;
import com.hy.lightning.boot.util.RequestException;

public class DefaultRequestHandlerAop implements RequestHandlerAop {

	private Logger logger = Logger.getLogger(DefaultRequestHandlerAop.class);
	
	@Override
	public void before(HttpServletRequest req, HttpServletResponse resp) throws RequestException {
		
	}

	@Override
	public void end(Object object) {
		if(logger.isDebugEnabled()) {
			logger.debug(ObjectMapperWrap.serialize(object));
		}
	}
}

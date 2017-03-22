package com.hy.lightning.boot.web.servlet;

import com.hy.lightning.boot.util.RequestException;

/**
 * 接收数据，返回结果
 * @author HY
 *
 */
public interface ServletInterlayer {

	public Object work() throws RequestException;
}

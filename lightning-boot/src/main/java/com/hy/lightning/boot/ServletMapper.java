package com.hy.lightning.boot;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.hy.lightning.boot.util.ObjectMapperWrap;
import com.hy.lightning.boot.util.RequestException;
import com.hy.lightning.boot.util.StringUtil;
import com.hy.lightning.boot.web.HttpServletWrapper;
import com.hy.lightning.boot.web.Services;
import com.hy.lightning.boot.web.Services.Service;
import com.hy.lightning.boot.web.URLPathHandler;

/**
 * 总控制器，会自动加载classpath目录下的*_services.xml接口配置文件
 * 
 * @author HY
 *
 */
@WebServlet("/")
public class ServletMapper extends HttpServletWrapper {

	private static final long serialVersionUID = 1L;

	private static Services services;

	private static volatile boolean flag;

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Object registerService(HttpServletRequest req) throws RequestException {
		loadServices(req);
		return doService(req);
	}

	private void loadServices(HttpServletRequest req) throws RequestException {
		if (!flag) {
			String[] arr = traverseFolder(ClassLoader.getSystemResource("").getPath());
			services = new Services(arr);
			try {
				services.loadServices();
			} catch (DocumentException e) {
				throw new RequestException(400, "", "*_serivices.xml解析错误");
			}
			flag = true;
		}
	}

	/**
	 * 
	 * @param req
	 * @return
	 * @throws RequestException
	 * @throws InvocationTargetException
	 */
	private Object doService(HttpServletRequest req) throws RequestException {
		URLPathHandler url = new URLPathHandler(req.getServletPath());
		String methodStr = url.getMethod();
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("method:[%s],params:[%s]", methodStr,
					ObjectMapperWrap.serialize(req.getParameterMap())));
		}
		Service service = services.getService(url.getPath());
		if (service == null) {
			throw new RequestException(0, url.getPath(), "调用服务不存在");
		}
		try {
			Class<?> c = Class.forName(service.getClassPath());
			Object object = c.newInstance();
			Method method = c.getDeclaredMethod(methodStr, getParamTypes(service.getParamTypes()));
			return method.invoke(object, getParams(req.getParameterMap(), service));
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			int state = 0;
			String resutDesc = "服务端异常";
			if(e instanceof InvocationTargetException) {
				String message =  e.getCause().getMessage();
				if(StringUtil.isNotNullOrEmpty(message)) {
					if(message.contains(":")) {
						state = Integer.valueOf(message.split(":")[0].trim());
						resutDesc = message.split(":")[1];
					} else {
						state = 0;
						resutDesc = message;
					}
				}
				logger.error(e.getCause(), e.getCause());
			} else {
				state = 300;
				resutDesc = "请求的方法名或参数不正确，无法反射到对应的服务，请查看接口文档，与接口说明";
			}
			throw new RequestException(state, "", resutDesc);
		}
	}

	/**
	 * 获得services.xml配置文件中方法参数类型
	 */
	private Class<?>[] getParamTypes(String[] paramTypes) throws ClassNotFoundException {
		Class<?>[] classes = new Class<?>[paramTypes.length];
		for (int i = 0; i < paramTypes.length; i++) {
			Class<?> c = Class.forName(paramTypes[i]);
			classes[i] = c;
		}
		return classes;
	}

	/**
	 * 获得services.xml配置文件中方法参数
	 * @param map
	 * @param service
	 * @return
	 * @throws ClassNotFoundException
	 * @throws RequestException
	 */
	private Object[] getParams(Map<String, String[]> map, Service service) throws ClassNotFoundException, RequestException {
		Class<?>[] paramTypes = getParamTypes(service.getParamTypes());
		String[] params = service.getParams();
		Object[] values = new Object[paramTypes.length];
		try {
			for (int i = 0; i < paramTypes.length; i++) {
				String value = map.get(params[i]) == null ? "" : map.get(params[i])[0];
				Object obj = value;
				if (paramTypes[i] != java.lang.String.class)
					obj = ObjectMapperWrap.getObjectMapper().readValue(value, paramTypes[i]);
				values[i] = obj;
			}
		} catch (Exception e) {
			throw new RequestException(300, "", "请求参数不合法");
		}
		return values;
	}

	/**
	 * 加载所有services文件
	 * 
	 * @param path
	 * @return
	 */
	private String[] traverseFolder(String path) {
		List<String> list = new ArrayList<>();
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				return new String[0];
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						// 文件夹
					} else {
						String name = file2.getName();
						if (name.endsWith("_services.xml")) {
							list.add(file2.getAbsolutePath());
						}
					}
				}
			}
		} else {
			logger.error(path + "_services.xml文件不存在", new NullPointerException());
		}
		return list.toArray(new String[list.size()]);
	}
}

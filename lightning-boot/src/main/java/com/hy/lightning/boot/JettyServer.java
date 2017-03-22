package com.hy.lightning.boot;

import java.io.File;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.hy.lightning.boot.util.XmlUtil;
import com.hy.lightning.boot.web.filter.BaseFilter;

public class JettyServer {
	
	public static void run(int port, Class<?> ...serviceClasses) throws Exception {
		//自动生成配置文件
		XmlUtil.run(serviceClasses);
		
		//加载log4j
		initLog4j();
		
		// 创建Server对象，并绑定端口
		Server server = new Server(port);
		ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
		servletContextHandler.setContextPath("/");
		server.setHandler(servletContextHandler);
		
		// 添加filter
		servletContextHandler.addFilter(new FilterHolder(new BaseFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));
		
		// 添加servlet
		servletContextHandler.addServlet(new ServletHolder(new ServletMapper()), "/");
		
		// 启动jetty
		server.start();
		server.join();
	}

	private static void initLog4j() {
		String path = ClassLoader.getSystemResource("log4j.properties").getPath();
		String log_dir = String.format("%slogs%s", path, File.separator);
		System.setProperty("log.dir", log_dir);
		PropertyConfigurator.configure(path);
		System.out.println("log4j is initialize ok !");
	}
}

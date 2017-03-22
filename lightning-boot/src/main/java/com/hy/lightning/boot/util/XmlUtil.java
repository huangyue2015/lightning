package com.hy.lightning.boot.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {

	public static void run(Class<?>... serviceClasses) {
		String contextPath = ClassLoader.getSystemResource("").getPath();
		run(contextPath, serviceClasses);
	}
	
	public static void run(String webcontextPath, Class<?>... serviceClasses) {
		try {
			produceXML(webcontextPath, serviceClasses);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void produceXML(String webcontextPath, Class<?>... cs) throws IOException {
		for (Class<?> c : cs) {
			produceXML(webcontextPath + c.getSimpleName(), c);
		}
	}

	private static int i;

	public static void produceXML(String outputPath, Class<?> c) throws IOException {
		String classPath = c.getName();
		String className = c.getSimpleName().replace("Impl", "");
		Method[] ms = c.getDeclaredMethods();

		Element root = DocumentHelper.createElement("services");
		Document document = DocumentHelper.createDocument(root);
		for (Method method : ms) {
			if (method.toString().contains("private") || method.getName().equals(c.getSimpleName()))
				continue;
			System.out.println(method.getName() + i++);
			// 给根节点添加孩子节点
			Element element1 = root.addElement("service");
			element1.addAttribute("name", String.format("/%s/%s", className, method.getName()))
					.addAttribute("classPath", classPath);
			element1.addAttribute("params", getParams(method.getParameters()));
			element1.addAttribute("paramTypes", getParamTypes(method.getParameterTypes()));
		}
		// 把生成的xml文档存放在硬盘上 true代表是否换行
		OutputFormat format = new OutputFormat("    ", true);
		format.setEncoding("UTF-8");// 设置编码格式
		XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(outputPath + "_services.xml"), format);
		xmlWriter.write(document);
		xmlWriter.close();
	}

	private static String getParams(Parameter... params) {
		StringBuilder sb = new StringBuilder();
		boolean b = true;
		for (Parameter c : params) {
			if (b) {
				b = false;
				sb.append(c.getName());
			} else {
				sb.append("," + c.getName());
			}
		}
		return sb.toString();
	}

	private static String getParamTypes(Class<?>... paramTypes) {
		StringBuilder sb = new StringBuilder();
		boolean b = true;
		for (Class<?> c : paramTypes) {
			if (b) {
				b = false;
				sb.append(c.getName());
			} else {
				sb.append("," + c.getName());
			}
		}
		return sb.toString();
	}

}
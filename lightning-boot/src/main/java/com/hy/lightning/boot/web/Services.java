package com.hy.lightning.boot.web;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hy.lightning.boot.util.StringUtil;

public class Services {
	
	private  Map<String,Service> map = new HashMap<>();
	
	private String[] servicesXmlPaths;
	
	public Services(String[] servicesXmlPaths) {
		this.servicesXmlPaths = servicesXmlPaths;
	}
	
	public Services loadServices() throws DocumentException {
		SAXReader reader = new SAXReader();
		for(String servicesXmlPath : servicesXmlPaths) {
			Document document = reader.read(new File(servicesXmlPath));
			Element root = document.getRootElement();
			List<?> elements = root.elements("service");
			for(Iterator<?> it = elements.iterator(); it.hasNext();) {
				Element e = (Element) it.next();
				String name = e.attributeValue("name");
				String classPath = e.attributeValue("classPath");
				String params = e.attributeValue("params");
				String paramTypes = e.attributeValue("paramTypes");
				String[] _params = StringUtil.isNullOrEmpty(params) ? new String[0] : params.split(",");
				String[] _paramTypes = StringUtil.isNullOrEmpty(paramTypes) ? new String[0] : paramTypes.split(",");
				Service service = new Service(name, classPath, _params, _paramTypes);
				this.map.put(name, service);
			}
		}
		return this;
	}
	
	public Map<String,Service> getServices() {
		return this.map;
	}
	
	public Service getService(String name) {
		return map.get(name);
	}
	
	public class Service {
		private String name;
		
		private String classPath;
		
		private String[] params;
		
		private String[] paramTypes;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getClassPath() {
			return classPath;
		}

		public void setClassPath(String classPath) {
			this.classPath = classPath;
		}

		public String[] getParams() {
			return params;
		}

		public void setParams(String[] params) {
			this.params = params;
		}

		public String[] getParamTypes() {
			return paramTypes;
		}

		public void setParamTypes(String[] paramTypes) {
			this.paramTypes = paramTypes;
		}

		public Service(String name, String classPath, String[] params, String[] paramTypes) {
			this.name = name;
			this.classPath = classPath;
			this.params = params;
			this.paramTypes = paramTypes;
		}
		
		@Override
		public String toString() {
			return String.format("name:%s,classPath:%s,params:(%s),paramTypes:(%s)", name, classPath, Arrays.deepToString(params), Arrays.deepToString(paramTypes));
		}
	}
}

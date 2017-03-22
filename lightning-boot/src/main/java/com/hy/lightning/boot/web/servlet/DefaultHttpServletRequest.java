package com.hy.lightning.boot.web.servlet;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DefaultHttpServletRequest extends HttpServletRequestWrapper
{

	private Map<String,String[]> map = new ConcurrentHashMap<>();
	
	public DefaultHttpServletRequest(HttpServletRequest request)
	{
		super(request);
		init();
	}

	@SuppressWarnings("rawtypes")
	private void init()
	{
		Map<String,String[]> superMap = super.getParameterMap();
		for(Iterator iter=superMap.entrySet().iterator();iter.hasNext();)
		{  
	        Map.Entry element=(Map.Entry)iter.next();  
	        Object strKey = element.getKey(); 
	        String[] value=(String[])element.getValue();
	        if(strKey != null)
	        map.put(strKey.toString(), value);
		}
	}
	
	@Override
	public Map<String,String[]> getParameterMap()
	{
		return this.map;
	}
	
	@Override
	public String getParameter(String name)
	{
		if(getParameterValues(name)!=null)
			return getParameterValues(name)[0];
		return null;
	}
	
	@Override
	public String[] getParameterValues(String name)
	{
		return this.map.get(name);
	}
}

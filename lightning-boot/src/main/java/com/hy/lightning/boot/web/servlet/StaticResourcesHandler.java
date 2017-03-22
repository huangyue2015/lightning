package com.hy.lightning.boot.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StaticResourcesHandler {

	private static final String[] STATIC_RESOURCES = {"jpg","png","gif","html"};
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public StaticResourcesHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public boolean exist(String name) {
		for(String s : STATIC_RESOURCES){
			if(s.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean mapping() {
		String path = request.getServletPath();
		if(path.contains(".")) {
			path = path.substring(path.lastIndexOf(".")+1);
//			System.out.println(path);
		}
		if(exist(path)) {
			try {
				doService();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	private void doService() throws IOException {
		String path = request.getServletPath();
		System.out.println(request.getServletContext().getRealPath(path));
		File file = new File(request.getServletContext().getRealPath(path));
		OutputStream outputStream = response.getOutputStream();
		InputStream in = null;
		byte[] rel = null;
		if(file.exists() && file.isFile()) {
			in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while ((len = in.read(bytes))!= -1) {
				out.write(bytes, 0, len);
			}
			rel = out.toByteArray();
			in.close();
		}else {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			rel = "404".getBytes();
		}
		outputStream.write(rel);
		outputStream.flush();
		outputStream.close();
	}
}

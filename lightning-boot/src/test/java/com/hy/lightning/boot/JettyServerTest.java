package com.hy.lightning.boot;

import java.util.HashMap;
import java.util.Map;

import com.hy.lightning.boot.util.Http;

public class JettyServerTest {

	public static void main(String[] arges) throws Exception {
		Thread main = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					JettyServer.run(8081, TestService.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		main.start();
		Map<String,String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age", "12");
		String rel = Http.post("http://localhost:8081/TestService/sayHello2", map);
		System.out.println(rel);
	}
}

class TestService {
	public String sayHello() {
		return "HelloWorld";
	}
	
	public String sayHello2(String name, Integer age) {
		return name + age;
	}
}

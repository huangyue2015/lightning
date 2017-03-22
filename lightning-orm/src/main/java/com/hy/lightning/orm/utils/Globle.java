package com.hy.lightning.orm.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Globle {

	private static ExecutorService executorService;

	/**
	 * 初始化线程池
	 */
	static {
		executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 100, 120L,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000));
	}

	/**
	 * 在线程池中执行任务
	 * 
	 * @param task
	 */
	public static void excute(Runnable task) {
		executorService.execute(task);
	}

	public static boolean isWindos() {
		String os_name = System.getProperty("os.name").toLowerCase();
		if (os_name.contains("windows")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 通过运行系统判定当前环境是local or remote
	 * @return
	 */
	public static String environment() {
		if(isWindos())
			return "local";
		else 
			return "remote";
	}
}

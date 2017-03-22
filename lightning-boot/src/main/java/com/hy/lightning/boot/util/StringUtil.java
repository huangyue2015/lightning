package com.hy.lightning.boot.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StringUtil {

	public static void main(String[] args) {
		String s = "1,2,3,4,5,6,,,1,2,3";
		String _s = "10";
		System.out.println(isContain2(s, _s));
	}

	public static boolean isNullOrEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}

	public static boolean isNotNullOrEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return false;
		return true;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	/**
	 * 生成4位随机数(纯数字)
	 */
	public static String getRandomNum4() {
		return (int) (Math.random() * 9000 + 1000) + "";
	}

	/**
	 * 将List转化为逗号分隔的字符串
	 */
	public static String getStringByList(List<String> list) {
		return getStringByList(list.toArray(new String[list.size()]));
	}

	/**
	 * 将数组转化为逗号分隔的字符串
	 */
	public static String getStringByList(String[] list) {
		if (list == null)
			return null;
		StringBuilder stringBuilder = new StringBuilder();
		boolean flag = true;
		for (String s : list) {
			if (flag) {
				if (StringUtil.isNotNullOrEmpty(s)) {
					stringBuilder.append(s);
				}
				flag = false;
			} else {
				if (StringUtil.isNotNullOrEmpty(s)) {
					stringBuilder.append("," + s);
				}
			}
		}
		return stringBuilder.toString();
	}

	public static String catString2(String arr, String _arr) {
		if (arr == null)
			return null;
		if (_arr == null)
			return arr;
		String[] ss = _arr.split(",");
		for (String s : ss) {
			arr = catString(arr, s);
		}
		return arr;
	}

	public static String catString(String arr, String _s) {
		if (arr == null)
			return "";
		if (_s == null)
			return arr;
		String[] ss = arr.split(",");
		List<String> list = new ArrayList<>();
		for (String s : ss) {
			if (!_s.equals(s))
				list.add(s);
		}
		return getStringByList(list);
	}

	public static Set<String> distinct(String[] arr) {
		Map<String, String> map = new HashMap<>();
		for (String s : arr) {
			if (StringUtil.isNotNullOrEmpty(s)) {
				map.put(s, "");
			}
		}
		return map.keySet();
	}

	public static String distinct(String _s) {
		String[] arr = _s.split(",");
		Map<String, String> map = new HashMap<>();
		for (String s : arr) {
			if (StringUtil.isNotNullOrEmpty(s)) {
				map.put(s, "");
			}
		}
		List<String> list = new ArrayList<>();
		for (String str : map.keySet())
			list.add(str);

		return getStringByList(list);
	}

	public static boolean isContain(String ss, String s) {
		if (isNullOrEmpty(ss) || isNullOrEmpty(s))
			return false;
		for (String _s : ss.split(",")) {
			if (s.equals(_s))
				return true;
		}
		return false;
	}

	public static int isContain2(String base, String s) {
		if (isNullOrEmpty(base) || isNullOrEmpty(s))
			return 0;
		for (String _s : base.split(",")) {
			if (s.equals(_s))
				return 1;
		}
		return 0;
	}

	/**
	 * 笔记点赞，收藏，以及关注专用
	 * 
	 * @throws Exception
	 */
	public static String dealString(int type, String base, String arg) throws Exception {
		if (type == 1)// 点赞。。收藏，，，关注
		{
			if (isContain(base, arg))
				return base;
			else
				return addString(base, arg);
		} else if (type == 0) {
			if (isContain(base, arg))
				return catString(base, arg);
			else
				return base;

		}
		return base;
	}

	public static String addString(String base, String arg) {
		if (StringUtil.isNullOrEmpty(base) && StringUtil.isNotNullOrEmpty(arg))
			return arg;
		return base + "," + arg;
	}

	public static void checkformatparam(String... args) throws Exception {
		for (String s : args) {
			if (StringUtil.isNullOrEmpty(s))
				throw new Exception("参数不能为空");
		}
	}
}

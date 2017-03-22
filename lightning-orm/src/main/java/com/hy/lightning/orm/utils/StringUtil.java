package com.hy.lightning.orm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class StringUtil {

	public static void main(String[] args) {
		System.out.println(areaidFormitMax("440305"));
		System.out.println(areaidFormitMin("110105"));
		String arr = getStringByList(new String[] { "1", "2" });
		System.out.println(arr);
		System.out.println(catString("123,hy,zhansan,ls", "123"));
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

	/**
	 * 获取32位UUID
	 * 
	 * @return
	 */
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
		for (String s : list) {
			if (StringUtil.isNotNullOrEmpty(s)) {
				stringBuilder.append(","+s);
			}
		}
		return stringBuilder.toString().substring(1);
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

	/**
	 * 获取地区编码最小值
	 * 
	 * @param areaid
	 * @return
	 */
	public static int areaidFormitMin(String areaid) {
		String[] areaids = { "11", "12", "31", "50", "71", "81", "82" };
		boolean b = false;
		for (String a : areaids) {
			if (areaid.startsWith(a)) {
				b = true;
				break;
			}
		}
		if (b) {
			areaid = (areaid + "0000").substring(0, 2) + "0000";
		} else {
			areaid = (areaid + "0000").substring(0, 4) + "00";
		}
		return Integer.valueOf(areaid);
	}

	/**
	 * 获取地区编码最大值
	 * 
	 * @param areaid
	 * @return
	 */
	public static int areaidFormitMax(String areaid) {
		String[] areaids = { "11", "12", "31", "50", "71", "81", "82" };
		boolean b = false;
		for (String a : areaids) {
			if (areaid.startsWith(a)) {
				b = true;
				break;
			}
		}
		if (b) {
			areaid = (areaid + "0000").substring(0, 2) + "9999";
		} else {
			areaid = (areaid + "0000").substring(0, 4) + "99";
		}
		return Integer.valueOf(areaid);
	}
}

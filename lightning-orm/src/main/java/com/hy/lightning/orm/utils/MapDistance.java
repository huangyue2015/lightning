package com.hy.lightning.orm.utils;

public class MapDistance {

	/**
	 * 计算地球上任意两点(经纬度)距离
	 * 
	 * @param long1
	 *            第一点经度
	 * @param lat1
	 *            第一点纬度
	 * @param long2
	 *            第二点经度
	 * @param lat2
	 *            第二点纬度
	 * @return 返回距离 单位：米
	 */
	public static double getDistance(double long1, double lat1, double long2, double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	public static void main(String[] args) {
		// 济南国际会展中心经纬度：117.11811 36.68484
		// 趵突泉：117.00999000000002 36.66123
		System.out.println(getDistance(114.287966,30.778198, 114.053401,22.569929));

		// System.out.println(getAround("117.11811", "36.68484", "13000"));
		// 117.01028712333508(Double), 117.22593287666493(Double),
		// 36.44829619896034(Double), 36.92138380103966(Double)

	}

}
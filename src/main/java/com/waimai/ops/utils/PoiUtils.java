package com.waimai.ops.utils;

public class PoiUtils {

	/**
	 * 计算地球两点之间的距离
	 * 
	 * @param firstLat
	 * @param firstLng
	 * @param secondLat
	 * @param secondLng
	 * @return
	 */
	public static Double getDistance(double firstLat, double firstLng, double secondLat, double secondLng) {
		Double s = 0.0;
		Double firstlatRad = rad(firstLat);
		Double firstlngRad = rad(firstLng);
		Double secondlatRad = rad(secondLat);
		Double secondlngRad = rad(secondLng);

		Double latResult = firstlatRad - secondlatRad;
		Double lngResult = firstlngRad - secondlngRad;
		s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(latResult / 2), 2)
				+ Math.cos(firstlatRad) * Math.cos(secondlatRad) * Math.pow(Math.sin(lngResult / 2), 2)));
		s = s * 6378137; // 地球半径 6378.137
		return s;

	}

	// 弧度转换
	private static double rad(Double location) {
		return location * Math.PI / 180.0;
	}

	public static void main(String args[]) {
		System.out.println("test ");
		//Double distance = getDistance(31.143764,97.170224,31.143764,97.170235);
		Double distance = getDistance(40.006184,116.488136,40.006344,116.487984);
		System.out.println("distance:" + String.valueOf(distance));
	}

}

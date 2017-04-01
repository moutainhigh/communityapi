package net.okdi.core.util;

/**
 * 
 * @author 
 * @version V1.0
 */
public class DistanceUtil {

	private static final double EARTH_RADIUS = 6378.137 * 1000;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>计算两组坐标点之间的距离(m)</dd>
	 * <dt><span class="strong">作者:</span></dt><dd></dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-9 上午11:52:25</dd>
	 * @param lat1 纬度1
	 * @param lng1 经度1
	 * @param lat2 纬度2
	 * @param lng2 经度2
	 * @return 108029.0(m)
	 * @since v1.0
	 */
	public static double getDistance(double lat1, double lng1, double lat2,double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
//		System.out.println(" getDistance 两个坐标的距离是 = "+s);
		return s;
	}
	
	public static void main(String[] args){
		System.out.println(DistanceUtil.getDistance(39.871362789040,116.385368559100, 39.142448048100, 117.215741081410));
	}
	
	/**
	 * 判断两点距离是否小在给定范围内，如果在给定的距离内返回 true 否则返回 false
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @param standard 给定的距离
	 * @return 
	 */
	public static boolean isInDistance(double lat1, double lng1, double lat2,double lng2,double standard){
//		System.out.println("in isInDistance");
		try{
			double d1=getDistance(lat1,lng1,lat2,lng2);
			if(d1<=standard){
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(" isInDistance  出错！"+ex.getMessage());
			return false;
		}
		return false;
	}
	
	/**
	 * 	1002-发货商家
		1060：单位客户
		1000 网络
		1003 网络直营站点
		1006 加盟公司
		1008 加盟公司站点
		1030 快递代理点
	 * @return 1,商家，2 企业)
	 */
	public static int retType(String strtype){
//
//		if(strtype.trim().equals("1002")){
//			return 1;
//		}
		if(strtype.trim().equals("1060")){
			return 2;
		}
		
		return 1;
	}
	
}

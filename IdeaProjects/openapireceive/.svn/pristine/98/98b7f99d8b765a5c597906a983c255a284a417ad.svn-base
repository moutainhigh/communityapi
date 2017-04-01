package net.okdi.core.util;

public class ElectronicFenceUtil {
	/**
	 * 
	 * 功能描述: 判断点是否在多边形内
	 * 创建人:  翟士贺
	 * 创建时间：Aug 13, 2014 3:57:16 PM
	 * 修改人：翟士贺
	 * 修改时间：Aug 13, 2014 3:57:16 PM
	 * 修改备注：
	 * @param x经度
	 * @param y纬度
	 * @param polyX经度数组
	 * @param polyY纬度数组
	 * @return boolean true在多边形内/false不在多边形内
	 *
	 */
	public static boolean pointInPolygon(double x , double y ,double[] polyX , double[] polyY) {
   	  int polySides=polyX.length;
   	  int   i,j=polySides-1 ;
   	  boolean  oddNodes=false;

   	  for (i=0;i<polySides; i++) {
   	    if((polyY[i]< y && polyY[j]>=y  //线段端点一个在扫描线下方，一个在扫描线上或上方
   	    ||   polyY[j]<y && polyY[i]>=y)
   	    && (polyX[i]<=x || polyX[j]<=x)) {//有一个端点在扫描线左侧或中心点
   	    	oddNodes^=(polyX[i]+(y-polyY[i])/(polyY[j]-polyY[i])*(polyX[j]-polyX[i])<x);
   	     }
   	    j=i;
   	  }
   	  return oddNodes; 
	}
	/**
	 * 
	 * 功能描述: 判断点是否在多边形内
	 * 创建人:  翟士贺
	 * 创建时间：Aug 13, 2014 3:57:16 PM
	 * 修改人：翟士贺
	 * 修改时间：Aug 13, 2014 3:57:16 PM
	 * 修改备注：
	 * @param x经度
	 * @param y纬度
	 * @param polyX经度字符串
	 * @param polyY纬度字符串
	 * @return boolean true在多边形内/false不在多边形内
	 *
	 */
	public static boolean pointInPolygon(double x , double y ,String polyXs , String polyYs) {
		String [] polyXss = polyXs.split(",");
		String [] polyYss = polyYs.split(",");
		if(polyXss.length!=polyYss.length||polyXss.length ==0||polyYss.length==0){return false;}
		double [] polyX = new double[polyXss.length];
		double [] polyY = new double[polyYss.length];
		for (int i = 0;i<polyXss.length;i++){
			polyX[i] = Double.parseDouble(polyXss[i]);
			polyY[i] = Double.parseDouble(polyYss[i]);
		}
     return pointInPolygon(x,y,polyX,polyY);
	}
}

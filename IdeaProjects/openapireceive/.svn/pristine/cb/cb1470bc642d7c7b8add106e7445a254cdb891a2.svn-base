package net.okdi.api.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.okdi.api.dao.DicAddressMapper;
import net.okdi.api.entity.DicAddressaid;
import net.okdi.api.entity.DicAddress;
import net.okdi.api.service.DicAddressService;
import net.okdi.core.util.PubMethod;
@Service
public class DicAddressServiceImpl implements DicAddressService{
	private static double EARTH_RADIUS = 6378.137 * 1000;
	@Autowired
	DicAddressMapper parseAddrDao;
	
	@Override
	public Map<String,Object> parseAddrByList(String lat, String lng) {
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("lat",lat);
		parameterMap.put("minlat",Double.parseDouble(lat)-0.1);
		parameterMap.put("maxlat",Double.parseDouble(lat)+0.1);
		parameterMap.put("lng",lng);
		parameterMap.put("minlng",Double.parseDouble(lng)-0.1);
		parameterMap.put("maxlng",Double.parseDouble(lng)+0.1);
		List<Map<String,Object>>dicAddressList  =  parseAddrDao.DicAddressByparseAddr(parameterMap);
	    for (Map map :dicAddressList) {
	        	map.put("distance", getDistance(Double.parseDouble(lat),Double.parseDouble(lng),Double.parseDouble(map.get("lat").toString()), Double.parseDouble(map.get("lng").toString())));
		}
	    Collections.sort(dicAddressList,new ComparatorList());
	    
		return dicAddressList.get(0);
}
	
	public class ComparatorList implements Comparator{
		public int compare(Object arg0, Object arg1) {
			  int flag;
			  Map map0=(Map)arg0;
			  Map map1=(Map)arg1;
			  double distance0=Double.parseDouble(map0.get("distance").toString());
			  double distance1=Double.parseDouble(map1.get("distance").toString());
			  if(distance0>distance1)
				  flag=1;
			  else if(distance0<distance1)
				  flag=-1;
			  else
				  flag=0;
			  return flag;
			 }
		}
	private  double rad(double d) {
		return d * Math.PI / 180.0;
	}
	public  double getDistance(double lat1, double lng1, double lat2,double lng2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}
	@Override
	public Map<String,Object> getObjectByPrimaryKey(Long addressId) {
		DicAddressaid ResultDicAddressaid =  parseAddrDao.getObjectByPrimaryKey(addressId);
		Map<String,Object> Resultmap = new HashMap<String,Object>();
		Resultmap.put("addressId",addressId);
		String cityName = ResultDicAddressaid.getCityName();//Add by ccs 20141223
		if(PubMethod.isEmpty(cityName)) {
			cityName = "";
		}
		cityName = cityName.replaceAll("北京市区", "").replaceAll("天津市区", "").replaceAll("重庆市区", "").replaceAll("上海市区", "");//Add by ccs 20141223
		
		//地址级别错误，需要修改下 Edit by ccs 20150102
		String countyName = ResultDicAddressaid.getCountyName();
		
		//countyName = countyName.replaceAll("城区", "");//Add by ccs 20141223   //所属区/县名称 
		//String addressName =  ResultDicAddressaid.getProvinceName()+cityName+townName;
		String addressName =  ResultDicAddressaid.getProvinceName()+cityName+countyName;
		
		String townName = ResultDicAddressaid.getTownName();//Add by ccs 20141223 //TOWN_NAME 所属乡镇名称
		if(PubMethod.isEmpty(townName)) {
			townName = "";
		}
		townName = townName.replaceAll("城区", "");//Add by ccs 20141223   //TOWN_NAME 所属乡镇名称
		Resultmap.put("addressName",addressName.replaceAll("null",""));
		
		//String lastLevelName =  ResultDicAddressaid.getCountyName()+ResultDicAddressaid.getVillageName();//Edit by ccs 20141223
		String lastLevelName =  townName+ResultDicAddressaid.getVillageName();//Edit by ccs 20141223
		if(PubMethod.isEmpty(lastLevelName)) {
			lastLevelName = "";
		}
		Resultmap.put("lastLevelName", lastLevelName.replaceAll("null",""));
		Resultmap.put("lastLevelId",addressId);
		Resultmap.put("townId",ResultDicAddressaid.getTownId());
		return Resultmap;
	}
}
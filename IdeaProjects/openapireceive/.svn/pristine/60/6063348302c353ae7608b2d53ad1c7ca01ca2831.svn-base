package net.okdi.apiExt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.service.RobInfoService;
import net.okdi.apiExt.service.ExtCompInfoService;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
@Service
public class ExtCompInfoServiceImpl implements ExtCompInfoService {

	@Autowired
	private BasCompInfoMapperV1 basCompInfoMapperV1;
	@Autowired
	private QueryNearInfoService queryNearInfoService;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	private RedisService redisService;
	@Override
	public List<Map<String, Object>> queryCompInfoByAddressAndRange(Double longitude,Double latitude, String range) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		//通过经纬度查询周边range这个值周围的站点
		Map<String,Object> mapParam = queryNearInfoService.queryFromPosition(longitude, latitude);
		list = this.basCompInfoMapperV1.queryCompInfoByRoleId(mapParam);
		//String distanceNumber = "";String distanceUnit = "";
		for(Map<String,Object> map : list){
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
			if(distance - Double.valueOf(range)*1000 <= 0 &&"1".equals(String.valueOf(map.get("compStatus")))){
				Map<String,Object> mapResult = new HashMap<String,Object>();
				mapResult.put("compId", String.valueOf(map.get("compId")));//站点id
				mapResult.put("compName", String.valueOf(map.get("compName")));//站点名称
				mapResult.put("compAddress", String.valueOf(map.get("compAddress")));//站点地址
				mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));//站点类型 1006 站点 1050 营业分部
				mapResult.put("compTelephone", String.valueOf(map.get("compTelephone")));//站点电话
				mapResult.put("distance", distance);//距离 m
				BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(Long.parseLong(String.valueOf(map.get("netId"))));
				mapResult.put("netId", basNetInfo.getNetId());//快递公司id
				mapResult.put("netName", basNetInfo.getNetName());//快递公司名称
				mapResult.put("memberInfoList", this.queryMemberInfoByCompId(String.valueOf(map.get("compId"))));//快递公司下面的收派员
				listResult.add(mapResult);
			}
		}
	       Collections.sort(listResult, new Comparator<Object>() {  
	           @SuppressWarnings("unchecked")
			public int compare(Object a, Object b) {
	        	   double one = Double.parseDouble(String.valueOf(((Map<String,Object>)a).get("distance")));  
	        	   double two = Double.parseDouble(String.valueOf(((Map<String,Object>)b).get("distance")));  
	        	   int k = (int)(one - two);
	             return k  ;   
	           }  
	        });
		return listResult;
	}
	@Override
	public List<Map<String, Object>> queryMemberInfoByCompId(String compId) {
//		List<Map<String,Object>> list = this.redisService.get("memberInfo-compId-cache", compId, List.class);
//		if(PubMethod.isEmpty(list)){
		List<Map<String,Object>> list = this.basCompInfoMapperV1.queryMemberInfoByCompId(compId);
//			this.redisService.put("memberInfo-compId-cache", compId, list);
//		}
		return list;
	}
	@Override
	public List<Map<String, Object>> queryBatchCompInfoByAddressAndRange(String listParam, String range) {
		List<Map<String,Object>> list = JSON.parseObject(listParam, List.class);
		List<Map<String,Object>> listReturn = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> mapData : list){//循环 查询
			Map<String,Object> mapReturn = new HashMap<String,Object>();
			Double longitude = Double.valueOf(String.valueOf(mapData.get("longitude")));
			Double latitude = Double.valueOf(String.valueOf(mapData.get("latitude")));
			String sendAddress = String.valueOf(mapData.get("sendAddress"));
			List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
			//用每个经纬度去查询附近新增的站点
			//通过经纬度查询周边range这个值周围的站点
			Map<String,Object> mapParam = queryNearInfoService.queryFromPosition(longitude, latitude);
			list = this.basCompInfoMapperV1.queryCompInfoByRoleId(mapParam);
			//String distanceNumber = "";String distanceUnit = "";
			for(Map<String,Object> map : list){
				BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(Long.parseLong(String.valueOf(map.get("compId"))));
				double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
				Long createTime = basCompInfo.getCreateTime().getTime();
				if(distance - Double.valueOf(range)*1000 <= 0 &&createTime + 7*24*60*60*1000>new Date().getTime()&&basCompInfo.getCompStatus()==1){
					Map<String,Object> mapResult = new HashMap<String,Object>();
					mapResult.put("compId", String.valueOf(map.get("compId")));//站点id
					mapResult.put("compName", String.valueOf(map.get("compName")));//站点名称
					mapResult.put("compAddress", String.valueOf(map.get("compAddress")));//站点地址
					mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));//站点类型 1006 站点 1050 营业分部
					mapResult.put("compTelephone", String.valueOf(map.get("compTelephone")));//站点电话
					mapResult.put("distance", distance);//距离 m
					BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(Long.parseLong(String.valueOf(map.get("netId"))));
					mapResult.put("netId", basNetInfo.getNetId());//快递公司id
					mapResult.put("netName", basNetInfo.getNetName());//快递公司名称
					mapResult.put("memberInfoList", this.queryMemberInfoByCompId(String.valueOf(map.get("compId"))));//快递公司下面的收派员
					listResult.add(mapResult);
				}
			}
		       Collections.sort(listResult, new Comparator<Object>() {  
		           @SuppressWarnings("unchecked")
				public int compare(Object a, Object b) {
		        	   double one = Double.parseDouble(String.valueOf(((Map<String,Object>)a).get("distance")));  
		        	   double two = Double.parseDouble(String.valueOf(((Map<String,Object>)b).get("distance")));  
		        	   int k = (int)(one - two);
		             return k  ;   
		           }  
		        });
		       mapReturn.put("sendAddress",  String.valueOf(mapData.get("sendAddress")));
		       mapReturn.put("compInfoList", listResult);
		       listReturn.add(mapReturn);
		}
		return listReturn;
	}
	@Override
	public Object queryBatchCompInfoByAddress(String listParam, String compId) {
		List<Map<String,Object>> list = JSON.parseObject(listParam, List.class);
		BasCompInfo basCompInfo = this.robInfoService.queryBasCompInfo(Long.parseLong(compId));
		BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(basCompInfo.getBelongToNetId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("compId", compId);
		map.put("compName", basCompInfo.getCompName());
		map.put("netId", basNetInfo.getNetId());
		map.put("netName", basNetInfo.getNetName());
		map.put("compAddress", basCompInfo.getCompAddress());
		map.put("compTelephone", basCompInfo.getCompTelephone());
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> mapData : list){
			Map<String,Object> mapDis = new HashMap<String,Object>();
			Double longitude = Double.valueOf(String.valueOf(mapData.get("longitude")));
			Double latitude = Double.valueOf(String.valueOf(mapData.get("latitude")));
			String sendAddress = String.valueOf(mapData.get("sendAddress"));
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(basCompInfo.getLatitude())), Double.parseDouble(String.valueOf(basCompInfo.getLongitude())));
			mapDis.put("distance", distance);
			mapDis.put("sendAddress", sendAddress);
			mapDis.put("compId", basCompInfo.getCompId());
			mapDis.put("compName", basCompInfo.getCompName());
			listResult.add(mapDis);
		}
		map.put("listResult", listResult);
		return map;
	}

}

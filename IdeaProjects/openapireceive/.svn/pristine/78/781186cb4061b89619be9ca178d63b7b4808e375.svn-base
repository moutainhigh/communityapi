package net.okdi.apiV1.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasNetInfo;
import net.okdi.api.service.RobInfoService;
import net.okdi.apiV1.dao.BasCompInfoMapperV1;
import net.okdi.apiV1.dao.BasCompimageMapperV1;
import net.okdi.apiV1.service.NearComInfoService;
import net.okdi.core.util.DistanceUtil;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NearComInfoServiceImpl implements NearComInfoService{
	private static long DISTANCE_VALUE = 5000;
	@Value("${compPic.readPath}")
	public String readPath;
	@Autowired
	private RobInfoService robInfoService;
	@Autowired
	private BasCompInfoMapperV1 basCompInfoMapperV1;
	@Autowired
	private BasCompimageMapperV1 compimageMapper;
	@Override
	public List<Map<String, Object>> queryCompInfo(Double longitude, Double latitude) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapParam = this.queryFromPosition(longitude,latitude);
		
		
			list = this.basCompInfoMapperV1.queryCompInfo(mapParam);
		
		for(Map<String,Object> map : list){
			double distance=DistanceUtil.getDistance(latitude, longitude, Double.parseDouble(String.valueOf(map.get("latitude"))), Double.parseDouble(String.valueOf(map.get("longitude"))));
			if(distance - DISTANCE_VALUE <= 0){
				Map<String,Object> mapResult = new HashMap<String,Object>();
				mapResult.put("compId", String.valueOf(map.get("compId")));
				mapResult.put("compName", String.valueOf(map.get("compName")));
				mapResult.put("compAddress", String.valueOf(map.get("compAddress")));
				mapResult.put("compTypeNum", String.valueOf(map.get("compTypeNum")));
				if((distance/1000)>1){
					mapResult.put("distance", distance/1000);
					mapResult.put("unit", "km");
				}else{
					mapResult.put("distance", distance);
					mapResult.put("unit", "m");
				}
				
				BasNetInfo basNetInfo = this.robInfoService.queryBasNetInfo(Long.parseLong(String.valueOf(map.get("netId"))));
				mapResult.put("netId", basNetInfo.getNetId());
				mapResult.put("netName", basNetInfo.getNetName());
				Map<String,Object> mapImg = this.compimageMapper.queryCompPic(Long.parseLong(String.valueOf(map.get("compId"))));
				mapResult.put("compImgUrl", PubMethod.isEmpty(mapImg)?"": readPath+String.valueOf(mapImg.get("imageUrl")));
				mapResult.put("longitude", String.valueOf(map.get("longitude")));
				mapResult.put("latitude", String.valueOf(map.get("latitude")));
				mapResult.put("resPhone", String.valueOf(map.get("resPhone")));
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
	public Map<String,Object> queryFromPosition(Double longitude, Double latitude) {
		int dis=5;//设置5公里范围内
		double EARTH_RADIUS = 6378.137 * 1000;
		double dlng = Math.abs(2 * Math.asin(Math.sin(dis*1000 / (2 * EARTH_RADIUS)) / Math.cos(latitude)));
		dlng = dlng*180.0/Math.PI;        //弧度转换成角度
		double dlat = Math.abs(dis*1000 / EARTH_RADIUS);
		dlat = dlat*180.0/Math.PI;     //弧度转换成角度
		double bottomLat=latitude - dlat;
		double topLat=latitude + dlat;
		double leftLng=longitude - dlng;
		double rightLng=longitude + dlng;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("bottomLat", bottomLat);
		params.put("topLat", topLat);
		params.put("leftLng", leftLng);
		params.put("rightLng", rightLng);
		return params;
		
	}
}

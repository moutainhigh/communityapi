package net.okdi.apiExt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiExt.service.ExtCompInfoService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.BaiDuClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class ExtCompInfoServiceImpl implements ExtCompInfoService {

	@Autowired
	private BaiDuClient baiDuClient;
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	public static final Log logger = LogFactory.getLog(ExtCompInfoServiceImpl.class);
	@Override
	public String queryCompInfoByAddressAndRange(String sendAddress, String range) {
		Double[] location = null ;
		try {
			location = baiDuClient.getResult(sendAddress);
		} catch (Exception e) {
			return null;
		}
		if(location == null || location.length==0){
			throw new ServiceException("publicapi.queryCompInfoByAddressAndRange.001", "数据请求失败");
		}
		Double longitude = location[0];
		Double latitude = location[1];
		logger.info("通过地址请求百度接口返回经纬度     longitude  :"+longitude+"    latitude  :"+latitude);
		//通过经纬度去openapi中去查这个经纬度附近Range公里之内的公司
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("longitude",String.valueOf(longitude));
		map.put("latitude",String.valueOf(latitude));
		map.put("range",range);
		String response = openApiHttpClient.doPassSendStr("extCompInfo/queryCompInfoByAddressAndRange/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.queryCompInfoByAddressAndRange.002","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryMemberInfoByCompId(String compId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStr("extCompInfo/queryMemberInfoByCompId/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.queryMemberInfoByCompId.001","数据请求异常");
		}
		return response;
	}
	@Override
	public String queryBatchCompInfoByAddressAndRange(String sendAddress, String range) {
		List<Map<String,Object>> listParam = new ArrayList<Map<String,Object>>();
		String[] sendAddressData = sendAddress.split(",");
		for(String sendAddr : sendAddressData){
			Map<String,Object> mapData = new HashMap<String,Object>();
			Double[] location = null ;
			try {
				location = baiDuClient.getResult(sendAddr);
			} catch (Exception e) {
				return null;
			}
			if(location == null || location.length==0){
				throw new ServiceException("publicapi.queryCompInfoByAddressAndRange.001", "数据请求失败");
			}
			Double longitude = location[0];
			Double latitude = location[1];
			logger.info("通过地址请求百度接口返回经纬度     longitude  :"+longitude+"    latitude  :"+latitude);
			mapData.put("longitude", longitude);
			mapData.put("latitude", latitude);
			mapData.put("sendAddress", sendAddr);
			listParam.add(mapData);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("listParam",JSON.toJSONString(listParam));
		map.put("range",range);
		String response = openApiHttpClient.doPassSendStr("extCompInfo/queryBatchCompInfoByAddressAndRange/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.queryBatchCompInfoByAddressAndRange.001","数据请求异常");
		}
		return response;
		
	}
	@Override
	public String queryBatchCompInfoByAddress(String sendAddress, String compId) {
		List<Map<String,Object>> listParam = new ArrayList<Map<String,Object>>();
		String[] sendAddressData = sendAddress.split(",");
		for(String sendAddr : sendAddressData){
			Map<String,Object> mapData = new HashMap<String,Object>();
			Double[] location = null ;
			try {
				location = baiDuClient.getResult(sendAddr);
			} catch (Exception e) {
				return null;
			}
			if(location == null || location.length==0){
				throw new ServiceException("publicapi.queryBatchCompInfoByAddress.001", "数据请求失败");
			}
			Double longitude = location[0];
			Double latitude = location[1];
			logger.info("通过地址请求百度接口返回经纬度     longitude  :"+longitude+"    latitude  :"+latitude);
			mapData.put("longitude", longitude);
			mapData.put("latitude", latitude);
			mapData.put("sendAddress", sendAddr);
			listParam.add(mapData);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("listParam",JSON.toJSONString(listParam));
		map.put("compId",compId);
		String response = openApiHttpClient.doPassSendStr("extCompInfo/queryBatchCompInfoByAddress/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("publicapi.queryBatchCompInfoByAddress.002","数据请求异常");
		}
		return response;
	}

}

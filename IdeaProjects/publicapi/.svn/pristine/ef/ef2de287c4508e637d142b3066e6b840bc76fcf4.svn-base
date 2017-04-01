package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.okdi.apiV1.service.LoginService;
import net.okdi.apiV1.service.QueryNearInfoService;
import net.okdi.apiV1.service.ShopService;
import net.okdi.apiV4.service.impl.TaskMassNoticeServiceImpl;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;


/**
 * @author 作者 :xingwei.zhang
 * @version 创建时间：2016-11-22 下午6:22:58
 * 类说明
 */
@Service
public class ShopServiceImpl extends AbstractHttpClient implements ShopService{
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	private static Logger logger = Logger.getLogger(TaskMassNoticeServiceImpl.class);
	@Override
	public String fetchRanges(String memberId,String fetchRangeData){
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("fetchRange", fetchRangeData);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/fetchRanges", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String takeTime(String memberId,String startTime, String endTime){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/takeTime", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
		
	}
	@Override
	public String pickupPrice(String memberId, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("region", region);
		map.put("fetchPrice", fetchPrice);
		map.put("renewPrice", renewPrice);
		map.put("netName", netName);
		map.put("photoUrl", photoUrl);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/pickupPrice", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
		
	}
	@Override
	public String serviceExplain(String memberId, String serviceDescription){
		logger.info("---pub----服务说明-serviceExplain()---->>>serviceDescription:"+serviceDescription);
		//  查询黑名单的敏感词
		Set<String> blackSet=sensitiveWordService.queryBlackList();
		SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
		boolean blackflag = blackFilter.isContaintSensitiveWord(serviceDescription, 1);
		if(blackflag){//blackflag||!whiteflag by zj 2016年10月19日 11:43:32
			logger.info("发送内容不符合要求黑词");
			Map<String,Object> errorMap = new HashMap<String,Object>();
			Set<String> words = blackFilter.getSensitiveWord(serviceDescription, 1);//获取命中的敏感词
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
			return JSON.toJSONString(errorMap);
		}	
		logger.info("-------------------->>>serviceDescription:"+serviceDescription);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("serviceDescription", serviceDescription);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/serviceExplain", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
		
	}
	@Override
	public String queryShopInfo(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/queryShopInfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String queryPickupPrice(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/queryPickupPrice", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String serviceExpress(String memberId, String serviceExpDate) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("serviceExpDate", serviceExpDate);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/serviceExpress", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String queryServiceExpress(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {	
			result = openApiHttpClient.doPassSendStr("shop/queryServiceExpress", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String queryIsAdd(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {	
			result = openApiHttpClient.doPassSendStr("shop/queryIsAdd", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String queryFetchRanges(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/queryFetchRanges", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String deleteFetchRange(String Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Id", Id);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/deleteFetchRange", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String updateRegion(String Id, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Id", Id);
		map.put("region", region);
		map.put("fetchPrice", fetchPrice);
		map.put("renewPrice", renewPrice);
		map.put("netName", netName);
		map.put("photoUrl", photoUrl);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/updateRegion", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String deleteRegion(String Id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Id", Id);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/deleteRegion", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String storeShare(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("shop/storeShare", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
}

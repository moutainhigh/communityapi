package net.okdi.mob.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.ShopBroadcastInfoService;

@Service
public class ShopBroadcastInfoServiceImpl implements ShopBroadcastInfoService {
	
	@Autowired
	OpenApiHttpClient openApiHttpClient;

	Logger logger = Logger.getLogger(ShopBroadcastInfoServiceImpl.class);

	/**
	 * 抢送货(进行中,以完成)
	 */
	@Override
	public String queryTakeDeliveryStation(Short queryStatus, Long memberId, Integer pageNo, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("queryStatus", queryStatus);
		params.put("memberId", memberId);
		params.put("pageNo", pageNo);
		params.put("pageSize", pageSize);
		String result = openApiHttpClient.doPassSendStr("shopBroadcastInfo/queryTakeDeliveryStation", params);
		return result;
	}

	/**
	 * 抢
	 */
	@Override
	public String rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("broadcastId", broadcastId);
		map.put("memberId", memberId);
		map.put("addresseeLongitude", PubMethod.isEmpty(addresseeLongitude)? null :addresseeLongitude.toString());
		map.put("addresseeLatitude", PubMethod.isEmpty(addresseeLatitude)? null :addresseeLatitude.toString());
		String result = openApiHttpClient.doPassSendStr("shopBroadcastInfo/rob", map);
		return result;
	}

	/**
	 * 确认取件
	 */
	@Override
	public String take(Long broadcastId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("broadcastId", broadcastId);
		String result = openApiHttpClient.doPassSendStr("shopBroadcastInfo/take", params);
		return result;
	}
	
	@Override
	public String selectGrabDeliveryByMemberId(Long memberId,
			BigDecimal sendDeliveryLongitude, BigDecimal sendDeliveryLatitude, Integer pageNo, Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("sendDeliveryLongitude", sendDeliveryLongitude);
		map.put("sendDeliveryLatitude", sendDeliveryLatitude);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		return openApiHttpClient.doPassSendStr("shopBroadcastInfo/queryGrab", map);
		
	}

	@Override
	public String configDelivery(Long broadcastId, Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("broadcastId", broadcastId);
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("shopBroadcastInfo/configDelivery", map);
	}

	@Override
	public String cancelBroadcast(Long broadcastId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("broadcastId", broadcastId);
		return openApiHttpClient.doPassSendStr("shopBroadcastInfo/cancelBroadcast", map);
	}

	@Override
	public String statisticalNumber(Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("shopBroadcastInfo/statistical", map);
	}
	
	/**
	 * 	加缓存
	 */
	@Override
	public String saveMemberOnlineStatus(Long memberId,
			BigDecimal sendDeliveryLongitude, BigDecimal sendDeliveryLatitude) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId",memberId);
		map.put("sendDeliveryLongitude", sendDeliveryLongitude);
		map.put("sendDeliveryLatitude", sendDeliveryLatitude);
		String result  = openApiHttpClient.doPassSendStr("shopBroadcastInfo/saveMemberOnlineStatus", map);
		return result;
	}
	/**
	 * 该类的主要作用是通过订单号取订单详情
	 * @author yangkai
	 * @time 2015-7-22
	 */
	@Override
	public String queryOrderDetails(Long broadcastId,Double lat,Double lng) {
		Map map = new HashMap();
		map.put("broadcastId",broadcastId);
		map.put("lat", lat);
		map.put("lng", lng);
		String result  = openApiHttpClient.doPassSendStr("shopBroadcastInfo/queryOrderDetails", map);
		return result;
	}

}

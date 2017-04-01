package net.okdi.mob.service;

import java.math.BigDecimal;

public interface ShopBroadcastInfoService {
	public String queryTakeDeliveryStation(Short queryStatus, Long memberId,Integer pageNo,Integer pageSize);

	public String rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude);

	public String take(Long broadcastId);
	
	/**
	 * 		查询抢送货列表	
	 * @param memberId
	 * 			当前登录人id
	 * @param sendDeliveryLongitude
	 * 			送单人经度
	 * @param sendDeliveryLatitude
	 * 			送单人纬度
	 * @return
	 */
	public String selectGrabDeliveryByMemberId(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude, Integer pageNo, Integer pageSize);
	
	
	/**
	 * 		确定送达
	 * @param memberId
	 * 			当前登录人Id
	 * @param grabDeliveryId
	 * 			当前抢的这个单的Id
	 * 			
	 */
	public String configDelivery(Long grabDeliveryId ,Long memberId);
	
	/**
	 * 		取消广播
	 * @param broadcastId
	 * 			广播id
	 * @return 
	 */
	public String cancelBroadcast(Long broadcastId);
	
	
	/**
	 * 		统计订单数
	 * @param memberId
	 * @return
	 */
	public String statisticalNumber(Long memberId);
	
	/**
	 * 		加缓存
	 * @param memberId
	 * 			送派员Id
	 * @param sendDeliveryLongitude
	 * 			经度
	 * @param sendDeliveryLatitude
	 * 			纬度
	 * @return
	 */
	public String saveMemberOnlineStatus(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude);
	
	
	/**
	 * 该类的主要作用是通过订单号取订单详情
	 * @author yangkai
	 * @time 2015-7-22
	 * @param broadcastId 广播id
	 * @param lat 纬度
	 * @param lng 经度
	 */
	public String queryOrderDetails(Long broadcastId,Double lat,Double lng); 
}

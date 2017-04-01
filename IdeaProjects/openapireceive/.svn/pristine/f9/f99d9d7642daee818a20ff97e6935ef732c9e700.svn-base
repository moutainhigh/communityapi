package net.okdi.api.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ShopBroadcastInfo;

public interface ShopBroadcastInfoService {
	public Map<String, Object> queryTakeDeliveryStation(Short queryStatus, Long memberId,Integer pageNo,Integer pageSize);

	public Map<String, Object> querySingleDetails(Long broadcastId,
			Long memberId);

	public Map<String, Object> queryShopBroadcastInfoById(Long broadcastId);

	public void rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude);

	public void take(Long broadcastId);
	
	public ShopBroadcastInfo findByMemberId(Long memberId);
	
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
	public Map<String,Object> selectGrabDeliveryByMemberId(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude,Integer pageNo,Integer pageSize);
	
	
	/**
	 * 		确定送达
	 * @param memberId
	 * 			当前登录人Id
	 * @param grabDeliveryId
	 * 			当前抢的这个单的Id
	 * 			
	 */
	public void configDelivery(Long broadcastId ,Long memberId);
	
	
	/**
	 * 		取消广播
	 * @param broadcastId
	 * 			广播id
	 */
	public void cancelBroadcast(Long broadcastId);
	
	/**
	 * 		统计订单数
	 * @param memberId
	 * @return
	 */
	public Map<String,Object> statisticalNumber(Long memberId);
	
	/**
	 * 	加缓存
	 */
	public void currentMemberCacheInfor(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude);
	
	/**
	 * 	清缓存
	 */
	public void removeCurrentMemberCache(Long memberId);
	
	
	/**
	 * 	查看附近送派员列表
	 * @return
	 */
	public List<Long> queryNearMemberList(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude) throws ParseException ;
	
	/**
	 * 	查看附近送派员总数
	 * @param sendDeliveryLongitude
	 * @param sendDeliveryLatitude
	 * @return
	 */
	public int queryNearMemberCount(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude) throws ParseException;
	/**
	 * 呼叫配送员，传送订单信息
	 * @author yangkai
	 * @param senderName 发件人姓名
	 * @param senderPhone 发件人电话
	 * @param senderTownId 发件人乡镇地址id
	 * @param senderTownName 发件人乡镇地址名
	 * @param senderAddress 发件人详细地址
	 * @param senderLongitude 发件人地址经度
	 * @param senderLatitude 发件人地址纬度
	 * @param addresseeName 收货人姓名
	 * @param addresseePhone 收货人电话
	 * @param addresseeTownId 收货人乡镇id
	 * @param addresseeTownName 收货人乡镇名称
	 * @param addresseeAddress 收货人详细地址
	 * @param addresseeLongitude 收货人地址经度
	 * @param addresseeLatitude 收货人地址纬度
	 * @param tradeDistance 发货人到收货人距离
	 * @param shopName 店铺名称
	 * @param serviceTime 送达时间
	 * @param tradeNum 交易号
	 * @param productNum 商品数量
	 * @param collectionMoney 代收货款
	 * @param freightMoney 运费
	 * @param json 商品信息{商品名称，商品数量}
	 */
	public String saveOrderInfo(String senderName, String senderPhone,
			Long senderTownId, String senderTownName, String senderAddress,
			BigDecimal senderLongitude, BigDecimal senderLatitude,
			String addresseeName, String addresseePhone,
			Long addresseeTownId, String addresseeTownName,
			String addresseeAddress, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, BigDecimal tradeDistance, String shopName,String shopPhone,
			String serviceTime, String tradeNum, short productNum,
			BigDecimal collectionMoney, BigDecimal freightMoney, String json);

	/**
	 * 该方法的主要作用是通过订单号取订单详情
	 * @author yangkai
	 * @time 2015-7-22
	 * @param broadcastId 广播id
	 * @param lat 快递员纬度
	 * @param lng 快递员经度
	 */
	public Map<String,Object> queryOrderDetails(Long broadcastId,Double lat,Double lng);
}

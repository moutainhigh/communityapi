package net.okdi.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.api.entity.ShopBroadcastInfo;
import net.okdi.api.service.ShopBroadcastInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

@Controller
@RequestMapping("/shopBroadcastInfo")
public class ShopBroadcastInfoController extends BaseController {

	@Autowired
	ShopBroadcastInfoService shopBroadcastInfoService;
	
	ShopBroadcastInfo shopBroadcastInfo;

	/**
	 * <dt><span class="strong">作者:</span></dt><dd>caina.cun</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午17:40:00</dd>
	 * @param queryStatus	1进行中列表 2 已完成列表
	 * @param memberId	接单人ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTakeDeliveryStation", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String queryTakeDeliveryStation(Short queryStatus, Long memberId,Integer pageNo,Integer pageSize) {
		try {
			if (PubMethod.isEmpty(queryStatus) || (queryStatus!=1 && queryStatus !=2)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.queryTakeDeliveryStation.001",
						"queryStatus参数异常");
			}
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure(
						"openapi.shopBroadcastInfo.queryTakeDeliveryStation",
						"memberId不能为空");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap = shopBroadcastInfoService.queryTakeDeliveryStation(queryStatus, memberId,pageNo,pageSize);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * @param broadcastId	广播Id
	 * @param memberId		接单人Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querySingleDetails", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String querySingleDetails(Long broadcastId, Long memberId) {
		try {
			if (PubMethod.isEmpty(broadcastId)) {
				return paramsFailure(
						"openapi.shopBroadcastInfo.queryTakeDeliveryStation",
						"broadcastId不能为空");
			}
			if (PubMethod.isEmpty(memberId)) {
				return paramsFailure(
						"openapi.shopBroadcastInfo.queryTakeDeliveryStation",
						"memberId不能为空");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap = shopBroadcastInfoService.querySingleDetails(broadcastId,
					memberId);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 
	 * @param broadcastId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryShopBroadcastInfoById", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String queryShopBroadcastInfoById(Long broadcastId) {
		try {
			if (PubMethod.isEmpty(broadcastId)) {
				return paramsFailure("basNetinfo.queryBasCompinfoById.compId",
						"broadcastId不能为空");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap = shopBroadcastInfoService
					.queryShopBroadcastInfoById(broadcastId);
			return jsonSuccess(dataMap);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>抢单</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>caina.cun</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午17:40:00</dd>
	 * @param broadcastId	广播ID
	 * @param memberId		接单人Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rob", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude) {
		try {
			if (PubMethod.isEmpty(broadcastId)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.rob.001",
						"broadcastId不能为空");
			}
			if (PubMethod.isEmpty(broadcastId)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.rob.002",
						"memberId不能为空");
			}
			if (PubMethod.isEmpty(addresseeLongitude)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.rob.003",
						"addresseeLongitude不能为空");
			}
			if (PubMethod.isEmpty(addresseeLatitude)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.rob.004",
						"addresseeLatitude不能为空");
			}
			this.shopBroadcastInfoService.rob(broadcastId, memberId,
					addresseeLongitude, addresseeLatitude);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>确认取件</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>caina.cun</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午17:40:00</dd>
	 * @param broadcastId	广播Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/take", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String take(Long broadcastId) {
		try {
			if (PubMethod.isEmpty(broadcastId)) {
				return paramsFailure("openapi.ShopBroadcastInfoController.take.001",
						"broadcastId不能为空");
			}
			this.shopBroadcastInfoService.take(broadcastId);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>查询抢送货列表</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chunyang.tan</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午15:25:00</dd>
	 * @param memberId	派送员ID
	 * @param sendDeliveryLongitude	送派员当前所在位置（经度）
	 * @param sendDeliveryLatitude	送派员当前所在位置（纬度）
	 * @return{"data": {"count": 1,"grabDeliveryList": [{"addresseeAddress": "zzz","addresseeTownName": "ggg","memberDistance": "5245.842km","senderAddress": "ddd","senderLatitude": 100.000000,"senderLongitude": 100.000000,"senderTownName": "ccc","serviceTime": 1444454145000,"tradeDistance": "0.1km"}]},"success": true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>ShopBroadcastInfoController.queryGrabDelivery.001 -  memberId参数非空异常</dd>
     * <dd>ShopBroadcastInfoController.queryGrabDelivery.002 -  sendDeliveryLongitude参数异常</dd>
     * <dd>ShopBroadcastInfoController.queryGrabDelivery.003 -  sendDeliveryLatitude参数异常</dd>
	 */
	@ResponseBody
	@RequestMapping(value = "/queryGrab", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryGrabDelivery(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude,Integer pageNo,Integer pageSize){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.GrabDeliveryController.selectGrabDelivery.001","查询待抢单列表，memberId参数非空异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLongitude) || sendDeliveryLongitude.compareTo(new BigDecimal(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.selectGrabDelivery.002","查询待抢单列表，sendDeliveryLongitude参数异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLatitude) || sendDeliveryLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.GrabDeliveryController.selectGrabDelivery.003","查询待抢单列表，sendDeliveryLatitude参数异常");	
			}
			return jsonSuccess(this.shopBroadcastInfoService.selectGrabDeliveryByMemberId(memberId, sendDeliveryLongitude, sendDeliveryLatitude,pageNo,pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>确认送达</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chunyang.tan</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午15:50:00</dd>
	 * @param broadcastId	广播id
	 * @param memberId	派送员ID
	 * @return JSON true
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>ShopBroadcastInfoController.determinationOfService.001 -  broadcastId参数非空异常</dd>
     * <dd>ShopBroadcastInfoController.determinationOfService.002 -  memberId参数非空异常</dd>
	 */
	@ResponseBody
	@RequestMapping(value = "/configDelivery", method = { RequestMethod.GET,RequestMethod.POST })
	public String determinationOfService(Long broadcastId ,Long memberId){
		try{
			if(PubMethod.isEmpty(broadcastId)&&broadcastId.compareTo(new Long(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.determinationOfService.001","修改确认送达状态，broadcastId参数非空异常");	
			}
			shopBroadcastInfoService.configDelivery(broadcastId, memberId);
			return jsonSuccess();
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>取消广播</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>chunyang.tan</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-22 下午15:50:00</dd>
	 * @param broadcastId	广播id
	 * @return JSON true
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>ShopBroadcastInfoController.cancelBroadcast.001 -  broadcastId参数非空异常</dd>
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelBroadcast", method = { RequestMethod.GET,RequestMethod.POST })
	public String cancelBroadcast(Long broadcastId){
		try{
			if(PubMethod.isEmpty(broadcastId)&&broadcastId.compareTo(new Long(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.cancelBroadcast.001","修改取消广播状态，broadcastId参数非空异常");	
			}
			shopBroadcastInfoService.cancelBroadcast(broadcastId);
			return jsonSuccess();
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 	统计订单数
	 * @param memberId
	 * 		派送员Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/statistical", method = { RequestMethod.GET,RequestMethod.POST })
	public String statisticalQuantities(Long memberId){
		try{
			if(PubMethod.isEmpty(memberId)&&memberId.compareTo(new Long(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.statisticalQuantities.001","修改统计订单数，memberId参数非空异常");	
			}
			return jsonSuccess(this.shopBroadcastInfoService.statisticalNumber(memberId));
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
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
	@ResponseBody
	@RequestMapping(value = "/saveMemberOnlineStatus", method = { RequestMethod.GET,RequestMethod.POST })
	public String saveMemberOnlineStatus(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude){
		try{
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.GrabDeliveryController.saveCacheByParam.001","加缓存，memberId参数非空异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLongitude) || sendDeliveryLongitude.compareTo(new BigDecimal(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.saveCacheByParam.002","加缓存，sendDeliveryLongitude参数异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLatitude) || sendDeliveryLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.GrabDeliveryController.saveCacheByParam.003","加缓存，sendDeliveryLatitude参数异常");	
			}
			shopBroadcastInfoService.currentMemberCacheInfor(memberId,sendDeliveryLongitude,sendDeliveryLatitude);
			return jsonSuccess();
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 	根据memberId 清缓存
	 * 	@param memberId
	 * 			送派员id
	 */
	@ResponseBody
	@RequestMapping(value = "/removeMemberOnlineStatus", method = { RequestMethod.GET,RequestMethod.POST })
	public String removeMemberOnlineStatus(Long memberId){
		try{
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.GrabDeliveryController.removeCacheByParam.001","清缓存，memberId参数非空异常");	
			}
			shopBroadcastInfoService.removeCurrentMemberCache(memberId);
			return jsonSuccess();
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 	查附近五公里，30分钟之内的所有送派员id
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNearMemberList", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryNearMemberList(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude){
		try{
			if(PubMethod.isEmpty(sendDeliveryLongitude) || sendDeliveryLongitude.compareTo(new BigDecimal(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.selectMemberByCondition.001","查附近五公里，30分钟之内的所有送派员，sendDeliveryLongitude参数异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLatitude) || sendDeliveryLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.GrabDeliveryController.selectMemberByCondition.002","查附近五公里，30分钟之内的所有送派员，sendDeliveryLatitude参数异常");	
			}
			return jsonSuccess(this.shopBroadcastInfoService.queryNearMemberList(sendDeliveryLongitude,sendDeliveryLatitude));
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 		查看附近送派员总数
	 * @param sendDeliveryLongitude
	 * 			送派员当前所在位置 经度
	 * @param sendDeliveryLatitude
	 * 			送派员当前所在位置 纬度
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNearMemberCount", method = { RequestMethod.GET,RequestMethod.POST })
	public String queryNearMemberCount(BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude){
		try{
			if(PubMethod.isEmpty(sendDeliveryLongitude) || sendDeliveryLongitude.compareTo(new BigDecimal(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.selectMemberByCondition.001","查附近五公里，30分钟之内的所有送派员，sendDeliveryLongitude参数异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLatitude) || sendDeliveryLatitude.compareTo(new BigDecimal(0))!= 1){
				throw new ServiceException("openapi.GrabDeliveryController.selectMemberByCondition.002","查附近五公里，30分钟之内的所有送派员，sendDeliveryLatitude参数异常");	
			}
			return jsonSuccess(this.shopBroadcastInfoService.queryNearMemberCount(sendDeliveryLongitude,sendDeliveryLatitude));
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * 呼叫配送员，传送订单信息
	 * @author yangkai
	 * @time 2015-7-22
	 */
	@RequestMapping(value="/callDistributionMember",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String callDistributionMember(String senderName, String senderPhone,
			Long senderTownId, String senderTownName, String senderAddress,
			BigDecimal senderLongitude, BigDecimal senderLatitude,
			String addresseeName, String addresseePhone,
			Long addresseeTownId, String addresseeTownName,
			String addresseeAddress, BigDecimal addresseeLongitude,
			BigDecimal addresseeLatitude, BigDecimal tradeDistance, String shopName,String shopPhone,
			String serviceTime, String tradeNum, short productNum,
			BigDecimal collectionMoney, BigDecimal freightMoney, String json) {
		try {
			return	jsonSuccess(shopBroadcastInfoService.saveOrderInfo(senderName, senderPhone,
					senderTownId, senderTownName, senderAddress,
					senderLongitude, senderLatitude, addresseeName,
					addresseePhone, addresseeTownId, addresseeTownName,
					addresseeAddress, addresseeLongitude, addresseeLatitude,
					tradeDistance, shopName, shopPhone, serviceTime, tradeNum, productNum,
					collectionMoney, freightMoney, json));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * 该方法的主要作用是通过订单号取订单详情
	 * @author yangkai
	 * @time 2015-7-22
	 */
	@RequestMapping(value="/queryOrderDetails",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String queryOrderDetails(Long broadcastId,Double lat,Double lng) {
		try {
			return jsonSuccess(this.shopBroadcastInfoService.queryOrderDetails(broadcastId,lat,lng));
		} catch (Exception e) {
			return jsonFailure(e);
		}
		
	}
}

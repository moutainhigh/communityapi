package net.okdi.mob.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.base.BaseController;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.ShopBroadcastInfoService;

@Controller
@RequestMapping("/shopBroadcastInfo")
public class ShopBroadcastInfoController extends BaseController {

	@Autowired
	ShopBroadcastInfoService shopBroadcastInfoService;

	/**
	 * @api {post} /shopBroadcastInfo/queryTakeDeliveryStation 抢送货(进行中,以完成)	caina.sun
	 * @apiPermission user
	 * @apiDescription 接口描述 抢送货(进行中,以完成)
	 * @apiparam {Short} queryStatus 广播状态 0待接单、1已接单（待取件）、2已完成
	 * @apiparam {Long} memberId 接单人ID
	 * @apiparam {Integer} pageNo 当前页
	 * @apiparam {Integer} pageSize 共多少页
	 * @apiGroup 抢送货
	 * @apiSampleRequest /shopBroadcastInfo/queryTakeDeliveryStation
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"data":{"list":[{"addersseeTownName":"ggg","addresseeAddress":"zzz","broadcastId":159100105703424,"broadcastStatus":0,"memberId":1,"modityTime":1437809097000,"senderAdderss":"ddd","senderTownName":"ccc","shopName":"xxx","tradeNum":"ccc"}],"totalCount":1},"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.ShopBroadcastInfoController.queryTakeDeliveryStation.001","message":"queryStatus参数异常","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/queryTakeDeliveryStation", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String queryTakeDeliveryStation(Short queryStatus, Long memberId,Integer pageNo,Integer pageSize) {
		try {
			return shopBroadcastInfoService.queryTakeDeliveryStation(queryStatus, memberId,pageNo,pageSize);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * @api {post} /shopBroadcastInfo/rob 抢单 caina.sun
	 * @apiPermission user
	 * @apiDescription 接口描述 抢单
	 * @apiparam {Long} broadcastId	广播ID
	 * @apiparam {Long} memberId 接单人ID
	 * @apiGroup 抢送货
	 * @apiSampleRequest /shopBroadcastInfo/rob
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubCode":"openapi.ShopBroadcastInfoServiceImpl.rob.001","message":"操作失败","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/rob", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String rob(Long broadcastId, Long memberId,
			BigDecimal addresseeLongitude, BigDecimal addresseeLatitude) {
		try {
			return shopBroadcastInfoService.rob(broadcastId, memberId,
					addresseeLongitude, addresseeLatitude);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

	/**
	 * @api {post} /shopBroadcastInfo/take 确认取件	caina.sun
	 * @apiPermission user
	 * @apiDescription 接口描述 确认取件
	 * @apiparam {Long} broadcastId	广播ID
	 * @apiGroup 抢送货
	 * @apiSampleRequest /shopBroadcastInfo/take
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
	 *   
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":1,"errSubCode":"","message":"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/take", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String take(Long broadcastId) {
		try {
			return shopBroadcastInfoService.take(broadcastId);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /shopBroadcastInfo/listGrabDelivery 抢送货列表(待抢单)
	 * @apiPermission user
	 * @apiDescription 接口描述 查询抢送货(待抢单)列表  chunyang.tan
	 * @apiparam {Long} memberId 送派员Id
	 * @apiparam {BigDecimal} sendDeliveryLongitude 发件人地址经度
	 * @apiparam {BigDecimal} sendDeliveryLatitude 发件人地址纬度
	 * @apiparam {Integer} pageNo 当前页
	 * @apiparam {Integer} pageSize 共多少页
	 * @apiGroup 社区配送
	 * @apiSampleRequest /shopBroadcastInfo/listGrabDelivery
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"data":{"count":1,"grabDeliveryList":[{"addresseeAddress":"zzz","addresseeTownName":"ggg","memberDistance":"5245.842km","senderAddress":"ddd","senderLatitude":100.000000,"senderLongitude":100.000000,"senderTownName":"ccc","serviceTime":1444454145000,"tradeDistance":"0.1km"}]},"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":0,"errSubcode":"openapi.GrabDeliveryController.selectGrabDelivery.001","查询待抢单列表，memberId参数非空异常","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("/listGrabDelivery")
	public String selectListGrabDelivery(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude, Integer pageNo, Integer pageSize){
		
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
    		return shopBroadcastInfoService.selectGrabDeliveryByMemberId(memberId, sendDeliveryLongitude, sendDeliveryLatitude, pageNo, pageSize);
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /shopBroadcastInfo/configDelivery 确认送达
	 * @apiPermission user
	 * @apiDescription 接口描述  配送中订单详情 确认送达	chunyang.tan
	 * @apiparam {Long} broadcastId 广播Id
	 * @apiparam {Long} memberId 送派员id
	 * @apiGroup 抢送货
	 * @apiSampleRequest /shopBroadcastInfo/configDelivery
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"errCode":1,"errSubCode":"","message":"java.lang.NumberFormatException: For input string: \"null\"","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/configDelivery", method = { RequestMethod.GET,RequestMethod.POST })
	public String determinationOfService(Long broadcastId ,Long memberId){
		try{
			if(PubMethod.isEmpty(broadcastId)&&broadcastId != 0){
				return paramsFailure("openapi.GrabDeliveryController.determinationOfService.001","修改确认送达状态，broadcastId参数非空异常");	
			}
			if(PubMethod.isEmpty(memberId)&&memberId != 0){
				return paramsFailure("openapi.GrabDeliveryController.determinationOfService.002","修改确认送达状态，broadcastId参数非空异常");	
			}
			return shopBroadcastInfoService.configDelivery(broadcastId, memberId);
		}catch(Exception e){
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /shopBroadcastInfo/cancelBroadcast 取消广播
	 * @apiPermission user
	 * @apiDescription 接口描述 取消呼叫附近的派送员	chunyang.tan
	 * @apiparam {Long} broadcastId 广播Id
	 * @apiGroup 社区配送
	 * @apiSampleRequest /shopBroadcastInfo/cancelBroadcast
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   {"success":true}
     *
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"errCode":1,"errSubCode":"","message":"java.lang.NullPointerException","success":false}
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelBroadcast", method = { RequestMethod.GET,RequestMethod.POST })
	public String cancelBroadcast(Long broadcastId){
		try{
			if(PubMethod.isEmpty(broadcastId)&&broadcastId.compareTo(new Long(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.cancelBroadcast.001","修改取消广播状态，broadcastId参数非空异常");	
			}
			return shopBroadcastInfoService.cancelBroadcast(broadcastId);
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
	/**
	 * @api {post} /shopBroadcastInfo/statistical 统计订单数
	 * @apiPermission user
	 * @apiDescription 接口描述 统计当前派送员今天和历史的订单数	chunyang.tan
	 * @apiparam {Long} memberId 派送员Id
	 * @apiGroup 社区配送
	 * @apiSampleRequest /shopBroadcastInfo/statistical
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"data":{"OrderStatisticsNumber":1,"todayOrderNumber":1},"success":true}
	 *   
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   result:null
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping(value = "/statistical", method = { RequestMethod.GET,RequestMethod.POST })
	public String statisticalQuantities(Long memberId){
		try{
			if(PubMethod.isEmpty(memberId)&&memberId.compareTo(new Long(0))!= 1){
				return paramsFailure("openapi.GrabDeliveryController.statisticalQuantities.001","查询统计订单数，memberId参数非空异常");	
			}
			return shopBroadcastInfoService.statisticalNumber(memberId);
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @api {post} /shopBroadcastInfo/queryOrderDetails 查询订单详情
	 * @apiPermission user
	 * @apiDescription 通过订单号取订单详情，还需要传快递员的经纬度来计算快递员与发货人的距离 kai.yang
	 * @apiparam {Long} broadcastId 纬度
	 * @apiparam {Double} lng 经度
	 * @apiparam {Double} lat 纬度
	 * @apiGroup 抢送货
	 * @apiSampleRequest /shopBroadcastInfo/queryOrderDetails
	 * @apiSuccess {String} addresseeAddress   收货人详细地址 
	 * @apiSuccess {String} addresseeName 收货人姓名
	 * @apiSuccess {String} addresseePhone 收货人电话 
	 * @apiSuccess {String} addresseeTownName 收货人乡镇名称
	 * @apiSuccess {String} broadcastId 广播id
	 * @apiSuccess {String} broadcastStatus 状态标识
	 * @apiSuccess {String} cancelTime 取消时间
	 * @apiSuccess {BigDecimal} collectionMoney 代收货款
	 * @apiSuccess {String} finishTime 完成时间 
	 * @apiSuccess {String} memberDistance 接单人到发货人距离
	 * @apiSuccess {String} productNum 商品数量 
	 * @apiSuccess {String} senderAddress 发件人详细地址 
	 * @apiSuccess {BigDecimal} senderLatitude 发件人地址纬度 
	 * @apiSuccess {BigDecimal} senderLongitude 发件人地址经度
	 * @apiSuccess {String} senderName 发件人姓名
	 * @apiSuccess {String} senderPhone 发件人电话
	 * @apiSuccess {String} senderTownName 发件人乡镇地址名
	 * @apiSuccess {String} serviceTime 送达时间
	 * @apiSuccess {String} shopName 店铺名称
	 * @apiSuccess {String} shopPhone 店铺号码
	 * @apiSuccess {String} shopTradeItem 商品列表
	 * @apiSuccess {String} singleTime 接单时间 
	 * @apiSuccess {String} takeTime 取件时间
	 * @apiSuccess {BigDecimal} totalAmount 总金额
	 * @apiSuccess {String} tradeDistance 发货人到收货人距离
	 * @apiSuccess {String} tradeNum 交易号
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 *   result:{"data":{"addresseeAddress":"花园北路","addresseeName":"周紧","addresseePhone":"18888888888","addresseeTownName":"北京市海淀区",
	 *   "broadcastId":167852934479872,"broadcastStatus":0,"cancelTime":"0000-00-00 00:00:00","collectionMoney":14.50,
	 *   "finishTime":"0000-00-00 00:00:00","memberDistance":"2724.4km","productNum":2,
	 *   "senderAddress":"龙翔路","senderLatitude":22.000000,"senderLongitude":11.000000,"senderName":"3超级无敌super大卖场啊啊啊啊啊啊啊",
	 *   "senderPhone":"15480000000","senderTownName":"北京市海淀区","serviceTime":"2015-07-29 21:28:00","shopName":"3超级无敌super大卖场啊啊啊啊啊啊啊",
	 *   "shopPhone":"15480000000","shopTradeItem":[{"productName":"暴走背包","productNum":2},{"productName":"达利园蛋黄派","productNum":3}],
	 *   "singleTime":"0000-00-00 00:00:00","takeTime":"0000-00-00 00:00:00","totalAmount":14.50,"tradeDistance":"1607.0km","tradeNum":"159824781869057"},
	 *   "success":true}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 * @apiVersion 0.2.0
	 */
	@RequestMapping(value="/queryOrderDetails",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
    public String queryInfoPage(Long broadcastId,Double lat,Double lng){//广播id，纬度，经度
		if(PubMethod.isEmpty(broadcastId)){
			return paramsFailure();
		}
		try {
			return this.shopBroadcastInfoService.queryOrderDetails(broadcastId,lat,lng);
		} catch (Exception e) {
			return jsonFailure(e);
		}
    }
	
	/**
	 * @api {post} /shopBroadcastInfo/saveMemberOnlineStatus 保存送派员在线状态
	 * @apiPermission user
	 * @apiDescription 通过送派员当前的经纬度保存送派员在线状态 chunyang.tang
	 * @apiparam {Long} memberId 纬度
	 * @apiparam {BigDecimal} sendDeliveryLongitude 经度
	 * @apiparam {BigDecimal} sendDeliveryLatitude 纬度
	 * @apiGroup ACCOUNT 社区配送
	 * @apiSampleRequest /shopBroadcastInfo/saveMemberOnlineStatus
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * {"success":true}
	 * 
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 * @apiVersion 0.2.0
	 */
	@RequestMapping(value="/saveMemberOnlineStatus",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public String saveMemberOnlineStatus(Long memberId,BigDecimal sendDeliveryLongitude,BigDecimal sendDeliveryLatitude){
		try{
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("openapi.ShopBroadcastInfoController.saveMemberOnlineStatus.001","查询统计订单数，memberId参数非空异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLongitude)){
				return paramsFailure("openapi.ShopBroadcastInfoController.saveMemberOnlineStatus.001","查询统计订单数，sendDeliveryLongitude参数非空异常");	
			}
			if(PubMethod.isEmpty(sendDeliveryLatitude)){
				return paramsFailure("openapi.ShopBroadcastInfoController.saveMemberOnlineStatus.001","查询统计订单数，sendDeliveryLatitude参数非空异常");	
			}
			return shopBroadcastInfoService.saveMemberOnlineStatus(memberId,sendDeliveryLongitude,sendDeliveryLatitude);
		}catch(Exception e){
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}

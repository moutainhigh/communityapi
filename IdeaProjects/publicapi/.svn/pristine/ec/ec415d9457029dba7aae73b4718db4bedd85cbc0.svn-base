package net.okdi.apiV1.controller;

import java.util.List;

import net.okdi.apiV1.service.ShopService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 好递好店接口
 * @Project Name:openapi
 * @Package net.okdi.apiV1.controller
 * @Title: ShopController.java
 * @ClassName: ShopController <br/>
 * @date: 2015-10-13 下午1:42:54 <br/>
 * @author xingwei.zhang
 * @version v2.0
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController {

	@Autowired
	private ShopService shopService;
	Logger log = Logger.getLogger(ExpressUserController.class);
	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/fetchRanges 取派范围(保存)
	 * @apiVersion 0.2.0
	 * @apiDescription  取派范围(保存)
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId   成员Id
	 * @apiParam {String} fetchRangeData json字符串格式:
	 * {"data":[{"fetchRange":"环形大厦","latitude":"116.239678","longitude":"40.033162"}, 
				{"fetchRange":"环形大厦a座","latitude":"111.239678","longitude":"41.033162"},
				{"fetchRange":"环形大厦b座","latitude":"116.239678","longitude":"40.033162"}]
	 * @apiSampleRequest /shop/fetchRanges
	 * @apiSuccessExample Success-Response:
		{"data":"ture",
		 	"success":true}
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/fetchRanges")
	public String fetchRanges(String memberId,String fetchRangeData) {
		log.info("pub-取派范围(保存)----->>>fetchRanges()-----memberId:"+memberId+",fetchRangeData"+fetchRangeData);
		return shopService.fetchRanges(memberId, fetchRangeData);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/takeTime 取派时间(保存)
	 * @apiVersion 0.2.0
	 * @apiDescription  取派时间(保存)
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId     成员Id
	 * @apiParam {String} startTime    开始时间
	 * @apiParam {String} endTime      结束时间
	 * @apiSampleRequest /shop/takeTime
	 * @apiSuccessExample Success-Response:
		{"data":"ture", 
			"success":true}
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/takeTime")
	public String takeTime(String memberId, String startTime, String endTime) {
		log.info("pub-取派时间(保存)----->>>takeTime()-----memberId:"+memberId+",startTime"+startTime+"endTime"+endTime);
		return shopService.takeTime(memberId, startTime, endTime);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/pickupPrices 取件价格(保存)
	 * @apiVersion 0.2.0
	 * @apiDescription  取件价格(保存)
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId     店长ID
	 * @apiParam {String} region       地区
	 * @apiParam {Double} fetchPrice   首价格
	 * @apiParam {Double} renewPrice   续价格
	 * @apiParam {String} netName      快递名称
	 * @apiParam {String} photoUrl     快递Log/URL
	 * @apiSampleRequest /shop/pickupPrice
	 * @apiSuccessExample Success-Response:
		{"data":"ture", 
		    "success":true}
		}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/pickupPrices")
	public String pickupPrices(String memberId, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl) {
		log.info("pub------>>>pickupPrices()-----memberId:"+memberId+",region"+region+"fetchPrice"+fetchPrice);
		return shopService.pickupPrice(memberId, region, fetchPrice, renewPrice, netName, photoUrl);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/serviceExpress 服务快递(保存)
	 * @apiVersion 0.2.0
	 * @apiDescription  服务快递(保存)
	 * @apiGroup 好递好店(新需求) 
	 * @apiParam {String} memberId         店长ID
	 * @apiParam {String} serviceExpDate json字符串格式:
	 * {"data":[{"netId":"250651540242432","firstLetter":"F","serviceExpress":"圆通快递","photoUrl"http://www.cn.dhl.com/"}, 
				{"netId":"250651540242432","firstLetter":"F","serviceExpress":"邮政快递","photoUrl"http://www.cn.dhl.com/"},
				{"netId":"250651540242432","firstLetter":"F","serviceExpress":"京东快递","photoUrl"http://www.cn.dhl.com/"}]
	 * @apiSampleRequest /shop/serviceExpress
	 * @apiSuccessExample Success-Response:
		{"data":"ture", 
			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/serviceExpress")
	public String serviceExpress(String memberId, String serviceExpDate) {
		log.info("pub-服务快递(保存)----->>>serviceExpress()-----memberId:"+memberId+",serviceExpDate"+serviceExpDate);
		return shopService.serviceExpress(memberId, serviceExpDate);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/serviceExplain 服务说明(保存)
	 * @apiVersion 0.2.0
	 * @apiDescription  服务说明(保存)
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId           成员Id
	 * @apiParam {String} serviceDescription 服务说明
	 * @apiSampleRequest /shop/serviceExplain
	 * @apiSuccessExample Success-Response:
		{"data":"ture", 
			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/serviceExplain")
	public String serviceExplain(String memberId, String serviceDescription) {
		log.info("pub---服务说明(保存)--->>>serviceExplain()-----memberId:"+memberId+",serviceDescription"+serviceDescription);
		return shopService.serviceExplain(memberId, serviceDescription);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/queryShopInfo 查询取件时间服务说明
	 * @apiVersion 0.2.0
	 * @apiDescription  查询取件时间服务说明
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId              成员Id
	 * @apiSampleRequest /shop/queryShopInfo
	 * @apiSuccess {String} endTime             开始时间
	 * @apiSuccess {String} startTime           结束时间
	 * @apiSuccess {String} serviceDescription  服务说明
	 * @apiSuccessExample Success-Response: 
	 * {"data":{"endTime":"15:25:05",
	 * 			"serviceDescription":"",
	 * 			"startTime":"15:25:02"},
	 * 	"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/queryShopInfo")
	public String queryShopInfo(String memberId) {
		log.info("pub---查询取件时间服务说明--->>>queryShopInfo()-----memberId:"+memberId);
		return shopService.queryShopInfo(memberId);
	}

	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/queryFetchRanges  查询取件范围
	 * @apiVersion 0.2.0
	 * @apiDescription   查询取件范围
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId         店长ID
	 * @apiSampleRequest /shop/queryFetchRanges
	 * @apiSuccess {String} fetchRange      取件范围
	 * @apiSuccess {String} latitude        经度
	 * @apiSuccess {String} longitube       纬度
	 * @apiSuccess {String} Id       		取件范围Id
	 * @apiSuccessExample Success-Response: 
	 * {"data":[{"fetchRange":"北京市-北京市区-海淀区",
	 * 			"latitude":"40.033162",
	 * 			"longitube":"116.239678"},
	 * 			{"fetchRange":"北京市-北京市区-海淀区",
	 * 			"latitude":"40.033162",
	 * 			"longitube":"116.239678"}],
	 * 			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/queryFetchRanges")
	public String queryFetchRanges(String memberId) {
		log.info("pub---查询取件范围--->>>queryPickupPrice()-----memberId:"+memberId);
		return shopService.queryFetchRanges(memberId);
	}

	
	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/queryPickupPrice  查询取件价格
	 * @apiVersion 0.2.0
	 * @apiDescription  查询取件价格
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId         店长ID
	 * @apiSampleRequest /shop/pickupPrice
	 * @apiSuccess {String} id     取件价格id
	 * @apiSuccess {String} fetchPrice     首价格
	 * @apiSuccess {String} renewPrice     续价格
	 * @apiSuccess {String} netName        服务快递
	 * @apiSuccess {String} region         地区
	 * @apiSuccess {String} photoUrl       服务快递Log
	 * @apiSuccessExample Success-Response: 
	 * {"data":[{"fetchPrice":32132.3,
	 * 	 "netName":"åéå¿«é","photoUrl":"http://dsdsddasfaf/dsadsadsaf/321321.jpg",
	 *	 "region":"å°å·,ä¸æµ·,å¤©æ´¥,æ¾³é¨"}],
	 * 	 "success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/queryPickupPrice")
	public String queryPickupPrice(String memberId) {
		log.info("pub---查询取件价格--->>>queryPickupPrice()-----memberId:"+memberId);
		return shopService.queryPickupPrice(memberId);
	}

	/**
	 * @author xingwei.zhang
	  * @api {post} /shop/queryServiceExpress 查询服务快递
	 * @apiVersion 0.2.0
	 * @apiDescription  查询服务快递
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId       店长ID
	 * @apiSampleRequest /shop/queryServiceExpress
	 * @apiSuccess {String} netId    	   服务快递ID
	 * @apiSuccess {String} firstLetter  服务快递首字母
	 * @apiSuccess {String} netName      服务快递
	 * @apiSuccess {String} url          快递log/URL
	 * @apiSuccessExample Success-Response: 
	 * "data":{"F":[{"code":"","firstLetter":"F",
	 * 		"isPartners":"","netId":250467605331968,
	 * 		"netName":"百世汇通","netNum":"","telephone":"",
	 * 		"url":""}],"S":[{"code":"","firstLetter":"S",
	 * 		"isPartners":"","netId":250467605331969,"netName":"西北快运",
	 * 		"netNum":"","telephone":"",
	 * 		"url":"http://expnew.okdit.net/nfs_data/complogo/1503.png"}]},
	 * 		"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/queryServiceExpress")
	public String queryServiceExpress(String memberId) {
		log.info("pub---查询服务快递--->>>queryServiceExpress()-----memberId:"+memberId);
		return shopService.queryServiceExpress(memberId);
	}
	
	/**
	 * @author xingwei.zhang
	  * @api {post} /shop/queryIsAdd 查询是否填写
	 * @apiVersion 0.2.0
	 * @apiDescription  查询是否填写
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId            店长ID
	 * @apiSampleRequest /shop/queryIsAdd
	 * @apiSuccess {String} fetchRange      0未填写 , 1已填写
	 * @apiSuccess {String} pickupPrice	    0未填写 , 1已填写
	 * @apiSuccess {String} serviceExpress  0未填写 , 1已填写
	 * @apiSuccessExample Success-Response: 
	 * {"data":[{"fetchRange":"0",
	 * 			"pickupPrice":"1",
	 * 			"serviceExpress":"1"}],
	 * 			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/queryIsAdd")
	 public String queryIsAdd(String memberId){	
		log.info("pub---查询是否填写--->>>queryIsAdd()-----memberId:"+memberId);
		return shopService.queryIsAdd(memberId);   
	 }	
	
	/**
	 * @author xingwei.zhang
	  * @api {post} /shop/deleteFetchRange 删除取件范围
	 * @apiVersion 0.2.0
	 * @apiDescription  删除取件范围
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} Id       取件范围Id
	 * @apiSampleRequest /shop/deleteFetchRange
	 * @apiSuccessExample Success-Response: 
	 * {"data":"ture",
	 * 	"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/deleteFetchRange")
	 public String deleteFetchRange(String Id){	
		log.info("pub---删除取件范围--->>>deleteFetchRange()-----Id:"+Id);
		return shopService.deleteFetchRange(Id);   
	 }
	
	/**
	 * @author xingwei.zhang
	  * @api {post} /shop/updateRegion 更新取件价格信息
	 * @apiVersion 0.2.0
	 * @apiDescription  更新取件价格信息
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} Id           取件价格Id
	 * @apiParam {String} memberId     店长ID
	 * @apiParam {String} region       地区
	 * @apiParam {Double} fetchPrice   首价格
	 * @apiParam {Double} renewPrice   续价格
	 * @apiParam {String} netName      快递名称
	 * @apiParam {String} photoUrl     快递Log/URL
	 * @apiSampleRequest /shop/updateRegion
	 * @apiSuccessExample Success-Response: 
	 * {"data":"ture",
	 * 	"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRegion", method = { RequestMethod.POST, RequestMethod.GET })
	 public String updateRegion(String Id, String region, Double fetchPrice, Double renewPrice, String netName, String photoUrl){
		log.info("pub--更新取件价格信息-->>>updateRegion()--Id:"+Id+",region"+region+",fetchPrice"+fetchPrice+",netName"+netName+",photoUrl"+photoUrl);
		return shopService.updateRegion(Id,region,fetchPrice,renewPrice,netName,photoUrl); 
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /shop/deleteRegion 删除取件价格信息
	 * @apiVersion 0.2.0
	 * @apiDescription  删除取件价格信息
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} Id       取件价格Id
	 * @apiSampleRequest /shop/deleteRegion
	 * @apiSuccessExample Success-Response: 
	 * {"data":"ture",
	 * 	"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/deleteRegion")
	 public String deleteRegion(String Id){ 
		log.info("pub---删除取件价格信息--->>>deleteRegion()-----Id:"+Id);
		return shopService.deleteRegion(Id);
	
	 }

	/**
	 * @author xingwei.zhang
	  * @api {post} /shop/storeShare 好店分享
	 * @apiVersion 0.2.0
	 * @apiDescription  好店分享
	 * @apiGroup 好递好店(新需求)
	 * @apiParam {String} memberId            店长ID
	 * @apiSampleRequest /shop/storeShare
	 * 服务说明
	 * @apiSuccess {String} endTime             开始时间
	 * @apiSuccess {String} startTime           结束时间
	 * @apiSuccess {String} serviceDescription  服务说明
	 * 取件范围
	 * @apiSuccess {String} fetchRange      取件范围
	 * @apiSuccess {String} latitude        经度
	 * @apiSuccess {String} longitube       纬度
	 * @apiSuccess {String} Id       		取件范围Id
	 * 服务快递
	 * @apiSuccess {String} fetchPrice     取件价格
	 * @apiSuccess {String} netName        服务快递
	 * @apiSuccess {String} region         地区
	 * @apiSuccess {String} photoUrl       服务快递Log
	 * 服务快递
	 * @apiSuccess {String} netId    	   服务快递ID
	 * @apiSuccess {String} firstLetter  服务快递首字母
	 * @apiSuccess {String} netName      服务快递
	 * @apiSuccess {String} url          快递log/URL
	 * 店长信息
	 * @apiSuccess {String} memberId 快递员id
	 * @apiSuccess {String} birthday  生日
	 * @apiSuccess {String} gender	性别
	 * @apiSuccess {String} idNum 身份证号码
	 * @apiSuccess {String} memberName 快递员姓名
	 * @apiSuccess {String} memberPhone 快递员手机号
	 * @apiSuccess {String} compId 站点id
	 * @apiSuccess {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSuccess {String} headPic 头像照片
	 * @apiSuccess {String} mePic 本人照片
	 * @apiSuccess {String} frontPic 身份证照片-正面
	 * @apiSuccess {String} backPic 身份证照片-反面
	 * @apiSuccess {String} inHandPic 手持身份证照片
	 * @apiSuccess {String} auditStatus -1 未完善 0 待审核  1通过 2拒绝
	 * @apiSuccess {String} auditRejectReason 拒绝原因
	 * @apiSuccessExample Success-Response: 
	 * {"data":[[{"Id":"253319864172544","fetchRange":"万盛商务会馆A区","latitude":"39.984288","longitube":"116.386420"},
	 * {"Id":"253323802624000","fetchRange":"牡丹园北里5号楼","latitude":"39.985221","longitube":"116.383007"},
	 * {"Id":"253323802624001","fetchRange":"环星大厦","latitude":"39.984923","longitube":"116.383824"},
	 * {"Id":"253323813109760","fetchRange":"华严里34号楼","latitude":"39.985939","longitube":"116.389663"},
	 * {"Id":"253323815206912","fetchRange":"北京精诚实验小学国际部","latitude":"39.985994","longitube":"116.389331"},
	 * {"Id":"253323815206913","fetchRange":"祁家豁子街2号院","latitude":"39.986727","longitube":"116.390103"},
	 * {"Id":"253323815206914","fetchRange":"马甸经典家园-B栋","latitude":"39.986167","longitube":"116.388262"}],
	 * {"B":[{"code":"","firstLetter":"B","isPartners":"","netId":253347668213760,"netName":"百世汇通","netNum":"","telephone":"","url":"http://expnew.okdit.net/nfs_data/complogo/1503.png"}],
	 * "E":[{"code":"","firstLetter":"E","isPartners":"","netId":253349964595200,"netName":"EMS速递","netNum":"","telephone":"","url":"http://expnew.okdit.net/nfs_data/complogo/999.png"},
	 * {"code":"","firstLetter":"E","isPartners":"","netId":253349964595201,"netName":"EMS经济快递","netNum":"","telephone":"","url":"http://expnew.okdit.net/nfs_data/complogo/1588.png"}],
	 * "S":[{"code":"","firstLetter":"S","isPartners":"","netId":253349964595202,"netName":"顺丰速运","netNum":"","telephone":"","url":"http://expnew.okdit.net/nfs_data/complogo/1001.png"},
	 * {"code":"","firstLetter":"S","isPartners":"","netId":253349964595203,"netName":"申通快递","netNum":"","telephone":"","url":"http://expnew.okdit.net/nfs_data/complogo/1500.png"}]},
	 * [{"fetchPrice":8,"netName":"百世汇通","photoUrl":"","region":"安徽,北京"},{"fetchPrice":7,"netName":"EMS速递","photoUrl":"","region":"甘肃,广东,广西"},{"fetchPrice":7,"netName":"百世汇通","photoUrl":"","region":"安徽,北京"},
	 * {"fetchPrice":10,"netName":"EMS速递","photoUrl":"","region":"甘肃,广东,广西"},{"fetchPrice":13,"netName":"顺丰速运","photoUrl":"","region":"安徽,澳门,北京,重庆,甘肃,福建,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,吉林,江苏,江西,辽宁,内蒙古,宁夏,青海,山东,山西,陕西,上海,四川,台湾,天津,西藏,香港,新疆,云南,浙江"},
	 * {"fetchPrice":13,"netName":"申通快递","photoUrl":"","region":"安徽,澳门,北京,重庆,福建,甘肃,广东,广西,贵州,海南,河北,河南,黑龙江,湖北,湖南,吉林,江苏,江西,辽宁,内蒙古,宁夏,青海,山东,山西"},{"fetchPrice":12.2,"netName":"而我却","photoUrl":"adsadsad12323223","region":"啦啦,订单,点多"},{"fetchPrice":12.5,"netName":"中通","photoUrl":"3123qewqeqwe","region":"新建,粉色,暗帝"}],
	 * {"endTime":"20:52:00","serviceDescription":"共产党测试豆腐饭发发发少数服从多数大地飞歌大学城刚刚地方呵呵地方很近如果健康大长今伤心好纠结地方好纠结地方好纠结地方好纠结地方很近额放寒假舒服乖宝宝地方好纠结是的v比较是的刚回家到底刚回家额电饭煲深度国际深度国际深度国际十常八九舒服回家豆腐干舒服回家舒服好看的感觉额附近少废话舒服好看舒服回家看地方很近舒服好看地方很近的更健康的更健康的更健康东方航空独孤九剑地方很健康的更健康舒服回家看额负借口地方很","startTime":"07:00:00"},
	 * {"auditRejectReason":"","auditStatus":1,"backPic":"http://cas.okdit.net/nfs_data/mob/id/back/2051393489222100.jpg","birthday":"2016-12-24","compId":250896936386560,"frontPic":"http://cas.okdit.net/nfs_data/mob/id/front/2051393489222100.jpg",
	 * "gender":"2","headPic":"http://cas.okdit.net/nfs_data/mob/head/2051393489222100.jpg","idNum":"150201198302162002","inHandPic":"http://cas.okdit.net/nfs_data/mob/id/inHand/2051393489222100.jpg","mePic":"http://cas.okdit.net/nfs_data/mob/id/me/2051393489222100.jpg",
	 * "memberId":2051393489222100,"memberName":"测顺丰丰","memberPhone":"13426469593","roleType":1}],
	 * "success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping("/storeShare")
	 public String storeShare(String memberId){
		log.info("pub---删除取件价格信息--->>>storeShare()-----memberId:"+memberId);
		return shopService.storeShare(memberId);
		
	 }
}

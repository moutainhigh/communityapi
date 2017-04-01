package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.SendPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 派件controller
 * @Project Name:publicapi 
 * @Package net.okdi.apiV4.controller  
 * @Title: SendPackageController.java 
 * @ClassName: SendPackageController <br/> 
 * @date: 2016-4-23 上午10:06:35 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/sendPackage")
public class SendPackageController  extends BaseController {

	@Autowired
	private SendPackageService sendPackageService;//注入service
	
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/queryParcelToBeTakenList 查询待提包裹列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待提包裹列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Integer} currentPage 当前页码
	 * @apiParam {Integer} pageSize 每页显示条数
	 * @apiSampleRequest /sendPackage/queryParcelToBeTakenList
	 * @apiSuccess {String} addresseeAddress  收件人地址
	 * @apiSuccess {String} addresseeMobile  收件人手机号
	 * @apiSuccess {String} createTime  创建时间
	 * @apiSuccess {String} expWaybillNum  快递单号
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccess {String} total  列表总数（data里面的total）
	 * @apiSuccess {String} ifFocusWeChat  是否关注过好递微信公众号 0否1是
	 * @apiSuccessExample Success-Response:
	     {
		    "data": {
		        "currentPage": 1,
		        "hasFirst": false,
		        "hasLast": false,
		        "hasNext": false,
		        "hasPre": false,
		        "items": [
		            {
		                "addresseeAddress": "马莲道中里",
		                "addresseeMobile": "13717560939",
		                "createTime": "2015-02-05 06:05",
		                "expWaybillNum": "888381356771",
		                "parId": 128565849169920
		            }
		        ],
		        "offset": 0,
		        "pageCount": 1,
		        "pageSize": 10,
		        "rows": [],
		        "total": 1
		    },
		    "success": true
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
	@RequestMapping(value = "/queryParcelToBeTakenList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelToBeTakenList(Long memberId,Integer currentPage,Integer pageSize){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(currentPage)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.002", "currentPage不能为空");
			}
			if(PubMethod.isEmpty(pageSize)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelToBeTakenList.003", "pageSize不能为空!");
			}
			return this.sendPackageService.queryParcelToBeTakenList(memberId,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/queryParcelDetail 待提包裹信息详情
	 * @apiVersion 0.2.0
	 * @apiDescription 待提包裹信息详情
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} parId 包裹id
	 * @apiSampleRequest /sendPackage/queryParcelDetail
	 * @apiSuccess {String} addressee_address  收件人详细地址
	 * @apiSuccess {String} addressee_cityName  收件人城市地区
	 * @apiSuccess {String} city_id  收件人城市地区id
	 * @apiSuccess {String} addressee_mobile  收件人手机号
	 * @apiSuccess {String} addressee_name  收件人姓名
	 * @apiSuccess {String} cod_amount  应收货款
	 * @apiSuccess {String} freight  应收运费
	 * @apiSuccess {String} exp_waybill_num  快递单号
	 * @apiSuccess {String} net_name  快递网络
	 * @apiSuccess {String} netId  快递网络id
	 * @apiSuccess {String} parId 包裹id
	 * @apiSuccess {String} isRegistered 是否是好递用户 0否1是
	 * @apiSuccessExample Success-Response:
	     {
		    "data": {
		        "addressee_address": "北京西城区马莲道中里",
		        "addressee_mobile": "13717560939",
		        "addressee_name": "阿凯",
		        "cod_amount": "",
		        "exp_waybill_num": "888381356771",
		        "freight": "",
		        "net_name": "圆通速递",
		        "netId": "88838135",
		        "parId": 128565849169920
		    },
		    "success": true
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
	@RequestMapping(value = "/queryParcelDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryParcelDetail(String parId){
		try {
			if(PubMethod.isEmpty(parId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryParcelDetail.001", "parId不能为空");
			}
			return this.sendPackageService.queryParcelDetail(parId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/changeSendPerson 待提包裹转单--批量
	 * @apiVersion 0.2.0
	 * @apiDescription 待提包裹转单-批量
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} newMemberId 被转单人id
	 * @apiParam {String} oldMemberId 转单人id
	 * @apiParam {String} parIds 包裹id，如果是多个包裹，用逗号隔开，如123,456,789
	 * @apiSampleRequest /sendPackage/changeSendPerson
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/changeSendPerson", method = { RequestMethod.POST, RequestMethod.GET })
	public String changeSendPerson(String newMemberId,String oldMemberId,String parIds){
		try {
			if(PubMethod.isEmpty(newMemberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.001", "newMemberId不能为空");
			}
			if(PubMethod.isEmpty(oldMemberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.002", "oldMemberId不能为空");
			}
			if(PubMethod.isEmpty(parIds)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeSendPerson.003", "parIds不能为空");
			}
			return this.sendPackageService.changeSendPerson( newMemberId, oldMemberId, parIds);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/pickUpParcel 确认提货--批量
	 * @apiVersion 0.2.0
	 * @apiDescription 确认提货-批量
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} memberPhone 快递员手机号
	 * @apiParam {String} parIds 包裹id，如果是多个包裹，用逗号隔开，如123,456,789
	 * @apiSampleRequest /sendPackage/pickUpParcel
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/pickUpParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String pickUpParcel(String parIds,Long memberId,String memberPhone){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.pickUpParcel.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(memberPhone)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.pickUpParcel.002", "memberPhone不能为空");
			}
			if(PubMethod.isEmpty(parIds)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.pickUpParcel.003", "parIds不能为空");
			}
			return this.sendPackageService.pickUpParcel( parIds, memberId, memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/querySendTaskList 查询待派任务列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待派任务列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Integer} currentPage 当前页码
	 * @apiParam {Integer} pageSize 每页显示条数
	 * @apiParam {String} mobilePhone 手机号
	 * @apiSampleRequest /sendPackage/querySendTaskList
	 * @apiSuccess {String} address  收件人地址
	 * @apiSuccess {String} mobile  收件人手机号
	 * @apiSuccess {String} parcelNum  包裹编号
	 * @apiSuccess {String} taskId  任务id
	 * @apiSuccess {String} parcelId  包裹id
	 * @apiSuccess {String} sendSmsType  群发通知类型 1.电话优先 2.仅电话 3.仅短信
	 * @apiSuccess {String} sendSmsStatus  群发通知状态  0.发送成功  1.呼叫成功  2.重复  3.呼叫失败  4.发送失败   5.用户退订6.拦截 7.延时发送8.微信发送成功 
	 * @apiSuccess {String} callBackStatus  群发通知回执状态 0.未知  1.成功  2.失败  3.呼叫未知  4.呼叫成功  5.呼叫失败 6.短信转通道 7微信发送失败 8.微信发送成功
	 * @apiSuccess {String} replyStatus  回复状态     0.未回复  1.客户回复  2.短信已读
	 * @apiSuccess {String} netId  快递网络id
	 * @apiSuccess {String} net_name  快递网络名称
	 * @apiSuccess {String} expNum  快递单号
	 * @apiSuccess {String} total  列表总数（data里面的total）
	 * @apiSuccess {String} isAgainSend  是否是重派的标识，空值为正常待派，1为重派待派
	 * @apiSuccess {String} ifFocusWeChat  是否关注过好递微信公众号 0否1是
	 * @apiSuccess {Short} whatFlag  微信模板是否推送成功,0:失败, 1:成功
	 * @apiSuccess {Integer} mark  是否标记,0:否, 1:是
	 * @apiSuccessExample Success-Response:
	     {
		    "data": {
		        "currentPage": 1,
		        "hasFirst": false,
		        "hasLast": false,
		        "hasNext": false,
		        "hasPre": false,
		        "items": [
		            {
		                "address": "马莲道中里",
		                "mobile": "13717560939",
		                "parcelNum": "1",
		                "taskId": "1234567",
		                "sendSmsType": "1",
		                "parcelId": 128565849169920
		                "sendSmsStatus": "2"
		                "callBackStatus": "1"
		                "replyStatus": "1"
		            }
		        ],
		        "offset": 0,
		        "pageCount": 1,
		        "pageSize": 10,
		        "rows": [],
		        "total": 1
		    },
		    "success": true
		}
		
		//状态判断逻辑
		if(sendSmsStatus==2){
				重复
		}else if(sendSmsStatus==5){
				用户退订
		}else if(sendSmsType==1){
			if(sendSmsStatus==0&&callBackStatus==0){
				短信已发送
			}else if(sendSmsStatus==0&&callBackStatus==1){
				if(replyStatus==0){
					短信已收到
				}else if(replyStatus==1){
					客户回复
				}else if(replyStatus==2){
					短信已读
				}else{
					短信发送失败
				}
			}else if(sendSmsStatus==0&&callBackStatus==2){
				短信发送失败
			}else if(sendSmsStatus==4){
				短信发送失败
			}else{
				短信发送失败
			}
		}else if(sendSmsType==2){
			if(sendSmsStatus==0&&callBackStatus==0){
				电话转短信短信已发送
			}else if(sendSmsStatus==0&&callBackStatus==1){
				if(replyStatus==0){
					电话转短信已收到
				}else if(replyStatus==1){
					客户回复
				}else if(replyStatus==2){
					短信已读
				}else{
					电话转短信发送失败
				}
			}else if(sendSmsStatus==0&&callBackStatus==2){
				电话转短信发送失败
			}else if(sendSmsStatus==4){
				电话转短信发送失败
			}else if(sendSmsStatus==1&&callBackStatus==3){
				电话优先呼叫中
			}else if(sendSmsStatus==1&&callBackStatus==4){
				电话优先已接听
			}else if(sendSmsStatus==1&&callBackStatus==5){
				电话优先未接听
			}else if(sendSmsStatus==3){
				电话优先未接听
			}else{
				电话优先未接听
			}
		}else if(sendSmsType==3){
			if(sendSmsStatus==1&&callBackStatus==3){
				呼叫中
			}else if(sendSmsStatus==1&&callBackStatus==4){
				已接听
			}else if(sendSmsStatus==1&&callBackStatus==5){
				未接听
			}else if(sendSmsStatus==3){
				未接听
			}else{
				未接听
			}
		}else{
			短信发送失败
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
	@RequestMapping(value = "/querySendTaskList", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendTaskList(Long memberId,Integer currentPage,Integer pageSize,String mobilePhone){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(currentPage)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.002", "currentPage不能为空");
			}
			if(PubMethod.isEmpty(pageSize)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendTaskList.003", "pageSize不能为空");
			}
			return this.sendPackageService.querySendTaskList( memberId, currentPage, pageSize,mobilePhone );
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/updateParcelInfo 编辑派件包裹信息
	 * @apiVersion 0.2.0
	 * @apiDescription 编辑派件包裹信息
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} parId 包裹id
	 * @apiParam {String} netId 快递网络id
	 * @apiParam {String} expNum 快递单号
	 * @apiParam {String} mobile 收件人手机号
	 * @apiParam {String} address 收件人详细地址
	 * @apiParam {String} codAmount 应收货款
	 * @apiParam {String} freight 应收运费
	 * @apiParam {String} name 收件人姓名
	 * @apiSampleRequest /sendPackage/updateParcelInfo
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/updateParcelInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateParcelInfo(String parId,String netId, String expNum,String mobile,String address,String codAmount,String freight,String name){
		try {
			if(PubMethod.isEmpty(parId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelInfo.001", "parId不能为空");
			}
			if(PubMethod.isEmpty(mobile)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateParcelInfo.002", "mobile不能为空");
			}
			return this.sendPackageService.updateParcelInfo( parId, netId, expNum, mobile, address, codAmount, freight, name);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	

	/**
	 * @author jianxin.ma
	 * @api {post} /sendPackage/queryNearCompInfo 转代收--查询附近5公里代收点
	 * @apiVersion 0.2.0
	 * @apiDescription 转代收--查询附近5公里代收点
	 * @apiGroup 新版-派件V4
	 * @apiParam {Double} longitude 经度
	 * @apiParam {Double} latitude 纬度
	 * @apiParam {Short} agentType '代收点类型: 1 社区小店  2 公司前台  3 收发室  4 小区物业'
	 * @apiSampleRequest /sendPackage/queryNearCompInfo
	 * @apiSuccess {String} agentType  '代收点类型: 1 社区小店  2 公司前台  3 收发室  4 小区物业'
	 * @apiSuccess {String} compAddress  代收点(站点)地址
	 * @apiSuccess {String} compId  代收点(站点)id
	 * @apiSuccess {String} compImgUrl  代收点(站点)头像(站点没有头像,查的是站点负责人的头像)
	 * @apiSuccess {String} compName  代收点(站点)名称
	 * @apiSuccess {String} compTypeNum  公司分类代码
	 * @apiSuccess {String} distance  距离(单位/米)
	 * @apiSuccess {String} latitude  纬度
	 * @apiSuccess {String} longitude 经度
	 * @apiSuccess {String} netId 所属网络id
	 * @apiSuccess {String} netName 所属网络名称
	 * @apiSuccess {String} resPhone 站点负责人电话
	 * @apiSuccessExample Success-Response:
	     {
		     "data":
		     [
			     {
				     "agentType":"null",
				     "compAddress":"北京市海淀区|花园路13号",
				     "compId":"180549897756672",
				     "compImgUrl": "http://cas.okdit.net/nfs_data/mob/head/179790737186816.jpg",
				     "compName":"试试快递",
				     "compTypeNum":"1040",
				     "distance":226,
				     "latitude":"39.979098",
				     "longitude":"116.370754",
				     "netId":"1501",
				     "netName":"圆通速递",
				     "resPhone":"18201538588"
			     },
			     {
				     "agentType":"null",
				     "compAddress":"北京市海淀区|中央民族大学5号学生宿舍",
				     "compId":"184706922782720",
				     "compImgUrl": "http://cas.okdit.net/nfs_data/mob/head/158638587322368.jpg",
				     "compName":"绿色新疆新疆特产水果店",
				     "compTypeNum":"1040",
				     "distance":4436,
				     "latitude":"39.958007",
				     "longitude":"116.325267",
				     "netId":"1501",
				     "netName":"圆通速递",
				     "resPhone":"18811497287"
			     }
		     ],
		     "success":true
	     }
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"01",
	 *    	"message":"longitude不能为空!",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNearCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNearCompInfos(Double longitude,Double latitude,Short agentType){
		if(PubMethod.isEmpty(longitude)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.01", "longitude不能为空");
		}
		if(PubMethod.isEmpty(latitude)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.02", "latitude不能为空");
		}
//		if(PubMethod.isEmpty(agentType)){
//			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryNearCompInfos.03", "agentType不能为空");
//		}
		try {
			return this.sendPackageService.queryNearCompInfo(longitude, latitude,agentType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	/**
	 * @author jianxin.ma
	 * @api {post} /sendPackage/saveNearComp 转代收--添加一个代收点
	 * @apiVersion 0.2.0
	 * @apiDescription 转代收--添加一个代收点
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} agentType   代收点类型
	 * @apiParam {String} compName    代收点名称
	 * @apiParam {String} compMobile  代收点电话
	 * @apiParam {String} compAddress 代收点地址
	 * @apiParam {Long} actorMemberId 添加人id
	 * @apiSampleRequest /sendPackage/saveNearComp
	 * @apiSuccessExample Success-Response:
	    {"success":true}
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"01",
	 *    	"message":"compName不能为空!",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/saveNearComp", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveNearComp(String agentType, String compName, String compMobile, String compAddress, Long actorMemberId){
		if(PubMethod.isEmpty(compName)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveNearComp.01", "compName不能为空");
		}
		if(PubMethod.isEmpty(compMobile)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveNearComp.02", "compMobile不能为空");
		}
		if(PubMethod.isEmpty(actorMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveNearComp.03", "actorMemberId不能为空");
		}
		try {
			return this.sendPackageService.saveNearComp(agentType, compName, compMobile,compAddress, actorMemberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
	/**
	 * @author jianxin.ma
	 * @api {post} /sendPackage/ifCompExist 转代收--根据代收点名称和手机号判断代收点是否存在
	 * @apiVersion 0.2.0
	 * @apiDescription 转代收--根据代收点名称和手机号判断代收点是否存在
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compMobile 代收点电话
	 * @apiSampleRequest /sendPackage/ifCompExist
	 * @apiSuccess {String} compId 所属站点(代收点)id
	 * @apiSuccess {String} memberId 所属站点手机号对应的memberId
	 * @apiSuccess {String} ifCompExist 对应的站点是否存在(0:不存在 1:存在) 调用时先判断这个字段的值,不存在则"compInfo": [],
	 * @apiSuccessExample Success-Response:
	 * 一.存在:
	    {
			"data": {
				"compInfo": [
					{
						"compId": 208569784123525,
						"memberId": 208569318555648
					}
					],
				"ifCompExist": "1"
				},
			"success": true
		}
		二.不存在:
		{
			"data": {
				"compInfo": [],
				"ifCompExist": "0"
			},
			"success": true
		}
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"01",
	 *    	"message":"compName不能为空!",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/ifCompExist", method = { RequestMethod.POST, RequestMethod.GET})
	public String ifCompExist(String compName,String compMobile){
		if(PubMethod.isEmpty(compName)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifCompExist.01", "compName不能为空");
		}
		if(PubMethod.isEmpty(compMobile)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifCompExist.02", "compMobile不能为空");
		}
		try {
			return this.sendPackageService.ifCompExist(compName, compMobile);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	
}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/normalSignParcel 派件正常签收
	 * @apiVersion 0.2.0
	 * @apiDescription 派件正常签收
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} taskId 任务id
	 * @apiParam {String} parcelId 包裹id
	 * @apiParam {Double} totalCodAmount 应收货款
	 * @apiParam {Double} totalFreight 应收运费
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} mobile 收件人手机号
	 * @apiParam {String} name 收件人姓名
	 * @apiParam {String} address 收件人地址
	 * @apiParam {String} sex 性别：1男2女 未知 传空值
	 * @apiParam {String} signType 1.签收并保存客户信息 2.签收
	 * @apiParam {String} signFlag 客户正常签收-传空值 ；有派无签-传1
	 * @apiParam {String} custlabel 客户标签（个人用户，-1,1；电商用户，-1,3；企业用户，8,5公司前台，6收发室，7小区物业；代收点，9,4社区小店）
	 * @apiParam {String} custParentLabel 客户父标签 （没有传-1）
	 * @apiParam {String} compName 公司名称
	 * @apiSampleRequest /sendPackage/normalSignParcel
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/normalSignParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String normalSignParcel(Long taskId,String parcelId,Double totalCodAmount,Double totalFreight,Long memberId,
			String mobile,String name,String address,String sex,String signType,String signFlag,String custlabel,String custParentLabel,String compName){
		try {
			if(PubMethod.isEmpty(taskId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.001", "taskId不能为空");
			}
			if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.002", "parcelId不能为空");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.003", "memberId不能为空");
			}
			if(PubMethod.isEmpty(signType)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcel.004", "signType不能为空");
			}
			return this.sendPackageService.normalSignParcel(taskId, parcelId, totalCodAmount, totalFreight, memberId, mobile, name, address, sex,signType,signFlag,custlabel,custParentLabel,compName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author jianxin.ma
	 * @api {post} /sendPackage/changeAccept  派件--转代收(批量)
	 * @apiVersion 0.2.0
	 * @apiDescription 派件--转代收(批量)
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} newMemberId 转给的代收点(站点)人员id
	 * @apiParam {Long} newCompId 转给的代收点(站点)id
	 * @apiParam {Long} newNetId 转给站点网络id(可为空)
	 * @apiParam {Long} oldMemberId 操作人id
	 * @apiParam {String} parIds 包裹id(多个用逗号分隔)
	 * @apiParam {Short} flag 操作类型(1:转代收到代收点列表中的代收点成员 2:添加代收点转代收)
	 * @apiSampleRequest /sendPackage/changeAccept
	 * @apiSuccessExample Success-Response:
	    {"success":true}
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"01",
	 *    	"message":"parIds不能为空!",
	 *    	"success":false
	 *    }
	 */
	@ResponseBody
	@RequestMapping(value = "/changeAccept", method = { RequestMethod.POST, RequestMethod.GET })
	public String changeAccept(Long newMemberId,Long newCompId,Long newNetId,Long oldMemberId,String parIds,Short flag){
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "flag不能为空");
		}
		if(1==flag){
//			if(PubMethod.isEmpty(newMemberId)){
//				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "newMemberId不能为空");
//			}
//			if(PubMethod.isEmpty(newCompId)){
//				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "newCompId不能为空");
//			}
//			if(PubMethod.isEmpty(newNetId)){
//				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "newNetId不能为空");
//			}
		}
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "parIds不能为空");
		}
		if(PubMethod.isEmpty(oldMemberId)){
			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.changeAccept", "oldMemberId不能为空");
		}
		try {
			return this.sendPackageService.changeAccept(newMemberId, newCompId, newNetId, oldMemberId, parIds,flag);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/normalSignParcelBatch 派件正常签收--批量(确认妥投)
	 * @apiVersion 0.2.0
	 * @apiDescription 派件正常签收--批量(确认妥投)
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} taskIds 任务id，用逗号隔开，例如123,456,789
	 * @apiParam {String} signType 签收类型：14(个人签收)客户正常签收15(他人代收)有派无签
	 * @apiSampleRequest /sendPackage/normalSignParcelBatch
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/normalSignParcelBatch", method = { RequestMethod.POST, RequestMethod.GET })
	public String normalSignParcelBatch(String taskIds,String signType){
		try {
			if(PubMethod.isEmpty(taskIds)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.normalSignParcelBatch.001", "taskIds不能为空");
			}
			return this.sendPackageService.normalSignParcelBatch(taskIds,signType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author jianxin.ma
	 * @api {post} /sendPackage/createSendTask 添加派件
	 * @apiVersion 0.2.0
	 * @apiDescription 添加派件
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId   创建人Id     
	 * @apiParam {String} actorPhone 	快递员(执行人)电话
	 * @apiParam {String} expWaybillNum 	运单号
	 * @apiParam {Long} compId  '任务受理方站点id',          
	 * @apiParam {Long} netId        '任务受理方网络id',          
	 * @apiParam {String} addresseeName       收件人姓名        
	 * @apiParam {String} addresseeMobile     收件人手机            
	 * @apiParam {Long} addresseeAddressId  收件人地址id          
	 * @apiParam {String} cityName    收件人城市名   
	 * @apiParam {String} addresseeAddress    收件人详细地址           
	 * @apiParam {BigDecimal} freight 包裹应收运费                                 
	 * @apiParam {BigDecimal} codAmount 代收货款金额                 
	 * @apiSampleRequest /sendPackage/createSendTask
	 * @apiSuccess {String} taskId  任务id
	 * @apiSuccess {String} parcelId  包裹id(-1 :该运单号已存在,不进行保存包裹操作.正常保存返回包裹id)

	 * @apiSuccessExample Success-Response:
		{
			"success":true,
			"taskId":209331652182016,
			"parcelId":208961735794688
		}
	 * @apiErrorExample Error-Response:
	 *    {
	 *    	"errCode":0,
	 *    	"errSubcode":"01",
	 *    	"message":"actualSendMember不能为空!",
	 *    	"success":false
	 *    }
	 */
    @ResponseBody
    @RequestMapping(value="/createSendTask",method = { RequestMethod.POST, RequestMethod.GET })
	public String createSendTask(String actorPhone, Long memberId,String expWaybillNum, Long compId, Long netId, String addresseeName, 
			String addresseeMobile,Long addresseeAddressId,String cityName,String addresseeAddress, BigDecimal freight,BigDecimal codAmount) {

		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("SendTaskController.createSendTask.memberId","memberId不能为空");
		}
		if(PubMethod.isEmpty(addresseeMobile)){
			return paramsFailure("SendTaskController.createSendTask.addresseeMobile","收件人电话不能为空");
		}
		try {

			return sendPackageService.createSendTask(actorPhone, memberId, expWaybillNum, compId, netId, addresseeName, addresseeMobile, addresseeAddressId,cityName, addresseeAddress, freight, codAmount);
		} catch (RuntimeException e) {
		   return jsonFailure(e);
		}
		 
	}

	@ResponseBody
	@RequestMapping(value="/createSendTaskBatch",method = { RequestMethod.POST,RequestMethod.GET })
	public String createSendTask(String info, Long memberId, Long netId, Long compId) {
		if(PubMethod.isEmpty(info) || PubMethod.isEmpty(memberId)){
			return paramsFailure("001","参数不正确");
		}
		if (PubMethod.isEmpty(netId.toString()) || PubMethod.isEmpty(compId)) {
			return paramsFailure("002","参数不正确");
		}
		return sendPackageService.createSendTask(info, memberId, netId, compId);
	}

	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/exceptionSignParcel 派件异常签收
	 * @apiVersion 0.2.0
	 * @apiDescription 派件异常签收
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} taskId 任务id
	 * @apiParam {String} parcelId 包裹id
	 * @apiParam {String} memberId 快递员id
	 * @apiParam {String} exceptionType 异常类型：1.超出收派范围2.忙不过来3.客户取消4.联系不到客户5.其他原因
	 * @apiParam {String} textValue 选择其他原因时填写的内容
	 * @apiParam {String} compId 所属站点id
	 * @apiSampleRequest /sendPackage/exceptionSignParcel
	 * @apiSuccessExample Success-Response:
	     {
		    "success": true
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
	@RequestMapping(value = "/exceptionSignParcel", method = { RequestMethod.POST, RequestMethod.GET })
	public String exceptionSignParcel(String taskId,String parcelId,String memberId,String exceptionType,String textValue,String compId){
		try {
			if(PubMethod.isEmpty(taskId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.001", "taskId不能为空");
			}
			if(PubMethod.isEmpty(parcelId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.002", "parcelId不能为空");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.003", "memberId不能为空");
			}
			if(PubMethod.isEmpty(exceptionType)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.exceptionSignParcel.004", "exceptionType不能为空");
			}
			return this.sendPackageService.exceptionSignParcel( taskId, parcelId, memberId, exceptionType, textValue, compId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/querySendRecordList 派件记录列表
	 * @apiVersion 0.2.0
	 * @apiDescription 派件记录列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} signDate 时间，年月日，例如2015-08-08
	 * @apiParam {String} mobilePhone 手机号（搜索条件）
	 * @apiSampleRequest /sendPackage/querySendRecordList
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccess {String} freight  应收运费
	 * @apiSuccess {String} codAmount  应收货款
	 * @apiSuccess {String} expWaybillNum  快递单号
	 * @apiSuccess {String} addresseeMobile  收件人电话
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} addresseeAddress 收件人地址
	 * @apiSuccess {String} createTime 签收时间
	 * @apiSuccess {String} net_name 快递网络名称
	 * @apiSuccess {String} disposalDesc 异常原因描述
	 * @apiSuccess {String} finishList 正常签收列表
	 * @apiSuccess {String} finishCount 正常签收列表数量
	 * @apiSuccess {String} exceptionList 异常签收列表
	 * @apiSuccess {String} exceptionCount 异常签收列表数量
	 * @apiSuccess {String} sendButNoSignList 有派无签列表
	 * @apiSuccess {String} sendButNoSignCount 有派无签列表数量
	 * @apiSuccess {String} changeSignList 转代收签收列表
	 * @apiSuccess {String} changeSignCount 转代收签收列表数量
	 * @apiSuccessExample Success-Response:
	     {
		              暂无
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
	@RequestMapping(value = "/querySendRecordList", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendRecordList(Long memberId,String signDate,String mobilePhone){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordList.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(signDate)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordList.002", "signDate不能为空");
			}
			return this.sendPackageService.querySendRecordList( memberId, signDate,mobilePhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}	
	/**
	 * 
	 * @author song
	 * @api {post} /sendPackage/querySendRecords 派件记录列表   2017-2-21
	 * @apiVersion 0.2.0
	 * @apiDescription 派件记录列表(new )(已派)
	 * @apiGroup 新版-报表V5
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} signDate 时间，年月日，例如2015-11-18
	 * @apiParam {String} mobilePhone 手机号（搜索条件）
	 * @apiParam {String} expWaybillNum 运单号（搜索条件）
	 * @apiParam {Short} signResult 签收类型   14：个人签收- 15：他人签收  3：转代收点- 16系统签收
	 * @apiParam {String} compId   站点id  本站全部
	 * @apiParam {Integer} currentPage
	 * @apiParam {Integer} pageSize
	 * @apiSampleRequest /sendPackage/querySendRecords
	 * @apiSuccess {String} parId  包裹id
	 * @apiSuccess {String} memberId  收派员id
	 * @apiSuccess {String} netName 快递网络名称 
	 * @apiSuccess {String} expWaybillNum  快递单号
	 * @apiSuccess {String} addresseeMobile  收件人电话
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} signTime 签收时间	
	 * @apiSuccess {Short} signResult 签收类型 
	 * @apiSuccessExample Success-Response:
	   {
    "data": {
        "listMap": [
            {
                 "parId": "5764684535346",
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"signTime": 1482833545837,
				"addresseeMobile" : "13535353500"
				"addresseeName": "李四"
            },
            {
             	"parId": "5764684535346",
				"expWaybillNum": "70567200043203",
				"netName": "AAE全球专递",
				"signTime": 1482833545837,
				"addresseeMobile" : "13535353500"
				"addresseeName": "李四"
            }
        ],
        "listMapNum": 2
    },
    "success": true 
	
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/querySendRecords", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendRecords(Long memberId,String signDate,String mobilePhone,String expWaybillNum,Short signResult,String compId,Integer currentPage,Integer pageSize){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecords.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(signDate)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecords.002", "signDate不能为空");
			}
			return this.sendPackageService.querySendRecords( memberId, signDate,mobilePhone,expWaybillNum,signResult,compId,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 
	 * @author song
	 * @api {post} /sendPackage/querySendRecordDetail 派件记录详情   2017-2-18
	 * @apiVersion 0.2.0
	 * @apiDescription 派件记录详情(new )(已派)
	 * @apiGroup 新版-报表V5
	 * @apiParam {Long} memberId 快递员id（不能为空）
	 * @apiParam {Long} parId  包裹id   （不能为空）
	 * @apiSampleRequest /sendPackage/querySendRecordDetail
	 * @apiSuccess {String} netName 快递网络名称
	 * @apiSuccess {String} expWaybillNum  快递单号
	 * @apiSuccess {String} addresseeMobile  收件人电话
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} addresseeAddress 收件人地址	
	 * @apiSuccess {String} signResult 签收类型
	 * @apiSuccess {String} recName   接受人姓名
	 * @apiSuccess {String} recMobile    接受人电话
	 * @apiSuccess {String} recUnits  接受人单位
	 * @apiSuccess {String} recAddress      接受人地址 
	 * @apiSuccess {String} createTime     接受时间  
	 * @apiSuccess {String} content     标记备注
	 * @apiSuccessExample Success-Response:
	     {
  		标记备注  "content":"[ { \"name\" : \"格格\" , \"content\" : \"放到 小树边 \" , \"time\" : 1486454389544} ,
		  { \"name\" : \"格格\" , \"content\" : \"联系不上 \" , \"time\" : 1486454379322}]"
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
	@RequestMapping(value = "/querySendRecordDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendRecordDetail(Long memberId,Long parId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordDetail.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(parId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecordDetail.002", "parId不能为空");
			}
			return this.sendPackageService.querySendRecordDetail( memberId, parId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * @author song
	 * @api {post} /sendPackage/querySendForms 投递报表   2016-12-29
	 * @apiVersion 0.2.0
	 * @apiDescription 签收报表(new )(已派)
	 * @apiGroup 新版-报表V5
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} signDate 时间，年月，例如2015-08
	 * @apiParam {Short} signResult 签收类型   14：个人签收- 15：他人签收  3：转代收点- 16系统签收    为空是默认全部类型   
	 * @apiParam {String} compId    compId  本站全部
	
	 * @apiSampleRequest /sendPackage/querySendForms
	 * @apiSuccessExample Success-Response:
	{
    "data": {
        "mapList": [
            {
                "date": "6月9日",
                "count": 1
            },
            {
                "date": "6月4日",
                "count": 17
            },
            {
                "date": "6月3日",
                "count": 4
            },
            {
                "date": "6月1日",
                "count": 3
            }
          ],
          "sum": 25
      },
         "success": true
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
	   	@RequestMapping(value = "/querySendForms", method = { RequestMethod.POST, RequestMethod.GET })
	   	public String querySendForms(Long memberId,String signDate,Short signResult,String compId){
	   		if(PubMethod.isEmpty(memberId)){
	   			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecord.001", "memberId不能为空");
	   		}
	   		if(PubMethod.isEmpty(signDate)){
	   			return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendRecord.003", "signDate不能为空");
	   		}
	   		try {
	   			return  this.sendPackageService.querySendForms(memberId,signDate,signResult,compId);
	   			
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   			return jsonFailure(e);
	   		}
	   	}
	
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/isRegisterByPhone 判断该手机号是否注册
	 * @apiVersion 0.2.0
	 * @apiDescription 判断该手机号是否注册-0否1是
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} memberPhone  手机号
	 * @apiSampleRequest /sendPackage/isRegisterByPhone
	 * @apiSuccess {String} isRegister  0否1是
	 * @apiSuccessExample Success-Response:
	     {
		     "data":
		     {
		     	"isRegister":"0"
		     }
		     "success":true
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
	@RequestMapping(value = "/isRegisterByPhone", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRegisterByPhone(String memberPhone){
		try {
			if(PubMethod.isEmpty(memberPhone)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.isRegisterByPhone.001", "memberPhone不能为空");
			}
			return this.sendPackageService.isRegisterByPhone(memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/querySendCountAll 查询待派数量和待提数量
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待派数量和待提数量
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} memberId  快递员id
	 * @apiSampleRequest /sendPackage/querySendCountAll
	 * @apiSuccess {Long} toBeTakencount  待提包裹数量
	 * @apiSuccess {Long} sendTaskcount  待派任务数量
	 * @apiSuccessExample Success-Response:
	     {
		     "data":
		     {
		     	"toBeTakencount":12,
		     	"sendTaskcount":22
		     }
		     "success":true
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
	@RequestMapping(value = "/querySendCountAll", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendCountAll(String memberId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.querySendCountAll.001", "memberId不能为空");
			}
			return this.sendPackageService.querySendCountAll( memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/newQuerySendCountAll 查询待派数量和待提数量
	 * @apiVersion 0.2.0
	 * @apiDescription 查询待派数量和待提数量
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} memberId  快递员id
	 * @apiSampleRequest /sendPackage/newQuerySendCountAll
	 * @apiSuccess {Long} toBeTakencount  待提包裹数量
	 * @apiSuccess {Long} sendTaskcount  待派任务数量
	 * @apiSuccessExample Success-Response:
	     {
		     "data":
		     {
		     	"toBeTakencount":12,
		     	"sendTaskcount":22
		     }
		     "success":true
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
	@RequestMapping(value = "/newQuerySendCountAll", method = { RequestMethod.POST, RequestMethod.GET })
	public String newQuerySendCountAll(String memberId){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.newQuerySendCountAll.001", "memberId不能为空");
			}
		
			return this.sendPackageService.newQuerySendCountAll( memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/leaveOffice 快递员离职
	 * @apiVersion 0.2.0
	 * @apiDescription 快递员离职
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} memberId  快递员id
	 * @apiParam {Long} compId  快递员所属站点id
	 * @apiParam {String} memberName  快递员姓名
	 * @apiParam {String} memberPhone  快递员登录手机号
	 * @apiSampleRequest /sendPackage/leaveOffice
	 * @apiSuccess {String} isFinish 是否有三天内抢单取件未完成的任务？0否--可以离职； 1是--不能离职
	 * @apiSuccessExample Success-Response:
	     {
		     "data":
		     {
		     	"isFinish":"1"
		     }
		     "success":true
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
	@RequestMapping(value = "/leaveOffice", method = { RequestMethod.POST, RequestMethod.GET })
	public String leaveOffice(Long memberId, Long compId,String memberName, String memberPhone){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.leaveOffice.001", "memberId不能为空");
			}
			return this.sendPackageService.leaveOffice(memberId,compId,memberName,memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/updateNumByParId 根据包裹parid修改编号
	 * @apiVersion 0.2.0
	 * @apiDescription 根据包裹parid修改编号
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} jsonData json格式字符串，格式如下：{"items":[{"parId":"123","parNum":"123","phone": "123"},{"parId":"456","parNum":"456","phone": "456"}]}，parId-包裹id，parNum-包裹编号，phone-手机号
	 * @apiSampleRequest /sendPackage/updateNumByParId
	 * @apiSuccessExample Success-Response:
	     {
		     "success":true
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
	@RequestMapping(value = "/updateNumByParId", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateNumByParId(String jsonData){
		try {
			if(PubMethod.isEmpty(jsonData)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updateNumByParId.001", "jsonData不能为空");
			}
			return this.sendPackageService.updateNumByParId(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/saveParcels 批量保存电话号码为包裹信息-旧版本
	 * @apiVersion 0.2.0
	 * @apiDescription 批量保存电话号码为包裹信息-旧版本
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} phone 收派员电话
	 * @apiParam {String} phoneData 手机号集合，逗号隔开，例如：15011232453,15688888888,15866666666
	 * @apiSampleRequest /sendPackage/saveParcels
	 * @apiSuccessExample Success-Response:
	     {
		     "success":true
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
	@RequestMapping(value = "/saveParcels", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveParcels(String memberId,String phone, String phoneData){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(phone)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.001", "phone不能为空");
			}
			if(PubMethod.isEmpty(phoneData)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcels.001", "phoneData不能为空");
			}
			return this.sendPackageService.saveParcels(memberId,phone,phoneData);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/saveParcelsNew 批量保存电话号码为包裹信息-新版本
	 * @apiVersion 0.2.0
	 * @apiDescription 批量保存电话号码为包裹信息-新版本
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} phone 收派员电话
	 * @apiParam {String} jsonData json字符串，格式如下：{"items":[{"parNum":"123","mobilePhone":"123"},{"parNum":"345","mobilePhone":"345"},{"parNum":"567","mobilePhone":"567"}]}
	 * 	parNum 包裹编号，mobilePhone 手机号
	 * @apiSampleRequest /sendPackage/saveParcelsNew
	 * @apiSuccessExample Success-Response:
	     {
		     "success":true
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
	@RequestMapping(value = "/saveParcelsNew", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveParcelsNew(String memberId,String phone, String jsonData){
		try {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcelsNew.001", "memberId不能为空");
			}
			if(PubMethod.isEmpty(phone)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcelsNew.001", "phone不能为空");
			}
			if(PubMethod.isEmpty(jsonData)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.saveParcelsNew.001", "jsonData不能为空");
			}
			return this.sendPackageService.saveParcelsNew(memberId,phone,jsonData);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/updatePhoneInWrongList 问题件中发送失败列表修改电话号码
	 * @apiVersion 0.2.0
	 * @apiDescription 问题件中发送失败列表修改电话号码
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} msgId 短信id
	 * @apiParam {String} taskId 派件任务id
	 * @apiParam {String} mobile 修改后的手机号
	 * @apiSampleRequest /sendPackage/updatePhoneInWrongList
	 * @apiSuccessExample Success-Response:
	     {
		     "success":true
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
	@RequestMapping(value = "/updatePhoneInWrongList", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatePhoneInWrongList(String msgId, String taskId,String mobile){
		try {
			if(PubMethod.isEmpty(msgId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updatePhoneInWrongList.001", "msgId不能为空");
			}
			if(PubMethod.isEmpty(taskId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updatePhoneInWrongList.002", "taskId不能为空");
			}
			if(PubMethod.isEmpty(mobile)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.updatePhoneInWrongList.003", "mobile不能为空");
			}
			return this.sendPackageService.updatePhoneInWrongList(msgId,taskId,mobile);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/queryOpenId 根据手机号查询微信openId（是否关注好递微信公众号）
	 * @apiVersion 0.2.0
	 * @apiDescription 根据手机号查询微信openId
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} mobile 手机号
	 * @apiSampleRequest /sendPackage/queryOpenId
	 * @apiSuccess {String} openId  微信openId（openId不为空表示已关注）
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "openId": "123456789"
		    },
		    "success": true
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
	@RequestMapping(value = "/queryOpenId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryOpenId(String mobile){
		try {
			if(PubMethod.isEmpty(mobile)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryOpenId.001", "mobile不能为空");
			}
			return this.sendPackageService.queryOpenId(mobile);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/queryOpenIdByMobiles 根据批量手机号查询微信openId（是否关注好递微信公众号）
	 * @apiVersion 0.2.0
	 * @apiDescription 根据手机号查询微信openId
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} mobiles 批量手机号，用逗号隔开，如15011232453,18866666666,14144144414
	 * @apiSampleRequest /sendPackage/queryOpenIdByMobiles
	 * @apiSuccess {String} mobile  手机号
	 * @apiSuccess {String} openId  微信openId（openId不为空表示已关注）
	 * @apiSuccessExample Success-Response:
	    {
		    "data": [
		        {
		            "mobile": "15011232453",
		            "openId": ""
		        },
		        {
		            "mobile": "13581652368",
		            "openId": "onxmgwRPJ3EvkxA5SXJc6T_hXKpM"
		        }
		    ],
		    "success": true
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
	@RequestMapping(value = "/queryOpenIdByMobiles", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryOpenIdByMobiles(String mobiles){
		try {
			if(PubMethod.isEmpty(mobiles)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.queryOpenIdByMobiles.001", "mobiles不能为空");
			}
			return this.sendPackageService.queryOpenIdByMobiles(mobiles);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/ifExistedCustomer 判断该手机号是否已经存在于该快递员客户列表
	 * @apiVersion 0.2.0
	 * @apiDescription 判断该手机号是否已经存在于该快递员客户列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} mobilePhone 手机号123
	 * @apiParam {String} memberId 收派员memberId
	 * @apiSampleRequest /sendPackage/ifExistedCustomer
	 * @apiSuccess {String} ifExisted  是否存在 0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "ifExisted": "0"
		    },
		    "success": true
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
	@RequestMapping(value = "/ifExistedCustomer", method = { RequestMethod.POST, RequestMethod.GET })
	public String ifExistedCustomer(String mobilePhone,String memberId){
		try {
			if(PubMethod.isEmpty(mobilePhone)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifExistedCustomer.001", "mobilePhone不能为空");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.apiV4.controller.SendPackageController.ifExistedCustomer.002", "memberId不能为空");
			}
			return this.sendPackageService.ifExistedCustomer(mobilePhone,memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /sendPackage/replacePhone 更换登录手机号
	 * @apiVersion 0.2.0
	 * @apiDescription 更换登录手机号
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} oldPhone 旧手机号
	 * @apiParam {String} newPhone 新手机号
	 * @apiParam {String} memberId 收派员memberId
	 * @apiSampleRequest /sendPackage/replacePhone
	 * @apiSuccess {Boolean} success true false
	 * @apiSuccessExample Success-Response:
	    {
		    "success": true
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
    @RequestMapping(value = "/replacePhone", method = { RequestMethod.POST, RequestMethod.GET })
    public String replacePhone(String oldPhone,String newPhone,String memberId){
    	if(PubMethod.isEmpty(oldPhone)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.replacePhone.001", "oldPhone不能为空");
    	}
    	if(PubMethod.isEmpty(newPhone)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.replacePhone.002", "newPhone不能为空");
    	}
    	if(PubMethod.isEmpty(memberId)){
    		return paramsFailure("net.okdi.apiV4.controller.SendPackageController.replacePhone.003", "memberId不能为空");
    	}
    	try {
    		return this.sendPackageService.replacePhone(oldPhone,newPhone,memberId);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return jsonFailure(e);
    	}
    }

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/sign 派件签收生成二维码
	 * @apiVersion 0.2.0
	 * @apiDescription 派件签收生成二维码. 用户扫码后会有推送. type是1000. 根据推送的extraParam来进行下一步操作. 二维码内容
	 * 				   http://personal.okdi(t).net/personalapi/appsign/detail?opsid=1234.
	 * 				   如果是个人端扫描, 需要在接口参数中加入额外参数: &mid=987654&f=1
	 *				   mid是扫描人的memberId, f为固定值1
	 * @apiGroup 新版-派件
	 * @apiParam {String} memberId 收派员memberId
	 * @apiParam {String} parInfo 任务json数组。格式：[{"parId": 123, "phone": 170xxxxxxxx, "orderNo": "1111"}, {"parId": 123, "phone": 170xxxxxxxx, "orderNo": "1111"}] orderNo: 单号
	 * @apiSampleRequest /sendPackage/sign
	 * @apiSuccess {Integer} stat 在extraParam中. 1: 本人签收 2: 不是本人签收
	 * @apiSuccess {Long} opsId 签收id 在extraParam中. 就是opsId, 在签收确认时需要回传给服务端
	 * @apiSuccess {String} phone 在extraParam中. 签收人手机号
	 * @apiSuccess {String} headImg 在extraParam中. 签收人头像
	 * @apiSuccess {String} name 在extraParam中. 签收人手机号
	 * @apiSuccessExample Success-Response:
		{
			"data": {
				"code": 1,  -- 1: 正常响应 可以生成二维码  2: 包裹已被签收 3: 生成二维码失败
				"errorMsg": "", --code为1时, 字段是空串
				"qrCode": "" --二维码内容,
				"opsId": 123456789
			},
			"success": true
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
	@RequestMapping(value = "/sign", method = RequestMethod.POST)
	public String signPkg(Long memberId, String parInfo) {

		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(parInfo)) {
			return paramsFailure("001", "参数不正确");
		}
		return sendPackageService.signPkg(memberId, parInfo);

	}

	/**
	 * 微信端扫码签收确认(签收人没有绑定手机号)
	 * 扫码人关注了公账号后的回调
	 */
	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/confirm_nobound 签收确认-没有绑定手机号(废弃)
	 * @apiVersion 0.2.0
	 * @apiDescription 签收确认-没有绑定手机号
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 收派员memberId
	 * @apiParam {Long} opsId 签收id, 推送获取
	 * @apiParam {String} ops 1. 客户拒收  2. 本人签收  3. 他人代收
	 * @apiParam {String} phone 他人签收时填写的手机号
	 * @apiSampleRequest /sendPackage/confirm_nobound
	 * @apiSuccessExample Success-Response:
	{

	"success": true
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
	@RequestMapping(value = "/confirm_nobound")
	public String signPkgConfirmNoound(Long memberId, Long opsId, String ops, String phone) {

		//ops: 1. 客户拒收  2. 本人签收  3. 他人代收
		if (!"123".contains(ops)) {
			return paramsFailure("01", "不支持的操作");
		}
		if ("3".equals(ops) && PubMethod.isEmpty(phone)) {
			return paramsFailure("02", "参数错误");
		}

		return sendPackageService.signPkgConfirmNobound(opsId, memberId, ops, phone);
	}

	/**
	 * 扫码签收确认 签收人扫码二维码后, 快递员端做签收
	 * type 13: 确认签收 14: 本人签收 15: 他人代收
	 */
	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/confirm_bound 签收确认
	 * @apiVersion 0.2.0
	 * @apiDescription 扫码签收确认 签收人扫码二维码后, 快递员端做签收
	 * @apiGroup 新版-派件
	 * @apiParam {Long} opsId 签收id, 推送获取
	 * @apiParam {type} type 13: 确认签收(推送后的签收确认) 14: 本人签收(标记签收) 15: 他人代收(标记签收)
	 * @apiSampleRequest /sendPackage/confirm_nobound
	 * @apiSuccessExample Success-Response:
	{

	"success": true
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
	@RequestMapping(value = "/confirm_bound")
	public String signPkgConfirmBound(Long opsId, String type) {

		if (PubMethod.isEmpty(opsId)) {
			return paramsFailure("001", "参数错误");
		}
		List<String> list = new ArrayList<>();
		for (int i = 13; i < 16; i++) {
			list.add(String.valueOf(i));
		}

		if (PubMethod.isEmpty(type) || !list.contains(type)) {
			return paramsFailure("002", "参数错误");
		}
		return sendPackageService.signPkgConfirmBound(opsId, type);
	}


	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/waybill 扫码更新单号
	 * @apiVersion 0.2.0
	 * @apiDescription 扫码更新单号
	 * @apiGroup 新版-派件
	 * @apiParam {String} json json数组。格式：[{"parId":111, "wayBillNum": ""}] parId: 包裹id wayBillNum: 单号
	 * @apiSampleRequest /sendPackage/waybill
	 * @apiSuccessExample Success-Response:
	{

	"success": true
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
	@RequestMapping(value = "waybill", method = RequestMethod.POST)
	public String updateWayBillNum(String json) {

		if (PubMethod.isEmpty(json)) {
			return paramsFailure("001", "参数不正确");
		}
		return this.sendPackageService.updateWaybillNum(json);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/parexist 单号查询包裹有没有
	 * @apiVersion 0.2.0
	 * @apiDescription 添加派件时，根据单号查询包裹有没有
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} wayBill 单号
	 * @apiSampleRequest /sendPackage/parexist
	 * @apiSuccessExample Success-Response:
	{
	 "data": 1, --1:已经存在 0：不存在
	"success": true
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
	@RequestMapping(value = "/parexist", method = {RequestMethod.POST, RequestMethod.GET})
	public String getParcelByWaybill(Long memberId, String wayBill) {
		if (PubMethod.isEmpty(wayBill) || PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "invalid params");
		}
		return sendPackageService.findParcelByWaybillNo(memberId, wayBill);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/waybill 扫码更新单号
	 * @apiVersion 0.2.0
	 * @apiDescription 扫码更新单号
	 * @apiGroup 新版-派件
	 * @apiParam {String} json json数组。格式：[{"taskId":111, "wayBillNum": ""}] taskId: 任务id wayBillNum: 单号
	 * @apiSampleRequest /sendPackage/waybill
	 * @apiSuccessExample Success-Response:
	{

	"success": true
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
	@RequestMapping(value = "signedlist")
	public String havaSignPackageList(Long memberId, @RequestParam("page") Integer currentPage) {
		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(currentPage)){
			return paramsFailure("001", "参数不正确");
		}
		return sendPackageService.haveSignPkgList(memberId, currentPage);
	}

	/**
	 * 报签收
	 */
	@ResponseBody
	@RequestMapping(value = "submit", method = RequestMethod.GET)
	public String submitPackage(Long memberId, String taskIds) {
		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(taskIds)) {
			return paramsFailure("001", "参数不正确");
		}
		String[] split = taskIds.split(",");
		List<Long> taskIdList = new ArrayList<>();
		try {
			for (String taskId : split) {
                if (PubMethod.isEmpty(taskId)) {
                    return paramsFailure("002", "参数不正确");
                }
                taskIdList.add(Long.valueOf(taskId));
            }
		} catch (NumberFormatException e) {
			return paramsFailure("003", "参数不正确");
		}
		return sendPackageService.submitPackage(memberId, taskIds);
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackage/staySign 扫码转签收查询
	 * @apiVersion 0.2.0
	 * @apiDescription 扫码转签收查询
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} memberId 快递员ID
	 * @apiParam {String} expNum   快递单号
	 * @apiParam {String} netId    快递网络id
	 * @apiParam {String} compId   站点Id
	 * @apiParam {String} netName  快递名称
	 * @apiParam {String} receName 收件人姓名
	 * @apiParam {String} mobile   收件人手机号
	 * @apiParam {String} actualSendMember  派件人ID
	 * @apiParam {String} contactAddress   接收人地址
	 * @apiParam {String} contactAddrLongitude  接收人地址经度
	 * @apiParam {String} contactAddrLatitude   接收人地址纬度
	 * @apiParam {String} numType  区分取消按钮接口传1
	 * @apiSampleRequest /sendPackage/staySign
	 * @apiSuccess {String} expNum      运单号
	 * @apiSuccess {String} addresseePhone  收件人手机号
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} createMemberId 包裹创建人ID
	 * @apiSuccess {String} parcelNum   取件码uid
	 * @apiSuccess {String} uid         包裹Id
	 * @apiSuccess {String} status      包裹当前状态 0:在途,未签收 1:已签收 2：拒收  
	 * @apiSuccess {String} type        数据状态 1:运单号查手机号不同 的包裹2:手机号查有多个包裹 3：直接创建的包裹  
	 * @apiSuccess {String} contactAddress   接收人地址
	 * @apiSuccessExample Success-Response:
	     {"data":{
	        "expNum":"12345678902655",
	     	"mobilePhone":"18640016307",
	     	"netId":1503,
	     	"net_name":"百世汇通",
	     	"sendTaskId":216434435497986},
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
	@RequestMapping(value = "/staySign", method = { RequestMethod.POST, RequestMethod.GET })
	public String staySign(String memberId, String expNum, String netId,String compId, String netName,
		String receName, String mobile, String actualSendMember,String contactAddress, String contactAddrLongitude,
		String contactAddrLatitude,String numType){
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("sendPackage.staySign.memberId","memberId不能为空");
		}
		if(PubMethod.isEmpty(netId)){
			return paramsFailure("sendPackage.staySign.netId","netId不能为空");
		}
		if(PubMethod.isEmpty(compId)){
			return paramsFailure("sendPackage.staySign.compId","compId不能为空");
		}
		if(PubMethod.isEmpty(netName)){
			return paramsFailure("sendPackage.staySign.netName","netName不能为空");
		}
		if(PubMethod.isEmpty(actualSendMember)){
			return paramsFailure("sendPackage.staySign.actualSendMember","actualSendMember不能为空");
		}
		try {
			return this.sendPackageService.staySign(memberId,expNum,netId,compId,netName,receName,mobile,actualSendMember,
					contactAddress,contactAddrLongitude,contactAddrLatitude,numType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackage/updatePhoWay 更新运单号,手机号,收件人姓名
	 * @apiVersion 0.2.0
	 * @apiDescription 更新运单号,手机号,收件人姓名
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} memberId  快递员ID
	 * @apiParam {String} uid       包裹ID
	 * @apiParam {String} expNum    运单号
	 * @apiParam {String} mobile    手机号
	 * @apiParam {String} netId     网络ID
	 * @apiParam {String} netName   快递名称
	 * @apiParam {String} compId    站点id
	 * @apiParam {String} receName  收件人姓名
	 * @apiSampleRequest /sendPackage/staySign
	 * @apiSuccessExample Success-Response:
	     {"data":{"ture",	
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
	@RequestMapping(value = "updatePhoWay", method = { RequestMethod.POST, RequestMethod.GET })
	public String updatePhoWay(String memberId, String uid, String expNum, String mobile, String netId, String netName,
			String compId, String receName) {
		
		if (PubMethod.isEmpty(uid)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updatePhoWay.001", "uid不能为空");
		}
		try {
			return this.sendPackageService.updatePhoWay(memberId,uid, expNum, mobile, netId, netName,compId,receName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackageReport/signRecord 签收记录
	 * @apiVersion 0.2.0
	 * @apiDescription 签收记录
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} memberId   快递员ID
	 * @apiParam {String} signTime   时间
	 * @apiParam {String} signResult   签收结果 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收） 14: 本人  15: 他人 16: 系统
	 * @apiSampleRequest /sendPackageReport/signRecord
	 * @apiSuccess {String} expNum      运单号
	 * @apiSuccess {String} netId       网络ID
	 * @apiSuccess {String} netName  	网络名称
	 * @apiSuccess {String} signTime   	签收时间
	 * @apiSuccess {String} signResult   签收结果 0：未签收/拒签 1：正常签收-签2：派而不签-派（正常签收）3：转代收-转（正常签收） 14: 本人  15: 他人 16: 系统
	 * @apiSuccess {String} uid         包裹Id
	 * @apiSuccessExample Success-Response:
	     "data":{"data":[{"expNum":"","mobilePhone":1476412570605,"netId":"","netName":"",
	     "signResult":1,"signTime":1476412570605,"uid":240275113181184},{"expNum":"","mobilePhone"
	     :1476412570404,"netId":"","netName":"","signResult":1,"signTime":1476412570404,"uid":240275113181186},
	     {"expNum":"","mobilePhone":1476412570183,"netId":"","netName":"","signResult":1,"signTime":1476412570183,
	     "uid":240275113181188},{"expNum":"","mobilePhone":1476412569971,"netId":"","netName":"","signResult":1,
	     "signTime":1476412569971,"uid":240276459552768},],"num":6},"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/signRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String signRecord(String memberId, String signTime, String signResult){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV4.controller.SendPackageController.signRecord.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(signTime)) {
			return PubMethod.paramsFailure("net.okdi.apiV4.controller.SendPackageController.signRecord.003", "signTime不能为空");
		}
		try {
			return this.sendPackageService.signRecord(memberId,signTime,signResult);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackage/createData 到件-扫运单号
	 * @apiVersion 0.2.0
	 * @apiDescription 到件-扫运单号
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} memberId 快递员ID(必填)
	 * @apiParam {String} expNum   快递单号(必填)
	 * @apiParam {String} netId    快递网络id(必填)
	 * @apiParam {String} compId   站点Id(必填)
	 * @apiParam {String} netName  快递名称(必填)
	 * @apiParam {String} receName 收件人姓名
	 * @apiParam {String} mobile   收件人手机号
	 * @apiParam {String} actualSendMember  派件人ID=memberId(必填)
	 * @apiParam {String} contactAddress   接收人地址
	 * @apiParam {Double} contactAddrLongitude  接收人地址经度
	 * @apiParam {Double} contactAddrLatitude   接收人地址纬度
	 * @apiSampleRequest /sendPackage/createData
	 * @apiSuccess {String} uid         包裹Id
	 * @apiSuccess {String} expNum      运单号
	 * @apiSuccess {String} addresseePhone 收件人手机号
	 * @apiSuccess {String} addresseeName  收件人姓名
	 * @apiSuccess {String} contactAddress 接收人地址
	 * @apiSuccess {String} createMemberId 包裹创建人ID
	 * @apiSuccess {String} parcelNum   取件码
	 * @apiSuccess {String} controllerTaskFlag 包裹状态 10未签收，11已签收
	 * @apiSuccess {String} status      包裹当前状态 0:在途,未签收 1:已签收 2：拒收  
	 * @apiSuccess {String} type        数据状态 1:运单号查 的包裹3：直接创建的包裹  
	 * @apiSuccessExample Success-Response:
	    {"data":{"pars":[{"uid":268412251340800,"addresseePhone"
	    :1476412570183}],"type":1},"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/createData", method = { RequestMethod.POST, RequestMethod.GET })
	public String createData(String memberId, String expNum, String netId,String compId,String netName,String receName,
			String mobile, String actualSendMember, String contactAddress, String contactAddrLongitude, String contactAddrLatitude){
		if (PubMethod.isEmpty(expNum)) {
			return PubMethod.paramsFailure("net.okdi.apiV4.controller.SendPackageController.createData.001", "expNum不能为空");
		}
		try {
			return this.sendPackageService.createData( memberId,  expNum,  netId, compId, netName, receName,
					 mobile,  actualSendMember,  contactAddress,  contactAddrLongitude,  contactAddrLatitude);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/create/sms 录入手机号生成待派包裹(群发/菜鸟通知)
	 * @apiVersion 0.2.0
	 * @apiDescription 录入手机号生成待派包裹(群发/菜鸟通知). 群发通知时手机号存在过个包裹时会弹框, 如果用户取消选择, 需要调用create/sms/phone/nooneselect接口创建新的包裹
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} parNum 取件码
	 * @apiParam {String} mobile 接收人手机号
	 * @apiParam {String} receName 接收人姓名
	 * @apiParam {String} wayBill 单号 群发通知时没有单号, 此时参数可选
	 * @apiParam {Long} companyId 快递公司id 发送菜鸟通知时选择的快递公司id, 群发通知时参数可选
	 * @apiParam {Integer} ali 是否是菜鸟通知 0否1是
	 * @apiSampleRequest /sendPackage/create/sms
	 * @apiSuccessExample Success-Response:
	{
		"data": {
			"flag": 0, 			-- 0时不需要弹框, 即手机号没有对应的包裹
			"taskId"： 123456,	-- flag为0时有数据
			"parId": 123456,	-- flag为0时有数据
			"contactName": "xx"	-- mobile对应的客户姓名, 和flag无关
			"wayBill": "单号",
			"netName": "快递公司",
			"similarParcels": [	-- 手机号存在多个包裹时返回的数据
				"taskId": 123,
				"parId": 123,
				"parNum": "22",		-- 取件码
				"mobile": "1111",	-- 手机号
				"wayBill": "单号",
				"netName": "快递公司"
			],
		},
		"success": true
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
	@RequestMapping(value = "/create/sms", method = {RequestMethod.POST, RequestMethod.GET})
	public String createParcelForSms(Long memberId, String parNum, String mobile, String receName, String wayBill, Long companyId, Integer ali) {

		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "invalid param");
		}
		if (PubMethod.isEmpty(ali)) {
			return paramsFailure("002", "invalid param");
		}
		if (ali == 1 && "1xxxxxxxxxx".equalsIgnoreCase(mobile)) {
			mobile = "";
		}
		return sendPackageService.createParcel(memberId, parNum, mobile, receName, wayBill, companyId, ali);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/create/sms/waybill 群发通知扫描单号生成待派包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 群发通知扫描单号生成待派包裹.
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} compId 快递员compId
	 * @apiParam {String} wayBill 单号
	 * @apiParam {Long} companyId 快递公司id 选择的快递公司id
	 * @apiParam {Object} signDetail 签收详情(flag = -1时)
	 * @apiSuccess {Integer} flag-1 flag = -1时, 包裹已经签收
	 * @apiSuccess {Integer} flag10 flag = 10时, 单号对应的包裹不存在, 需要用户填写手机号. 填写手机号后要调用sendPackage/create/sms接口
	 * @apiSuccess {Integer} flag11 flag = 11时, 单号对应的包裹存在且有手机号, 此时不生成待派包裹
	 * @apiSuccess {Integer} flag12 flag = 12时, 单号对应的包裹存在但没有手机号, 需要用户填写手机号. 填写手机号后要调用sendPackage/update/sms接口
	 * @apiSampleRequest /sendPackage/create/sms/waybill
	 * @apiSuccessExample Success-Response:
	{
		"data": {
			"flag": 0,
			"taskId"： 123456,
			"parId": 123456,
			"contactName": "xx",
			"wayBill": "单号",
			"netName": "快递公司",
			"phone": "单号对应的手机号"
			"similarParcels": [],
			"signDetail": {
				"deliveryName": "派件人",
				"signName": "签收人",
				"signTime": 1234, --签收时间
				"signAddress": "签收地点",
				"signFlag": 14 --签收类型  14: 本人签收 15: 他人签收
			}
		},
		"success": true
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
	@RequestMapping(value = "/create/sms/waybill", method = {RequestMethod.POST, RequestMethod.GET})
	public String createParcelForSms(Long memberId, Long compId, String wayBill, Long companyId) {

		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(compId)) {
			return paramsFailure("001", "invalid param");
		}
		if (PubMethod.isEmpty(wayBill)) {
			return paramsFailure("002", "invalid param");
		}
		if (PubMethod.isEmpty(companyId)) {
			return paramsFailure("003", "invalid param");
		}
		return sendPackageService.smsCreatePracleByWayBill(memberId, compId, wayBill, companyId);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/create/sms/waybill/ali 菜鸟通知扫描单号生成待派包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 菜鸟通知扫描单号生成待派包裹.
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} compId 快递员compId
	 * @apiParam {String} wayBill 单号
	 * @apiParam {Long} companyId 快递公司id 发送菜鸟通知时选择的快递公司id
	 * @apiParam {String} parNum 取件码
	 * @apiParam {Object} signDetail 签收详情(flag = -1时)
	 * @apiSuccess {Integer} flag-1 flag = -1时, 单号已经签收
	 * @apiSuccess {Integer} flag1 flag = 1时, 单号对应的包裹不存在, 能通过阿里发短信, 此时已经生成待派包裹
	 * @apiSuccess {Integer} flag2 flag = 2时, 单号对应的包裹不存在, 无法通过阿里发短信, 此时需要用户填写手机号. 写了手机号以后要调用sendPackage/create/sms/phone/nooneselect生成待派. 不写不处理.
	 * @apiSuccess {Integer} flag3 flag = 3时, 单号对应的包裹存在, 有手机号, 不需要填写手机号. 不生成待派, 拿着返回值直接展示
	 * @apiSuccess {Integer} flag4 flag = 4时, 单号对应的包裹存在, 没有手机号, 也不能通过阿里发短信, 需要填写手机号. 写了手机号要调用sendPackage/update/sms更新包裹
	 * @apiSuccess {Integer} flag5 flag = 5时, 单号对应的包裹存在, 没有手机号, 能通过阿里发短信, 不需要填写手机号.
	 * @apiSampleRequest /sendPackage/create/sms/waybill/ali
	 * @apiSuccessExample Success-Response:
	{
		"data": {
			"flag": 0,
			"taskId"： 123456,
			"parId": 123456,
			"contactName": "xx",
			"wayBill": "单号",
			"netName": "快递公司",
			"phone": "单号对应的手机号"
			"similarParcels": [],
			"signDetail": {
				"deliveryName": "派件人",
				"signName": "签收人",
				"signTime": 1234, --签收时间
				"signAddress": "签收地点",
				"signFlag": 14 --签收类型  14: 本人签收 15: 他人签收
			}
		},
		"success": true
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
	@RequestMapping(value = "/create/sms/waybill/ali", method = {RequestMethod.POST, RequestMethod.GET})
	public String createParcelForSmsAli(Long memberId, Long compId, String wayBill, Long companyId, String parNum) {

		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(compId)) {
			return paramsFailure("001", "invalid param");
		}
		if (PubMethod.isEmpty(wayBill)) {
			return paramsFailure("002", "invalid param");
		}
		if (PubMethod.isEmpty(companyId)) {
			return paramsFailure("003", "invalid param");
		}
		return sendPackageService.smsCreatePracleByWayBillForAli(memberId, compId, wayBill, companyId, parNum);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/create/sms/phone/nooneselect 录入手机号弹框时没有选择, 新建包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 录入手机号弹框时没有选择, 新建包裹
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} compId 快递员公司id
	 * @apiParam {String} mobile 手机号
	 * @apiParam {String} parNum 取件码
	 * @apiParam {String} wayBill 单号
	 * @apiParam {Long} companyId 选择的快递公司id. 没有可不传
	 * @apiParam {Integer} ali 0:群发通知 1: 菜鸟通知
	 * @apiSampleRequest /sendPackage/create/sms/phone/nooneselect
	 * @apiSuccessExample Success-Response:
	{
	"data": {
	"flag": 0, 			-- 5
	"taskId"： 123456,
	"parId": 123456,
	"contactName": "xx",
	"wayBill": "单号",
	"netName": "快递公司",
	"similarParcels": []
	},
	"success": true
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
	@RequestMapping(value = "/create/sms/phone/nooneselect", method = {RequestMethod.POST})
	public String createParcelForSms(Long memberId, Long compId, String mobile, String parNum,
									 String wayBill, Long companyId, Integer ali) {

		if (PubMethod.isEmpty(memberId) || PubMethod.isEmpty(compId)) {
			return paramsFailure("001", "invalid param");
		}
		if (PubMethod.isEmpty(mobile)) {
			return paramsFailure("002", "invalid param");
		}
		return sendPackageService.smsCreateParcelWhenSelectNoPhone(memberId, compId, mobile, parNum, wayBill, companyId, ali);
	}


	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/delete 删除包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 删除包裹
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} parId 包裹id
	 * @apiSampleRequest /sendPackage/delete
	 * @apiSuccessExample Success-Response:
	{
		"success": true
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
	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteParcle(Long memberId, Long parId) {

		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "invalid param");
		}
		if (PubMethod.isEmpty(parId)) {
			return paramsFailure("002", "invalid param");
		}
		return sendPackageService.deleteParcelByParId(memberId, parId);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/update/sms 群发/菜鸟通知更新待派包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 群发/菜鸟通知更新待派包裹
	 * @apiGroup 新版-派件
	 * @apiParam {String} upinfo json数组. [{"memberId": 1234, "parId": 123, "parNum": "取件码", "receName": "接收人姓名", "mobile": "接收人手机号", "wayBill": "单号"}] parId: 包裹id
	 * @apiSampleRequest /sendPackage/update/sms
	 * @apiSuccessExample Success-Response:
	{

	"success": true
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
	@RequestMapping(value = "/update/sms", method = {RequestMethod.POST, RequestMethod.GET})
	public String updateParcelForSms(@RequestParam(value = "upinfo") String upInfo) {
		return sendPackageService.updateParcelForSmsOrAli(upInfo);
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackage/delePack 到件-删除包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 到件-删除包裹
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} uid 包裹id
	 * @apiSampleRequest /sendPackage/delete
	 * @apiSuccessExample Success-Response:
	{
		"success": true
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
	@RequestMapping(value = "/delePack", method = {RequestMethod.POST, RequestMethod.GET})
	public String delePack(String uid) {

		if (PubMethod.isEmpty(uid)) {
			return paramsFailure("001", "invalid param");
		}
		return sendPackageService.delePack(uid);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/batch_sign 批量签收
	 * @apiVersion 0.2.0
	 * @apiDescription 批量签收
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} netId 快递员快递公司id
	 * @apiParam {Long} compId 快递员公司id
	 * @apiParam {String} parcelIds 包裹id, 英文逗号分隔
	 * @apiSampleRequest /sendPackage/batch_sign
	 * @apiSuccessExample Success-Response:
	{
		"data": {
			"auth": true/false, 	--false: 快递员或者快递公司未认证
			"total": 2,				--所选包裹总数, 成功上传的包裹 = total - fail.length
			"fail": [				--报签收失败的包裹, auth为true时有数据
				"parId": 11111,
				"wayBill": "单号",
				"name": "收件人姓名",
				"mobile": "收件人手机号",
				"reason": "失败原因"
			]
		},
		"success": true
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
	@RequestMapping("/batch_sign")
	public String signPackageBatch(Long memberId, Long netId, Long compId, String parcelIds) {
		return sendPackageService.signPackageBatch(memberId, netId, compId, parcelIds);
	}


	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/create/sms/batch 手机号批量创建包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 手机号批量创建包裹
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {Long} compId 快递员公司id
	 * @apiParam {String} jsonParam json数组. [{"phone": "手机号", "parNum": "取件码"}]
	 * @apiSampleRequest /sendPackage/create/sms/batch
	 * @apiSuccessExample Success-Response:
	{
		"data": [
			{
				"taskId": 11,
				"parId": 22,
				"phone": "手机号",
				"parNum": "取件码"
			}
		],
		"success": true
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
	@RequestMapping(value = "/create/sms/batch", method = {RequestMethod.POST, RequestMethod.GET})
	public String createParcelBatchForSms(Long memberId, Long compId, String jsonParam) {
		return sendPackageService.smsCreateParcelBatchByPhone(memberId, compId, jsonParam);
	}

	/**
	 * @author hang.yu
	 * @api {post} /sendPackage/delete_batch 批量删除包裹
	 * @apiVersion 0.2.0
	 * @apiDescription 批量删除包裹
	 * @apiGroup 新版-派件
	 * @apiParam {Long} memberId 快递员id
	 * @apiParam {String} parIds 包裹id, 英文逗号分隔
	 * @apiSampleRequest /sendPackage/delete_batch
	 * @apiSuccessExample Success-Response:
	{
		"success": true
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
	@RequestMapping(value = "/delete_batch", method = {RequestMethod.POST, RequestMethod.GET})
	public String deleteParcelBatchByParId(Long memberId, String parIds) {
		return sendPackageService.deleteParcelBatchByParId(memberId, parIds);
	}
	
	/**
	 * @author xingwei.zhang
	 * @api {post} /sendPackage/updateAddress 更新地址
	 * @apiVersion 0.2.0
	 * @apiDescription 更新地址
	 * @apiGroup 转签收-新需求
	 * @apiParam {String} memberId  快递员ID
	 * @apiParam {String} uid       包裹ID
	 * @apiParam {String} contactAddress  地址
	 * @apiParam {String} contactAddrLongitude  经度
	 * @apiParam {String} contactAddrLatitude  纬度
	 * @apiSampleRequest /sendPackage/updateAddress
	 * @apiSuccessExample Success-Response:
	     {"data":{"ture",	
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
	@RequestMapping(value = "updateAddress", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAddress(String memberId, String uid, String contactAddress, String contactAddrLongitude, String contactAddrLatitude) {
		
		if (PubMethod.isEmpty(uid)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.sendPackage.updateAddress.001", "uid不能为空");
		}
		try {
			return this.sendPackageService.updateAddress(memberId,uid,contactAddress,contactAddrLongitude,contactAddrLatitude);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}

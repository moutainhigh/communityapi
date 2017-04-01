package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.service.ExpressRegisterService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/expressRegister")
public class ExpressRegisterController extends BaseController{
	
	

	@Autowired
	private ExpressRegisterService expressRegisterService;
	
	
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressRegister/registerCourier  注册收派员或者后勤角色(静国强)
	 * @apiVersion 0.2.0
	 * @apiDescription  注册收派员或者后勤角色(静国强)
	 * @apiGroup  新版-注册
	 * @apiParam {Long}   memberId    快递员membrId,ucenter自动生成,不能null 
	 * @apiParam {String} memberName  实名
	 * @apiParam {Long}   netId       所属网络ID,  不能null
	 * @apiParam {String}   compId      所属站点ID,可null 
	 * @apiParam {String} compTypeNum  公司分类，可null  1003:网络抓取,1006:加盟公司,1008:加盟公司站点,1030:快递代理点 1040快递代收点认领或者创建，1050:营业分部'
	 * @apiParam {String} compName    站点名称,不能null
	 * @apiParam {String} applicationRoleType 申请角色类型   0 收派员 -1 后勤 
	 * @apiParam {String} employeeNum 员工代码
	 * @apiSuccess {String} compId  站点
	 * @apiSampleRequest /expressRegister/registerCourier
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": "1234657890123"
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
	@RequestMapping(value = "/registerCourier", method = { RequestMethod.POST, RequestMethod.GET })
	public String registerCourier(Long memberId,String memberName,Long netId,String compId,String compTypeNum,String compName,String applicationRoleType,String employeeNum ,String externalAccount){
		try {
			return   expressRegisterService.registerCourier(memberId,memberName, netId, compId, compTypeNum,compName, applicationRoleType, employeeNum, externalAccount);
		} catch (Exception e) {
			e.printStackTrace();
		   return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/queryAuditStatus 获取快递员的身份和快递认证状态V2
	 * @apiVersion 0.2.0
	 * @apiDescription 获取快递员的身份和快递认证状态V2
	 * @apiGroup 新版-注册
	 * @apiParam {String} memberId 收派员id
	 * @apiSampleRequest /expressRegister/queryAuditStatus
	 * @apiSuccess {String} memberStatusKD  快递认证状态：-1 未完善/未提交 0 待审核  1通过 2拒绝
	 * @apiSuccess {String} memberStatusSF  身份认证状态：-1 未完善/未提交 0 待审核  1通过 2拒绝
	 * @apiSuccessExample Success-Response:
	    {
	    "data": {
	        "memberStatusKD": -1,
	        "memberStatusSF": -1
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
	@RequestMapping(value = "/queryAuditStatus", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAuditStatus(String memberId){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryAuditStatus.001", "memberId不能为空");
			}
			return this.expressRegisterService.queryAuditStatus(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/queryCompInfoByFirstLetter 获取站点信息列表-按首字母排序V2
	 * @apiVersion 0.2.0
	 * @apiDescription 获取站点信息列表-按首字母排序V2
	 * @apiGroup 新版-注册
	 * @apiParam {String} netId 快递网络id
	 * @apiParam {String} addressName 选择的二级地区名称，例如：海淀区、保定市
	 * @apiSampleRequest /expressRegister/queryCompInfoByFirstLetter
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} compTypeNum  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiSuccess {String} firstLetter  站点名称首写字母
	 * @apiSuccess {String} latitude  经度
	 * @apiSuccess {String} longitude  纬度
	 * @apiSuccessExample Success-Response:
	    {
	    "data": {
	        "B": [
	            {
	                "compId": 145000040308736,
	                "compName": "北京人大",
	                "compTypeNum": "1006",
	                "firstLetter": "B",
	                "latitude": "",
	                "longitude": ""
	            },
	            {
	                "compId": 146108146065408,
	                "compName": "北京牡丹园站",
	                "compTypeNum": "1006",
	                "firstLetter": "B",
	                "latitude": "",
	                "longitude": ""
	            }
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
	@RequestMapping(value = "/queryCompInfoByFirstLetter", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByFirstLetter(String netId,String addressName,String latitude,String longitude){
		try {
			if (PubMethod.isEmpty(addressName)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCompInfoByFirstLetter.001", "addressName不能为空");
			}
			return this.expressRegisterService.queryCompInfoByFirstLetter(netId,addressName, latitude, longitude);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	@ResponseBody
	@RequestMapping(value = "/queryCompInfoByFirstLetter2", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCompInfoByFirstLetter2(String netId,String addressName,String latitude,String longitude,String compName,Integer compType){
		try {
			if (PubMethod.isEmpty(addressName)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCompInfoByFirstLetter.001", "addressName不能为空");
			}
			return this.expressRegisterService.queryCompInfoByFirstLetter2(netId,addressName, latitude, longitude, compName, compType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressRegister/queryRepeatOrder  查询单号是否被重复认证(静国强)
	 * @apiVersion 0.2.0
	 * @apiDescription  查询单号是否被重复认证(静国强)
	 * @apiGroup 新版-注册
	 * @apiParam {String}   waybillNums      订单数据组成的字符串,逗号分隔
	 * @apiParam {Long}     netId         所属网络ID,  不能null
	 * @apiParam {String}   phone         本人电话，用于查单号除本人外是否被其他人认证过,不能null
	 * @apiSampleRequest /expressRegister/queryRepeatOrder
	 * @apiSuccessExample Success-Response:
		 有重复：{"data":["0015479822","0015479822"], "success":true}
	 *   没重复：{"data":[],"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRepeatOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRepeatOrder(String waybillNums,Long netId,String phone){
		
		try {
			return  expressRegisterService.queryRepeatOrder(waybillNums,String.valueOf(netId),phone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/isRelationByLeader 判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色V2
	 * @apiVersion 0.2.0
	 * @apiDescription 判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色V2
	 * @apiGroup 新版-注册
	 * @apiParam {String} compId  站点id
	 * @apiParam {String} compNumType  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiSampleRequest /expressRegister/isRelationByLeader
	 * @apiSuccess {String} compId  站点id（为空说明是新建的站点而非选择的站点）
	 * @apiSuccess {String} compNumType  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiSuccess {String} siteRelationFlag  0：否（未领取）1：是（已领取）-1：说明是新建的站点而非选择的站点
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": "155016069718016",
		        "compNumType": "1006",
		        "siteRelationFlag": "1"
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
	@RequestMapping(value = "/isRelationByLeader", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRelationByLeader(String compId,String compNumType){
		try {
			return this.expressRegisterService.isRelationByLeader(compId,compNumType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/submitUseCompInfo 站长领用网络抓取的站点V2
	 * @apiVersion 0.2.0
	 * @apiDescription 站长领用网络抓取的站点V2
	 * @apiGroup 新版-注册
	 * @apiParam {Long} webCompId  所选网络抓取的站点id
	 * @apiParam {String} compName  站点名称
	 * @apiParam {String} belongToNetId 快递网络id 
	 * @apiParam {String} county  地区名称，例如：北京市-海淀区
	 * @apiParam {String} member_id  快递员id
	 * @apiParam {String} addressId  二级地区id
	 * @apiParam {String} roleType  角色类型：1 站长 0 收派员 -1 后勤
	 * @apiParam {String} memberName  选角色页面自己填写的姓名
	 * @apiSampleRequest /expressRegister/submitUseCompInfo
	 * @apiSuccess {String} compId  领取网络站点后新生成的站点id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": 183430498091008
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
	@RequestMapping(value = "/submitUseCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitUseCompInfo(Long webCompId, String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName){
		try {
			if (PubMethod.isEmpty(webCompId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRelationByLeader.001", "webCompId不能为空");
			}
			if (PubMethod.isEmpty(member_id)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRelationByLeader.002", "member_id不能为空");
			}
			if (PubMethod.isEmpty(belongToNetId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRelationByLeader.003", "belongToNetId不能为空");
			}
			if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRelationByLeader.004", "addressId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRelationByLeader.005", "roleType不能为空");
			}
			return this.expressRegisterService.submitUseCompInfo(webCompId,compName,belongToNetId,county,member_id,addressId,roleType,memberName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/isRepeatCompInfoName 查询在同一个快递网络下是否有重名的站点V2
	 * @apiVersion 0.2.0
	 * @apiDescription 查询在同一个快递网络下是否有重名的站点V2
	 * @apiGroup 新版-注册
	 * @apiParam {String} compName  站点名称
	 * @apiParam {String} netId 快递网络id 
	 * @apiSampleRequest /expressRegister/isRepeatCompInfoName
	 * @apiSuccess {String} isRepeat  是否重复：0否1是
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "isRepeat": "0"
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
	@RequestMapping(value = "/isRepeatCompInfoName", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRepeatCompInfoName(String netId,String compName){
		try {
			if (PubMethod.isEmpty(netId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRepeatCompInfoName.001", "netId不能为空");
			}
			if (PubMethod.isEmpty(compName)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.isRepeatCompInfoName.002", "compName不能为空");
			}
			return this.expressRegisterService.isRepeatCompInfoName(netId,compName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/saveNewCompInfo 站长创建新站点/营业分部V2
	 * @apiVersion 0.2.0
	 * @apiDescription 站长创建新站点/营业分部V2
	 * @apiGroup 新版-注册
	 * @apiParam {Long} memberId  快递员id
	 * @apiParam {Long} netId 快递网络id 
	 * @apiParam {String} compTypeNum 快递网络id 1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiParam {String} compName 站点/营业分部名称
	 * @apiParam {String} compTelephone 站点联系电话
	 * @apiParam {String} county 地区名称（一级+二级名称），例如：北京市-海淀区
	 * @apiParam {String} addressId 二级地区id
	 * @apiParam {String} descriptionMsg 创建营业分部时，前面所选的站点名称
	 * @apiParam {String} roleType  角色类型：1 站长 0 收派员 -1 后勤
	 * @apiParam {String} memberName  选角色页面自己填写的姓名
	 * @apiParam {String} province  选择的省份名称（一级）
	 * @apiParam {String} city  选择的城市名称（二级）
	 * @apiSampleRequest /expressRegister/saveNewCompInfo
	 * @apiSuccess {String} compId  新建站点id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": 183430498091008
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
	@RequestMapping(value = "/saveNewCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveNewCompInfo(Long memberId, Long netId, String compTypeNum, String compName, String compTelephone,String county,String addressId,String descriptionMsg,String roleType,String memberName,String province,String city,String compNum,String longitude,String latitude){
		try {
		/*	if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.001", "memberId不能为空");
			}*/
			if (PubMethod.isEmpty(netId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.002", "netId不能为空");
			}
			if (PubMethod.isEmpty(compTypeNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.003", "compTypeNum不能为空");
			}
		/*	if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveNewCompInfo.004", "addressId不能为空");
			}*/
			return this.expressRegisterService.saveNewCompInfo(memberId,netId,compTypeNum,compName,compTelephone,county,addressId,descriptionMsg,roleType,memberName,province,city, compNum, longitude, latitude);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/queryCountInCompInfo 查询此站点下的收派员和后勤人员数量，不包括站长角色V2
	 * @apiVersion 0.2.0
	 * @apiDescription 查询此站点下的收派员和后勤人员数量，不包括站长角色V2
	 * @apiGroup 新版-注册
	 * @apiParam {String} compId  站点id
	 * @apiSampleRequest /expressRegister/queryCountInCompInfo
	 * @apiSuccess {int} data  数量
	 * @apiSuccessExample Success-Response:
	    {
		    "data": 0,
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
	@RequestMapping(value = "/queryCountInCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCountInCompInfo(String compId){
		try {
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCountInCompInfo.001", "compId不能为空");
			}
			return this.expressRegisterService.queryCountInCompInfo(compId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/leaveOfficeByLeader 站长离职V2
	 * @apiVersion 0.2.0
	 * @apiDescription 站长离职V2
	 * @apiGroup 新版-注册
	 * @apiParam {Long} memberId  快递员id
	 * @apiParam {Long} compId 站点id 
	 * @apiParam {String} memberName 站长姓名
	 * @apiParam {String} memberPhone 站长手机号 
	 * @apiSampleRequest /expressRegister/leaveOfficeByLeader
	 * @apiSuccess {String} isFinish  有无未完成任务：0否 1是
	 * @apiSuccess {String} isSendFinish  有无待派包裹：0否1是
	 * @apiSuccess {String} isTaskFinish  有无未完成派件任务：0否 1是
	 * @apiSuccessExample Success-Response:
	   {"data":"{"isFinish":1,"isSendFinish":1,"isTaskFinish":1}","success":true}
	     isFinish:大于1有任务未完成   0无未完成任务
	     isSendFinish:1.有待派包裹 
	     isTaskFinish:1有任务未完成
	     if(isFinish>=1){
	     	有任务未完成
	     }else{
	     	if(isSendFinish==1){
	     		有待派包裹
	     	}else if(isTaskFinish==1){
	     		有任务未完成
	     	}else if(isFinish==0){
	    		删除成功
	     	}
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
	@RequestMapping(value = "/leaveOfficeByLeader", method = { RequestMethod.POST, RequestMethod.GET })
	public String leaveOfficeByLeader(Long memberId, Long compId, String memberName, String memberPhone){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.001", "memberId不能为空");
			}
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.002", "compId不能为空");
			}
			if (PubMethod.isEmpty(memberPhone)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.leaveOfficeByLeader.003", "memberPhone不能为空");
			}
			return this.expressRegisterService.leaveOfficeByLeader(memberId,compId,memberName,memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
	
	/**
	 * @author guoqiang.jing
	 * @api {post} /expressRegister/queryGSInfo 查询快递员/后勤归属详情(静国强)
	 * @apiVersion 0.2.0
	 * @apiDescription 查询快递员/后勤归属详情(静国强)
	 * @apiGroup 新版-注册
	 * @apiParam {Long}     memberId  人员id
	 * @apiParam {Integer}   roleId   角色id
	 * @apiSampleRequest /expressRegister/queryGSInfo
	 
	 * @apiSuccess {String} application_role_type  申请角色类型  0 收派员 -1 后勤
	 * @apiSuccess {String} audit_opinion           审核结果 :-1 为完善 0 待审核  1通过 2拒绝
	 * @apiSuccess {String} comp_address           站点地址
	 * @apiSuccess {String} comp_name              站点名称、
	 * @apiSuccess {String} member_id              人员id
	 * @apiSuccess {String} net_name               网点名称
	 * @apiSuccess {String} responsible            负责人
	 * @apiSuccess {String} responsible_id_num     负责人身份证号
	 * @apiSuccess {String} business_license_num     营业执照号
	 * @apiSuccess {String} responsible_telephone  负责人电话
	 * @apiSuccess {String} headImgPath            头像
	 * @apiSuccessExample Success-Response:
	    {
		 "data": {
		        "application_role_type": "0", 
		        "audit_opinion": "1", 
		        "comp_address": "河南省-南阳市-邓州市|北环路环保局东隔墙", 
		        "comp_name": "邓州圆通速递", 
		        "member_id": "1521392185909865", 
		        "net_name": "圆通速递", 
		        "responsible": "许星凯", 
		        "responsible_id_num": "411303199005095971", 
		        "responsible_telephone": ""
		        "headImgPath":"http://cas.okdit.net/nfs_data/mob/head/175639978360832.jpg"
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
	@RequestMapping(value = "/queryGSInfo", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryGSInfo(Long memberId,int roleId) {
		
		try {
			return expressRegisterService.queryGSInfo(memberId,roleId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/queryCountInShop 查询此代收点下的店员数量，不包括店长角色V3
	 * @apiVersion 0.2.0
	 * @apiDescription 查询此代收点下的店员数量，不包括店长角色V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} compId  站点id
	 * @apiSampleRequest /expressRegister/queryCountInShop
	 * @apiSuccess {int} data  数量
	 * @apiSuccessExample Success-Response:
	    {
		    "data": 12,
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
	@RequestMapping(value = "/queryCountInShop", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryCountInShop(String compId){
		try {
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.queryCountInShop.001", "compId不能为空");
			}
			return this.expressRegisterService.queryCountInShop(compId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/isIdentifyCompInfo 判断该站点是否已认证V3
	 * @apiVersion 0.2.0
	 * @apiDescription 判断该站点是否已认证V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} compTypeNum  站点类型：1003网络抓取 1006已被领取新建的站点 1050营业分部
	 * @apiParam {String} compId 站点id
	 * @apiSampleRequest /expressRegister/isIdentifyCompInfo
	 * @apiSuccess {String} isIdentify  该站点是否已认证：0否1是
	 * @apiSuccess {String} compId 站点id（为空说明是新建的站点而非选择的站点）
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "isIdentify": "0"
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
	@RequestMapping(value = "/isIdentifyCompInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String isIdentifyCompInfo(String compTypeNum,String compId){
		try {
			return this.expressRegisterService.isIdentifyCompInfo(compTypeNum,compId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/saveCompInfoByCourier 收派员/后勤创建或领取未认证站点并提交站点认证信息--收派员/后勤使用V3
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员/后勤创建或领取未认证站点并提交站点认证信息--收派员/后勤使用V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} compId  选择的站点id
	 * @apiParam {String} memberId 快递员id 
	 * @apiParam {String} netId 快递网络id
	 * @apiParam {String} compTypeNum  站点类型：1003网络抓取站点 1006加盟站点 1050营业分部
	 * @apiParam {String} compName  站点/营业分部名称
	 * @apiParam {String} compTelephone  站点/营业分部联系电话
	 * @apiParam {String} county  地区名称（一二级名称），例如：北京市-海淀区
	 * @apiParam {String} addressId  二级地区id
	 * @apiParam {String} roleType  角色类型：1 站长 0 收派员 -1 后勤
	 * @apiParam {String} memberName  选角色页面自己填写的姓名
	 * @apiParam {String} responsible  负责人姓名
	 * @apiParam {String} responsibleTelephone  负责人电话
	 * @apiParam {String} responsibleNum  负责人身份证号码
	 * @apiParam {String} licenseNum  营业执照号
	 * @apiParam {String} holdImg  营业执照照片
	 * @apiParam {String} reverseImg  站点门店照片
	 * @apiParam {String} province  选择的省份名称（一级）
	 * @apiParam {String} city  选择的城市名称（二级）
	 * @apiSampleRequest /expressRegister/saveCompInfoByCourier
	 * @apiSuccess {String} compId  最终的站点id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": 183430498091008
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
	@RequestMapping(value = "/saveCompInfoByCourier", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompInfoByCourier(String compId,String memberId, String netId, String compTypeNum, String compName, String compTelephone,String county,String addressId,String roleType,String memberName,
			String responsible, String responsibleTelephone, String responsibleNum, String licenseNum,
			String holdImg, String reverseImg,String province ,String city){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(netId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.002", "netId不能为空");
		}
		if (PubMethod.isEmpty(addressId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.004", "addressId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.005", "roleType不能为空");
		}
		if (PubMethod.isEmpty(compName)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.006", "compName不能为空");
		}
		if (PubMethod.isEmpty(county)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.008", "county不能为空");
		}
		if (PubMethod.isEmpty(memberName)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.009", "memberName不能为空");
		}
		if (PubMethod.isEmpty(holdImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.010", "holdImg不能为空");
		}
		if (PubMethod.isEmpty(reverseImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompInfoByCourier.011", "reverseImg不能为空");
		}
		try {
			return this.expressRegisterService.saveCompInfoByCourier(compId,memberId,netId,compTypeNum,compName,compTelephone,county,addressId,roleType,memberName,
					responsible,responsibleTelephone,responsibleNum,licenseNum,holdImg,reverseImg,province,city);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/saveCompVerifyInfoV3  提交站点认证信息V3
	 * @apiVersion 0.2.0
	 * @apiDescription 提交站点认证信息V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} loginCompId  站点id
	 * @apiParam {String} memberId  快递员id
	 * @apiParam {String} responsible  负责人姓名
	 * @apiParam {String} responsibleTelephone  负责人电话
	 * @apiParam {String} responsibleNum  负责人身份证号码
	 * @apiParam {String} licenseNum  营业执照号
	 * @apiParam {String} holdImg  营业执照照片
	 * @apiParam {String} reverseImg  站点门店照片
	 * @apiSampleRequest /expressRegister/saveCompVerifyInfoV3
	 * @apiSuccess {String} compId  最终的站点id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "success": "true"
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
	@RequestMapping(value = "/saveCompVerifyInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveCompVerifyInfoV3(String loginCompId, String responsible, String responsibleTelephone, String responsibleNum, 
			String holdImg, String reverseImg,String memberId,String licenseNum) {
		if (PubMethod.isEmpty(loginCompId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompVerifyInfoV3.001", "loginCompId不能为空");
		}
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompVerifyInfoV3.002", "memberId不能为空");
		}
		if (PubMethod.isEmpty(holdImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompVerifyInfoV3.003", "holdImg不能为空");
		}
		if (PubMethod.isEmpty(reverseImg)) {
			return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.saveCompVerifyInfoV3.004", "reverseImg不能为空");
		}
		try {
			return this.expressRegisterService.saveCompVerifyInfoV3(loginCompId, responsible, responsibleTelephone, responsibleNum,holdImg, reverseImg,memberId, licenseNum);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/isRelationByLeaderV3 判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色V3
	 * @apiVersion 0.2.0
	 * @apiDescription 判断选择的加盟站点/网络抓取站点/营业分部是否被领用--站长角色V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} compId  站点id
	 * @apiParam {String} compNumType  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiSampleRequest /expressRegister/isRelationByLeaderV3
	 * @apiSuccess {String} compId  站点id（为空说明是新建的站点而非选择的站点）
	 * @apiSuccess {String} compNumType  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiSuccess {String} siteRelationFlag  0：否（未领取）1：是（已领取）-1：说明是新建的站点而非选择的站点
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": "155016069718016",
		        "compNumType": "1006",
		        "siteRelationFlag": "1"
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
	@RequestMapping(value = "/isRelationByLeaderV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String isRelationByLeaderV3(String compId,String compNumType){
		try {
			return this.expressRegisterService.isRelationByLeaderV3(compId,compNumType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/submitUseCompInfoV3  站长领用网络抓取的站点V3
	 * @apiVersion 0.2.0
	 * @apiDescription 站长领用网络抓取的站点V3
	 * @apiGroup 新版-注册
	 * @apiParam {Long} webCompId  所选网络抓取的站点id
	 * @apiParam {String} compName  站点名称
	 * @apiParam {String} belongToNetId 快递网络id 
	 * @apiParam {String} county  地区名称，例如：北京市-海淀区
	 * @apiParam {String} member_id  快递员id
	 * @apiParam {String} addressId  二级地区id
	 * @apiParam {String} roleType  角色类型：1 站长 0 收派员 -1 后勤
	 * @apiParam {String} memberName  选角色页面自己填写的姓名
	 * @apiParam {String} compTypeNum  1003：网络抓取站点 1006:加盟站点 1050:营业分部
	 * @apiParam {String} province  选择的省份名称（一级）
	 * @apiParam {String} city  选择的城市名称（二级）
	 * @apiSampleRequest /expressRegister/submitUseCompInfoV3
	 * @apiSuccess {String} compId  领取网络站点后新生成的站点id
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "compId": 183430498091008
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
	@RequestMapping(value = "/submitUseCompInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String submitUseCompInfoV3(Long webCompId, String compName, String belongToNetId, String county,String member_id,String addressId,String roleType,String memberName,String compTypeNum,String province,String city){
		try {
			if (PubMethod.isEmpty(webCompId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.001", "webCompId不能为空");
			}
			if (PubMethod.isEmpty(member_id)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.002", "member_id不能为空");
			}
			if (PubMethod.isEmpty(belongToNetId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.003", "belongToNetId不能为空");
			}
			if (PubMethod.isEmpty(addressId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.004", "addressId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.005", "roleType不能为空");
			}
			if (PubMethod.isEmpty(compTypeNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.submitUseCompInfoV3.006", "compTypeNum不能为空");
			}
			return this.expressRegisterService.submitUseCompInfoV3(webCompId,compName,belongToNetId,county,member_id,addressId,roleType,memberName,compTypeNum,province,city);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressRegister/showSiteInfoV3  查看站点认证信息V3
	 * @apiVersion 0.2.0
	 * @apiDescription 查看站点认证信息V3
	 * @apiGroup 新版-注册
	 * @apiParam {String} memberId  快递员id
	 * @apiSampleRequest /expressRegister/showSiteInfoV3
	 * @apiSuccess {String} responsible  负责人姓名
	 * @apiSuccess {String} responsibleIdNum  负责人姓名身份证号码
	 * @apiSuccess {String} businessLicenseNum  营业执照号
	 * @apiSuccess {String} responsibleTel  负责人电话
	 * @apiSuccess {String} image_type_2	营业执照照片
	 * @apiSuccess {String} image_type_8	站点门店照片
	 * @apiSuccess {String} compStatus	站点状态：-1未提交0待审核1通过2未通过
	 * @apiSuccess {String} auditReason   审核失败原因
	 * @apiSuccessExample Success-Response:
	    {
		    "data": {
		        "businessLicenseNum": "",
		        "compStatus": 0,
		        "image_type_2": "http://expnew.okdit.net/nfs_data/comp/2016010911143122630.jpg",
		        "image_type_8": "http://expnew.okdit.net/nfs_data/comp/201601091114365087.jpg",
		        "responsible": "是啊",
		        "responsibleIdNum": "110106199010253168",
		        "responsibleTel": "18811111018"
		        "auditReason": "审核失败原因"
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
	@RequestMapping(value = "/showSiteInfoV3", method = { RequestMethod.POST, RequestMethod.GET })
	public String showSiteInfoV3(String memberId){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV2.controller.ExpressRegisterController.showSiteInfoV3.001", "memberId不能为空");
			}
			return this.expressRegisterService.showSiteInfoV3(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.showSiteInfoV3 
	 * @Description: TODO(更新省非空，市id为空数据--处理旧数据(comp_address_id IS NOT NULL  comp_type_num != '网点类型' !=1005 comp_address_id != 71)) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-7-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/updateProvinceNotNullCityNull", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateProvinceNotNullCityNull(Long pageSize){
		try {
			return this.expressRegisterService.updateProvinceNotNullCityNull(pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.showSiteInfoV3 
	 * @Description: TODO(快递员加入默认群程序--处理旧数据) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-7-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/joinDefaultOldData", method = { RequestMethod.POST, RequestMethod.GET })
	public String joinDefaultOldData(){
		try {
			return this.expressRegisterService.joinDefaultOldData();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	/**
	 * 
	 * @MethodName: net.okdi.apiV2.controller.ExpressRegisterController.java.showSiteInfoV3 
	 * @Description: TODO(处理旧数据---addressId为空的，多为代收点) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-7-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/updateAddressIdIsNull", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateAddressIdIsNull(){
		try {
			return this.expressRegisterService.updateAddressIdIsNull();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	
}

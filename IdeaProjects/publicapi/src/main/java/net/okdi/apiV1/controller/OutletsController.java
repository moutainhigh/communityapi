package net.okdi.apiV1.controller;

import net.okdi.apiV1.service.OutletsService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/outlets")
public class OutletsController extends BaseController {

	@Autowired
	private OutletsService outletsService;

	/**
	 * @api {post} /outlets/querySiteDetailed 店长店员查看店铺详情-(郑炯，胡宣化)
	 * @apiVersion 0.2.0
	 * @apiDescription 店长店员查看店铺详情 (郑炯，胡宣化)
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {Long} compid 公司id
	 * @apiSampleRequest /outlets/querySiteDetailed
	 * @apiSuccess {String} compAddress  站点详细地址
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} responesible  负责人手机号
	 * @apiSuccess {String} compMobile  固定电话
	 * @apiSuccess {String} netId 网络id
	 * @apiSuccess {String} memberId 店长的id
	 * @apiSuccess {String} memberPhone 店长的手机号
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compAddress": "北京市-北京市区-海淀区|文超", 
		        "compId": 3435104, 
		        "compName": "北京田村站点", 
		        "responesible":"15811583966",
		        "compMobile": "15811583966",
		        "netId": "1501",
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
	@RequestMapping(value = "/querySiteDetailed",  method = { RequestMethod.POST,
			RequestMethod.GET })
	public String querySiteDetailed(Long compid) {

		if (PubMethod.isEmpty(compid)) {
			return PubMethod.paramError(
					"publicapi.OutletsController.querySiteDetailed",
					"comid(公司id) 不能为空!");
		}
		return outletsService.querySiteDetailed(compid);
	} 

	/**
	 * @api {post} /outlets/saveSite 1.店长店员入驻店铺,2.店员申请加入店铺(共用一个接口)-(郑炯，胡宣化)
	 * @apiVersion 0.2.0
	 * @apiDescription 店长店员入驻店铺(郑炯，胡宣化)
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {Long} compId 公司id  
	 * @apiParam {Long} roleId 角色id  
	 * @apiParam {Long} memberId 店员id
	 * @apiParam {String} managerId 店长id
	 * @apiParam {String} mobile 店长手机号
	 * @apiSampleRequest /outlets/saveSite
	 * @apiSuccessExample Success-Response:
		{"data":"","success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSite", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveSite(Long compId, Long roleId, Long memberId, String managerId, String mobile){
		///System.out.println("saveSite  方法进来了          1");
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramError("publicapi.OutletsController.saveSite",
					"compId(公司id) 不能为空!");
		}
		if (PubMethod.isEmpty(roleId)) {
			return PubMethod.paramError("publicapi.OutletsController.saveSite",
					"roleId(角色id) 不能为空!");
		}
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramError("publicapi.OutletsController.saveSite",
					"memberId(用户id) 不能为空!");
		}
		//System.out.println("saveSite  方法进来了          2");
		String nN = outletsService.saveSite(compId, roleId, memberId, managerId, mobile);
		return nN;
	}

	/**
	 * @api {post} /outlets/insertBasCompinfo 店长创建代售点-(郑炯，胡宣化)
	 * @apiVersion 0.2.0
	 * @apiDescription 店长未找到代售点，进而创建代售点,bas_compinfo,bas_compbusiness-(郑炯，胡宣化)
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {String} comp_name 代售点名称
	 * @apiParam {String} comp_address 代售点的详细地址 =====格式：北京市-海淀区-花园北路14号
	 * @apiParam {String} comp_mobile 负责人电话
	 * @apiParam {String} comp_telephone 代售点的固定电话
	 * @apiParam {String} responsible 负责人
	 * @apiParam {String} roleId 登录人角色
	 * @apiParam {String} longitude 登录人经度
	 * @apiParam {String} latitude 登录人纬度
	 * @apiParam {String} member_id 登录人id
	 * @apiParam {String} agentType :代收点类型 1 社区小店  2 公司前台  3 收发室  4 小区物业
	 * @apiSampleRequest /outlets/insertBasCompinfo
	 * @apiSuccess {String} compId 新建代收点的id
	 * @apiSuccessExample {json} Success-Response: HTTP/1.1 200 OK
	 *                            {"data":{"compId":117103623716864},"success":true}
	 * @apiErrorExample {json} Error-Response: HTTP/1.1 200 OK
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/insertBasCompinfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String insertBasCompinfo(String comp_name, String comp_address,
			String comp_mobile, String comp_telephone, String roleId,
			String longitude, String latitude,String member_id,String responsible, String agentType) {
		if(PubMethod.isEmpty(longitude) || PubMethod.isEmpty(latitude)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.001", "经纬度异常");
		}
		
		if(PubMethod.isEmpty(comp_name)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.002", "给公司名称异常");
		}
		if(PubMethod.isEmpty(comp_address)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.003", "公司地址异常");
		}
		if(PubMethod.isEmpty(comp_mobile)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.004", "手机号异常");
		}
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.005", "角色异常");
		}
		if(PubMethod.isEmpty(member_id)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.006", "人员Id异常");
		}
		if(PubMethod.isEmpty(responsible)||responsible.length()>5){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.007", "负责人异常");
		}
		if(PubMethod.isEmpty(agentType)){
			return PubMethod.paramError("publicapi.OutletsController.insertBasCompinfo.008", "代收点类型异常");
		}
		String insertStatus = outletsService.insertBasCompinfo(comp_name,
				comp_address, comp_mobile, comp_telephone, roleId, longitude,
				latitude,member_id,responsible, agentType);
		return insertStatus;
	}

	/**
	 * @api {post} /outlets/invite 店员通过短信,微信邀请店长入驻(未入驻)-(郑炯，胡宣化)
	 * @apiVersion 0.2.0
	 * @apiDescription 根据手机号邀请店长加入（微信或者短信）
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {String} fromMemberId 邀请人的ID
	 * @apiParam {String} fromMemberPhone 邀请人的手机号
	 * @apiParam {Integer} fromMemberRoleid 邀请人的角色id
	 * @apiParam {String} toMemberPhone 被邀请人的手机号
	 * @apiParam {Integer} toRoleId 被邀请人的角色id
	 * @apiParam {Integer} flag 1:发短信(参数1为发短信,其他数字不发短信)
	 * @apiSampleRequest /outlets/invite
	 * @apiSuccessExample  Success-Response: HTTP/1.1 200 OK
        {
		    "data": "已成功邀请店长!!!", 
		    "success": true
		}

	 * @apiErrorExample {json} Error-Response: HTTP/1.1 200 OK
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "message":"XXX"
	 *     }
	 */
	@ResponseBody
	@RequestMapping(value = "/invite", method = { RequestMethod.POST, RequestMethod.GET })
	public String invite(String fromMemberId, String fromMemberPhone,Integer fromMemberRoleid, 
			String toMemberPhone, Integer toRoleId, Integer flag) {
		String insertStatus = outletsService.invite(fromMemberId, fromMemberPhone, 
				fromMemberRoleid, toMemberPhone, toRoleId, flag);
		return insertStatus;
	}

	/**
	 * @api {post} /outlets/findShopowner 收派员查找店长(找到了)-(郑炯，胡宣化)
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员查找店长,bas_compinfo,bas_compbusiness-(郑炯，胡宣化)@apiDescription 通过店长手机号查询店长是否入驻
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {String} memberTelephone  店长手机号
	 * @apiSampleRequest /outlets/findShopowner
	 * @apiSuccess {String} comp_id 公司id
	 * @apiSuccess {String} comp_name 店铺名称
	 * @apiSuccess {String} image_url 店铺图片
	 * @apiSuccess {String} comp_address 公司地址
	 * @apiSuccess {String} member_id 店长Id
	 * @apiSuccess {String} netId 网络id
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "comp_address": "北京-海淀区|田村路43号京粮物流", 
		        "comp_id": 117103623716864, 
		        "comp_name": "青山绿水", 
		        "image_url": "http://expnew.okdit.net/nfs_data/comp/2014120315521338831.png"
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
	@RequestMapping(value = "/findShopowner", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String findShopowner(String memberTelephone) {
		if(PubMethod.isEmpty(memberTelephone)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.findShopowner.006", "收派员手机号不能为空");
		}
		String result = outletsService.findShopowner(memberTelephone);
		return result;
	}

	/*
	@ResponseBody
	@RequestMapping(value = "/apply", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String apply(Long roleId, Long memberId, Long compId) {
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.001", "角色id不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.002", "人员id不能为空");
		}
		if(PubMethod.isEmpty(compId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.003", "公司id不能为空");
		}
		String status = outletsService.apply(roleId, memberId, compId);
		return status;
	}*/
	/**
	 * @api {post} /outlets/managerAauditShopAssistant 店长审核店员(归属审核)
	 * @apiVersion 0.2.0
	 * @apiDescription 店长审核店员(归属审核)
	 * @apiGroup ACCOUNT 选择附近代收点
	 * @apiParam {Long} memberId 店员id
	 * @apiParam {Long} compId 公司id
	 * @apiParam {String} roleId 被审核的人员的角色
	 * @apiParam {String} moblie 店员手机号
	 * @apiParam {String} auditOpinion 通过不通过(1:通过, 2:不通过)
	 * @apiParam {Long} auditUser 店长（审核人）ID
	 * @apiSampleRequest /outlets/managerAauditShopAssistant
	 * @apiSuccessExample Success-Response:
		{
		    "data": , 
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
	@RequestMapping(value = "/managerAauditShopAssistant", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String managerAauditShopAssistant(String memberId, String compId, String roleId, String moblie, 
			String auditOpinion, String auditUser){
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.001", "角色id不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.002", "人员id不能为空");
		}
		if(PubMethod.isEmpty(compId)){
			return PubMethod.paramError("net.okdi.apiV1.controller.OutletsController.apply.003", "公司id不能为空");
		}
		String result = outletsService.managerAauditShopAssistant(Long.valueOf(memberId), Long.valueOf(compId), roleId, moblie, auditOpinion, Long.valueOf(auditUser));
		System.out.println("");
		return result;
	}
}

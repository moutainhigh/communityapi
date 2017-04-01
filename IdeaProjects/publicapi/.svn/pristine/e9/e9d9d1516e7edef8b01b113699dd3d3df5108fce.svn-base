package net.okdi.apiV2.controller;

import net.okdi.apiV2.service.CollectionService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 代收业务
 * 
 * @author xuanhua.hu@amssy.com
 * @version 1.0.0
 * @2015-12-2
 * @return {@link CollectionController.java}
 */
@Controller
@RequestMapping("/collection")
public class CollectionController extends BaseController {

	@Autowired
	private CollectionService collectionService;

	/**
	 * @api {post} /collection/getMemberInfo 代收业务-点击代收点业务将基本信息带入-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 点击代收点业务将基本信息带入(无信息，全部信息带入空值)-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} memberId 人员ID
	 * @apiSampleRequest /collection/getMemberInfo
	 * @apiSuccess {String} auditId  身份认证的ID
	 * @apiSuccess {String} idNum  身份证号
	 * @apiSuccess {String} meImgPath  本人照片
	 * @apiSuccess {String} inHandImgPath  手持身份证照片
	 * @apiSuccess {String} frontImgPath 身份证照片
	 * @apiSuccess {String} memberName 人员姓名
	 * @apiSuccessExample Success-Response:
		    {
		    "data": {
		        "auditId": 129382496935941, 
		        "idNum": "371001198010082394", 
		        "frontImgPath": "http://cas.okdit.net/nfs_data/mob/id/front/13591370100375552.jpg", 
		        "inHandImgPath": "http://cas.okdit.net/nfs_data/mob/id/inHand/13591370100375552.jpg", 
		        "meImgPath": "http://ucenter.okdi.net/nfs_data/mob/id/me/13591370100375552.jpg", 
		        "memberName": "吕帅",
		        "memberId": "150475177795584"
		    }, 
		    "success": true
		}  
				
		{
		    "data": {
		        "auditId": "", 
		        "frontImgPath": "", 
		        "idNum": "", 
		        "inHandImgPath": "", 
		        "meImgPath": "", 
		        "memberName": ""
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
	@RequestMapping(value = "/getMemberInfo", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String getMemberInfo(String memberId) {
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod
					.paramsFailure(
							"net.okdi.apiV2.controller.CollectionController.getMemberInfo.001",
							"memberId不能为空");
		}
		return this.collectionService.getMemberInfo(memberId);

	}

	/**
	 * @api {post} /collection/isHasCollection 代收业务-判断代收点是否存在-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 判断代收点是否存在-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compAddress 代收点地址
	 * @apiSampleRequest /collection/isHasCollection
	 * @apiSuccess {String} compId  null:不存在    有值:存在  
	 * @apiSuccess {String} type  2:有店长    3：没有店长  
	 * @apiSuccessExample Success-Response:
			{
			    "data": {
			        "compId": 178846504181760, 
			        "type": 2
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
	@RequestMapping(value = "/isHasCollection", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String isHasCollection(String compName, String compAddress) {
		if (PubMethod.isEmpty(compName)) {
			return PubMethod.paramError(
					"publicapi.CollectionController.isHasCollection.001",
					"compName不能为空");
		}
		if (PubMethod.isEmpty(compAddress)) {
			return PubMethod.paramError(
					"publicapi.CollectionController.isHasCollection.002",
					"compAddress不能为空");
		}
		return this.collectionService.isHasCollection(compName, compAddress);
	}
	
	/**
	 * @api {post} /collection/insertClerkAudit1 代收业务-店员提交代收业务(代收点不存在)-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 店员提交代收业务(代收点不存在)-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compAddress 代收点地址
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} idNum 身份证号
	 * @apiParam {String} roleId 角色 2: 代收站店长 3:代收站店员
	 * @apiParam {String} latitude 纬度
	 * @apiParam {String} longitude 经度
	 * @apiParam {String} memberId 姓名Id
	 * @apiParam {String} compId 代收点ID
	 * @apiParam {String} auditId 身份审核ID
	 * @apiSampleRequest /collection/insertClerkAudit1
	 * @apiSuccess {String} compId  代收点的ID  
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": "183434063110144"
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
	@RequestMapping(value = "/insertClerkAudit1", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		 if(PubMethod.isEmpty(compName)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.001",
				 "compName不能为空");
				 }
				 if(PubMethod.isEmpty(compAddress)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.002",
				 "compAddress不能为空");
				 }
				 if(PubMethod.isEmpty(memberName)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.003",
				 "memberName不能为空");
				 }
				 if(PubMethod.isEmpty(idNum)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.004",
				 "idNum不能为空");
				 }
				 if(PubMethod.isEmpty(roleId)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.005",
				 "roleId不能为空");
				 }
				 if(PubMethod.isEmpty(latitude)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.006",
				 "latitude不能为空");
				 }
				 if(PubMethod.isEmpty(longitude)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.007",
				 "longitude不能为空");
				 }
				 if(PubMethod.isEmpty(memberId)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.008",
				 "memberId不能为空");
				 }
//				 if(PubMethod.isEmpty(compId)){
//				 return
//				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.007",
//				 "compId不能为空");
//				 }
//				 if(PubMethod.isEmpty(auditId)){
//				 return
//				 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit1.008",
//				 "auditId不能为空");
//				 }
		return collectionService.insertClerkAudit1(compName, compAddress,
				memberName, idNum, roleId, latitude, longitude, memberId,
				compId, auditId);

	}
 
	/**
	 * @api {post} /collection/insertClerkAudit2 代收业务-店员提交代收业务(代收点存在)-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 店员提交代收业务(代收点存在)-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compAddress 代收点地址
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} idNum 身份证号
	 * @apiParam {String} roleId 角色 2: 代收站店长 3:代收站店员
	 * @apiParam {String} latitude 纬度
	 * @apiParam {String} longitude 经度
	 * @apiParam {String} memberId 姓名Id
	 * @apiParam {String} compId 代收点ID
	 * @apiParam {String} auditId 身份审核ID
	 * @apiSampleRequest /collection/insertClerkAudit2
	 * @apiSuccess {String} compId  代收点的ID  
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": "183434063110144"
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
	@RequestMapping(value = "/insertClerkAudit2", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		 if(PubMethod.isEmpty(compName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.001",
			 "compName不能为空");
			 }
			 if(PubMethod.isEmpty(compAddress)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.002",
			 "compAddress不能为空");
			 }
			 if(PubMethod.isEmpty(memberName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.003",
			 "memberName不能为空");
			 }
			 if(PubMethod.isEmpty(idNum)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.004",
			 "idNum不能为空");
			 }
			 if(PubMethod.isEmpty(roleId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.005",
			 "roleId不能为空");
			 }
			 if(PubMethod.isEmpty(latitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.006",
			 "latitude不能为空");
			 }
			 if(PubMethod.isEmpty(longitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.007",
			 "longitude不能为空");
			 }
			 if(PubMethod.isEmpty(memberId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.008",
			 "memberId不能为空");
			 }
			 if(PubMethod.isEmpty(compId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.009",
			 "compId不能为空");
			 }
//			 if(PubMethod.isEmpty(auditId)){
//			 return
//			 PubMethod.paramError("publicapi.CollectionController.insertClerkAudit2.010",
//			 "auditId不能为空");
//			 }
		return this.collectionService.insertClerkAudit2(compName, compAddress,
				memberName, idNum, roleId, latitude, longitude, memberId,
				compId, auditId);

	}

	
	/**
	 * @api {post} /collection/insertShopkeeperAudit1 代收业务-店长提交代收业务(代收点不存在)-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 店长提交代收业务(代收点不存在)-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compAddress 代收点地址
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} idNum 身份证号
	 * @apiParam {String} roleId 角色 2: 代收站店长 3:代收站店员
	 * @apiParam {String} latitude 纬度
	 * @apiParam {String} longitude 经度
	 * @apiParam {String} memberId 姓名Id
	 * @apiParam {String} compId 代收点ID
	 * @apiParam {String} auditId 身份审核ID
	* @apiParam {String} phone 登录人号码(负责人手机号)
	 * @apiSampleRequest /collection/insertShopkeeperAudit1
	 * @apiSuccess {String} compId  代收点的ID  
	 * @apiSuccessExample Success-Response:
		{
	    "data": {
	        "compId": "183445779898368", 
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
	@RequestMapping(value = "/insertShopkeeperAudit1", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone) {
		
		 if(PubMethod.isEmpty(compName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.001",
			 "compName不能为空");
			 }
			 if(PubMethod.isEmpty(compAddress)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.002",
			 "compAddress不能为空");
			 }
			 if(PubMethod.isEmpty(memberName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.003",
			 "memberName不能为空");
			 }
			 if(PubMethod.isEmpty(idNum)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.004",
			 "idNum不能为空");
			 }
			 if(PubMethod.isEmpty(roleId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.005",
			 "roleId不能为空");
			 }
			 if(PubMethod.isEmpty(latitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.006",
			 "latitude不能为空");
			 }
			 if(PubMethod.isEmpty(longitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.007",
			 "longitude不能为空");
			 }
			 if(PubMethod.isEmpty(memberId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.008",
			 "memberId不能为空");
			 }
			 if(PubMethod.isEmpty(phone)){
				 return
				 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.009",
				 "phone不能为空");
				 }
//			 if(PubMethod.isEmpty(compId)){
//			 return
//			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.009",
//			 "compId不能为空");
//			 }
//			 if(PubMethod.isEmpty(auditId)){
//			 return
//			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit1.010",
//			 "auditId不能为空");
//			 }
		return this.collectionService.insertShopkeeperAudit1(compName,
				compAddress, memberName, idNum, roleId, latitude, longitude,
				memberId, compId, auditId,phone);

	}

	/**
	 * @api {post} /collection/insertShopkeeperAudit2 代收业务-店长提交代收业务(代收点存在)-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 店长提交代收业务(代收点存在)-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} compName 代收点名称
	 * @apiParam {String} compAddress 代收点地址
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} idNum 身份证号
	 * @apiParam {String} roleId 角色 2: 代收站店长 3:代收站店员
	 * @apiParam {String} latitude 纬度
	 * @apiParam {String} longitude 经度
	 * @apiParam {String} memberId 姓名Id
	 * @apiParam {String} compId 代收点ID
	 * @apiParam {String} auditId 身份审核ID
	 * @apiSampleRequest /collection/insertShopkeeperAudit2
	 * @apiSuccess {String} compId  代收点的ID  
	 * @apiSuccess {String} isInsert  1:提交成功 0:已经有站长 
	 * @apiSuccessExample Success-Response:
		{
	    "data": {
	        "compId": "183445779898368", 
	        "isInsert": 1
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
	@RequestMapping(value = "/insertShopkeeperAudit2", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		 if(PubMethod.isEmpty(compName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.001",
			 "compName不能为空");
			 }
			 if(PubMethod.isEmpty(compAddress)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.002",
			 "compAddress不能为空");
			 }
			 if(PubMethod.isEmpty(memberName)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.003",
			 "memberName不能为空");
			 }
			 if(PubMethod.isEmpty(idNum)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.004",
			 "idNum不能为空");
			 }
			 if(PubMethod.isEmpty(roleId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.005",
			 "roleId不能为空");
			 }
			 if(PubMethod.isEmpty(latitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.006",
			 "latitude不能为空");
			 }
			 if(PubMethod.isEmpty(longitude)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.007",
			 "longitude不能为空");
			 }
			 if(PubMethod.isEmpty(memberId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.008",
			 "memberId不能为空");
			 }
			 if(PubMethod.isEmpty(compId)){
			 return
			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.009",
			 "compId不能为空");
			 }
//			 if(PubMethod.isEmpty(auditId)){
//			 return
//			 PubMethod.paramError("publicapi.CollectionController.insertShopkeeperAudit2.010",
//			 "auditId不能为空");
//			 }
		return this.collectionService.insertShopkeeperAudit2(compName,
				compAddress, memberName, idNum, roleId, latitude, longitude,
				memberId, compId, auditId);

	}

	/**
	 * @api {post} /collection/findIdentity 代收业务-判断身份证号是否存在-胡宣化
	 * @apiVersion 0.3.0
	 * @apiDescription 判断身份证号是否存在-胡宣化
	 * @apiGroup ACCOUNT 代收业务
	 * @apiParam {String} idNum 人员身份证号
	 * @apiSampleRequest /collection/findIdentity
	 * @apiSuccess {String} memberId 0:不存在 (可以提交)   >=1:存在(对比登录memberId跟返回的memberId是否相同，相同可以提交，不同，不让提交)  
	 * @apiSuccessExample Success-Response:
			 {
		    "data": {
		        "memberId": 450226198305185910
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
	@RequestMapping(value = "/findIdentity", method = {
			RequestMethod.POST, RequestMethod.GET })
	public String findIdentity(String idNum) {
		if (PubMethod.isEmpty(idNum)) {
			return PubMethod
					.paramsFailure(
							"net.okdi.apiV2.controller.CollectionController.findIdentity.001",
							"idNum不能为空");
		}
		return this.collectionService.findIdentity(idNum);

	}
	
}

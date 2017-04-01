package net.okdi.apiV1.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.DesEncrypt;
import net.okdi.core.util.GenMD5;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 收派员信息接口
 * @Project Name:openapi 
 * @Package net.okdi.apiV1.controller  
 * @Title: CourierInfoController.java 
 * @ClassName: CourierInfoController <br/> 
 * @date: 2015-10-13 下午1:42:54 <br/> 
 * @author zhaohu 
 * @version v2.0 
 * @since JDK 1.6
 */
@Controller
@RequestMapping("/expressUser")
public class ExpressUserController extends BaseController {

	@Autowired
	private ExpressUserService expressUserService;
	Logger log = Logger.getLogger(ExpressUserController.class);
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/updateMemberInfoAndAudit 修改姓名、身份账号（提交并继续）
	 * @apiVersion 0.2.0
	 * @apiDescription 修改姓名、身份账号（提交并继续）
	 * @apiGroup 快递注册
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} idNum 身份证号码
	 * @apiParam {String} memberId 快递员id
	 * @apiParam {String} compId 站点id
	 * @apiParam {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSampleRequest /expressUser/updateMemberInfoAndAudit
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
	@RequestMapping(value = "/updateMemberInfoAndAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateMemberInfoAndAudit(String memberName,String idNum,String memberId,String compId,String roleType,String gender, String birthday,String address){
		try {
			if (PubMethod.isEmpty(memberName)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAudit.001", "姓名不能为空");
			}
			if (PubMethod.isEmpty(idNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAudit.002", "身份证号不能为空");
			}
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAudit.003", "memberId不能为空");
			}
			if (PubMethod.isEmpty(compId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAudit.004", "compId不能为空");
			}
			if (PubMethod.isEmpty(roleType)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAudit.005", "roleType不能为空");
			}
			return this.expressUserService.updateMemberInfoAndAudit(memberName,idNum,memberId,compId,roleType, gender,  birthday, address);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/upLoadPhotoInfo 上传照片(本人照、身份证、手持身份证)
	 * @apiVersion 0.2.0
	 * @apiDescription 上传照片(本人照、身份证、手持身份证)
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 快递员id
	 * @apiParam {file} uploadImage 上传file名称
	 * @apiParam {String} type 图片类型：contact：联系人 front：身份证前身照  back：身份证背面照 inHand：手持 head：接单王头像 photoPar:包裹拍照 me:本人照
	 * @apiSampleRequest /expressUser/upLoadPhotoInfo
	 * @apiSuccess {String} upload  true:上传成功  false：上传失败
	 * @apiSuccess {String} url  上传照片URL
	 * @apiSuccessExample Success-Response:
		{
		    "upload": "true",
		    "url": "http://publicapi.okdit.net/nfs_data/mob/id/me/123456789.jpg"
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
	@RequestMapping(value = "/upLoadPhotoInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String upLoadPhotoInfo(String memberId,@RequestParam(value = "uploadImage", required = false) MultipartFile[] uploadImage,String type){
		try {
			if(PubMethod.isEmpty(memberId) || PubMethod.isEmpty(uploadImage) || PubMethod.isEmpty(type)){
				 return paramsFailure();
			  }
			log.info("开始上传图片："+memberId+"--"+type);
			return expressUserService.upLoadPhotoInfo(type, memberId, uploadImage);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/updateMemberInfoAndAuditJump 身份认证--跳过
	 * @apiVersion 0.2.0
	 * @apiDescription 身份认证--跳过
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} compId 站点id
	 * @apiParam {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiParam {String} memberName 快递员名字（选角色时候填写的名字，需要带过来）
	 * @apiSampleRequest /expressUser/updateMemberInfoAndAuditJump
	 * @apiSuccessExample Success-Response:
		{
		    "success": "true"
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
	@RequestMapping(value = "/updateMemberInfoAndAuditJump", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateMemberInfoAndAuditJump(String memberId,String compId,String roleType,String memberName){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAuditJump.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAuditJump.002", "compId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.updateMemberInfoAndAuditJump.003", "roleType不能为空");
		}
		try {
			return expressUserService.updateMemberInfoAndAuditJump(memberId,compId,roleType,memberName);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/isIdNumRepeat 验证身份账号是否重复注册
	 * @apiVersion 0.2.0
	 * @apiDescription 验证身份账号是否重复注册
	 * @apiGroup 快递注册
	 * @apiParam {String} idNum 身份证号码
	 * @apiParam {String} memberPhone 手机号
	 * @apiSampleRequest /expressUser/isIdNumRepeat
	 * @apiSuccess {List} phones 身份证号重复注册的手机号
	 * @apiSuccess {String} yesOrNo yes:重复注册 no：不重复注册
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "phones": [
		            "13125426980",
		            "13199529699",
		            "13199529698"
		        ],
		        "yesOrNo": "yes"
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
	@RequestMapping(value = "/isIdNumRepeat", method = { RequestMethod.POST, RequestMethod.GET })
	public String isIdNumRepeat(String idNum,String memberPhone){
		try {
			if (PubMethod.isEmpty(idNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isIdNumRepeat.001", "idNum不能为空");
			}
			return this.expressUserService.isIdNumRepeat(idNum,memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
//	/**
//	 * @author zhaohu
//	 * @api {post} /expressUser/guishuAudit 收派员归属认证审核（手机端）
//	 * @apiVersion 0.2.0
//	 * @apiDescription 收派员归属认证审核
//	 * @apiGroup 快递注册
//	 * @apiParam {String} memberId 收派员id
//	 * @apiParam {String} status 审核状态:1通过2拒绝
//	 * @apiSampleRequest /expressUser/guishuAudit
//	 * @apiSuccessExample Success-Response:
//		{
//    		"success": true
//		}
//	
//	 * @apiErrorExample Error-Response:
//	 *     HTTP/1.1 404 Not Found
//	 *     {
//	 *	     "success":	false,
//	 *	     "errCode":	"err.001",
//	 *	     "message":"XXX"
//	 *     }
//	 */
	@ResponseBody
	@RequestMapping(value = "/guishuAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String guishuAudit(String memberId,String status){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.guishuAudit.001", "memberId不能为空");
			}
			if (PubMethod.isEmpty(status)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.guishuAudit.002", "status不能为空");
			}
			return this.expressUserService.guishuAudit(memberId,status);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/queryNetInfo 获取快递网络信息
	 * @apiVersion 0.2.0
	 * @apiDescription 获取快递网络信息
	 * @apiGroup 快递注册
	 * @apiSampleRequest /expressUser/queryNetInfo
	 * @apiSuccess {String} netId 快递网络id
	 * @apiSuccess {String} netName 快递网络名称
	 * @apiSuccessExample Success-Response:
		{
	    "data": 
	    	[
		        {
		            "netId": "999",
		            "netName": "EMS速递"
		        },
		        {
		            "netId": "1001",
		            "netName": "顺丰速运"
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
	@RequestMapping(value = "/queryNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNetInfo(){
		try {
			return this.expressUserService.queryNetInfo();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/saveExpressAudit 快递认证
	 * @apiVersion 0.2.0
	 * @apiDescription 快递认证
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} expressNumStr	扫描快递单号（逗号隔开,如：张三，18888888888,1,2,3）
	 * @apiParam {String} compId 站点id
	 * @apiParam {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSampleRequest /expressUser/saveExpressAudit
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
	@RequestMapping(value = "/saveExpressAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveExpressAudit(String memberId,String expressNumStr,String compId,String roleType){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(expressNumStr)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.002", "expressNumStr不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.003", "compId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.004", "roleType不能为空");
		}
		try {
			return this.expressUserService.saveExpressAudit(memberId,expressNumStr,compId,roleType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/saveExpressAuditJump 快递认证--跳过
	 * @apiVersion 0.2.0
	 * @apiDescription 快递认证--跳过
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} compId 站点id
	 * @apiParam {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSampleRequest /expressUser/saveExpressAuditJump
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
	@RequestMapping(value = "/saveExpressAuditJump", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveExpressAuditJump(String memberId,String compId,String roleType){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.002", "compId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAudit.003", "roleType不能为空");
		}
		try {
			return this.expressUserService.saveExpressAuditJump(memberId,compId,roleType);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/queryExpressAuditStatusByMemberId 查询快递认证信息
	 * @apiVersion 0.2.0
	 * @apiDescription 查询快递认证信息
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiSampleRequest /expressUser/queryExpressAuditStatusByMemberId
	 * * @apiSuccess {String} memberId 快递员id
	 * * @apiSuccess {String} auditStatus 快递认证状态：-1:未认证或者未完善	0:认证中（待审核）	1:已认证（通过）	2：认证失败（不通过）
	 * * @apiSuccess {String} expressNum 扫描快递单号（逗号隔开,如：张三，18888888888,1,2,3）
	 * * @apiSuccess {String} compId 站点id
	 * * @apiSuccess {String} roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * * @apiSuccess {String} auditRejectReason 拒绝原因
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "auditStatus": "2",
		        "compId": "3435125",
		        "expressNum": "1,2,3",
		        "memberId": 889,
		        "roleType": "0"
		        "auditRejectReason": "大幅度"
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
	@RequestMapping(value = "/queryExpressAuditStatusByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryExpressAuditStatusByMemberId(String memberId){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryExpressAuditStatusByMemberId.001", "memberId不能为空");
		}
		try {
			return this.expressUserService.queryExpressAuditStatusByMemberId(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/queryRealNameAuditInfo 查询身份认证信息
	 * @apiVersion 0.2.0
	 * @apiDescription 查询身份认证信息
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiSampleRequest /expressUser/queryRealNameAuditInfo
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
		{
		    "data": {
		        "compId": "3435125",
		        "birthday":"2016-12-03",
		        "gender":"1",
		        "frontPic": "http://cas.okdit.net/nfs_data/mob/id/front/889.jpg",
		        "headPic": "http://cas.okdit.net/nfs_data/mob/head/889.jpg",
		        "idNum": "130627000000000000",
		        "inHandPic": "http://cas.okdit.net/nfs_data/mob/id/inHand/889.jpg",
		        "mePic": "http://cas.okdit.net/nfs_data/mob/id/me/889.jpg",
		        "backPic": "http://cas.okdit.net/nfs_data/mob/id/back/889.jpg",
		        "memberId": "889",
		        "memberName": "赵虎测试2",
		        "memberPhone": "15888888888",
		        "roleType": "0"
		        "auditRejectReason": "水电费"
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
	@RequestMapping(value = "/queryRealNameAuditInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRealNameAuditInfo(String memberId){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryRealNameAuditInfo.001", "memberId不能为空");
		}
		try {
			return this.expressUserService.queryRealNameAuditInfo(memberId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author weixing.zhang
	 * @api {post} /expressUser/completeMemberInfo 完善个人信息
	 * @apiVersion 0.3.0
	 * @apiDescription 完善个人信息 
	 * @apiGroup 我的页面(新需求)
	 * @apiParam {String} memberId 用户memberId
	 * @apiParam {String} nickName 昵称
	 * @apiParam {String} gender 性别1男2女
	 * @apiParam {String} birthday 生日 2016-12-17 10:45:00
	 * @apiSampleRequest /expressUser/completeMemberInfo
	 * @apiSuccessExample Success-Response:
	 *   {"data":"ture",
	 *   			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "msg":"XXX"
	 *     }
	 */
	//@RequestParam(value = "myfiles", required = false)  MultipartFile myfiles,
	@ResponseBody
	@RequestMapping(value = "/completeMemberInfo")
	public String  completeMemberInfo(String memberId,String nickName,String gender,String birthday) {
		System.out.println("--memberId"+memberId+"--memberName"+nickName+"--gender"+gender+"--birthday"+birthday);
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.completeMemberInfo.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(nickName)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.completeMemberInfo.002", "nickName不能为空");
		}
		if (PubMethod.isEmpty(gender)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.completeMemberInfo.003", "gender不能为空");
		}
		if (PubMethod.isEmpty(birthday)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.completeMemberInfo.004", "birthday不能为空");
		}
		return expressUserService.completeMemberInfo(memberId,nickName,gender,birthday);

	}
	   
    /**
     * @author xingwei.zhang
     * @api {post} /expressUser/isBoundWechat 微信登录
     * @apiVersion 0.3.0
     * @apiDescription 微信登录
     * @apiGroup 我的页面(新需求)
     * @apiParam {String} weNumber 微信号
     * @apiParam {String} version  版本号
     * @apiSampleRequest /expressUser/isBoundWechat
     * @apiSuccess {char} isBound   (0绑定 ,""未绑定)
     * @apiSuccess {Short} roleType 申请角色类型 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员        ""未选择角色
     * @apiSuccess {String} weName 微信昵称
     * @apiSuccessExample Success-Response:
     *{"data":{"data1":{"accountId":"119882629557268480",
     *	"applicationTime":-1,"compId":"",
     *	"compStatus":-1,"expressFlag":-1,
     *	"headImgUrl":"http://cas.okdit.net/nfs_data/mob/head/239765259862016.jpg",
     *	"isVirtualuser":"","memberId":239765259862016,
     *	"memberName":"胡高家家","relationFlag":"-1","roleId":"","verifyFlag":0},
     *	"isBound":"0","numberPhone":"15011006238","roleType":"",
     *	"weName":"dsadad"},"success":true}
     * @apiErrorExample Error-Response:
     * {"errCode":0,
     * "errSubcode":"001",
     * "message":"memberId不能为空",
     * "success":false}
     */
    @ResponseBody
    @RequestMapping(value = "/isBoundWechat", method = {RequestMethod.GET, RequestMethod.POST})
    public String isBoundWechat(String weNumber, HttpServletRequest request) {
    	log.info("pub---------微信登录isBoundWechat()-------weNumber"+weNumber);
        if (PubMethod.isEmpty(weNumber)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isBoundWechat.001", "weNumber不能为空");
		}  
        Enumeration myEnumeration = request.getHeaderNames();
		String mobile = "";
		String headVersion = "";
		while (myEnumeration.hasMoreElements()) {
			Object element = myEnumeration.nextElement();
			if ("version".equals(element)) {
				headVersion = request.getHeader(element.toString());
			}
		}
		String jsonString = expressUserService.isBoundWechat(weNumber);
		JSONObject jsonObject = JSON.parseObject(jsonString);
		JSONObject data = jsonObject.getJSONObject("data");
		String isBound = data.getString("isBound");
		if (isBound.equals("0")) {
			mobile = data.getString("numberPhone");
			log.info("微信登录isBoundWechat() getKey() 参数: " + mobile + ", " + headVersion);
			String key = getKey(mobile, headVersion);
			jsonObject.put("key", key);
		}else{
			jsonObject.put("key", "");
		}
        return jsonObject.toJSONString();
    }
    
    /**
     * @author xingwei.zhang
     * @api {post} /expressUser/unlockWechats 解绑微信
     * @apiVersion 0.3.0
     * @apiDescription 解绑微信
     * @apiGroup 我的页面(新需求)
     * @apiParam {String} memberId 收派员id
     * @apiSampleRequest /expressUser/unlockWechats
     * @apiSuccessExample Success-Response:
     * {"data":"ture",
     * 			"success":true}
     * @apiErrorExample Error-Response:
     * {"errCode":0,
     * "errSubcode":"001",
     * "message":"memberId不能为空",
     * "success":false}
     */
    @ResponseBody
    @RequestMapping(value = "/unlockWechats", method = {RequestMethod.GET, RequestMethod.POST})
    public String unlockWechats(String memberId) {
        log.info("pub---解绑微信---, memberId:"+memberId);
        return expressUserService.unlockWechat(memberId);
    }
    
    /**
     * @author xingwei.zhang
     * @api {post} /expressUser/isBoundWx 是否绑定微信
     * @apiVersion 0.3.0
     * @apiDescription 是否绑定微信
     * @apiGroup 我的页面(新需求)
     * @apiParam {String} memberId 成员Id
     * @apiSampleRequest /expressUser/isBoundWx
     * @apiSuccess {char} isBound   (0绑定 ,1解绑,""未绑定)
     * @apiSuccess {String} weName    微信昵称
     * @apiSuccess {String} nickName  昵称 
     * @apiSuccess {String} gender    性别
     * @apiSuccess {String} birthday  生日
     * @apiSuccessExample Success-Response:
     *{"data":{"isBound":"1","weName":"1",
     *          "nickName":"1","gender":"1",
     *			"birthday":"dsad"},
     *			"success":true}     
     * @apiErrorExample Error-Response:
     * {"errCode":0,
     * "errSubcode":"001",
     * "message":"memberId不能为空",
     * "success":false}
     */
    @ResponseBody
    @RequestMapping(value = "/isBoundWx", method = {RequestMethod.GET, RequestMethod.POST})
    public String isBoundWx(String memberId) {
        log.info("pub---------------是否绑定微信-------------memberId"+memberId);
        if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isBoundWx.001", "memberId不能为空");
		}
        return expressUserService.isBoundWx(memberId);
    }
    
    /**
	 * @author weixing.zhang
	 * @api {post} /expressUser/insertWeChat 绑定微信 
	 * @apiVersion 0.3.0
	 * @apiDescription 绑定微信 
	 * @apiGroup 我的页面(新需求)
	 * @apiParam {String} memberId 用户memberId
	 * @apiParam {String} weNumber 微信号
	 * @apiParam {String} weName 微信昵称
	 * @apiSampleRequest /expressUser/insertWeChat
	 * @apiSuccessExample Success-Response:
	 *   {"data":"ture",
	 *   			"success":true}
	 * @apiErrorExample Error-Response:
	 *     HTTP/1.1 404 Not Found
	 *     {
	 *	     "success":	false,
	 *	     "errCode":	"err.001",
	 *	     "msg":"XXX"
	 *     }
	 */
	@ResponseBody
    @RequestMapping(value = "/insertWeChat", method = {RequestMethod.GET, RequestMethod.POST})
    public String insertWeChat(String memberId, String weNumber,String weName) {
		 log.info("pub---------------绑定微信-------------memberId"+memberId);
	        if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isBoundWx.001", "memberId不能为空");
			}
	        if (PubMethod.isEmpty(weNumber)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isBoundWx.002", "weNumber不能为空");
			}
	        if (PubMethod.isEmpty(weName)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isBoundWx.003", "weName不能为空");
			}
		return expressUserService.insertWeChat(memberId, weNumber, weName);
    }
	
	/**
     * @author xingwei.zhang
     * @api {post} /expressUser/insertPhoneWeChat 已注册绑定微信
     * @apiVersion 0.3.0
     * @apiDescription 已注册绑定微信
     * @apiGroup 我的页面(新需求)
     * @apiParam {String} memberPhone 手机号
     * @apiParam {String} weNumber 微信号
	 * @apiParam {String} weName 微信昵称
     * @apiSampleRequest /expressUser/insertPhoneWeChat
     * @apiSuccessExample Success-Response:
     * {"data":"ture",
	 *   			"success":true}
     * @apiErrorExample Error-Response:
     * {"errCode":0,
     * "errSubcode":"001",
     * "message":"memberId不能为空",
     * "success":false}
     */
    @ResponseBody
    @RequestMapping(value = "/insertPhoneWeChat", method = {RequestMethod.GET, RequestMethod.POST})
    public String insertPhoneWeChat(String memberPhone, String weNumber,String weName) {
    	log.info("pub---------------已注册绑定微信-------------memberId"+weNumber);
        return expressUserService.insertPhoneWeChat(memberPhone,weNumber,weName);
    }
    

    private String getKey(String headMobile, String version) {
		log.info("登陆接口, 接收到的请求头参数: headMobile:{} version:{}"+headMobile+","+version);
		String mobileMD5 = GenMD5.generateMd5(headMobile);
		DesEncrypt des = new DesEncrypt();
		des.getKey("okdi"); //生成密匙
		return des.getEncString(mobileMD5 + version);
	}
    

}



package net.okdi.apiV1.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import net.okdi.apiV1.service.ExpressUserService;
import net.okdi.core.base.BaseController;
import net.okdi.core.common.page.Page;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.saveMemberInfoAndAudit 
	 * @Description: TODO(修改人员信息和审核信息--姓名、身份账号、照片) 
	 * @param @param memberName
	 * @param @param idNum
	 * @param @param memberId
	 * @param @param compId
	 * @param @param roleType 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-14
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemberInfoAndAudit", method = { RequestMethod.POST, RequestMethod.GET })
	public String updateMemberInfoAndAudit(String memberName,String idNum,String memberId,String compId,String roleType){
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
			expressUserService.updateMemberInfoAndAudit(memberName,idNum,memberId,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.updateMemberInfoAndAuditJump 
	 * @Description: TODO(跳过身份认证) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param roleType	1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-20
	 * @auth zhaohu
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
			expressUserService.updateMemberInfoAndAuditJump(memberId,compId,roleType,memberName);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/queryUserAuditList 收派员审核列表信息（运营平台）
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员审核列表信息（运营平台）
	 * @apiGroup 快递注册
	 * @apiParam {Integer} auditStatus 审核状态 0待审核1审核通过2审核不通过
	 * @apiParam {String} memberName 姓名
	 * @apiParam {String} memberPhone 手机号
	 * @apiParam {String} idNum 身份证号码
	 * @apiParam {String} roleId 角色id  1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiParam {String} netId 快递网络id
	 * @apiParam {String} provinceId 省份id
	 * @apiParam {String} realNameStatus 实名认证状态	-1 未提交 0 待审核  1通过 2不通过
	 * @apiParam {String} expressStatus 快递员认证状态	-1 未提交 0 待审核  1通过 2不通过
	 * @apiParam {String} startTime 注册开始时间
	 * @apiParam {String} endTime 注册结束时间
	 * @apiParam {String} currentPage  当前页码
	 * @apiParam {String} pageSize  每页显示条数
	 * @apiSampleRequest /expressUser/queryUserAuditList
	 * @apiSuccess {String} currentPage  当前页码
	 * @apiSuccess {String} pageSize  每页显示条数
	 * @apiSuccess {String} total  items的条数
	 * @apiSuccess {String} applicationRoleType  申请角色类型 1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSuccess {String} compId  站点id
	 * @apiSuccess {String} createTime  注册时间
	 * @apiSuccess {String} idNum 身份证号码
	 * @apiSuccess {String} memberId  快递员id
	 * @apiSuccess {String} memberName  快递员姓名
	 * @apiSuccess {String} memberPhone 手机号(***加密)
	 * @apiSuccess {String} memberPhoneReal 手机号(非加密)
	 * @apiSuccess {String} netName 快递网络名称
	 * @apiSuccess {String} realNameStatus 实名认证状态	-1 未提交 0 待审核  1通过 2不通过
	 * @apiSuccess {String} expressStatus 快递员认证状态	-1 未提交 0 待审核  1通过 2不通过
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
				        "applicationRoleType": "0",
				        "compId": "3435125",
				        "createTime": "",
				        "expressStatus": "0",
				        "idNum": "130627000000000000",
				        "memberId": "889",
				        "memberName": "赵虎测试2",
				        "memberPhone": "158***88888",
				        "memberPhoneReal": "15888888888",
				        "netName": "韵达快递",
				        "realNameStatus": "0"
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
	@RequestMapping(value = "/queryUserAuditList", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryUserAuditList(Integer auditStatus,String memberName,String memberPhone,String idNum,
				String roleId,String netId,String startTime,String endTime,Integer currentPage,Integer pageSize,String realNameStatus,String expressStatus,String provinceId,String sureStatus,String auditItem){
		try {
			if (PubMethod.isEmpty(auditStatus)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryUserAuditList.001", "审核状态不能为空");
			}
			if (PubMethod.isEmpty(currentPage)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryUserAuditList.002", "currentPage不能为空");
			}
			if (PubMethod.isEmpty(pageSize)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryUserAuditList.003", "pageSize不能为空");
			}
			Page page = expressUserService.queryUserAuditList(auditStatus,memberName,memberPhone,idNum,roleId,netId,startTime,endTime,currentPage,pageSize,realNameStatus,expressStatus,provinceId,sureStatus, auditItem);
			return jsonSuccess(page);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @author zhaohu
	 * @api {post} /expressUser/queryUserDetail 收派员审核信息详情（身份和归属）
	 * @apiVersion 0.2.0
	 * @apiDescription 收派员审核信息详情（身份和归属）
	 * @apiGroup 快递注册
	 * @apiParam {String} memberId 收派员id
	 * @apiParam {String} compId 站点id
	 * @apiSampleRequest /expressUser/queryUserDetail
	 * @apiSuccess {String} compId 站点id
	 * @apiSuccess {String} memberId 收派员id
	 * @apiSuccess {String} memberName 姓名
	 * @apiSuccess {String} memberPhone 手机号
	 * @apiSuccess {String} idNum 身份证号码
	 * @apiSuccess {String} headPic 本人大头照URL
	 * @apiSuccess {String} frontPic 身份证正面照URL
	 * @apiSuccess {String} inHandPic 手持身份证照URL
	 * @apiSuccess {String} compName  站点名称
	 * @apiSuccess {String} compStatus 站点认证状态：1：已认证  其它：未认证 
	 * @apiSuccess {String} netName 快递网络名称
	 * @apiSuccess {String} memberStatus 归属认证状态：1已认证2未认证
	 * @apiSuccess {String} memberStatusSF 身份认证状态：-1 未提交 0 待审核  1通过 2不通过
	 * @apiSuccess {String} memberStatusKD 快递员认证状态：-1 未提交 0 待审核  1通过 2不通过
	 * @apiSuccess {String} roleType 角色：1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @apiSuccess {String} expressNum 快递认证单号
	 * @apiSuccessExample Success-Response:
		{
		    "data": {
		        "compId": 3435125,
		        "compName": "来广营站",
		        "compStatus": 2,
		        "frontPic": "http://cas.okdit.net/nfs_data/mob/id/front/889.jpg",
		        "headPic": "http://cas.okdit.net/nfs_data/mob/id/me/889.jpg",
		        "idNum": "130627000000000000",
		        "inHandPic": "http://cas.okdit.net/nfs_data/mob/id/inHand/889.jpg",
		        "memberId": 889,
		        "memberName": "赵虎测试2",
		        "memberPhone": "15888888888",
		        "memberStatus": 0,
		        "memberStatusKD": 0,
		        "memberStatusSF": 0,
		        "netName": "韵达快递",
		        "roleType": 0
		        "expressNum": ""
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
	@RequestMapping(value = "/queryUserDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryUserDetail(String memberId,String compId){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryUserDetail.001", "memberId不能为空");
			}
//			if (PubMethod.isEmpty(compId)) {
//				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryUserDetail.002", "compId不能为空");
//			}
			HashMap<String,Object> map = expressUserService.queryUserDetail(memberId,compId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.isIdNumRepeat 
	 * @Description: TODO( 验证身份证号是否重复注册 ) 
	 * @param @param idNum
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-14
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/isIdNumRepeat", method = { RequestMethod.POST, RequestMethod.GET })
	public String isIdNumRepeat(String idNum,String memberPhone){
		try {
			if (PubMethod.isEmpty(idNum)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.isIdNumRepeat.001", "idNum不能为空");
			}
			HashMap<String,Object> map = expressUserService.isIdNumRepeat(idNum,memberPhone);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.guishuAudit 
	 * @Description: TODO( 归属地认证审核 ) 
	 * @param @param memberId
	 * @param @param status
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-14
	 * @auth zhaohu
	 */
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
			expressUserService.guishuAudit(memberId,status);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.checkMemberInfo 
	 * @Description: TODO( 运营平台审核收派员（实为身份审核） ) 
	 * @param @param memberId	快递员id
	 * @param @param compId	站点id
	 * @param @param status	1通过2拒绝
	 * @param @param remark	备注描述
	 * @param @param reasonNum 1图片模糊不清2经人工电话核实，与实际情况不符3其他
	 * @param @param auditType	1实名认证3快递员认证
	 * @param @param memberPhone	注册手机号
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-14
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/checkMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String checkMemberInfo(String memberId,String compId,String status,String remark,String reasonNum,String auditType,String memberPhone){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.checkMemberInfo.001", "memberId不能为空");
			}
			if (PubMethod.isEmpty(status)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.checkMemberInfo.002", "status不能为空");
			}
			HashMap<String, Object> map = expressUserService.checkMemberInfo(memberId,compId,status,remark,reasonNum,auditType,memberPhone);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.forceCheckMemberInfo 
	 * @Description: TODO( 运营平台强制审核收派员（实为身份审核） ) 
	 * @param @param memberId	快递员id
	 * @param @param compId	站点id 
	 * @param @param  roleType 角色类型 ：1 站长 0 收派员 -1 后勤
	 * @param @param status	1通过2拒绝
	 * @param @param remark	备注描述
	 * @param @param auditType	1实名认证3快递员认证
	 * @param @param memberPhone	注册手机号
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-14
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/forceCheckMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String forceCheckMemberInfo(String memberId,String compId,String roleType,String status,String remark,String auditType,String memberPhone){
		try {
			if (PubMethod.isEmpty(memberId)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.forceCheckMemberInfo.001", "memberId不能为空");
			}
			if (PubMethod.isEmpty(status)) {
				return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.forceCheckMemberInfo.002", "status不能为空");
			}
			HashMap<String, Object> map = expressUserService.forceCheckMemberInfo(memberId,compId,roleType,status,remark,auditType,memberPhone);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryNetInfo 
	 * @Description: TODO( 获取所有快递网络信息 ) 
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-15
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryNetInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryNetInfo(){
		try {
			List<HashMap<String, String>> list = expressUserService.queryNetInfo();
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryProvinceInfo 
	 * @Description: TODO( 获取省份信息 ) 
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-23
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryProvinceInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryProvinceInfo(){
		try {
			List<HashMap<String, String>> list = expressUserService.queryProvinceInfo();
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.saveExpressAudit 
	 * @Description: TODO( 快递认证 ) 
	 * @param @param memberId	收派员id
	 * @param @param expressNumStr	扫描快递单号（逗号隔开,如：张三,18888888888,1,2,3）
	 * @param @param compId	站点id
	 * @param @param roleType	1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-24
	 * @auth zhaohu
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
			expressUserService.saveExpressAudit(memberId,expressNumStr,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.saveExpressAuditJump 
	 * @Description: TODO(快递认证--跳过) 
	 * @param @param memberId
	 * @param @param expressNumStr	扫描快递单号（逗号隔开）
	 * @param @param compId
	 * @param @param roleType	1 站长 0 收派员 -1 后勤 2 代收站店长 3 代收站店员
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-27
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/saveExpressAuditJump", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveExpressAuditJump(String memberId,String compId,String roleType){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAuditJump.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(compId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAuditJump.002", "compId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.saveExpressAuditJump.003", "roleType不能为空");
		}
		try {
			expressUserService.saveExpressAuditJump(memberId,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryExpressAuditStatusByMemberId 
	 * @Description: TODO(通过memberId查询快递认证状态信息) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-26
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryExpressAuditStatusByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryExpressAuditStatusByMemberId(String memberId){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryExpressAuditStatusByMemberId.001", "memberId不能为空");
		}
		try {
			HashMap<String, Object> map = expressUserService.queryExpressAuditStatusByMemberId(memberId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryRealNameAuditInfo 
	 * @Description: TODO(查询身份认证信息) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-27
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRealNameAuditInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRealNameAuditInfo(String memberId){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryRealNameAuditInfo.001", "memberId不能为空");
		}
		try {
			HashMap<String, Object> map = expressUserService.queryRealNameAuditInfo(memberId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryAddressAuditInfo 
	 * @Description: TODO(查询归属认证状态) 
	 * @param @param memberId
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-10-30
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryAddressAuditInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryAddressAuditInfo(String memberId){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryAddressAuditInfo.001", "memberId不能为空");
		}
		try {
			HashMap<String, Object> map = expressUserService.queryAddressAuditInfo(memberId);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.sendMsgToInviter 
	 * @Description: TODO( 为邀请者推送消息 ) 
	 * @param @param memberId
	 * @param @param memberPhone
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-11-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMsgToInviter", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendMsgToInviter(String memberId,String memberPhone){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sendMsgToInviter.001", "memberId不能为空");
		}
		try {
			expressUserService.sendMsgToInviter(memberId,memberPhone);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.sendMsgToInvitee 
	 * @Description: TODO(为被邀请者推送消息 ) 
	 * @param @param memberId
	 * @param @param memberPhone
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-11-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/sendMsgToInvitee", method = { RequestMethod.POST, RequestMethod.GET })
	public String sendMsgToInvitee(String memberId,String memberPhone){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sendMsgToInvitee.001", "memberId不能为空");
		}
		try {
			expressUserService.sendMsgToInvitee(memberId,memberPhone);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryAddressAuditInfo 
	 * @Description: TODO(通过手机号集合查询memberid集合) 
	 * @param @param memberPhones	手机号集合参数，以逗号隔开。
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2015-11-26
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMemberInfoByMemberPhones", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMemberInfoByMemberPhones(String memberPhones){
		if (PubMethod.isEmpty(memberPhones)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.queryMemberInfoByMemberPhones.001", "memberPhones不能为空");
		}
		try {
			HashMap<String, Object> map = expressUserService.queryMemberInfoByMemberPhones(memberPhones);
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.queryRepeatOrder 
	 * @Description: 查询单号是否被重复认证
	 * @param orderArr
	 * @param netId
	 * @param phone
	 * @return   
	 * @return String  返回值类型
	 *      {"data":
	 *        [
	 *        {"invitePhone":"","member_id":179073353097216,"member_phone":"14740000569","orderNo":"0015479822"},
	 *        {"invitePhone":"14742000001","member_id":181101617848321,"member_phone":"14742000005","orderNo":"0015479822"},
	 *        {"invitePhone":"14742000001","member_id":181103828246528,"member_phone":"14742000008","orderNo":"0015479822"},
	 *        {"invitePhone":"","member_id":181105384333312,"member_phone":"14742000011","orderNo":"0015479822"},
	 *        {"invitePhone":"14742000001","member_id":181101617848321,"member_phone":"14742000005","orderNo":"0015479855"},
	 *        {"invitePhone":"14742000001","member_id":181103828246528,"member_phone":"14742000008","orderNo":"0015479855"},
	 *        {"invitePhone":"","member_id":181105384333312,"member_phone":"14742000011","orderNo":"0015479855"},
	 *        {"invitePhone":"14742000001","member_id":181105828929536,"member_phone":"14742000012","orderNo":"0015479855"},
	 *        {"invitePhone":"14742000001","member_id":181101617848321,"member_phone":"14742000005","orderNo":"0015479870"},
	 *        {"invitePhone":"14742000001","member_id":181103828246528,"member_phone":"14742000008","orderNo":"0015479870"},
	 *        {"invitePhone":"","member_id":181105384333312,"member_phone":"14742000011","orderNo":"0015479870"},
	 *        {"invitePhone":"14742000001","member_id":181105828929536,"member_phone":"14742000012","orderNo":"0015479870"}
	 *        ],
	 *        "success":true
	 *      }
	 * @throws 
	 * @date 2015-11-26
	 * @auth amssy
	 */
	@ResponseBody
	@RequestMapping(value = "/queryRepeatOrder", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryRepeatOrder(String orderArr,String netId,String phone){
		if (PubMethod.isEmpty(netId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sendMsgToInvitee.001", "netId不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sendMsgToInvitee.002", "phone不能为空");
		}
		try {
			List list = expressUserService.queryRepeatOrder(orderArr,netId,phone);
			return jsonSuccess(list);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.waitSureOper 
	 * @Description: TODO(客服操作快递员标记--待确认按钮) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param roleType
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-1-6
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/waitSureOper", method = { RequestMethod.POST, RequestMethod.GET })
	public String waitSureOper(String memberId,String compId,String roleType){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.waitSureOper.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.waitSureOper.001", "roleType不能为空");
		}
		try {
			this.expressUserService.waitSureOper(memberId,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	/**
	 * @MethodName: net.okdi.apiV1.controller.ExpressUserController.java.sureOper 
	 * @Description: TODO(客服操作快递员标记--确认是快递员按钮) 
	 * @param @param memberId
	 * @param @param compId
	 * @param @param roleType
	 * @param @return   
	 * @return String  返回值类型
	 * @throws 
	 * @date 2016-1-7
	 * @auth zhaohu
	 */
	@ResponseBody
	@RequestMapping(value = "/sureOper", method = { RequestMethod.POST, RequestMethod.GET })
	public String sureOper(String memberId,String compId,String roleType){
		if (PubMethod.isEmpty(memberId)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sureOper.001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(roleType)) {
			return PubMethod.paramsFailure("net.okdi.apiV1.controller.ExpressUserController.sureOper.001", "roleType不能为空");
		}
		try {
			this.expressUserService.sureOper(memberId,compId,roleType);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure();
		}
	}
	
	
	
	
	
	
	
	
	
	
}

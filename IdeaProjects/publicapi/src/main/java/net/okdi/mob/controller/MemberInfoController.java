package net.okdi.mob.controller;


import java.util.HashMap;
import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.MemberInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 个人端的业务模块
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/memberInfo")
public class MemberInfoController extends BaseController {
	@Autowired
	MemberInfoService memberInfoService;
	
		/**
		 * @api {post} /memberInfo/querySiteMemberList 查询当前站点人员列表
		 * @apiPermission user
		 * @apiDescription 查询站点或营业分部下的人员列表 kai.yang
	 	 * @apiparam {Long} compId 网点id
	 	 * @apiparam {Long} logMemberId 当前登录用户的memberId
	 	 * @apiGroup 人员管理
	 	 * @apiSampleRequest /memberInfo/querySiteMemberList
	 	 * @apiSuccess {Integer} auditingMemberNum 待审核人员总数
	 	 * @apiSuccess {Date} createTime 创建时间 
	 	 * @apiSuccess {Long} compId 网点id
	 	 * @apiSuccess {String} memberPhone 人员电话 
	 	 * @apiSuccess {String} memberName 人员姓名 
	 	 * @apiSuccess {Long} memberId 人员id
	 	 * @apiSuccess {Short} roleId 角色id -1: 后勤 0 : 收派员  1 : 大站长  2:店长 3：店员
	 	 * @apiSuccess {String} memberDetaileDisplay 人员头像 
	 	 * @apiError {String} result false
	 	 * @apiSuccessExample {json} Success-Response:
	 	 *     HTTP/1.1 200 OK
	 	 * "data": {
    	 *    "auditingMemberNum": 0,
     	 *   "memberList": [
      	 *      {
      	 *        "auditOpinion": "1",
      	 *        "compId": 122900080541696,
       	 *        "createTime": 1420358550000,
         *        "employeeWorkStatusFlag": 1,
         *        "memberId": 122903192715264,
         *        "memberName": "站3",
         *        "memberPhone": "13652123001",
         *        "memberSource": 1,
         *        "roleId": -2,
         *        "logId":"",
         *        "memberDetaileDisplay": "http://cas.okdit.net/nfs_data/mob/head/122899895992320.jpg"
         *    }
         *]
         *},
    	 *"success": true
    	 *}
	 	 * @apiErrorExample {json} Error-Response:
	 	 *     HTTP/1.1 200 OK
	 	 *   {"message":"Parameter is not correct or Parameter format error","success":false}
	 	 * @apiVersion 0.2.0
	 	 */
		@ResponseBody
		@RequestMapping(value = "/querySiteMemberList", method = { RequestMethod.POST, RequestMethod.GET })
		public String querySiteMemberList(Long compId, Long logMemberId) {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.querySiteMemberList.001", "compId不能为空！");
			}

			try {
				return this.memberInfoService.querySiteMemberList(compId, logMemberId);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/queryPendingAuditMemberList 查询当前站点待审核人员列表
		 * @apiPermission user
		 * @apiDescription 查询站点或营业分部下的待审核人员列表 kai.yang
		 * @apiparam {Long} compId 网点id
		 * @apiparam {Long} logMemberId 当前登录用户的memberId
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/queryPendingAuditMemberList
		 * @apiSuccess {Date} createTime 创建时间 
	 	 * @apiSuccess {Long} compId 网点id
	 	 * @apiSuccess {String} memberPhone 人员电话 
	 	 * @apiSuccess {String} memberName 人员姓名 
	 	 * @apiSuccess {Long} memberId 人员id
	 	 * @apiSuccess {Long} logId 审核表主键id
	 	 * @apiSuccess {Short} roleId 角色id -1: 后勤0 : 收派员1 : 大站长 
	 	 * @apiSuccess {String} memberDetaileDisplay 人员头像
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		  * "data":
     	 *    [
      	 *      {
      	 *        "auditOpinion": "1",
      	 *        "compId": 122900080541696,
       	 *        "createTime": 1420358550000,
         *        "employeeWorkStatusFlag": 1,
         *        "memberId": 122903192715264,
         *        "memberName": "站3",
         *        "memberPhone": "13652123001",
         *        "memberSource": 1,
         *        "roleId": -2,
         *        "logId": 1,
         *        "memberDetaileDisplay": "http://cas.okdit.net/nfs_data/mob/head/122899895992320.jpg"
         *    }
         *]
         *,
    	 *"success": true
    	 *}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/queryPendingAuditMemberList", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryPendingAuditMemberList(Long compId, Long logMemberId) {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.querySiteMemberList.001", "	不能为空！");
			}
			try {
				return this.memberInfoService.queryPendingAuditMemberList(compId, logMemberId);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/siteMemberToexamine 审核站点人员
		 * @apiPermission user
		 * @apiDescription 手机端添加的人员，在站点进行审核操作(通过/拒绝)kai.yang
		 * @apiparam {Long} logId 日志表id
		 * @apiparam {String} memberPhone 人员电话
		 * @apiparam {Long} userId 登陆人员的memberId
		 * @apiparam {Short} flag 标志 1通过2拒绝
		 * @apiparam {Long} compId 网点id
		 * @apiparam {String} memberName 人员姓名
		 * @apiparam {Long} memberId 人员id
		 * @apiparam {String} refuseDesc 拒绝原因 可以为空
		 * @apiparam {String} areaColor 片区颜色
		 * @apiparam {Short} roleId 角色id -1: 后勤0 : 收派员1 : 大站长 
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/siteMemberToexamine
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":"","success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/siteMemberToexamine", method = { RequestMethod.POST, RequestMethod.GET })
		public String siteMemberToexamine(Long logId,String memberPhone, Long userId, Short flag, Long compId,
				String memberName, Long memberId, String refuseDesc, String areaColor, Short roleId) {
			if(PubMethod.isEmpty(logId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.001", "logId不能为空！");
			}
			if(PubMethod.isEmpty(userId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.002", "userId不能为空！");
			}
			if(PubMethod.isEmpty(flag)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.003", "flag不能为空！");
			}
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.004", "compId不能为空！");
			}
			if(PubMethod.isEmpty(memberName)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.005", "memberName不能为空！");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.006", "memberId不能为空！");
			}
	/*		if(PubMethod.isEmpty(areaColor)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.008", "areaColor不能为空！");
			}*/
			if(PubMethod.isEmpty(roleId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.doAuditMember.009", "roleId不能为空！");
			}
			try {
				this.memberInfoService.siteMemberToexamine(logId, memberPhone,userId, flag, compId, memberName, memberId,
						refuseDesc, areaColor, roleId);
				return jsonSuccess(null);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/insertMemberInfo 添加站点人员
		 * @apiPermission user
		 * @apiDescription 添加站点人员kai.yang
		 * @apiparam {Long} compId 网点id
		 * @apiparam {String} associatedNumber 手机号
		 * @apiparam {String} memberName 人员姓名
		 * @apiparam {Short} roleId 注册角色id -1: 后勤0 : 收派员1 : 大站长 
		 * @apiparam {String} areaColor 片区颜色
		 * @apiparam {Long} userId 审核人
		 * @apiparam {Short} memberSource 来源 0手机端1站点
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/insertMemberInfo
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":"","success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/insertMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public String insertMemberInfo(Long compId, String associatedNumber, String memberName,
				Short roleId, String areaColor, Long userId, Short memberSource) {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.001", "compId不能为空！");
			}
			if(PubMethod.isEmpty(associatedNumber)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.002", "associatedNumber不能为空！");
			}
			if(PubMethod.isEmpty(memberName)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.003", "memberName不能为空！");
			}
			if(PubMethod.isEmpty(roleId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.004", "roleId不能为空！");
			}
			if(PubMethod.isEmpty(userId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.005", "userId不能为空！");
			}
			if(PubMethod.isEmpty(memberSource)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.006", "memberSource不能为空！");
			}
			if(memberSource !=0 &&memberSource !=1){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.007", "memberSource参数错误！");
			}
			if(roleId==1 || roleId==2){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.008", "roleId参数错误！");
			}
			try {
				return this.memberInfoService.insertMemberInfo(compId, associatedNumber, memberName, roleId,
						areaColor, userId, memberSource);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/queryMemberInfoById 查询人员详细信息
		 * @apiPermission user
		 * @apiDescription 查询人员详细信息 kai.yang
		 * @apiparam {Long} memberId 人员id
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/queryMemberInfoById
		 * @apiSuccess {String} areaColor 片区颜色
		 * @apiSuccess {Short} employeeWorkStatusFlag 工作状态 1．在岗2．下班3．休假4．任务已满，暂不接单
		 * @apiSuccess {Long} memberId 人员id
		 * @apiSuccess {String} memberName 人员姓名
		 * @apiSuccess {String} memberPhone 人员手机号
		 * @apiSuccess {Short} memberSource 人员来源 0手机端1站点
		 * @apiSuccess {Short} roleId 角色id -1: 后勤0 : 收派员1 : 大站长 
		 * @apiSuccess {String} memberDetaileDisplay 人员头像
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 * {
		 *"data": {
		 *    "addressType": "",
		 *   "areaColor": "#c2c2c2",
		 *   "associatedNumber": "",
		 *   "birthday": "",
		 *   "compId": 169976168497152,
		 *   "createTime": 1442804694000,
		 *   "employeeWorkStatusFlag": 1,
		 *   "erpCustomerId": "",
		 *   "gender": "",
		 *   "idNum": "",
		 *   "memberDetaileDisplay": "http://cas.okdit.net/nfs_data/mob/head/169976168497152.jpg",
		 *   "memberDetailedAddress": "",
		 *   "memberId": 169976168497152,
		 *   "memberName": "男王磊",
		 *   "memberPhone": "13521283934",
		 *   "memberSort": "",
		 *   "memberSource": 1,
		 *   "num": "",
		 *   "referralsId": "",
		 *   "registFlag": "",
		 *   "roleId": 1,
		 *   "verifFlag": ""
		 *},
    	 * "success": true
   		 *}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/queryMemberInfoById", method = { RequestMethod.POST, RequestMethod.GET })
		public String queryMemberInfoById(Long memberId) {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.getMemberInfoById.001", "memberId不能为空！");
			}
			try {
				return this.memberInfoService.queryMemberInfoById(memberId);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/updateMemberInfo 修改人员信息
		 * @apiPermission user
		 * @apiDescription 修改人员信息 kai.yang
		 * @apiparam {Long} userId 操作人id(当前站长id)
		 * @apiparam {Long} compId 网点id
		 * @apiparam {Long} memberId 人员id
		 * @apiparam {Short} roleId 角色id -1:后勤   0:收派员    1:站长 2：店长
		 * @apiparam {Short} employeeWorkStatusFlag 工作状态标识  ' 1:在岗,2:下班,3:休假,4:任务已满暂不接单', 只能传1和2
		 * @apiparam {String} areaColor 片区颜色
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/updateMemberInfo
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":"","success":true}
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		@ResponseBody
		@RequestMapping(value = "/updateMemberInfo", method = { RequestMethod.POST, RequestMethod.GET })
		public String updateMemberInfo(Long compId, Long memberId, Short roleId,
				Short employeeWorkStatusFlag, String areaColor,Long userId) {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.001", "compId不能为空！");
			}
			if(PubMethod.isEmpty(userId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.002", "userId不能为空！");
			}
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.003", "memberId不能为空！");
			}
			if(PubMethod.isEmpty(roleId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.004", "roleId不能为空！");
			}
			if(PubMethod.isEmpty(employeeWorkStatusFlag)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.005", "employeeWorkStatusFlag不能为空！");
			}
			if(PubMethod.isEmpty(areaColor)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.updateMemberInfo.006", "areaColor不能为空！");
			}
			Map<String, Object> dataMap = new HashMap<String, Object>();
			try {
				return this.memberInfoService.updateMemberInfo(compId, memberId, roleId,
						employeeWorkStatusFlag, areaColor,userId);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		/**
		 * @api {post} /memberInfo/deleteSiteMember 站点添加的收派员的删除
		 * @apiPermission user
		 * @apiDescription 站点添加的收派员的删除操作(包括手机端的离职接口 ) kai.yang
		 * @apiparam {Long} userId 操作人id
		 * @apiparam {Long} memberId 人员id
		 * @apiparam {Long} compId 站点id
		 * @apiparam {String} memberName 人员姓名
		 * @apiparam {String} memberPhone 人员手机号
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/deleteSiteMember
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *   {"data":"{"isFinish":1,"isSendFinish":1,"isTaskFinish":1}","success":true}
		 *    isFinish:大于1有任务未完成   0无未完成任务
		 *    isSendFinish:1.有待派包裹 
		 *    isTaskFinish:1有任务未完成
		 *    if(isFinish>=1){
		 *    	有任务未完成
		 *    }else{
		 *    	if(isSendFinish==1){
		 *    		有待派包裹
		 *    	}else if(isTaskFinish==1){
		 *    		有任务未完成
		 *    	}else if(isFinish==0){
		 *    		删除成功
		 *    	}
		 *    }
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/deleteSiteMember", method = { RequestMethod.POST, RequestMethod.GET })
		public String deleteSiteMember(Long userId,Long memberId, Long compId, String memberName, String memberPhone) {
			if(PubMethod.isEmpty(memberId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.001", "memberId不能为空！");
			}
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.002", "compId不能为空！");
			}
//			if(PubMethod.isEmpty(memberName)){
//				return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.003", "memberName不能为空！");
//			}
			if(PubMethod.isEmpty(memberPhone)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.deleteMemberInfo.004", "memberPhone不能为空！");
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
					return this.memberInfoService.deleteSiteMember(userId,memberId, compId, memberName, memberPhone);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
		
		/**
		 * @api {post} /memberInfo/checkForExistence 检查人员是否重复或存在
		 * @apiPermission user
		 * @apiDescription 检查人员是否重复或存在kai.yang
		 * @apiparam {Long} compId 网点id
		 * @apiparam {String} associatedNumber 手机号
		 * @apiGroup 人员管理
		 * @apiSampleRequest /memberInfo/checkForExistence
		 * @apiError {String} result false
		 * @apiSuccessExample {json} Success-Response:
		 *     HTTP/1.1 200 OK
		 *  {"data":{"flag":2},"success":true} 
		 *  flag = 2  数据库中不存在这个手机号 flag = 1 本站点存在 flag = 5 非本站点存在
		 * @apiErrorExample {json} Error-Response:
		 *     HTTP/1.1 200 OK
		 *   {"message":"Parameter is not correct or Parameter format error","success":false}
		 * @apiVersion 0.2.0
		 */
		
		@ResponseBody
		@RequestMapping(value = "/checkForExistence", method = { RequestMethod.POST, RequestMethod.GET })
		public String checkForExistence(Long compId, String associatedNumber) {
			if(PubMethod.isEmpty(compId)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.001", "compId不能为空！");
			}
			if(PubMethod.isEmpty(associatedNumber)){
				return paramsFailure("net.okdi.api.controller.MemberInfoController.addMemberInfo.002", "associatedNumber不能为空！");
			}
			try {
				return this.memberInfoService.checkForExistence(compId, associatedNumber);
			} catch (Exception e) {
				e.printStackTrace();
				return jsonFailure(e);
			}
		}
}

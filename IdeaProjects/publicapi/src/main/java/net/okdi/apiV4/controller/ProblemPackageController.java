package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.ProblemPackageService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/problemPackage")
public class ProblemPackageController extends BaseController{

	@Autowired
	private ProblemPackageService problemPackageService;
	
	/**
	 * @author jianxin.ma
	 * @api {post} /problemPackage/queryProblemPackageList 问题件--查询异常件列表
	 * @apiVersion 0.2.0
	 * @apiDescription 问题件--查询异常件列表
	 * @apiGroup 新版-派件V4
	 * @apiParam {Long} actualSendMember 实际派件人id
	 * @apiSampleRequest /problemPackage/queryProblemPackageList
	 * @apiSuccess {String} addresseeAddress  异常包裹地址
	 * @apiSuccess {String} addresseeMobile  收件人电话
	 * @apiSuccess {String} callBackStatus  群发通知回执状态 0.未知 1.成功 2.失败 3.呼叫未知 4.呼叫成功 5.呼叫失败
	 * @apiSuccess {String} sendSmsStatus  群发通知状态 0.发送成功 1.呼叫成功 2.重复 3.呼叫失败 4.发送失败 5.用户退订 
	 * @apiSuccess {String} replyStatus 回复状态      0.未回复  1.客户回复  2.短信已读
	 * @apiSuccess {String} sendSmsType  群发通知类型：2.电话优先 3.仅电话 1.仅短信
	 * @apiSuccess {String} errorMessage  异常信息
	 * @apiSuccess {String} parcelNum  包裹编号
	 * @apiSuccess {String} uid  包裹id
	 * @apiSuccess {String} problemCount  问题件数量
	 * @apiSuccess {String} ifFocusWeChat  是否关注过好递微信公众号 0否1是
	 * @apiSuccessExample Success-Response:
	   {
		   "data":
			   [
				   {
					   "addresseeAddress":"环星大厦",
					   "addresseeMobile":"13356893955",
					   "callBackStatus":"",
					   "errorMessage":"超出收派范围",
					   "parcelNum":"",
					   "sendSmsStatus":"",
					   "sendSmsType":"",
					   "uid":209478440493056
					   "problemCount":2
					   "replyStatus":0
				   }
			   ],
		   "success":true
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
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"actualSendMember不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/queryProblemPackageList" ,method={RequestMethod.GET,RequestMethod.POST})
	public String queryProblemPackageList(Long actualSendMember) {
		try {
			if(PubMethod.isEmpty(actualSendMember)){
				return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.queryProblemPackageList.001", "actualSendMember不能为空");
			}
			 return problemPackageService.queryProblemPackageList(actualSendMember); //问题件的派件包裹和地址 
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author jianxin.ma
	 * @api {post} /problemPackage/probPackAssignAgain 问题件-异常未签收--重派
	 * @apiVersion 0.2.0
	 * @apiDescription 问题件-异常未签收--重派
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} parIds 包裹Id,多个用逗号隔开
	 * @apiParam {Long} memberId 操作人memberId
	 * @apiParam {String} memberPhone 操作人手机号
	 * @apiSampleRequest /problemPackage/probPackAssignAgain
	 * @apiSuccessExample Success-Response:
		{"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"parIds不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/probPackAssignAgain" , method={RequestMethod.GET,RequestMethod.POST})
	public String probPackAssignAgain(String parIds,Long memberId,String memberPhone){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.01", "parIds不能为空!");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.02", "memberId不能为空!");
		}
		if(PubMethod.isEmpty(memberPhone)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackAssignAgain.03", "memberPhone不能为空!");
		}
		try {
			return problemPackageService.probPackAssignAgain(parIds,memberId,memberPhone);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

	/**
	 * @author jianxin.ma
	 * @api {post} /problemPackage/probPackBackComp 问题件-异常未签收-退回站点(批量)
	 * @apiVersion 0.2.0
	 * @apiDescription 问题件-异常未签收-退回站点(批量)
	 * @apiGroup 新版-派件V4
	 * @apiParam {String} parIds 包裹Id,多个用逗号隔开
	 * @apiSampleRequest /problemPackage/probPackBackComp
	 * @apiSuccessExample Success-Response:
		{"success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"parIds不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/probPackBackComp" , method={RequestMethod.GET,RequestMethod.POST})
	public String probPackBackComp(String parIds){
		if(PubMethod.isEmpty(parIds)){
			return paramsFailure("net.okdi.apiV4.controller.ProblemPackageController.probPackBackComp.01", "parIds不能为空!");
		}
		try {
			return problemPackageService.probPackBackComp(parIds);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}

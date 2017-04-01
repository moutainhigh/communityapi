package net.okdi.apiV2.controller;

import net.okdi.apiV2.service.InvitedRecordService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/inVitedRecord")
public class InvitedRecordController extends BaseController{
	@Autowired
	private InvitedRecordService invitedRecordService;
	/**
	 * @author 韩爱军
	 * @api {post} /inVitedRecord/queryInVitedRecord 邀请记录列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询邀请记录列表 -韩爱军
	 * @apiGroup 邀请
	 * @apiParam {String} memberId 登录人memberId 不能为null
	 * @apiParam {String} phone 	被邀请者手机号(模糊搜索可为null)
	 * @apiparam {Integer} pageNo  页码(从1开始，不传默认为1)
	 * @apiparam {Integer} pageSize 每页数量(不传默认为10)
	 * @apiSampleRequest /inVitedRecord/queryInVitedRecord
	 * @apiSuccess {String} netName  网络名称
	 * @apiSuccess {String} phone  	  被邀请者手机号
	 * @apiSuccess {String} rewardStatus  奖励状态 1:已奖励  0:无奖励
	 * @apiSuccess {String} status  状态    0：未注册 1：完成认证  2:认证中 3:已注册
	 * @apiSuccess {String} time    时间
	 * @apiSuccessExample Success-Response:
	{
    "data": [
        {
            "netName": "宅急送",
            "phone": "18633333300",
            "rewardStatus": 0,
            "status": 3,
            "time": "注册时间:2015-12-03 15:41:14"
        },
        {
            "netName": "百世汇通",
            "phone": "18633333301",
            "rewardStatus": 0,
            "status": 3,
            "time": "注册时间:2015-12-03 15:59:38"
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
	@RequestMapping(value = "/queryInVitedRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryInVitedRecord(String memberId, String phone, Integer pageNo,Integer pageSize){
		System.out.println("查询邀请记录的接口==================进来了====================queryInVitedRecord");
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramError("net.okdi.apiV2.controller.InvitedRecordController","memberId不能为空!");
		}
		if(PubMethod.isEmpty(pageNo)){
			pageNo=1;
		}
		if(PubMethod.isEmpty(pageSize)){
			pageSize=10;
		}
		try {
			return invitedRecordService.invitedRecord(memberId, phone, pageNo, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * @author mjx
	 * @api {post} /inVitedRecord/wechatInviteList 查询邀请关注微信公众号记录列表
	 * @apiVersion 0.2.0
	 * @apiDescription 查询邀请关注微信公众号记录列表 -mjx
	 * @apiGroup 邀请
	 * @apiParam {Long} memberId 登录memberId   不能为null
	 * @apiParam {String} searchPhone 搜索手机号
	 * @apiParam {Integer} currentPage 当前页
	 * @apiParam {Integer} pageSize 每页显示条数
	 * @apiSampleRequest /inVitedRecord/wechatInviteList
	 * @apiSuccess {String} inviteState  邀请状态 0 失败 1成功
	 * @apiSuccess {String} inviteTime  邀请时间
	 * @apiSuccess {String} inviteeMemberId  被邀请者memberId
	 * @apiSuccess {String} inviteePhone  被邀请者手机号
	 * @apiSuccessExample Success-Response:
			{
		    "data": [
		        {
		            "inviteState": 1,
		            "inviteTime": "07/22 10:42",
		            "inviteeMemberId": 225237279227904,
		            "inviteePhone": "15066693661"
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
	@RequestMapping(value = "/wechatInviteList", method = {RequestMethod.POST , RequestMethod.GET })
	public String wechatInviteList(Long memberId, String searchPhone, Integer currentPage, Integer pageSize){
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramError("net.okdi.apiV2.controller.wechatInviteList","memberId不能为空!");
		}
		if(PubMethod.isEmpty(currentPage)){
			return PubMethod.paramError("net.okdi.apiV2.controller.wechatInviteList","currentPage不能为空!");
		}
		if(PubMethod.isEmpty(pageSize)){
			return PubMethod.paramError("net.okdi.apiV2.controller.wechatInviteList","pageSize不能为空!");
		}
		try {
			return invitedRecordService.wechatInviteList(memberId,searchPhone,currentPage,pageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
}

package net.okdi.apiV2.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV2.service.SendReceiveCountService;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sendReceiveCount")
public class SendReceiveCountController extends BaseController {

	@Autowired
	private SendReceiveCountService sendReceiveCountService;
	
	
	/**
	 * @author 郑炯
	 * @api {post} /sendReceiveCount/SendReceiveCount 快递圈查询取件派件数量
	 * @apiVersion 0.2.0
	 * @apiDescription 查询取件派件数量-郑炯
	 * @apiGroup 快递圈-群
	 * @apiparam {String} memberId 登录人memberId
	 * @apiparam {String} roleId 登录人角色
	 * @apiparam {String} compId 登录人公司
	 * @apiSampleRequest /sendReceiveCount/SendReceiveCount
	 * @apiSuccess {String} receiveCount: 取件数量
	 * @apiSuccess {String} sendCount: 派件数量
	 * @apiSuccess {String} attentionCount: 关注数量
	 * @apiSuccess {String} fansCount: 粉丝数量
	 * @apiSuccessExample Success-Response:
		{
	    "data": {
	        "receiveCount": 1,
	        "sendCount": 11
	        "attentionCount": 11
	        "fansCount": 11
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
	@RequestMapping(value = "/SendReceiveCount", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySendReceiveCount(String memberId, String roleId, String compId){
		
		String result = sendReceiveCountService.querySendReceiveCount(memberId, roleId, compId);
		return result;
	}
	
	
	/**
	 * @author 郑炯
	 * @api {post} /sendReceiveCount/queryPhoneNameByMemberId 查询客户姓名和手机号,查询派件姓名和手机号
	 * @apiVersion 0.2.0
	 * @apiDescription 查询客户姓名和手机号,查询派件姓名和手机号-郑炯
	 * @apiGroup 好递快递员
	 * @apiparam {Long} memberId 登录人memberId
	 * @apiSuccess {String} phone-name : 15366666666-王子,{手机号-姓名}(但有可能姓名为空{就直接给了一个""})
	 * @apiSampleRequest /sendReceiveCount/queryPhoneNameByMemberId
	 * @apiSuccessExample Success-Response:
		{
		    "data": [
		        "18640016307-张雨茵",
		        "18510813031-王二明",
		        "15366666666-王子",
		        "13130291296-饿迷糊",
		        "18640016307-"
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
	@RequestMapping(value = "/queryPhoneNameByMemberId", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryPhoneNameByMemberId(Long memberId){
		
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("001", "memberId 不能为空!!!");
		}
		String list = sendReceiveCountService.queryPhoneNameByMemberId(memberId);
		return list;
	}
	
}

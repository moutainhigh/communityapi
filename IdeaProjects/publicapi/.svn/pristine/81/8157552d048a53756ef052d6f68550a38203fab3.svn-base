package net.okdi.apiV2.controller;

import net.okdi.apiV2.service.SmsStatusChangeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("SmsStatusChangeController")
@RequestMapping("smsStatusChange")
public class SmsStatusChangeController extends BaseController{
	Logger logger = Logger.getLogger(SmsStatusChangeController.class);
	
	@Autowired
	private SmsStatusChangeService smsStatusChangeService;
	/**
	 * @api {post} /smsStatusChange/unReply 更改客户回复和取件状态为已读
	 * @apiPermission user
	 * @apiDescription  群发送至 kai.yang
	 * @apiparam {String} msgId 短信id
	 * @apiGroup 通知记录
	 * @apiSampleRequest /smsStatusChange/unReply
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   	
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("unReply")
	public String unReply(String msgId,Short flag,Long memberId){
		if(PubMethod.isEmpty(msgId)){
			return paramsFailure("net.okdi.apiV2.controller.SmsStatusChangeController.unRead", "msgId不能为空");
		}
/*		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("net.okdi.apiV2.controller.SmsStatusChangeController.memberId", "memberId不能为空");
		}*/
		String result="";
		try {
			result=this.smsStatusChangeService.unReply(msgId,flag,memberId);
		} catch (Exception e) {
			logger.info(e);
			jsonFailure(e);
		}
		return jsonSuccess(result);
	}
	/**
	 * @api {post} /smsStatusChange/unRead 更改短信状态为已读
	 * @apiPermission user
	 * @apiDescription  群发送至 kai.yang
	 * @apiparam {String} msgId 短信id
	 * @apiGroup 通知记录
	 * @apiSampleRequest /smsStatusChange/unRead
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   	
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("unRead")
	public String unRead(String msgId,Short flag){
		if(PubMethod.isEmpty(msgId)){
			return paramsFailure("net.okdi.apiV2.controller.SmsStatusChangeController.unRead", "msgId不能为空");
		}
		String result="";
		try {
			result=this.smsStatusChangeService.unRead(msgId,flag);
		} catch (Exception e) {
			logger.info(e);
			jsonFailure(e);
		}
		return jsonSuccess(result);
	}
}

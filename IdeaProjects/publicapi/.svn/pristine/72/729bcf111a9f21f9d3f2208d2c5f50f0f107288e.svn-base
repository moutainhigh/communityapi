package net.okdi.apiV3.controller;

import net.okdi.apiV3.service.SmallBellService;
import net.okdi.apiV3.service.WechatMpService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechatmp")
public class WechatMpController extends BaseController{

	private @Autowired WechatMpService wechatMpService;
	private @Autowired SmallBellService smallBellService;
	
	/**
	 * @author hang.yu
	 * @api {post} /wechatmp/send 快递员发送微信消息 (从app提配中对任务进行 同意或拒绝操作调用)
	 * @apiVersion 0.2.0
	 * @apiDescription 快递员发送微信消息
	 * @apiGroup 说明
	 * @apiParam {String} openId 关注公众号的微信用户id
	 * @apiParam {String} site 站点[可选]
	 * @apiParam {String} courierName 快递员姓名
	 * @apiParam {String} phone 快递员手机号
	 * @apiParam {Integer} status 0: 拒收  1:接单
	 * @apiParam {String} reason 拒收时：拒收原因, 接单时：对用户说的话.这时可为空
	 * @apiParam {String} taskId  取件任务Id
	 * @apiParam {String} memberId  收派员ID
	 * @apiSampleRequest /wechatmp/send
	 * @apiSuccessExample Success-Response:
	    {"data": "ok", "success":true}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"invalid openid",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/send", method={ RequestMethod.GET, RequestMethod.POST })
	public String send(String openId, String site, String courierName, String phone, String status, String reason,String taskId) {
		try {
			
		if (PubMethod.isEmpty(openId)) {
			return paramsFailure("001", "openId不能为空");
		}
		if (PubMethod.isEmpty(courierName)) {
			return paramsFailure("002", "courierName不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return paramsFailure("003", "phone不能为空");
		}
		if (PubMethod.isEmpty(status) || !"0,1".contains(status)) {
			return paramsFailure("004", "status不合法");
		}
		if (PubMethod.isEmpty(reason) && "0".equals(status)) {
			return paramsFailure("005", "reason不能为空");
		}
		if (PubMethod.isEmpty(taskId)) {
			return paramsFailure("006", "taskId不能为空");
		}
		return jsonSuccess(wechatMpService.send(openId, site, courierName, phone, status, reason ,taskId));
		} catch (Exception e) {
			return paramsFailure();
		}
	}
	
	
	
	/**
	 * @author yangkai
	 * @api {post} /wechatmp/rob 快递员发送微信消息叫快递抢单
	 * @apiVersion 0.2.0
	 * @apiDescription 叫快递抢单
	 * @apiGroup 说明
	 * @apiParam {Long} memberId 用户ID
	 * @apiParam {String} netName 用户站点
	 * @apiParam {String} netId 用户站点id
	 * @apiParam {String} phone 快递员手机号
	 * @apiParam {String} name 快递员姓名
	 * @apiParam {Long} taskId 任务id
	 * @apiSampleRequest /wechatmp/rob
	 * @apiSuccessExample Success-Response:
	   {"success":true}
	
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value = "/rob", method = {RequestMethod.GET , RequestMethod.POST })
	public String rob(Long memberId, String netName, String netId, String phone, String name, Long taskId) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		/*if (PubMethod.isEmpty(netName)) {
			return paramsFailure("002", "netName不能为空");
		}
		if (PubMethod.isEmpty(netId)) {
			return paramsFailure("003", "netId不能为空");
		}*/
		if (PubMethod.isEmpty(phone)) {
			return paramsFailure("002", "phone不能为空");
		}
		if (PubMethod.isEmpty(name)) {
			return paramsFailure("003", "name不能为空");
		}
		if (PubMethod.isEmpty(taskId)) {
			return paramsFailure("004", "taskId不能为空");
		}
		return smallBellService.wechatRob(memberId, netName, netId, phone, name, taskId);
	}
	
	
}



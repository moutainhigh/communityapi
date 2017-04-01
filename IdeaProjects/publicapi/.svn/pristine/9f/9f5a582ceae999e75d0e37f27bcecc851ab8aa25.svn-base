package net.okdi.apiV5.controller;

import net.okdi.apiV3.service.SmallBellService;
import net.okdi.apiV4.service.WrongPriceService;
import net.okdi.apiV5.service.NewWrongPriceService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;


@Controller("newWrongPriceController")
@RequestMapping("/newWrongPrice")
public class NewWrongPriceController extends BaseController{
	
	Logger logger = Logger.getLogger(NewWrongPriceController.class);
	@Autowired
	NewWrongPriceService newWrongPriceService;

	@Autowired
	SmallBellService bellService;
	/**
	 * @author jiong.zheng
	 * @api {post} /newWrongPrice/newWrongResend 问题件重发接口
	 * @apiVersion 5.0.0
	 * @apiDescription 问题件重发接口
	 * @apiGroup 新版-短信
	 * @apiParam {Long} memberId 用户ID(不能为空)
	 * @apiParam {String} msgIds 普通msgId集合 用"-"拼起来
	 * @apiParam {String} aliMsgIds 淘宝单msgId集合 用"-"拼起来
	 * @apiParam {String} accountId 钱包id (不能为空)
	 * @apiParam {String} memberPhone 快递员手机号 (不能为空)
	 * @apiparam {String} version 版本号 如5.0.0
	 * @apiparam {String} system 操作系统  安卓 传 Android, 苹果  传 IOS(不能为空)
	 * @apiSampleRequest /newWrongPrice/newWrongResend
	 * @apiSuccessExample Success-Response:
	    {"cause":"发送成功","flag":"","success":true}
	 * @apiErrorExample Error-Response:
	 *    {"errCode":0,
	 *    "errSubcode":"001",
	 *    "message":"memberId不能为空",
	 *    "success":false}
	 */
	@ResponseBody
	@RequestMapping(value="/newWrongResend",method={RequestMethod.POST, RequestMethod.GET})
	public String newWrongResend(Long memberId, String msgIds, String aliMsgIds, String version, 
			String system, String accountId,String memberPhone) {
		logger.info("新版问题件重发接口********memberId: "+memberId+" ,普通单msgIds: "+msgIds+" ,淘宝单aliMsgIds: "
			+aliMsgIds+" ,version:"+version+" ,system: "+system+" ,accountId: "+accountId+" ,memberPhone: "+memberPhone);
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("001", "memberId不能为空");
		}
		if (PubMethod.isEmpty(msgIds) && PubMethod.isEmpty(aliMsgIds)) {
			return paramsFailure("002", "普通单或者菜鸟单不能同时为空");
		}
		if (PubMethod.isEmpty(msgIds) && PubMethod.isEmpty(aliMsgIds)) {
			return paramsFailure("002", "普通单或者菜鸟单不能同时为空");
		}
		if (PubMethod.isEmpty(accountId)) {
			return paramsFailure("003", "accountId不能为空");
		}
		if (PubMethod.isEmpty(memberPhone)) {
			return paramsFailure("004", "memberPhone不能为空");
		}
		try {
			String result="";
			String aliResult="";
			if(!PubMethod.isEmpty(msgIds)){
				result = newWrongPriceService.resend(memberId, msgIds, version, system, accountId, memberPhone);
			}
			if(!PubMethod.isEmpty(aliMsgIds)){
				aliResult = newWrongPriceService.aliResend(memberId, aliMsgIds, version, system, accountId, memberPhone);
			}
			//先放这吧,有问题在判断去
			if(!PubMethod.isEmpty(result)){
				JSONObject parseObject = JSONObject.parseObject(result);
			}
			System.out.println("普通问题件发送返回的结果result:"+result+" ,淘宝单发送返回的结果aliResult: "+aliResult);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
		
	}
	
}

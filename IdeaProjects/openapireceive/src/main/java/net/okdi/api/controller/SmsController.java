package net.okdi.api.controller;

import net.okdi.api.service.SendNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * 短信
 * 
 * @author feng.wang
 * @version V1.0
 */
@Controller
@RequestMapping("/sms")
public class SmsController extends BaseController {

	@Autowired
	private SendNoticeService sendNoticeService;
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>呼叫快递哥发送短信</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午16:05:14</dd>
	 * @param mobile  快递哥手机号
	 * @return
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/callExp", method = {RequestMethod.GET,RequestMethod.POST})
	public String callExp(String mobile) {
		if (PubMethod.isEmpty(mobile)) {
			return paramsFailure("SmsController.callExp.mobile", "手机号为必填项！");
		}
		try {
			this.sendNoticeService.callExp(mobile);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>接单王免费短信日志记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xiangwei.liu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-19 下午16:05:14</dd>
	 * @param memberPhone  收派员电话
	 * @param receiver_mobile 收件人手机号,多个用,好分隔
	 * @param sms_source  短信来源 0:接单王 1:个人端
	 * @param phone_type  手机类型 0:Android 1:Ios
 	 * @return	{"success","true"}
	 * @since v1.0
	*/
	
	@ResponseBody
	@RequestMapping(value = "/sendSmsAudit", method = {RequestMethod.GET,RequestMethod.POST})
	public String sendSmsAudit(String memberPhone, String receiver_mobile , Short sms_source , Short phone_type, Short sendSuccess , String sendVersion) {
		try {
			if (PubMethod.isEmpty(memberPhone)) {
				return paramsFailure("SmsController.sendSmsAudit.memberPhone", "收派员手机号为必填项！");
			}
			if (PubMethod.isEmpty(receiver_mobile)) {
				return paramsFailure("SmsController.sendSmsAudit.receiver_mobile", "收件人手机号为必填项！");
			}
			this.sendNoticeService.sendSmsAudit(memberPhone, receiver_mobile, sms_source, phone_type,sendSuccess,sendVersion);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return  jsonFailure(e);
		}
	}
	
}

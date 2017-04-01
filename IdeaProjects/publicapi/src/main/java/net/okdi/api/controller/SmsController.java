package net.okdi.api.controller;

import net.okdi.api.service.SmsService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


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
	private SmsService smsService;
	
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>呼叫快递哥发送短信</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-1-17 下午18:05:14</dd>
	 * @param mobile  快递哥手机号
	 * @return
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/callExp", method = {RequestMethod.GET,RequestMethod.POST})
	public String callExp(String mobile) {
		if (PubMethod.isEmpty(mobile)) {
			return PubMethod.paramsFailure();
		}
		try {
			String result=this.smsService.callExp(mobile);
			if(PubMethod.isEmpty(result)){
				return PubMethod.sysError();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}
	
}

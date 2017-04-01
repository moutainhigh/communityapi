package net.okdi.api.controller;



import net.okdi.api.service.SmsInterceptionService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/smsInterception")
public class SmsInterceptionController extends BaseController {
	
	@Autowired
	private SmsInterceptionService smsInterceptionService;
	

	// 更新错误类型
	@ResponseBody
	@RequestMapping(value = "/updateErrorType", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String updateErrorType(Long memberId, Integer disableType) {
		try {
			return jsonSuccess(this.smsInterceptionService.updateErrorType(memberId, disableType));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}

}

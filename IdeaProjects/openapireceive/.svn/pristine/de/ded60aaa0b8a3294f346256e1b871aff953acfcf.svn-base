package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.WechatJsApiTicketService;
import net.okdi.apiV2.service.WechatMpService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/wechatmp")
public class WechatMpController extends BaseController{

	private @Autowired WechatMpService wechatMpService;
	private @Autowired WechatJsApiTicketService wechatJsApiTicketService;
	
	private static final Logger logger = LoggerFactory.getLogger(WechatMpController.class);
	@ResponseBody
	@RequestMapping(value="/send", method={ RequestMethod.GET, RequestMethod.POST })
	public String send(String openId, String site, String courierName, String phone, Integer status, String reason ,Long taskId) {
		/*if (PubMethod.isEmpty(openId)) {
			return paramsFailure("001", "openId不能为空");
		}*/
		
		if (PubMethod.isEmpty(courierName)) {
			return paramsFailure("002", "courierName不能为空");
		}
		if (PubMethod.isEmpty(phone)) {
			return paramsFailure("003", "phone不能为空");
		}
		if (PubMethod.isEmpty(status) || !"0,1".contains(String.valueOf(status))) {
			return paramsFailure("004", "status不合法");
		}
		if (PubMethod.isEmpty(reason) && status == 0) {
			return paramsFailure("005", "reason不能为空");
		}
		if (PubMethod.isEmpty(taskId)) {
			return paramsFailure("006", "taskId不能为空");
		}
		try {
			return wechatMpService.send(openId, site, courierName, phone, status, reason ,taskId);
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		} 
	}
	/**
	 * 
	 * @Description: 微信端获取签名
	 * @return String
	 * @throws
	 * @author jianxin.ma
	 * @date 2016-4-7
	 */
	@ResponseBody
	@RequestMapping(value="/getEcdsa", method={ RequestMethod.GET, RequestMethod.POST })
	public String getEcdsa() {
		try {
			Map<String,Object> map=wechatJsApiTicketService.getEcdsa();
			return jsonSuccess(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("获得微信签名失败==================================");
			return jsonFailure();
		}
	}
}




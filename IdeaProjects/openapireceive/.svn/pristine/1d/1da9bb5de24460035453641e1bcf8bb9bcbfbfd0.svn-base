package net.okdi.apiV4.controller;

import net.okdi.apiV4.service.MessageInfoService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公告信息
 * @author jingguoqiang
 * @version V1.0
 */
@Controller
@RequestMapping("/messageInfo")
public class MessageInfoController extends BaseController {
	
	@Autowired 
	MessageInfoService messageInfoService;

	/**
	 * 查询最新公告时间
	 */
	@ResponseBody
	@RequestMapping(value = "/queryMessageTime", method = { RequestMethod.POST, RequestMethod.GET })
	public String queryMessageTime() {
		try {
			return jsonSuccess(messageInfoService.queryMessageTime());
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}
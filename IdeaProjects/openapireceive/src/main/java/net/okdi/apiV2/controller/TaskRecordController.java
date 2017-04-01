package net.okdi.apiV2.controller;

import java.util.Map;

import net.okdi.apiV2.service.TaskRecordService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author jingguoqiang
 * @desc  取派件和通知数量查询类
 */
@Controller
@RequestMapping("/taskRecord")
public class TaskRecordController extends BaseController {

	@Autowired
	private TaskRecordService tcaskRecordService;

	/**
	 * 今天取件数量
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryReceiveCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String queryReceiveCount(String memberId) {
		
		try {
			int count = tcaskRecordService.queryReceiveCount(memberId);
			return count+"";
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	/**
	 * 今天派件数量
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link String}
	 * @param memberId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/querySendCount", method = { RequestMethod.POST,RequestMethod.GET })
	public String querySendCount(String memberId) {
		
		try {
			int count = tcaskRecordService.querySendCount(memberId);
			return count+"";
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}
	
	

	
}

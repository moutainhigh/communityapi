package net.okdi.api.controller;

import net.okdi.api.service.SendNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 消息推送
 * 
 * @author feng.wang
 * @version V1.0
 */
@Controller
@RequestMapping("/notice")
public class SendNoticeController extends BaseController {

	@Autowired
	private SendNoticeService sendNoticeService;

	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>分配包裹给收派员推送消息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-12-17 上午11:12:53</dd>
	 * @param memberId 收派员ID
	 * @param mob 收派员手机号
	 * @param parNum 包裹数量
	 * @return {"success":true}
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>SendNoticeController.parNoticeToExp.memberId - 收派员ID为必填项</dd>
     * <dd>SendNoticeController.parNoticeToExp.mob - 收派员手机号为必填项</dd>
     * <dd>SendNoticeController.parNoticeToExp.parNum - 包裹数量不能为0</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/parNoticeToExp", method = { RequestMethod.POST, RequestMethod.GET })
	public String parNoticeToExp(Long memberId, String mob, int parNum) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("SendNoticeController.parNoticeToExp.memberId", "收派员ID为必填项");
		}
		if (PubMethod.isEmpty(mob)) {
			return paramsFailure("SendNoticeController.parNoticeToExp.mob", "收派员手机号为必填项");
		}
		if (0 == parNum) {
			return paramsFailure("SendNoticeController.parNoticeToExp.parNum", "包裹数量不能为0");
		}
		try {
			this.sendNoticeService.parNoticeToExp(memberId, mob, parNum);
			return jsonSuccess();
		} catch (Exception e) {
			e.printStackTrace();
			return jsonFailure(e);
		}
	}

}

package net.okdi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.api.service.SmsWhiteListService;
import net.okdi.core.base.BaseController;

@Controller
@RequestMapping("smsWhiteList")
public class SmsWhiteListController extends BaseController{
	@Autowired
	SmsWhiteListService smsWhiteListService; 
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>是否在白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:39:10</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/isInWhiteList")
	public String isInWhiteList(Long memberId){
		try {
			return jsonSuccess(this.smsWhiteListService.isInWhiteList(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>加入到白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:40:51</dd>
	 * @param memberPhone		收派员手机号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addInWhiteList")
	public String addInWhiteList(String memberPhone){
		try {
			return jsonSuccess(this.smsWhiteListService.addInWhiteList(memberPhone));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	
	/**
	 * <dt><span class="strong">方法描述:</span></dt><dd>加入到白名单中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>amssy</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-7-1 上午11:40:51</dd>
	 * @param memberId		收派员id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFromWhiteList")
	public String deleteFromWhiteList(Long memberId){
		try {
			return jsonSuccess(this.smsWhiteListService.deleteFromWhiteList(memberId));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
}

package net.okdi.apiV3.controller;

import net.okdi.apiV3.service.WaybillSearchInfoService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wechat")
public class WaybillSearchInfoController extends BaseController {

	@Autowired
	WaybillSearchInfoService waybillSearchInfoService;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信端查询查询记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd></dd>
	 * @param memerId 
	 * @param taskId
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/waybillSearchInfo", method = {RequestMethod.GET , RequestMethod.POST })
	public String WaybillSearchInfo(Long memberId,Integer currentPage,String netName,String waybillNo) {
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("WeChatNumberController.WaybillSearchInfo.memberId", "memberId不能为空");
		}
		try{
			return jsonSuccess(waybillSearchInfoService.waybillSearchInfo(memberId,currentPage,netName,waybillNo));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>微信端插入查询记录</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jianxin.ma</dd>
	 * <dt><span class="strong">时间:</span></dt><dd></dd>
	 * @param memerId 查询Id
	 * @param taskId
	 * @since v1.0
	 */
	@ResponseBody
	@RequestMapping(value = "/insertWaybillInfo", method = {RequestMethod.GET , RequestMethod.POST })
	public String InsertWaybillInfo(String waybillNo,String netId,String netName,Long memberId) {	
		if (PubMethod.isEmpty(memberId)) {
			return paramsFailure("WeChatNumberController.InsertWaybillInfo.memberId", "memberId不能为空");
		}
		try{
			return jsonSuccess(waybillSearchInfoService.insertWaybillInfo(waybillNo,netId,netName,memberId));
		}catch(RuntimeException re){
			re.printStackTrace();
			return this.jsonFailure(re);
		}
	}	
	
}
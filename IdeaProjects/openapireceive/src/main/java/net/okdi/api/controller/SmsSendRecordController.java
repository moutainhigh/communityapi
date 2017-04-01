package net.okdi.api.controller;

import java.util.Date;

import net.okdi.api.service.SmsSendRecordService;
import net.okdi.core.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/smsSendRecord")
public class SmsSendRecordController extends BaseController {
	@Autowired
	private SmsSendRecordService smsSendRecordService;
	
	@ResponseBody
	@RequestMapping(value = "/querySmsSendRecord", method = { RequestMethod.POST, RequestMethod.GET })
	public String querySmsSendRecord(Long memberId,int pageNo,int pageSize,Date date){
		try {
			return jsonSuccess(this.smsSendRecordService.querySmsSendRecord(memberId,pageNo,pageSize,date));
		} catch (Exception e) {
			return jsonFailure(e);
		}
	}
	/** 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据网络ID和省份ID获取网络报价</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>feng.wang</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2014-11-12 下午05:39:53</dd>
	 * @param netId 网络ID
	 * @param provinceId 收件地址省份ID
	 * @return {"success":true,"data":{"netPriceList":[{"continueWeight":5,"firstFreight":10,"province":"甘肃省"}]}}
	 * <dd>firstFreight -  首重价格 </dd>
	 * <dd>continueWeight -  续重价格 </dd>
	 * <dd>province -  派件省份名称 </dd>
	 * <dt><span class="strong">异常:</span></dt>
     * <dd>NetInfoController.getNetPrice.001 - 网络ID为必填项</dd>
     * <dd>NetInfoController.getNetPrice.002 - 省份ID为必填项</dd>
	 * @since v1.0
	*/
	@ResponseBody
	@RequestMapping(value = "/saveSmsSendRecordBatch", method = { RequestMethod.POST, RequestMethod.GET })
	public String saveSmsSendRecordBatch(String content,Long memberId,String memberMobile,Long compId) {
		try {
			this.smsSendRecordService.saveSmsSendRecordBatch(content, memberId, memberMobile, compId);
			return jsonSuccess();
		}catch (Exception e) {
			return jsonFailure(e);
		}
	}
}

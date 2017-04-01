package net.okdi.api.controller;

import net.okdi.api.service.PayService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author feng.wang
 * @version V1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {

	@Autowired
	private PayService payService;

	@ResponseBody
	@RequestMapping(value = "/addPayInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public String addPayInfo(Long fromMemberId, Long toMemberId, String orderId,
			String orderName, Double totalFee) {
		if (PubMethod.isEmpty(fromMemberId)) {
			return PubMethod.paramError("err.201","fromMemberId(支付用户ID)不能为空！");
		}
		if (PubMethod.isEmpty(orderId)) {
			return PubMethod.paramError("err.202","orderId(订单ID)不能为空！");
		}
		if (PubMethod.isEmpty(orderName)) {
			return PubMethod.paramError("err.203","orderName(订单名称)不能为空！");
		}
		if (PubMethod.isEmpty(totalFee)) {
			return PubMethod.paramError("err.204","totalFee(总金额)不能为空！");
		}
		try {
			return payService.addPayInfo(fromMemberId, toMemberId, orderId, orderName, totalFee);
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/modifyPayStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public String modifyPayStatus(String orderId) {
		if (PubMethod.isEmpty(orderId)) {
			return PubMethod.paramError("err.202","orderId(订单ID)不能为空！");
		}
		try {
			return payService.modifyPayStatus(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return PubMethod.sysError();
		}
	}

}

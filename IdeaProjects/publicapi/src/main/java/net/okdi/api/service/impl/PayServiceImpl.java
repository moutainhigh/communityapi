package net.okdi.api.service.impl;

import net.okdi.api.service.PayService;
import net.okdi.httpClient.PayHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PayHttpClient payHttpClient;

	@Override
	public String addPayInfo(Long fromMemberId, Long toMemberId, String orderId, String orderName,
			Double totalFee) {
		return payHttpClient.addPayInfo(fromMemberId,toMemberId,orderId, orderName, totalFee);
	}

	@Override
	public String modifyPayStatus(String orderId) {
		return payHttpClient.modifyPayStatus(orderId);
	}

}

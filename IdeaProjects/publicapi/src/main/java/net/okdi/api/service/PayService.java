package net.okdi.api.service;

public interface PayService {

	public String addPayInfo(Long fromMemberId, Long toMemberId, String orderId,
			String orderName, Double totalFee);

	public String modifyPayStatus(String orderId);

}

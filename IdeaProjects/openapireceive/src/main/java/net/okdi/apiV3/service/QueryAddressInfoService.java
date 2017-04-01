package net.okdi.apiV3.service;

import java.util.List;
import java.util.Map;

import net.okdi.apiV3.entity.WechatAddress;



public interface QueryAddressInfoService {
	

	public List<Map<String, Object>> queryAddressInfo(Long parentId);
	
	public Map<String, String> addWechatAddressInfo(Long memberId,String senderName,
			String senderPhone,Long addresseeTownId,String addresseeTownName,
			String addresseeAddress,Short defaultMark,Short flag);
	
	public List<WechatAddress> getWechatAddressInfo(Long memberId,Short flag);
	
	public Map<String, String> deleteWechatAddressInfo(Long memberId, Long addressId);
	
	public List<WechatAddress> queryWechatDefaultAddress(Long memberId,Short flag);
	
	public Map<String, String> editWechatAddressInfo(Long memberId,Long addressId,String senderName, String senderPhone, Long addresseeTownId,
			String addresseeTownName, String addresseeAddress, Short defaultMark ,Short flag);
	public void modefyDefaultAddress(Long memberId,Long addressId,Short flag);


}

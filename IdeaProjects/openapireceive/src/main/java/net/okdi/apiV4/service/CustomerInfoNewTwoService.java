package net.okdi.apiV4.service;

import java.util.List;

import net.okdi.apiV4.entity.ExpCustomerInfo;


public interface CustomerInfoNewTwoService {
	String insertCustomerV4(Long compId, String customerName,Byte gender,
			Short customerType, String customerPhone, String townName,
			String compName, String iphoneTwo, String iphoneThree,
			String addressTwo, String addressThree, String userId,Long memberId);

	String deleteCustomerV4(Long customerId, Long compId, Long memberId);

	List<ExpCustomerInfo> selectCustomer(Long compId, Long memberId);

	String editCustomer(Long customerId, Long compId, String customerName,
			Short customerType, String customerPhone, Byte gender,
			String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId);
	String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId);
	String addContactsInfo(Long compId, String contacts,Long memberId);

	List<ExpCustomerInfo> queryCustomersJDW(Long memberId, String quertCondition);
	
	String valContactsExits(Long compId, String customerName);

}

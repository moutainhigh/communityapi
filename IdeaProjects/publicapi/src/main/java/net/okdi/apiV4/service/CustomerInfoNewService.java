package net.okdi.apiV4.service;

import java.math.BigDecimal;

public interface CustomerInfoNewService {
	//String queryCustomers(Long compId, String customerName, Short customerType,String customerPhone, Long expMemberId, Page page);

	String getByCustomerId(Long compId, Long customerId);

	//String getContactsByCustomerId(Long customerId);

	String queryExpMembers(Long compId, Long customerId);

	String getCustomerIdName(Long compId);

	String valContactsExits(Long compId, String customerName);

	String getContactsCount(Long compId, Long customerId);

	String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone, Long townId,
			String townName, String detailedAddress, Short discountGroupId, BigDecimal agencyFee, Short settleType,
			String expMemberIds, String contactMsgs, Long userId,String parceId);

	String updateCustomer(Long customerId, Long compId, String customerName, Short customerType, String customerPhone,
			Long townId, String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee, Short settleType,
			String expMemberIds, String contactMsgs);

	String deleteCustomer(Long customerId, Long compId);

	String valPhoneExist(Long compId, Long customerId, String phone);

	String queryContactsByCustomerId(Long compId, Long customerId);

	//String queryContacts(Long compId, String customerName, String contactsName, String contactsPhone);

	String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone, Long memberId);

	String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone);

	String deleteContacts(Long compId, Long contactId);

	String getAllByCustomerId(Long compId, Long customerId);

	String queryCustomersJDW(Long memberId, String quertCondition);

	String queryContactsJDW(Long compId, String quertCondition);

	String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId);

	String editCustomer(Long customerId, Long compId,
			String customerName, String labelIds,String labelNames, String customerPhone,
			Byte gender, String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId,Short parentCustomerType);


	String selectCustomer(Long compId,Long memberId);

	String deleteCustomerV4(Long customerId,Long compId,Long memberId);

	String insertCustomerV4(String compId, String customerName, String gender, String labelIds, String labelNames, String customerPhone,
			String townName, String compName, String iphoneTwo, String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, String memberId, String parentCustomerType);

	String addContactsInfo(Long compId, String mobileData,Long memberId);

	String saomaCreate(String tradeNum, Double tradeTotalAmount);

	String updateWeChatPayStatus(String payNum, String string);

	String findPayStatusByPayNum(String tradeNo);

	String isExistPhone(Long memberId, String phone);

	String customerDetail(String memberId, String customerId);
}

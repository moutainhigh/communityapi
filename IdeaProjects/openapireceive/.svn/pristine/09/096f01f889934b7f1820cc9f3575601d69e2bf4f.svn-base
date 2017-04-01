package net.okdi.apiV4.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpCustomerContactsInfo;
import net.okdi.api.entity.ExpCustomerInfo;
import net.okdi.api.entity.ExpCustomerMemberRelation;
import net.okdi.api.vo.VO_JustCustomerIdName;
import net.okdi.core.common.page.Page;

public interface ExpCustomerInfoNewService {
	Map<String, Object> queryCustomers(Long compId, String customerName, Short customerType,String customerPhone, Long expMemberId, Page page);

	ExpCustomerInfo getByCustomerId(Long compId, Long customerId);

	ExpCustomerContactsInfo getContactsByCustomerId(Long customerId);

	List<ExpCustomerMemberRelation> queryExpMembers(Long compId,Long customerId);

	List<VO_JustCustomerIdName> getCustomerIdName(Long compId);

	String valContactsExits(Long compId, String customerName);

	String getContactsCount(Long compId, Long customerId);

	String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone, Long erpCustomerId,
			Long townId,
			String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee, Short settleType,
			String expMemberIds, String contactMsgs, Long userId,String parcelId);

	String updateCustomer(Long customerId, Long compId, String customerName, Short customerType, String customerPhone,
			Long townId, String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee, Short settleType,
			String expMemberIds, String contactMsgs);

	String deleteCustomer(Long customerId, Long compId);
	
	Boolean valPhoneExist(Long compId,Long customerId,String phone);
	
	List<ExpCustomerContactsInfo> queryContactsByCustomerId(Long compId,Long customerId);

	Map<String, Object> queryContacts(Long compId, String customerName, String contactsName, String contactsPhone,
			Page page);

	String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone,Long memberId);

	String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone);

	String deleteContacts(Long compId, Long contactId);

	ExpCustomerInfo getAllByCustomerId(Long compId,Long customerId);

	List<ExpCustomerInfo> queryCustomersJDW(Long compId, String quertCondition);

	List<ExpCustomerContactsInfo> queryContactsJDW(Long compId, String quertCondition);

	
	void deleteByMemberId(Long compId,Long memberId);
	
	List<ExpCustomerInfo> queryByErpIdandCompId(Long compId,Long erpId);

	void insertExpList(Long compId, Long customerId, String expMemberIds);

	void clearDiscountGroupIdByCompId(Long compId, Long discountGroupId);

	String insertCustomerV4(Long compId, String customerName,Byte gender,
			Short customerType, String customerPhone, String townName,
			String compName, String iphoneTwo, String iphoneThree,
			String addressTwo, String addressThree, String userId,Long memberId);

	String deleteCustomerV4(Long customerId,Long compId,Long memberId);

	List<ExpCustomerInfo> selectCustomer(Long compId,Long memberId);

	String editCustomer(Long customerId, Long compId, String customerName,
			Short customerType, String customerPhone,Byte gender, String townName,
			String compName, String iphoneTwo, String iphoneThree,
			String addressTwo, String addressThree, String expMemberIds,Long memberId);

	String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId);

	String addContactsInfo(Long compId, String contacts,Long memberId);

	Map<String,Object> saomaCreate(String tradeNum, Double tradeTotalAmount);
}

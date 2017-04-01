package net.okdi.mob.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.mob.service.CustomerInfoService;

@SuppressWarnings({"rawtypes","unchecked"})
@Service("customerInfoService")
public class CustomerInfoServiceImpl extends AbstractHttpClient implements CustomerInfoService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Value("${pay_url}")
	private String payUrl; //新版财务url

	@Override
	public String getByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/getByCustomerId", map);
	}


	@Override
	public String queryExpMembers(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/queryExpMembers", map);
	}

	@Override
	public String getCustomerIdName(Long compId) {
		Map map = new HashMap();
		map.put("compId", compId);
		return openApiHttpClient.doPassSendStr("customerInfo/getCustomerIdName", map);
	}

	@Override
	public String valContactsExits(Long compId, String customerName) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerName", customerName);
		return openApiHttpClient.doPassSendStr("customerInfo/valContactsExits", map);
	}

	@Override
	public String getContactsCount(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/getContactsCount", map);
	}

	@Override
	public String insertCustomer(Long compId, String customerName, Short customerType, String customerPhone,
			Long townId, String townName, String detailedAddress, Short discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs, Long userId,String parceId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerName", customerName);
		map.put("customerType", customerType);
		map.put("customerPhone", customerPhone);
		map.put("townId", townId);
		map.put("townName", townName);
		map.put("detailedAddress", detailedAddress);
		map.put("discountGroupId", discountGroupId);
		map.put("agencyFee", agencyFee);
		map.put("settleType", settleType);
		map.put("expMemberIds", expMemberIds);
		map.put("contactMsgs", contactMsgs);
		map.put("userId", userId);
		map.put("parceId", parceId);
		return openApiHttpClient.doPassSendStr("customerInfo/insertCustomer", map);
	}

	@Override
	public String updateCustomer(Long customerId, Long compId, String customerName, Short customerType,
			String customerPhone, Long townId, String townName, String detailedAddress, Long discountGroupId, BigDecimal agencyFee,
			Short settleType, String expMemberIds, String contactMsgs) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		map.put("customerName", customerName);
		map.put("customerType", customerType);
		map.put("customerPhone", customerPhone);
		map.put("townId", townId);
		map.put("townName", townName);
		map.put("detailedAddress", detailedAddress);
		map.put("discountGroupId", discountGroupId);
		map.put("agencyFee", agencyFee);
		map.put("settleType", settleType);
		map.put("expMemberIds", expMemberIds);
		map.put("contactMsgs", contactMsgs);
		return openApiHttpClient.doPassSendStr("customerInfo/updateCustomer", map);
	}

	@Override
	public String deleteCustomer(Long customerId, Long compId) {
		
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/deleteCustomer", map);
	}

	@Override
	public String valPhoneExist(Long compId, Long customerId, String phone) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		map.put("phone", phone);
		return openApiHttpClient.doPassSendStr("customerInfo/valPhoneExist", map);
	}

	@Override
	public String queryContactsByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/queryContactsByCustomerId", map);
		
	}


	@Override
	public String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone, Long memberId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		map.put("contactsName", contactsName);
		map.put("contactsPhone", contactsPhone);
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("customerInfo/addContacts", map);
	}

	@Override
	public String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("contactId", contactId);
		map.put("contactsName", contactsName);
		map.put("contactsPhone", contactsPhone);
		return openApiHttpClient.doPassSendStr("customerInfo/updateContacts", map);
		
	}

	@Override
	public String deleteContacts(Long compId, Long contactId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("contactId", contactId);
		return openApiHttpClient.doPassSendStr("customerInfo/deleteContacts", map);
	}

	@Override
	public String getAllByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerInfo/getAllByCustomerId", map);
	}

	@Override
	public String queryCustomersJDW(Long compId, String quertCondition) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("quertCondition", quertCondition);
		return openApiHttpClient.doPassSendStr("customerInfo/queryCustomersJDW", map);
	}

	@Override
	public String queryContactsJDW(Long compId, String quertCondition) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("quertCondition", quertCondition);
		return openApiHttpClient.doPassSendStr("customerInfo/queryContactsJDW", map);
	}


	@Override
	public String addCustomerType(String addCustomerIds,String delCustomerIds,Long labelId,Long memberId,String labelName) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("addCustomerIds", addCustomerIds);
		map.put("delCustomerIds", delCustomerIds);
		map.put("labelId", String.valueOf(labelId));
		map.put("memberId", String.valueOf(memberId));
		map.put("labelName", labelName);
		return openApiHttpClient.doPassSendStr("customerInfo/addCustomerType", map);

	}


	@Override
	public String editCustomer(Long customerId, Long compId,
			String customerName, Short customerType, String customerPhone,Byte gender,
			String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("customerId", String.valueOf(customerId));
		map.put("compId", String.valueOf(compId));
		map.put("customerName", customerName);
		map.put("customerType", String.valueOf(customerType));
		map.put("customerPhone", customerPhone);
		map.put("townName", townName);
		map.put("compName", compName);
		map.put("iphoneTwo", iphoneTwo);
		map.put("iphoneThree", iphoneThree);
		map.put("addressTwo", addressTwo);
		map.put("gender", gender==null ? "" : String.valueOf(gender));
		map.put("addressThree", addressThree);
		map.put("expMemberIds", expMemberIds);
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doPassSendStr("customerInfo/editCustomer", map);
	}


	@Override
	public String selectCustomer(Long compId,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("compId", String.valueOf(compId));
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doPassSendStr("customerInfo/selectCustomer", map);
	}


	@Override
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("customerId", String.valueOf(customerId));
		map.put("compId", String.valueOf(compId));
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doPassSendStr("customerInfo/deleteCustomerV4", map);
	}


	@Override
	public String insertCustomerV4(Long compId, String customerName,
			Short customerType, String customerPhone, Byte gender,String townName,
			String compName, String iphoneTwo, String iphoneThree,
			String addressTwo, String addressThree, String expMemberIds,Long memberId) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("compId", String.valueOf(compId));
			map.put("customerName", customerName);
			map.put("customerType", customerType==null ? "" : String.valueOf(customerType));
			map.put("customerPhone", customerPhone);
			map.put("townName", townName);
			map.put("compName", compName);
			map.put("iphoneTwo", iphoneTwo);
			map.put("iphoneThree", iphoneThree);
			map.put("addressTwo", addressTwo);
			map.put("addressThree", addressThree);
			map.put("expMemberIds", expMemberIds);		
			map.put("gender", gender==null ? "":String.valueOf(gender));		
			map.put("memberId", String.valueOf(memberId));		
			return openApiHttpClient.doPassSendStr("customerInfo/insertCustomerV4", map);
	}


	@Override
	public String addContactsInfo(Long compId, String mobileData,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("compId", String.valueOf(compId));
		map.put("mobileData", mobileData);
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doPassSendStr("customerInfo/addContactsInfo", map);
	}

	@Override
	public String saomaCreate(String tradeNum, Double tradeTotalAmount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tradeNum", tradeNum);
		map.put("tradeTotalAmount", String.valueOf(tradeTotalAmount));
		return openApiHttpClient.doPassSendStr("customerInfo/saomaCreate", map);

	}


	@Override
	public String updateWeChatPayStatus(String payNum, String state) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("payNum", payNum);
		map.put("payStatus", state);
		return openApiHttpClient.doPassSendStr("receivePackage/updateWeChatPayStatus", map);
	}

}

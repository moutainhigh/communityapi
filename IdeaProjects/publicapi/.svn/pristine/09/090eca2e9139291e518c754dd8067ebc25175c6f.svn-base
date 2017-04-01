package net.okdi.apiV4.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.CustomerInfoNewService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.httpClient.AbstractHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@SuppressWarnings({"rawtypes","unchecked"})
@Service("customerInfoNewService")
public class CustomerInfoNewServiceImpl extends AbstractHttpClient implements CustomerInfoNewService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Value("${pay_url}")
	private String payUrl; //新版财务url

	@Override
	public String getByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/getByCustomerId", map);
	}


	@Override
	public String queryExpMembers(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/queryExpMembers", map);
	}

	@Override
	public String getCustomerIdName(Long compId) {
		Map map = new HashMap();
		map.put("compId", compId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/getCustomerIdName", map);
	}

	@Override
	public String valContactsExits(Long memberId, String customerName) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("customerName", customerName);
		return openApiHttpClient.doOpencustomer("customerNewInfoTwo/valContactsExits", map);
	}

	@Override
	public String getContactsCount(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/getContactsCount", map);
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
		return openApiHttpClient.doPassSendStr("customerNewInfo/insertCustomer", map);
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
		return openApiHttpClient.doPassSendStr("customerNewInfo/updateCustomer", map);
	}

	@Override
	public String deleteCustomer(Long customerId, Long compId) {
		
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/deleteCustomer", map);
	}

	@Override
	public String valPhoneExist(Long compId, Long customerId, String phone) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		map.put("phone", phone);
		return openApiHttpClient.doPassSendStr("customerNewInfo/valPhoneExist", map);
	}

	@Override
	public String queryContactsByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/queryContactsByCustomerId", map);
		
	}


	@Override
	public String addContacts(Long compId, Long customerId, String contactsName, String contactsPhone, Long memberId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		map.put("contactsName", contactsName);
		map.put("contactsPhone", contactsPhone);
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/addContacts", map);
	}

	@Override
	public String updateContacts(Long compId, Long contactId, String contactsName, String contactsPhone) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("contactId", contactId);
		map.put("contactsName", contactsName);
		map.put("contactsPhone", contactsPhone);
		return openApiHttpClient.doPassSendStr("customerNewInfo/updateContacts", map);
		
	}

	@Override
	public String deleteContacts(Long compId, Long contactId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("contactId", contactId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/deleteContacts", map);
	}

	@Override
	public String getAllByCustomerId(Long compId, Long customerId) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfo/getAllByCustomerId", map);
	}

	@Override
	public String queryCustomersJDW(Long memberId, String quertCondition) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("quertCondition", quertCondition);
		return openApiHttpClient.doOpencustomer("customerNewInfoTwo/queryCustomersJDW", map);
	}

	@Override
	public String queryContactsJDW(Long compId, String quertCondition) {
		Map map = new HashMap();
		map.put("compId", compId);
		map.put("quertCondition", quertCondition);
		return openApiHttpClient.doPassSendStr("customerNewInfo/queryContactsJDW", map);
	}


	@Override
	public String addCustomerType(String addCustomerIds,String delCustomerIds,String customerType,Long compId,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("addCustomerIds", addCustomerIds);
		map.put("delCustomerIds", delCustomerIds);
		map.put("customerType", customerType);
		map.put("compId", String.valueOf(compId));
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doOpencustomer("customerNewInfoTwo/addCustomerType", map);

	}


	@Override
	public String editCustomer(Long customerId, Long compId,
			String customerName, String labelIds,String labelNames, String customerPhone,
			Byte gender, String townName, String compName, String iphoneTwo,
			String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, Long memberId,Short parentCustomerType) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("customerId", String.valueOf(customerId));
		map.put("compId", String.valueOf(compId));
		map.put("customerName", customerName);
		map.put("labelIds", PubMethod.isEmpty(labelIds) ? "" : labelIds);
		map.put("labelNames", PubMethod.isEmpty(labelNames) ? "" : labelNames);
		map.put("customerPhone", customerPhone);
		map.put("townName", townName);
		map.put("compName", compName);
		map.put("iphoneTwo", iphoneTwo);
		map.put("iphoneThree", iphoneThree);
		map.put("addressTwo", addressTwo);
		map.put("gender", gender==null ? "" : String.valueOf(gender));
		map.put("addressThree", addressThree);
		map.put("expMemberIds", expMemberIds==null?"":expMemberIds);
		map.put("memberId", String.valueOf(memberId));
		map.put("parentCustomerType",PubMethod.isEmpty(parentCustomerType)?"": String.valueOf(parentCustomerType));		
		return openApiHttpClient.doPassSendStr("customerNewInfoTwo/editCustomer", map);
	}


	@Override
	public String selectCustomer(Long compId,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("compId", String.valueOf(compId));
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doOpencustomer("customerNewInfoTwo/selectCustomer", map);
	}

    //路径由doOpencustomer改为doPassSendStr---2017,1,5
	@Override
	public String deleteCustomerV4(Long customerId,Long compId,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("customerId", String.valueOf(customerId));
		map.put("compId", String.valueOf(compId));
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doPassSendStr("customerNewInfoTwo/deleteCustomerV4", map);
	}

	//路径由doOpencustomer改为doPassSendStr---2017,1,5
	@Override
	public String insertCustomerV4(String compId, String customerName, String gender, String labelIds, String labelNames, String customerPhone,
			String townName, String compName, String iphoneTwo, String iphoneThree, String addressTwo, String addressThree,
			String expMemberIds, String memberId, String parentCustomerType) {
			Map<String,String> map = new HashMap<String,String>();
			map.put("compId", compId);
			map.put("customerName", customerName);
			map.put("labelIds", PubMethod.isEmpty(labelIds) ? "" : labelIds);
			map.put("labelNames", PubMethod.isEmpty(labelNames) ? "" : labelNames);
			map.put("customerPhone", customerPhone);
			map.put("townName", townName);
			map.put("compName", compName);
			map.put("iphoneTwo", iphoneTwo);
			map.put("iphoneThree", iphoneThree);
			map.put("addressTwo", addressTwo);
			map.put("addressThree", addressThree);
			map.put("expMemberIds", expMemberIds==null?"":expMemberIds);	
			map.put("gender", PubMethod.isEmpty(gender) ? "":gender);		
			map.put("memberId", memberId);		
			map.put("parentCustomerType",PubMethod.isEmpty(parentCustomerType)?"": parentCustomerType);		
			return openApiHttpClient.doPassSendStr("customerNewInfoTwo/insertCustomerV4", map);
	}


	@Override
	public String addContactsInfo(Long compId, String mobileData,Long memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("compId", String.valueOf(compId));
		map.put("mobileData", mobileData);
		map.put("memberId", String.valueOf(memberId));
		return openApiHttpClient.doOpencustomer("customerNewInfoTwo/addContactsInfo", map);
	}

	@Override
	public String saomaCreate(String tradeNum, Double tradeTotalAmount) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("tradeNum", tradeNum);
		map.put("tradeTotalAmount", String.valueOf(tradeTotalAmount));
		return openApiHttpClient.doPassSendStr("customerNewInfoTwo/saomaCreate", map);

	}


	@Override
	public String updateWeChatPayStatus(String payNum, String state) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("payNum", payNum);
		map.put("payStatus", state);
		return openApiHttpClient.doPassSendStr("receivePackage/updateWeChatPayStatus", map);
	}


	@Override
	public String findPayStatusByPayNum(String payNum) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("payNum", payNum);
		return openApiHttpClient.doPassSendStr("receivePackage/findPayStatusByPayNum", map);
	}


	@Override
	public String isExistPhone(Long memberId, String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("phone", phone);
		return openApiHttpClient.doPassSendStr("customerNewInfoTwo/isExistPhone", map);
	}
	
	@Override
	public String customerDetail(String memberId, String customerId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("customerId", customerId);
		return openApiHttpClient.doPassSendStr("customerNewInfoTwo/customerDetail", map);
	}

}

package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV4.service.CustomerAddLabelService;
import net.okdi.core.passport.OpenApiHttpClient;

@Service
public class CustomerAddLabelServiceImpl implements CustomerAddLabelService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public String insertLabel(String labelName, Long memberId, String customerIds) {
		Map<String,Object> map = new HashMap<>();
		map.put("labelName", labelName);
		map.put("memberId", memberId);
		map.put("customerIds", customerIds);
		String methodName="CustomerAddLabel/insertLabel";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}
	@Override
	public String queryLabelList(Long memberId) {

		Map<String,Object> map = new HashMap<>();
		map.put("memberId",memberId);
		String methodName="CustomerAddLabel/queryLabelList";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}
	@Override
	public String getLabel(Long memberId) {

		Map<String,Object> map = new HashMap<>();
		map.put("memberId",memberId);
		String methodName="CustomerAddLabel/getLabel";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}
	@Override
	public String updateLabel(Long customerId, Long memberId, Long labelId) {

		Map<String,Object> map = new HashMap<>();
		map.put("customerId",customerId);
		map.put("memberId",memberId);
		map.put("labelId",labelId);
		String methodName="CustomerAddLabel/updateLabel";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}
	@Override
	public String editLabelCustomer(Long memberId, Long labelId,
			String customerIds,String labelName) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId",memberId);
		map.put("labelId",labelId);
		map.put("customerIds",customerIds);
		map.put("labelName",labelName);
		String methodName="CustomerAddLabel/editLabelCustomer";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}
	@Override
	public String deleteLabel(Long memberId, Long labelId) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId",memberId);
		map.put("labelId",labelId);
		String methodName="CustomerAddLabel/deleteLabel";
		return openApiHttpClient.doPassSendStr(methodName, map);
	}

}
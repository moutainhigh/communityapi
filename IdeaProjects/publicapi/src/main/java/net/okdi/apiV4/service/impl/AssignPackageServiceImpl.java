package net.okdi.apiV4.service.impl;

import net.okdi.apiV4.service.AssignPackageService;
import net.okdi.core.passport.OpenApiHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@Service
public class AssignPackageServiceImpl implements AssignPackageService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String queryEmployeeByCompId(Long compId) {
		Map<String,Object> map = new HashMap<>();
		map.put("compId", compId);
		String methodName="assignPackage/querySiteMemberListV2";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}
	@Override
	public String saveParcelInfo(String expWaybillNum, Long compId, Long netId,String addresseeName, String addresseeMobile,
			Long addresseeAddressId,String cityName, String addresseeAddress,BigDecimal freight, BigDecimal codAmount, Long createUserId,Long actualSendMember) {
		Map<String,Object> map = new HashMap<>();
		map.put("expWaybillNum", expWaybillNum);
		map.put("compId", compId);
		map.put("netId", netId);
		map.put("addresseeName", addresseeName);
		map.put("addresseeMobile", addresseeMobile);
		map.put("addresseeAddressId", addresseeAddressId);
		map.put("cityName", cityName);
		map.put("addresseeAddress", addresseeAddress);
		map.put("freight", freight);
		map.put("codAmount", codAmount);
		map.put("createUserId", createUserId);
		map.put("actualSendMember", actualSendMember);
		String methodName="assignPackage/saveParcelInfo";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}
	@Override
	public String parcelIsExist(String addresseeMobile,Long compId) {
		Map<String,Object> map = new HashMap<>();
		map.put("addresseeMobile", addresseeMobile);
		map.put("compId", compId);
		String methodName="assignPackage/parcelIsExist";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}

	@Override
	public String saveParcelInfo(String info, Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("info", info);
		map.put("memberId", memberId);
		String methodName="assignPackage/saveParcelInfoBatch";
		return openApiHttpClient.doPassSendStrParcel(methodName, map);
	}
	@Override
	public Long queryAssignException(Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName="assignPackage/queryAssignException";
		String result = openApiHttpClient.doPassSendStrParcel(methodName, map);
		Map<String, Object> returnmap = (Map<String, Object>) JSONObject.parse(result);
//		if ("true".equals(returnmap.get("success"))) {
//		}
		return Long.parseLong(String.valueOf(returnmap.get("data")));
		//return 0l;
	}
}

package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.IAttributionService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class IAttributionServiceImpl implements IAttributionService  {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	public String findShopowner(String memberId,String roleId) {
		
		if(PubMethod.isEmpty(memberId)){
			return PubMethod.paramError("publicapi.Attribution.findShopowner.001", "memberId不能为空");
		}
		if(PubMethod.isEmpty(roleId)){
			return PubMethod.paramError("publicapi.Attribution.findShopowner.002", "roleId不能为空");
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"attribution/findShopowner/", map);
			
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

}

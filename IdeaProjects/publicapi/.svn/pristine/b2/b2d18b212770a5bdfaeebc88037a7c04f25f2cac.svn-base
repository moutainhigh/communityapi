package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.ShopMemberPushSetService;

@Service
public class ShopMemberPushSetServiceImpl implements ShopMemberPushSetService {

	@Autowired
	OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String pushStatus(Long memberId, Short pushStatus) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberId", memberId);
		params.put("pushStatus", pushStatus);
		String result = openApiHttpClient.doPassSendStr("shopMemberPushSet/pushStatus", params);
		return result;
	}

	@Override
	public String queryShopMemberPushSetById(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassSendStr("shopMemberPushSet/queryShopMemberPushSetById", map);
		return result;
	}

}

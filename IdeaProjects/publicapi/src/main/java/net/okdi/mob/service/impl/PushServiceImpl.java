package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.PushService;

@Service
public class PushServiceImpl implements PushService{

	@Autowired
	private OpenApiHttpClient openapiHttpClient;
	
	@Override
	public String initBadge(String deviceToken) {
		Map map = new HashMap();
		map.put("deviceToken", deviceToken);
		return openapiHttpClient.doPassSendStr("mobPush/initBadge", map);
	}
}

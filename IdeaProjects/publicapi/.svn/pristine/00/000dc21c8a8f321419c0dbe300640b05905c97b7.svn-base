package net.okdi.apiV3.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV3.service.WechatMpService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.httpClient.SmsHttpClient;

@Service
public class WechatMpServiceImpl extends AbstractHttpClient implements WechatMpService{

	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	@Override
	public String send(String openId, String site, String courierName, String phone, String status, String reason ,String taskId) {
		Map<String, String> map = new HashMap<>();
		map.put("openId", openId);
		map.put("site", site);
		map.put("courierName", courierName);
		map.put("phone", phone);
		map.put("status", status);
		map.put("reason", reason);
		map.put("openId", openId);
		map.put("taskId", taskId);
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/wechatmp/send",map);
	}

}

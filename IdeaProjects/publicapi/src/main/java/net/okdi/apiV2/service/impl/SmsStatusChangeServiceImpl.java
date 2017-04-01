package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV2.service.SmsStatusChangeService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

@Service
public class SmsStatusChangeServiceImpl implements SmsStatusChangeService{
	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	
	@Override
	public String unReply(String msgId,Short flag,Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("msgId",msgId);
		map.put("memberId",memberId+"");
		//更新短信状态
		smsHttpClient.Post(constPool.getSmsHttpUrl()+"smsStatusChange/unReply",map);
		//更新取件任务状态
		smsHttpClient.Post(constPool.getOpenApiUrl()+"taskRemind/unReply",map);
		return "true";
	}
	@Override
	public String unRead(String msgId,Short flag) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("msgId",msgId);
		//更新短信状态
		smsHttpClient.Post(constPool.getSmsHttpUrl()+"smsStatusChange/unRead",map);
		return "true";
	}

}

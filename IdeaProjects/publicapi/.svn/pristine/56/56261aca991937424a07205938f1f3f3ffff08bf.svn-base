package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.InterceptService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterceptServiceImpl implements InterceptService{

	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	
	@Override
	public void saveIntercept(String accountId, String channelNo, String msgId,
			String phoneAndNum, Long memberId, String memberPhone,
			Short flag, String content, Short isNum) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", channelNo);
		map.put("msgId", msgId);
		map.put("phoneAndNum", phoneAndNum);
		map.put("memberId", memberId+"");
		map.put("memberPhone", memberPhone);
		map.put("flag", flag+"");
		map.put("content", content);
		map.put("isNum", isNum+"");
		smsHttpClient.Post(constPool.getSmsHttpUrl()+"/forgeHeadSms/intercept",map);
	}

	@Override
	public void saveTaskIntercept(String accountId, String channelNo,
			 String phoneAndNum, Long memberId,
			String memberPhone, Short flag, String content, Short isNum) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("accountId", accountId);
		map.put("channelNo", channelNo);
		map.put("phoneAndNum", phoneAndNum);
		map.put("memberId", memberId+"");
		map.put("memberPhone", memberPhone);
		map.put("flag", flag+"");
		map.put("content", content);
		map.put("isNum", isNum+"");
		smsHttpClient.Post(constPool.getSmsHttpUrl()+"/forgeHeadSms/taskIntercept",map);
	}

}

package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.TaskRecordService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskRecordServiceImpl implements TaskRecordService {
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	
	@Override
	public String queryReceiveCount(String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("memberId",String.valueOf(memberId));
		
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("taskRecord/queryReceiveCount", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	
	

	@Override
	public String querySendCount(String memberId) {
       Map<String, String> map = new HashMap<String, String>();
		
		map.put("memberId",String.valueOf(memberId));
		
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("taskRecord/querySendCount", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	
	public String queryNoticeCount(String memberId) {
		 Map<String, String> map = new HashMap<String, String>();
			
		map.put("memberId",String.valueOf(memberId));
		
		String result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"vlinkCall/queryNoticeCount", map);
		return result;
	}

}

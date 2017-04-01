package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.CallPhoneService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CallPhoneServiceImpl implements CallPhoneService {

	@Autowired
	private ConstPool constPool;
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Override
	public String callPhoneByPhone(String caller, String callee) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("caller", caller);
		map.put("callee", callee);
		String result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"callPhone/callPhoneByPhone", map);
		return result;
	}
	@Override
	public String backPhoneToValue(String id, Short flag, String duration, String answerTime, String endTime) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("flag", flag+"");
		map.put("duration", duration);
		map.put("answerTime", answerTime);
		map.put("endTime", endTime);
		String result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"callPhone/backPhoneToValue", map);
		return result;
	}
	@Override
	public String queryPhoneRecordList(String caller) {
		// TODO Auto-generated method stub
		Map<String,String> map = new HashMap<String,String>();
		map.put("caller", caller);
		String result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"callPhone/queryPhoneRecordList", map);
		return result;
	}

}

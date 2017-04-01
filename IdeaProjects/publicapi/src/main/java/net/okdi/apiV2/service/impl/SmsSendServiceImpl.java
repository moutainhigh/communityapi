package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsSendServiceImpl extends AbstractHttpClient implements SmsSendService{
	@Autowired
	private ConstPool constPool;
	
	public static final Log logger = LogFactory.getLog(SmsSendServiceImpl.class);
	
	@Override
	public String memberSendNumQueryOneDay(Long memberId,Integer num){
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("num", num+"");
		String url=constPool.getOpenApiUrl()+"/smsSend/statisticalNumberOneDay";
		return Post(url, map);
	}
	
	@Override
	public String memberSendNumQueryOneHour(Long memberId,Integer num){
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("num", num+"");
		String url=constPool.getOpenApiUrl()+"/smsSend/statisticalNumberOneHour";
		return Post(url, map);
	}
	
	@Override
	public String memberAuthentication(Long memberId){
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String url=constPool.getOpenApiUrl()+"/smsSend/memberAuthentication";
		return Post(url, map);
	}

	@Override
	public String isExist(Long memberId, String sbresult) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("receiverPhones",sbresult);
		String url=constPool.getSmsHttpUrl()+"smsLogQuery/isExist";
		return Post(url, map);
	}

	@Override
	public String queryHoursAndDaynum(Integer len, Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("num",len+"");
		map.put("memberId",memberId+"");
		String url=constPool.getOpenApiUrl()+"/smsSend/queryHoursAndDaynum";
		return Post(url, map);
	}
}

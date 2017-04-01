package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.MessageInfoService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

@Service
public class MessageInfoServiceImpl implements MessageInfoService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String queryMessageTime() {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = openApiHttpClient.doPassSendStr("messageInfo/queryMessageTime/", map);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MessageInfoServiceImpl.queryMessageTime.001","数据请求异常");
		}
		return result;
	}

	@Override
	public String queryInfoInWechat(String firstMsgId, String mobilePhone) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mobilePhone", mobilePhone);
		map.put("firstMsgId", firstMsgId);
		String result = openApiHttpClient.doPassSendStr("messageInfo/queryInfoInWechat/", map);
		if(PubMethod.isEmpty(result)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MessageInfoServiceImpl.queryInfoInWechat.001","数据请求异常");
		}
		return result;
	}
	
}

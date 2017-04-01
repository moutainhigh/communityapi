package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.WeiChatPubNoService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

@Service
public class WeiChatPubNoServiceImpl implements WeiChatPubNoService{

	private @Autowired OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String findWeichatkeyBymemberId(String memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		String response = openApiHttpClient.doPassSendWeichat("weichat/QRcodeUrl/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.WeiChatPubNoServiceImpl.findWeichatkeyBymemberId.001","数据请求异常");
		}
		return response;
	}

}

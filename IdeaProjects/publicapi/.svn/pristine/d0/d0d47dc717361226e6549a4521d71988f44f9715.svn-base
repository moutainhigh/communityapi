package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.NearInfoService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.httpClient.AbstractHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NearInfoServiceImpl extends AbstractHttpClient implements NearInfoService{
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public String collectingPoints(String longitude, String latitude) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("longitude", longitude); 
		map.put("latitude", latitude);
		
		return openApiHttpClient.doPassSendStr("queryNearInfo/queryCompInfo/", map);
	}

}

package net.okdi.circle.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.circle.service.ExpressCircleService;
import net.okdi.core.passport.OpenApiHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressCircleServiceImpl implements ExpressCircleService {

	@Autowired
	private OpenApiHttpClient apiHttpClient;
	
	@Override
	public String queryCircleList(String circleMember, String circleLongitude,
			String circleLatitude) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("circleMember", circleMember);
		map.put("circleLongitude", circleLongitude);
		map.put("circleLatitude", circleLatitude);
		apiHttpClient.doPassSendStr("expressCircle/queryCircleList", map);
		return null;
	}

}

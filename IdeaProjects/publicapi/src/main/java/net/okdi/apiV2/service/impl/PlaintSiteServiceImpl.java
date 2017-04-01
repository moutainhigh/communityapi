package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.impl.ExpressUserServiceImpl;
import net.okdi.apiV2.service.PlaintSiteService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaintSiteServiceImpl implements PlaintSiteService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ConstPool constPool;
	Logger log = Logger.getLogger(ExpressUserServiceImpl.class);
	@Override
	public String savePlaintSiteInfo(Long memberId, Long compId,
			String compName, Short compType, String plaintPhone,String headPhone,
			String responsible,  String idNum, String business, String netId,
			String businessLicenseImg, String shopImg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("compName", compName);
		map.put("compType", compType);
		map.put("plaintPhone", plaintPhone);
		map.put("responsible", responsible);
		map.put("headPhone", headPhone);
		map.put("idNum", idNum);
		map.put("business", business);
		map.put("netId", netId);
		map.put("businessLicenseImg", businessLicenseImg);
		map.put("shopImg", shopImg);
		
		String result = openApiHttpClient.doPassSendStr("plaintSite/savePlaintSiteInfo", map);
		return result;
	}
	
}

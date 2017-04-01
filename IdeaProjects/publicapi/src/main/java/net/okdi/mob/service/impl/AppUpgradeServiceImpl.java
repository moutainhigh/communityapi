package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.AppUpgradeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUpgradeServiceImpl implements AppUpgradeService{
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String  queryAppUpgrade(Short appType,String selfVersion,Short phoneType){
		Map <String,String>map = new HashMap<String,String>();
		map.put("appType",String.valueOf(appType));
		map.put("selfVersion",selfVersion );
		map.put("phoneType", String.valueOf(phoneType));
		String result = openApiHttpClient.doPassSendStr("appUpgrade/queryAppUpgrade", map);
		return result;
	}
}

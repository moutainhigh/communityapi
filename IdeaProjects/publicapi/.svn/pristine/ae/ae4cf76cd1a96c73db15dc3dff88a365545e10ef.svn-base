package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.AppVersionInfoService;

@Service
public class AppVersionInfoServiceImpl implements AppVersionInfoService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	@Override
	public String queryAppVersionInfo(Short appType) {
		Map <String,String>map = new HashMap<String,String>();
		map.put("appType", String.valueOf(appType));
	    String result = openApiHttpClient.doPassSendStr("appVersionInfo/queryAppVersionInfo", map);
		return result;
	}
	@Override
	public String queryAppVersionInfoIos() {
		Map <String,String>map = new HashMap<String,String>();
	    String result = openApiHttpClient.doPassSendStr("appVersionInfo/queryAppVersionInfoIos", map);
		return result;
	}
	@Override
	public String updateAppVersionInfo(Short appType) {
		Map <String,String>map = new HashMap<String,String>();
		map.put("appType", String.valueOf(appType));
	    String result = openApiHttpClient.doPassSendStr("appVersionInfo/updateAppVersionInfo", map);
		return result;
	}
}

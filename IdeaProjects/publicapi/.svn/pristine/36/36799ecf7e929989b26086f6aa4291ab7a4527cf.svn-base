package net.okdi.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.api.service.HandleDataService;
import net.okdi.core.passport.OpenApiHttpClient;

@Service
public class HandleDataServiceImpl implements HandleDataService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public String handleData() {
		Map<String,String> map = new HashMap<String,String>();
		String result = openApiHttpClient.doPassSendStr("handleDataInfo/handleData", map);
		return result;
	}

}

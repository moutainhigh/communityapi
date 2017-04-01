package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.ExpBasGatewayService;

@Service
public class ExpBasGatewayServiceImpl extends BaseService_jdw implements ExpBasGatewayService{
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	@Override
	public String getNets() {
		Map<String, Object>  ResultMeterMap  =openApiHttpClient.doPassSendObj("netInfo/getNetFirstLetter", null);
		return JSON.toJSONString(ResultMeterMap);
		//	        return changeValue(ResultMeterMap);
	}

	@Override
	public String getNetArea(Long compId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("compId",compId);
		return openApiHttpClient.doPassSendStr("compInfo/getCompareaList", paraMeterMap);
	}
}
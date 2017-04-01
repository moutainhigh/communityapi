package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.apiV1.service.impl.ExpressUserServiceImpl;
import net.okdi.apiV2.service.SendReceiveCountService;
import net.okdi.core.passport.OpenApiHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendReceiveCountServiceImpl implements SendReceiveCountService {

	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	Logger logger = Logger.getLogger(SendReceiveCountServiceImpl.class);
	@Override
	public String querySendReceiveCount(String memberId, String roleId,
			String compId) {
		logger.info("1 快递圈中去查询取件派件数量SendReceiveCountServiceImpl ---- querySendReceiveCount{{{}}}}:::memberId : "+memberId+"== roleId: "+roleId+"== compId: "+compId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("roleId", roleId);
		map.put("compId", compId);
		String methodName = "sendReceiveCount/querySendReceiveCount";
		String receiveCount = openApiHttpClient.doPassSendStr(methodName, map);
		logger.info("2 快递圈中去查询取件派件数量SendReceiveCountServiceImpl ---- querySendReceiveCount{{{}}}}:::receiveCount: "+receiveCount);
		
		return receiveCount;
	}
	@Override
	public String queryPhoneNameByMemberId(Long memberId) {
		logger.info("1 根据memberId查询客户姓名和手机号, 查询派件任务中姓名和手机号{{{{{{{{{{{}}}}}}}}}memberId: "+memberId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		String methodName = "sendReceiveCount/queryPhoneNameByMemberId";
		String receiveCount = openApiHttpClient.doPassSendStr(methodName, map);
		logger.info("2 根据memberId查询客户姓名和手机号, 查询派件任务中姓名和手机号{{{{{{{{{{{}}}}}}}}}receiveCount: "+receiveCount);
		
		return receiveCount;
	}

}

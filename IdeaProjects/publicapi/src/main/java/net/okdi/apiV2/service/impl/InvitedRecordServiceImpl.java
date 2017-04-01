package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.InvitedRecordService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitedRecordServiceImpl implements InvitedRecordService{
	public static final Log logger = LogFactory.getLog(InvitedRecordServiceImpl.class);
	
	@Autowired
	PassportHttpClient passportHttpClient;
	private @Autowired OpenApiHttpClient openApiHttpClient;
	@Override
	public String invitedRecord(String memberId, String phone,Integer pageNo, Integer pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("phone", phone);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		System.out.println("邀请记录查询开始去调openapi===========================/inVitedRecord/queryInVitedRecord");
		String result=passportHttpClient.doPassSendStr("/inVitedRecord/queryInVitedRecord", map);
		return result;
	}
	@Override
	public String wechatInviteList(Long memberId, String searchPhone, Integer currentPage, Integer pageSize) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("searchPhone", searchPhone);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String result=openApiHttpClient.doPassSendUcenter("/promo/wechatInviteList", map);
		return result;
	}
}

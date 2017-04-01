package net.okdi.apiV4.service.impl;

import com.alibaba.fastjson.JSON;
import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV4.service.SmsTimedServiceV1;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.mob.service.SensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class SmsTimedServiceImplV1 implements SmsTimedServiceV1 {
	
	private @Autowired OpenApiHttpClient openApiHttpClient;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private InterceptService interceptService;
	@Override
	public String updateSmsTimedInfo(Long id, String number, String phone, Integer flag) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		map.put("number",number);
		map.put("phone",phone);
		map.put("flag",flag);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/updateNumOrPhone/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SmsTimedServiceImpl.updateSmsTimedInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String deleteSmsTimedInfo(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/deleteSmsTimedInfo/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SmsTimedServiceImpl.deleteSmsTimedInfo.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String sendSmsTimed(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id",id);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/sendSmsTimed/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SmsTimedServiceImpl.sendSmsTimed.001","数据请求异常");
		}
		return response;
	}

	@Override
	public String timeDelaySendSms(String groupName, Long warnTime,
			Long sendTime, Short flag, String content, Long memberId,
			String memberPhone, String accountId, String phoneAndNum,String type) {
		if(true){//验证黑白词汇，语音不验证
			//  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//
			boolean blackflag = blackFilter.isContaintSensitiveWord(content, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(content, 1);
			if(blackflag){
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				return JSON.toJSONString(errorMap);
			}
			if(flag==1){
				if(!whiteflag){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("cause", "通知内容不符合发送要求");
					sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
					return JSON.toJSONString(errorMap);
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupName",groupName);
		map.put("warnTime",warnTime);
		map.put("sendTime",sendTime);
		map.put("flag",flag);
		map.put("content",content);
		map.put("memberId",memberId);
		map.put("memberPhone",memberPhone);
		map.put("accountId",accountId);
		map.put("phoneAndNum",phoneAndNum);
		map.put("type",type);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/timeDelaySendSms", map);
		return response;
	}

	@Override
	public String aliTimeDelaySendSms(String groupName, Long warnTime, Long sendTime,
									  Short flag, String content, String templateId, Long memberId, String memberPhone,
									  String accountId, String phoneAndNum, String aliParams, String type, String pickupAddr, String version, String system) {
		Map<String, Object> map = new HashMap<>();
		map.put("groupName",groupName);
		map.put("warnTime",warnTime);
		map.put("sendTime",sendTime);
		map.put("flag",flag);
		map.put("content",content);
		map.put("templateId", templateId);
		map.put("memberId",memberId);
		map.put("memberPhone",memberPhone);
		map.put("accountId",accountId);
		map.put("phoneAndNum",phoneAndNum);
		map.put("type",type);
		map.put("aliParams", aliParams);
        map.put("pickupAddr", pickupAddr);
        map.put("version", version);
        map.put("system", system);
        return openApiHttpClient.doPassSendStr("smsTimedController/v1/aliTimeDelaySendSms", map);
	}

	@Override
	public String timeDelaySendSmsList(Long memberId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/timeDelaySendSmsList", map);
		return response;
	}

	@Override
	public String DelaySendSmsDetial(Long groupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId",groupId);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/delaySendSmsDetial", map);
		return response;
	}

	@Override
	public String updateSmsWarnTime(Long groupId, Long warnTime,String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("groupId",groupId);
		map.put("warnTime",warnTime);
		map.put("type",type);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/updateSmsWarnTime", map);
		return response;
	}

	@Override
	public String deleteSmsList(String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		String response = openApiHttpClient.doPassSendStr("smsTimedController/v1/deleteSmsList", map);
		return response;
	}

}

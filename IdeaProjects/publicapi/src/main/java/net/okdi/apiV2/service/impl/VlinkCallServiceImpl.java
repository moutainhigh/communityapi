package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.VlinkCallService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class VlinkCallServiceImpl implements VlinkCallService {

	@Autowired
	private PassportHttpClient passportHttpClient;
	@Override
	public Map<String, Object> sendSmsCode(String phone,String type,String verifyCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(phone)) {
			map.put("moblie", phone);
			map.put("verifyCode", verifyCode);
			if("findVoice".equals(type)){
				map.put("type", "findVoice");
			}else if("updateVoice".equals(type)){
				map.put("type", "updateVoice");
			}else if("regVoice".equals(type)){
				map.put("type", "regVoice");
			}else if("payVoice".equals(type)){
				map.put("type", "payVoice");
			}else if("replaceVoice".equals(type)){
				map.put("type", "replaceVoice");
			}else if("findPayVoice".equals(type)){
				map.put("type", "findPayVoice");
			}else{
				throw new ServiceException("你的验证短信码类型不符合规范为空");
			}
		} else {
			throw new ServiceException("你的电话号码为空");
		}
		try {
			Map result = passportHttpClient.doPassSendObj("register/sendVlinkCode", map);
			if(result==null){
				result = new HashMap<String,Object>();
				result.put("message", "调用通信 返回值通信失败");
				result.put("code","");
			}
			return result;
		} catch (Exception e) {
			throw new ServiceException("与通行证通信失败");
		}
	}

	
}

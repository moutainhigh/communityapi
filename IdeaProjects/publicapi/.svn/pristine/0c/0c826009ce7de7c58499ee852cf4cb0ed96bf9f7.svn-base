package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.RegisterCodeService;

@Service("registerCodeService")
public class RegisterCodeServiceImpl extends BaseService_jdw implements RegisterCodeService {
	
	@Autowired
	private PassportHttpClient passportHttpClient;

	@Override
	public Map<String, Object> newSendSmsCode(String phone, String type, String verifyCode,String version, String codeFilter) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("verifyCode", verifyCode);
		if (!PubMethod.isEmpty(phone)) {
			map.put("moblie", phone);
			map.put("codeFilter", codeFilter);
			if("findpwd".equals(type)){
				map.put("type", "findpwd");
			}else if("reg".equals(type)){
				map.put("type", "reg");
			}else if("modifyPh".equals(type)){
				map.put("type", "modifyPh");
			}else{
				throw new ServiceException("你的验证短信码类型不符合规范为空");
			}
		} else {
			throw new ServiceException("你的电话号码为空");
		}
		try {
			map.put("version", version);
			Map result = passportHttpClient.doPassSendObj("register/newSendSmsCode", map);
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

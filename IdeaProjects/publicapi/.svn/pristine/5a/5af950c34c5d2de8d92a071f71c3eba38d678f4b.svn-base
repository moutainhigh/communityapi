package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.PassportHttpClient;
import net.okdi.mob.service.OkdiAccountInfoService;

@Service
public class OkdiAccountInfoServiceImpl implements OkdiAccountInfoService{

	@Autowired
	PassportHttpClient passportHttpClient;
	
	@Override
	public String isHaveOkdiAccount(String accountType, String memberId,
			String shopId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountType", accountType);
		map.put("memberId", memberId);
		map.put("shopId", shopId);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/isHaveOkdiAccount", map);
	}

	@Override
	public String createOkdiAccount(String accountType, String memberId,
			String shopId, String accountCard, String userPhone, String nick) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountType", accountType);
		map.put("memberId", memberId);
		map.put("shopId", shopId);
		map.put("accountCard", accountCard);
		map.put("userPhone", userPhone);
		map.put("nick", nick);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/createOkdiAccount", map);
	}

	@Override
	public String isHaveBankOrWechat(String accountId, String flag) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("flag", flag);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/isHaveBankOrWechat", map);
	}

	@Override
	public String createOkdiWechatInfo(String accountId, String wechatCard) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("wechatCard", wechatCard);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/createOkdiWechatInfo", map);
	}

	@Override
	public String deleteOkdiWechatInfo(String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("id", id);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/deleteOkdiWechatInfo", map);
	}

	@Override
	public String queryOkdiWechatInfo(String accountType, String memberId,
			String shopId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountType", accountType);
		map.put("memberId", memberId);
		map.put("shopId", shopId);
		return passportHttpClient.doPassSendStr("okdiAccountInfo/queryOkdiWechatInfo", map);
	}

}

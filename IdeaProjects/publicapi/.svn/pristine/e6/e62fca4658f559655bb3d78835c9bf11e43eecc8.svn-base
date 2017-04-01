package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.PassportHttpClient;
import net.okdi.mob.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService{

	@Autowired
	PassportHttpClient passportHttpClient;
	
	@Override
	public String findBankCark(String accountId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		return passportHttpClient.doPassSendStr("bankAccount/findBankCark", map);
	}

	@Override
	public String deleteBankCark(String accountId,String id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		return passportHttpClient.doPassSendStr("bankAccount/deleteBankCark", map);
	}

	@Override
	public String insertBankCark(String accountId, String bankName,
			String memberName, String bankCard, String idNum, String memberPhone) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("accountId", accountId);
		map.put("bankName", bankName);
		map.put("memberName", memberName);
		map.put("bankCard", bankCard);
		map.put("idNum", idNum);
		map.put("memberPhone", memberPhone);
		return passportHttpClient.doPassSendStr("bankAccount/insertBankCark", map);
	}

}

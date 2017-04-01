package net.okdi.api.service.impl;

import net.okdi.api.service.SmsService;
import net.okdi.httpClient.SmsHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

	@Autowired
	private SmsHttpClient smsHttpClient;

	/**
	 * 呼叫快递哥发送短信
	 * 
	 * @param mobile
	 *            快递哥手机号
	 * @return
	 */
	@Override
	public String callExp(String mobile) {
		return smsHttpClient.callExp(mobile);
	}
}

package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.core.passport.PassportHttpClient;
import net.okdi.mob.service.MobDeviceRecordService;

@Service
public class MobDeviceRecordServiceImpl implements MobDeviceRecordService{


	@Autowired
	PassportHttpClient ucenterHttpClient;
	
	@Override
	public String record(Long memberId, String memberPhone, String channelNo, String regip, String deviceType,
			String deviceToken) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId",memberId.toString());
		map.put("memberPhone",memberPhone);
		map.put("channelNo",channelNo);
		map.put("regip",regip);
		map.put("deviceType",deviceType);
		map.put("deviceToken",deviceToken);
		return ucenterHttpClient.doPassSendStr("mobDeviceRecord/record", map);
	}

}

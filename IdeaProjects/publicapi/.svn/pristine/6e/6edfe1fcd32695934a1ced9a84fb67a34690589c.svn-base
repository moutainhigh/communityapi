package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.NoticeOrderService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeOrderServiceImpl implements NoticeOrderService {

	Logger logger = Logger.getLogger(NoticeOrderServiceImpl.class);
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	ConstPool constPool;
	@Override
	public String queryNoderDetailByCreateTime(String startTime, String endTime) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		String url = constPool.getSmsHttpUrl();
		logger.info("查询短信订单详情的url: "+url);
		String string = smsHttpClient.Post(url+"noticeOrderController/queryNoderDetailByCreateTime", map);
		logger.info("查询短信订单详情的返回数据: "+string);
		return string;
	}

}

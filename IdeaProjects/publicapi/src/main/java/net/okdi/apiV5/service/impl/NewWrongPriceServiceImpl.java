package net.okdi.apiV5.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.WrongPriceService;
import net.okdi.apiV5.service.NewWrongPriceService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
@Service
public class NewWrongPriceServiceImpl implements NewWrongPriceService{
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	Logger logger = Logger.getLogger(NewWrongPriceServiceImpl.class);
	@Override
	public String aliResend(Long memberId, String msgIds, String version, String system, String accountId,String memberPhone) {
		String result=null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("memberId", memberId);
			map.put("msgIds", msgIds);
			map.put("version", version);
			map.put("system", system);
			map.put("accountId",accountId);
			map.put("memberPhone",memberPhone);
			result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"/newSmallBell/newAliResend",map);
			logger.info("aliResend 通知异常发送失败的返回结果result: "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("淘宝单问题件重发失败,失败原因: "+e.getMessage());
		}
		return result;
	}


	@Override
	public String resend(Long memberId, String msgIds, String version, String system, String accountId,String memberPhone) {
		String result = "";
		try {
			Map<String,String> map=new HashMap<String, String>();
			map.put("memberId",memberId+"");
			map.put("msgIds",msgIds);
			map.put("version",version);
			map.put("system",system);
			map.put("accountId",accountId);
			map.put("memberPhone",memberPhone);
			result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"/newSmallBell/newResend",map);
			logger.info("resend 通知异常发送失败的返回结果result: "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("普通单一键重发失败,失败原因: "+e.getCause());
		}
		return result;
	}
}

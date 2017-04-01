package net.okdi.apiV3.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV3.service.PhoneRecordService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author jingguoqiang
 * @desc  
 */
@Service
public class PhoneRecordServiceImpl implements  PhoneRecordService{
	Logger logger = Logger.getLogger(PhoneRecordServiceImpl.class);
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	
	
	/** 
	* @Description: 插入通话记录  
	* @author: jingguoqiang
	* @date 2016-4-7 上午10:10:27 
	*/ 
	@Override
	public String savePhoneRecord(String sendPhone,String receivePhone,String flag) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sendPhone",sendPhone);
		map.put("receivePhone",receivePhone);
		map.put("flag",flag);
		String response = openApiHttpClient.doPassSendStr("phoneRecord/savePhoneRecord", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV3.service.impl.PhoneRecordServiceImpl.savePhoneRecord.001","数据请求异常");
		}
		return response;
	}


	@Override
	public String queryPhoneRecord(String sendPhone,String receivePhone, Integer currentPage,Integer pageSize) {
		Map map = new HashMap();
		    map.put("sendPhone",sendPhone);
			map.put("receivePhone",receivePhone);
			map.put("currentPage",currentPage);
			map.put("pageSize",pageSize);
		String response = openApiHttpClient.doPassSendStr("phoneRecord/queryPhoneRecord", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV3.service.impl.PhoneRecordServiceImpl.queryPhoneRecord.001","数据请求异常");
		}
		return response;
	
	}


	@Override
	public String deletePhoneRecord(String id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id",id);
		String response = openApiHttpClient.doPassSendStr("phoneRecord/deletePhoneRecord", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV3.service.impl.PhoneRecordServiceImpl.deletePhoneRecord.001","数据请求异常");
		}
		return response;
	}

	

	

	
}



package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.WrongPriceService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
@Service
public class WrongPriceServiceImpl implements WrongPriceService{
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	@Override
	public String query(Long memberId, Integer pageSize, Integer pageNo, String flag) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("pageNo",pageNo+"");
		map.put("pageSize",pageSize+"");
		map.put("flag",flag+"");
		return smsHttpClient.Post(constPool.getSmsHttpUrl()+"/wrongPrice/query",map);
	}

	@Override
	public Long queryCount(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getOpenApiUrl()+"/exceptionsNoSign/query",map);
		Long smsNum=0l;
		if(!PubMethod.isEmpty(result)){
			smsNum+=this.getNum(result);			
		}
		return smsNum;
	}
	
	@Override
	public Long queryWrong(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/wrongPrice/queryCount",map);
		Long smsNum=0l;
		if(!PubMethod.isEmpty(result)){
			smsNum+=this.getNum(result);			
		}
		return smsNum;
	}
	
	private Long getNum(String result){
		Long num=0l;
		JSONObject json=JSONObject.parseObject(result);
		if(json.getBooleanValue("success")){
			if(!PubMethod.isEmpty(json.get("data"))){
				num=json.getLong("data");
			}
		}
		return num;
	}

	@Override
	public String aliResend(Long memberId, String msgIds) {
		String result=null;
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("memberId", memberId);
			map.put("msgIds", msgIds);
			result = smsHttpClient.Post(constPool.getSmsHttpUrl()+"/smallBell/aliResend",map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("淘宝单问题件重发失败,失败原因: "+e.getMessage());
		}
		return result;
	}

	@Override
	public Long queryAliWrong(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/wrongPrice/queryAliWrong",map);
		Long smsNum=0l;
		if(!PubMethod.isEmpty(result)){
			smsNum+=this.getNum(result);			
		}
		return smsNum;
	}
}

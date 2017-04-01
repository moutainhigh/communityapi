package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.mob.service.SensitiveWordService;
@Service
public class SensitiveWordServiceImpl implements SensitiveWordService{

	@Autowired
	OpenApiHttpClient openApiHttpClient;

	Logger logger = Logger.getLogger(ShopBroadcastInfoServiceImpl.class);
	
	@Override
	public Set<String> queryBlackList() {
		Set<String> set=null;
		Map<String, String> params = new HashMap<String, String>();
		String result = openApiHttpClient.doPassSendStr("sensitiveWord/queryBlackList", params);
		Map<String,String> map=new HashMap<String,String>();
		set=new HashSet<String>();
		JSONObject fromObject=JSONObject.parseObject(result);
		JSONArray jsonArray = fromObject.getJSONArray("data");
		List<String> list1 = JSON.parseArray(jsonArray.toString(),String.class);
		for(String o:list1){
			set.add(o);
		}
		return set;
	}

	@Override
	public Set<String> queryWhiteList() {
		Set<String> set=null;
		Map<String, String> params = new HashMap<String, String>();
		String result = openApiHttpClient.doPassSendStr("sensitiveWord/queryWhiteList", params);
		Map<String,String> map=new HashMap<String,String>();
		set=new HashSet<String>();
		JSONObject fromObject=JSONObject.parseObject(result);
		JSONArray jsonArray = fromObject.getJSONArray("data");
		List<String> list1 = JSON.parseArray(jsonArray.toString(),String.class);
		for(String o:list1){
			set.add(o);
		}
		return set;
	}

	@Override
	public String addWrongNumber(String memberId,String phone,String sendContent){
		Map<String,String> map=new HashMap<String,String>();
		map.put("memberId",memberId);
		map.put("phone",phone);
		map.put("sendContent",sendContent);
		String result = openApiHttpClient.doPassSendStr("sensitiveWord/addWrongNumber", map);
		return result;
	}
	/**
	 * 查询错误次数，判断是否可以发短信
	 * @param memberId 用户id
	 */
	@Override
	public String queryWrongNumber(String memberId){
		Map<String,String> map=new HashMap<String,String>();
		map.put("memberId",memberId);
		String result = openApiHttpClient.doPassSendStr("sensitiveWord/queryWrongNumber", map);
		return result;
	}

	@Override
	public void removeWrongNumber(String memberId){
		Map<String,String> map=new HashMap<String,String>();
		map.put("memberId",memberId);
		String result = openApiHttpClient.doPassSendStr("sensitiveWord/removeWrongNumber", map);
	}

	@Override
	public List<String> queryBlackCollen() {
		Map<String,String> map=new HashMap<String,String>();
		List<String> list1=null;
		try {
			String result = openApiHttpClient.doPassSendStr("sensitiveWord/queryBlackCollen", map);
			JSONObject parseObject = JSONObject.parseObject(result);
			JSONArray jsonArray = parseObject.getJSONArray("data");
			list1 = JSON.parseArray(jsonArray.toString(),String.class);
		} catch (ServiceException e) {
			//e.printStackTrace();
			logger.info("new 查询敏感词失败, 失败原因: "+e.getStackTrace());
		}
		return list1;
	}

}

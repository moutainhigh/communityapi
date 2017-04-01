package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.LoginService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.util.Base64;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

@Service
public class LoginServiceImpl  extends AbstractHttpClient implements LoginService {
	@Autowired
	private ConstPool constPool; 
	
	@Autowired
	private PassportHttpClient passportHttpClient;
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public Map<String, Object> login(String channelNo, String userName, String password, String deviceType,
			String deviceToken, String version, String address, Integer source) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!PubMethod.isEmpty(userName) && !PubMethod.isEmpty(password)) {
			map.put("mobile", userName);
			map.put("password", deBase64Pwd(password));
		} else {
			throw new ServiceException("你的电话号码或密码为空");
		}
		map.put("source", source);
		Map result = null;
		try {
			//{"memberId":"3111399625520007","success":true}   记录日志,添加钱包
			result = passportHttpClient.doPassSendObj("user/mobLogin", map);
		} catch (Exception e) {
			throw new ServiceException("连接通行证系统错误");
		}
		if(result.get("success")==null||!"true".equals(result.get("success").toString())){
			throw new ServiceException("publicapi.0001", "用户名或密码不正确");
		}
		String str = null;
		Map resultMap = null;
		for(int i = 0 ; i < 5; i++){
			String memberId = result.get("memberId").toString();
			Map<String,String> phoneMap = new HashMap<String,String>();
			phoneMap.put("channelNo", channelNo);
			phoneMap.put("memberId", memberId);
			phoneMap.put("deviceType", deviceType);
			phoneMap.put("deviceToken", deviceToken);
			phoneMap.put("version", version);
			//m.put("loginIn", "success");
			resultMap = openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", phoneMap);
			System.out.println("（phoneLoginIn）login方法--net.okdi.apiV1.service.impl.LoginServiceImpl.login！！！deviceType="+deviceType+"!!!"+i);
			if("success".equals(resultMap.get("loginIn")))
				break;
		}
		//mobMemberLoginService.saveOrUpdateMobMemberLogin(channelNo, Long.parseLong(memberId), deviceType, deviceToken, version, address);
		return result;
	}
	private String deBase64Pwd(String password){
		try{
			return new String(Base64.decode(password.getBytes()));
		}catch(Exception e){
			throw new ServiceException("密码: "+password+" 反向解析错误");
		}
	}
	@Override
	public String longResult(String  userName,String password,Integer source) {
		String str = "";
		Map<String,String> mapU = new HashMap<String,String>();
		mapU.put("mobile", userName); 
//		mapU.put("password", password); 
		mapU.put("password",  deBase64Pwd(password));
		
		String urlUcenter = constPool.getPassPortUrl()+"user/checkPasswordIsRight";
		
		String ucResult = Post(urlUcenter, mapU);
		
		String success=String.valueOf(JSON.parseObject(ucResult).get("success"));
		if(!"true".equals(success)){
			throw new ServiceException("访问失败");
		}
		String rsultdata=String.valueOf(JSON.parseObject(ucResult).get("data"));
		Map<String, String> parseMap = JSON.parseObject(rsultdata,Map.class);
		String isRight = String.valueOf(parseMap.get("isRight"));
		if("1".equals(isRight)){
			Map<String,String> mapP = new HashMap<String,String>();
			mapP.put("mobile", userName); 
			//str = openApiHttpClient.doPassSendStr("queryNearInfo/getValidationStatus/", mapP);
			String url = constPool.getOpenApiUrl()+"queryNearInfo/getValidationStatus";
			str = Post(url, mapP);
		}else{
			Map<String,Object> mapP = new HashMap<String,Object>();
			mapP.put("success", false); 
			mapP.put("errCode", "0000008888"); 
			mapP.put("message", "用户名或密码不正确"); 
			return JSON.toJSONString(mapP);
		}
		return str;
//		
//			Map<String,Object> ResultMap = new HashMap<String,Object>();
//			if(map.get("success").toString().equals("false")){
//				return JSON.toJSONString(map);
//			} 
//			Long memberId =  Long.parseLong(map.get("memberId").toString());
//			ResultMap.put("memberId",memberId);
//			this.setauThentiCate(ResultMap,memberId);
//			ResultMap.put("head_phone_Url",constPool.getReadPath()+constPool.getHead()+memberId+".jpg");
//			ResultMap.put("success",true);
//			return JSON.toJSONString(ResultMap);
	}
	public Map<String,Object> setauThentiCate(Map<String, Object> ResultMap, Long memberId) {
		
		//查看审核通过或者是失败(拒绝) 还有等待
		Map<String, Object> validationStatusMap = this.getValidationStatus(memberId);
		if(null != validationStatusMap){
		if(validationStatusMap.get("success").toString().equals("true")){
			  if(null == validationStatusMap.get("data"))
			  return null;
			  String memberflagStr =  validationStatusMap.get("data").toString();
			  if(PubMethod.isEmpty(memberflagStr)){
				  Map<String,Object> CompInfoMap  = JSONObject.parseObject(memberflagStr);
				  ResultMap.put("roleId", CompInfoMap.get("roleId"));
				  ResultMap.put("compId", CompInfoMap.get("compId"));
				  ResultMap.put("compStatus", CompInfoMap.get("compStatus")); //-1创建 0提交待审核 1审核通过 2审核失败
				  if(null == CompInfoMap.get("relationFlag") || "".equals(CompInfoMap.get("relationFlag")) || "null".equals(CompInfoMap.get("relationFlag"))){
					  ResultMap.put("relationFlag","-1");
				  } else {
					  ResultMap.put("relationFlag",CompInfoMap.get("relationFlag"));
				  }
				  if(null == CompInfoMap.get("veriFlag") || "".equals(CompInfoMap.get("veriFlag")) ||  "null".equals(CompInfoMap.get("veriFlag"))){
					  ResultMap.put("veriFlag","-1");
				  } else {
					  ResultMap.put("veriFlag",CompInfoMap.get("veriFlag"));
				  }
			  } 
		  }
		}                  
		  return ResultMap;
	}
	private Map<String, Object> getValidationStatus(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendObj("queryNearInfo/getValidationStatus",paraMeterMap);
	}
}

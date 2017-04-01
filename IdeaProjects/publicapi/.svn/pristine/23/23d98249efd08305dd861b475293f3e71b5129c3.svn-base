/**  
 * @Project: publicapi_use
 * @Title: LoginHandler.java
 * @Package net.okdi.core.common.handleMessage
 * @author xiangwei.liu
 * @date 2015-11-5 下午8:29:16
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.core.common.handleMessage;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.PassportHttpClient;
import net.okdi.core.util.Base64;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

/**
 * @author xiangwei.liu
 * @version V1.0
 */
public class LoginHandler {
	@Autowired
	private ConstPool constPool; 
	@Autowired
	private PassportHttpClient passportHttpClient;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	public static final Log logger = LogFactory.getLog(LoginHandler.class);
	public void handleMessage(String message) { 
		if(PubMethod.isEmpty(message)){
			return;
		}
		try {
			
			
			Map<String,Object> parseMap = JSON.parseObject(message);
			if(PubMethod.isEmpty(parseMap))return;
			String  channelNo    =   PubMethod.isEmpty(parseMap.get("channelNo"))?"":parseMap.get("channelNo").toString();
			String  userName     =   PubMethod.isEmpty(parseMap.get("userName"))?"":parseMap.get("userName").toString();
			String  deviceType   =   PubMethod.isEmpty(parseMap.get("deviceType"))?"":parseMap.get("deviceType").toString();
			String  deviceToken  =   PubMethod.isEmpty(parseMap.get("deviceToken"))?"":parseMap.get("deviceToken").toString();
			String  version      =   PubMethod.isEmpty(parseMap.get("version"))?"":parseMap.get("version").toString();
			Integer source       =   PubMethod.isEmpty(parseMap.get("source"))?0:Integer.parseInt(parseMap.get("source").toString());
			String  memberId     =   PubMethod.isEmpty(parseMap.get("memberId"))?"":parseMap.get("memberId").toString();
			String  regIp        =   PubMethod.isEmpty(parseMap.get("regIp"))?"":parseMap.get("regIp").toString();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("mobile", userName);
			map.put("memberId", memberId);
			map.put("source", source);
			try {
				passportHttpClient.doPassSendObj("user/mobLoginAccount", map);
			} catch (Exception e) {
				throw new ServiceException("连接通行证系统错误"); 
			}
			try {
				for(int i = 0 ; i < 5; i++){
					Map<String,String> phoneMap = new HashMap<String,String>();
					phoneMap.put("channelNo", channelNo);
					phoneMap.put("memberId", memberId);
					phoneMap.put("deviceType", deviceType);
					phoneMap.put("deviceToken", deviceToken);
					phoneMap.put("version", version);
					Map<String,Object> resultMap = openApiHttpClient.doPassSendObj("mobPush/phoneLoginIn", phoneMap);
					System.out.println("（phoneLoginIn）handleMessage队列方法--net.okdi.core.common.handleMessage.LoginHandler.handleMessage！！！deviceType="+deviceType+"！！！！"+i);
					if(PubMethod.isEmpty(resultMap)|| PubMethod.isEmpty(resultMap.get("loginIn"))) throw new  ServiceException("记录推送信息日志失败");
					if("success".equals(resultMap.get("loginIn").toString()))	break;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				 throw new  ServiceException("记录推送信息日志失败");
			}
			
			try{
				//(Long memberId, String memberPhone, String channelNo, String regip, String deviceType, String deviceToken)
				
				logger.info("调用ucenter同步mob_devihce_record数据:memberPhone:"+userName+"  memberId:"+memberId+"  channelNo:"+channelNo+" regip:"+regIp+" deviceType:"+deviceType+" deviceToken:"+deviceToken);
				Map<String, Object> map1 = new HashMap<String, Object>();
				map1.put("memberId", memberId);
				map1.put("memberPhone", userName);
				map1.put("channelNo", channelNo);
				map1.put("regip", regIp);
				map1.put("deviceType", deviceType);
				map1.put("deviceToken", deviceToken);
				
				passportHttpClient.doPassSendObj("mobDeviceRecord/record", map1);
			}catch (Exception e) {
				e.printStackTrace();
				 throw new  ServiceException("记录mob_device_record数据失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String deBase64Pwd(String password){
		try{
			return new String(Base64.decode(password.getBytes()));
		}catch(Exception e){
			throw new ServiceException("密码: "+password+" 反向解析错误");
		}
	}
}

package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.impl.ExpressUserServiceImpl;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
@Service
public class TelephoneRewardServiceImpl implements TelephoneRewardService {

	@Autowired
	private RedisService redisService;
	//注册弹窗
	@Value("${reg_bounced_url}")
	private String regBouncedUrl;
	//登录弹窗
	@Value("${login_bounced_url}")
	private String loginBouncedUrl;
	//实名认证弹窗
	@Value("${relNameCert_url}")
	private String relNameCertUrl;
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private ExpGatewayService expGatewayService;
	Logger log = Logger.getLogger(ExpressUserServiceImpl.class);
	@Override
	public String loginReceiveRewardNum(String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/loginReceiveRewardNum", map);
		/*if(!PubMethod.isEmpty(result)){
			JSONObject parseObject = JSONObject.parseObject(result);
			if(parseObject.getBooleanValue("success")){
				String str = parseObject.getString("data");
				if(){
					
				}
			}
		}*/
		return result;
	}

	@Override
	public String getSMSOrPhoneNum(String accountId, String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/getSMSOrPhoneNum", map);
		return result;
	}

	@Override
	public Map<String,String> queryIsGetReward(String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/queryIsGetReward", map);
		System.out.println("根据memberId查询是否领取过奖励的接口 queryIsGetReward {{{{{{{{{}}}}}}}}::::::::result: "+result+"==phone: "+phone);
		if("001".equals(result) || "003".equals(result)){//已经领取过, bool 为false, 或者没有奖励活动
			map.put("rewBool", "false");//返回boolean类型是否有url,true 有url,false 没有
			map.put("rewRewardUrl", "");
		}else {//未领取过, bool 为true
			map.put("rewBool", "true");//返回boolean类型是否有url,true 有url,false 没有
			map.put("rewRewardUrl", loginBouncedUrl);
		}
		System.out.println("每天领取免费奖励的弹窗:{}{}{}{{{}}}}:::>>>>>>>>>>>>>>>>>>>>>>>: "+regBouncedUrl);
		return map;
	}

	@Override
	public Map<String,String> queryIsGetRegister(String memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassSendStr("marketManage/queryIsGetRegister", map);
		System.out.println("根据memberId查询是否领取过注册奖励的接口:memberId: "+memberId+"==返回的参数result: "+result);
		if("001".equals(result) || "003".equals(result)){//已经领取过, bool 为false, 或者没有奖励活动
			map.put("regBool", "false");//返回boolean类型是否有url,true 有url,false 没有
			map.put("regRewardUrl", "");
		}else {//未领取过, bool 为true
			map.put("regBool", "true");//返回boolean类型是否有url,true 有url,false 没有
			map.put("regRewardUrl", regBouncedUrl);
		}
		System.out.println("注册是否领取的弹窗::{{{{}}}}::::::<<<<<<<<<<<<<<<<<<<<<<<<<<<<<: "+loginBouncedUrl);
		return map;
	}

	/*@Override
	public void updateLoginAndRegStatus(String phone, String memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassSendStr("marketManage/updateLoginAndRegStatus", map);
	}*/

	@Override
	public void updateLoginStatus(String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/updateLoginStatus", map);
	}

	@Override
	public void updateRegisterStatus(String memberId) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberId", memberId);
		String result = openApiHttpClient.doPassSendStr("marketManage/updateRegisterStatus", map);
	}

	@Override
	public String isReceiveLoginReward(String phone) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/isReceiveLoginReward", map);
		return result;
	}


	@Override
	public String queryAuditItemByPhone(String phone) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", phone);
		String result = openApiHttpClient.doPassSendStr("marketManage/queryAuditItemByPhone", map);
		return result;
	}


	@Override
	public String getSMSOrPhoneNum(Long memberId,String accountId) {
		String memberInfo = this.expGatewayService.getMemberInfoById(memberId);
		Map<String, Object> memberMap = JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		log.info(memberId+"：查询出发送人的登陆号码为："+memberPhone);
		Map<String,String> map = new HashMap<String,String>();
		map.put("accountId", accountId);
		map.put("phone", memberPhone);
		String result = openApiHttpClient.doPassSendStr("marketManage/getSMSOrPhoneNum", map);
		return result;
	}

}

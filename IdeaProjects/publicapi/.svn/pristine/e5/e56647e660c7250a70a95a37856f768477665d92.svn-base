package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV4.service.SendSmsAndPhoneService;
import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class SendSmsAndPhoneServiceImpl extends BaseService_jdw implements SendSmsAndPhoneService {
	
	private static Logger logger = Logger.getLogger(SendSmsAndPhoneServiceImpl.class);
//	@Autowired
//	private additionalMemberInfoMapper additionalMemberInfo;
	
	@Autowired
	private SmsHttpClient smsHttpClient;
	//2015 08 20 kai.yang添加
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private InterceptService interceptService;
	@Autowired
	private ExpGatewayService expGatewayService;
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 */
	@Override
	public String sendSms(String accountId,String smsContent,String content,String phoneAndWaybillNum,String smsAndWaybillNum,Long memberId,boolean isOld,boolean isValidate,
			 Short isNum){
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString());
		JSONObject jsons=JSONObject.parseObject(resultvalue);
		String success=jsons.getString("success").toString();
		if("false".equals(success)){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause",jsons.getString("message"));
			return JSON.toJSONString(errorMap);
		}
		//获取发送人的电话号码
		String memberInfo = this.expGatewayService.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		logger.info("需要验证的内容："+content);
		if(isValidate){//验证黑白词汇，语音不验证
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(content, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(content, 1);
			if(blackflag){
				this.interceptService.saveIntercept(accountId, "07", "", smsAndWaybillNum, memberId, memberPhone, (short)7, content, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag){
				this.interceptService.saveIntercept(accountId, "07", "", smsAndWaybillNum, memberId, memberPhone, (short)7, content, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容不符合发送要求");
				sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				return JSON.toJSONString(errorMap);
			}
		}
		
		int smsLength=content.length();
		int newSmsLength=content.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
			return JSON.toJSONString(errorMap);
		}
		
		String channelNO = "03";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		smsAndWaybillNum =  SetSmsContentNew(content,smsAndWaybillNum,memberPhone,null,null);
		if(PubMethod.isEmpty(smsAndWaybillNum)){
			throw new ServiceException("发送通知异常");	
		}
		String doSmsSend = smsHttpClient.sendSmsAndPhone
				(accountId,channelNO, content,memberId,memberPhone, (short)7,phoneAndWaybillNum,smsAndWaybillNum);
			if(PubMethod.isEmpty(doSmsSend)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}else{
				JSONObject json=JSONObject.parseObject(doSmsSend);
				if(json.getBooleanValue("success")){
					sensitiveWordService.removeWrongNumber(memberId.toString());
				}
			}
			return 	doSmsSend;
	}
}
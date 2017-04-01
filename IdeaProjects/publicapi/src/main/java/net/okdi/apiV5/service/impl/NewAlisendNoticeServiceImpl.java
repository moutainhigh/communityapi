package net.okdi.apiV5.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.apiV5.service.NewAlisendNoticeService;
import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.RegisterService;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;

@Service
public class NewAlisendNoticeServiceImpl extends BaseService_jdw  implements NewAlisendNoticeService {
	private static Logger logger = Logger.getLogger(NewAlisendNoticeServiceImpl.class);
	
	
//	@Autowired
//	private additionalMemberInfoMapper additionalMemberInfo;
	
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Autowired
	private RegisterService registerService;
	@Autowired
	private TaskExecutor taskExecutor;
	//2015 08 20 kai.yang添加
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private InterceptService interceptService;
//	private static SensitivewordFilter blackFilter = null;
//	private static SensitivewordFilter whiteFilter = null;

	@Autowired
	private TaskMassNoticeService taskMassNoticeService;
	@Autowired
	private SmsSendService smsSendService;
	private static Integer ONESENDNUM=500;//1000;  //群发通知每次提交上限
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 */
	@Override
	public String newSendAliSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			String md5Hex, String versionCode, String timeStamp, String system){//
		if(!PubMethod.isEmpty(msgId)){
			flag = 4;
		}
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString());
		JSONObject jsons=JSONObject.parseObject(resultvalue);
		String success=jsons.getString("success").toString();
		if("false".equals(success)){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause",jsons.getString("message"));
			errorMap.put("flag", "");
			return JSON.toJSONString(errorMap);
		}
		//获取发送人的电话号码
		String memberInfo = this.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		logger.info("阿里大于需要验证的内容："+smsTemplate);
		/*if(isValidate){
			//验证繁体字
			char[] charArray = smsTemplate.toCharArray();
			for (char c : charArray) {
				boolean b = ChineseHelper.isTraditionalChinese(c);
				if(b){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加"+constPool.getCustomerQq());
					errorMap.put("flag", "2");
					taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
					taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
					return JSON.toJSONString(errorMap);
				}
			}
			//验证黑白词汇，语音不验证
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(smsTemplate, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(smsTemplate, 1);
			if(blackflag){
				this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				errorMap.put("flag", "2");
				sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				logger.info("首页群发短信,清空缓存..........黑名单");
				taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return JSON.toJSONString(errorMap);
			}
			if(flag==9){//新加阿里大于短信
				if(!whiteflag){
					this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
					errorMap.put("flag", "2");
					sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
					logger.info("首页群发短信,清空缓存..........白名单");
					taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
					taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
					return JSON.toJSONString(errorMap);
				}
			}
		}*/
		
		int smsLength=smsTemplate.length();
		int newSmsLength=smsTemplate.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求!!!");
			errorMap.put("flag", "");
			taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
			taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
			sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
			return JSON.toJSONString(errorMap);
		}
		
		String channelNO = "03";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		String compId = String.valueOf(memberMap.get("compId"));//快递员所属站点id
		if(PubMethod.isEmpty(compId)){
			compId = "33620";
		}
		String phoneAndWaybillNumNew =  SetSmsContentThreeNew(smsTemplate,phoneAndWaybillNum);
		//正常发送成功的短信才会给存入到黑名单中
		String[] arrs = phoneAndWaybillNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("flag","");
			errorMap.put("cause", "群发通知-短信，每次发送数量最多为"+this.ONESENDNUM+"条");//每次最多1000条
			taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
			taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
			return JSON.toJSONString(errorMap);
			}
			String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length); //每天5000条
			JSONObject json=JSONObject.parseObject(res);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
			res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length);//每小时1000条
			json=JSONObject.parseObject(res);
			logger.info("每小时发送返回的json串-短信: "+json);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
		logger.info("开始调用发送短信接口,,,....smsHttpClient.doSmsSend_HJZX........................................");
		String doSmsSend = smsHttpClient.newDoAliSendSms
				(accountId,channelNO, phoneAndWaybillNumNew,memberId,memberPhone, flag,voiceUrl,msgId,isNum, pickupTime,pickupAddr, compName, versionCode,system);
			if(PubMethod.isEmpty(doSmsSend)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}else{
				JSONObject jsonn=JSONObject.parseObject(doSmsSend);
				if(jsonn.getBooleanValue("success")){
					sensitiveWordService.removeWrongNumber(memberId.toString());
				}
			}
			return 	doSmsSend;
	}
	
	public String getMemberInfoById(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("memberInfo/getMemberInfoById",paraMeterMap);	
	}
}

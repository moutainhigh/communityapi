package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.MsgTemplateService;
import net.okdi.apiV4.service.SmsTemplateService;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

	Logger logger = Logger.getLogger(SmsTemplateServiceImpl.class);
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	
	@Autowired
	private MsgTemplateService msgTemplateService;
	
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Override
	public String querySmsTemplateContent(Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		String url = constPool.getSmsHttpUrl();
		String result = smsHttpClient.Post(url+"addedLogic/querySmsTemplateContent", map);
		return result;
	}
	@Override
	public String saveSmsTemplateContent(Long memberId, String content,
			String name, Long phone) {
		/*Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("content", content);
		map.put("name", name);
		map.put("phone", phone);
		String url = constPool.getSmsHttpUrl();
		//不验证黑白词,直接保存为待审核
		msgTemplateService.saveTemplate("", content, phone+"", memberId+"");
		String result = smsHttpClient.Post(url+"addedLogic/saveSmsTemplateContent", map);
		 */		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateName",memberId+"--"+phone);
		map.put("templateContent",content);
		map.put("phone",phone);
		map.put("memberId",memberId);
		logger.info("*************老版本新添加模板调用新版本接口添加************** map: "+map);
		String response = smsHttpClient.Post(constPool.getSmsHttpUrl()+"msgTemplate/saveTemplate/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.SmsTemplateServiceImpl.saveSmsTemplateContent.001","数据请求异常,可能带有敏感词");
		}
		return "{\"success\":true}";
	}
	@Override
	public String updateSmsTemplateContent(Long id, Long memberId,
			String content) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("memberId", memberId);
		map.put("content", content);
		String url = constPool.getSmsHttpUrl();
		String result = smsHttpClient.Post(url+"addedLogic/updateSmsTemplateContent", map);
		return result;
	}
	@Override
	public String deleteSmsTemplateContent(Long id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		String url = constPool.getSmsHttpUrl();
		String result = smsHttpClient.Post(url+"addedLogic/deleteSmsTemplateContent", map);
		return result;
	}
	@Override
	public String killToken(String token) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("token", token);
		String url = constPool.getSmsHttpUrl();
		String result = smsHttpClient.Post(url+"addedLogic/killToken", map);
		logger.info("请求短信返回的结果result: "+result+" 要存入的token: "+token);
		JSONObject parseObject = JSONObject.parseObject(result);
		Map<String,Object> maps = new HashMap<String,Object>();
		if(parseObject.getBooleanValue("success")){
			String string = parseObject.getString("data");
			if("1".equals(string)){
				maps.put("data", token);
				maps.put("success", true);
			}else{
				maps.put("data", token);
				maps.put("success", false);
			}
		}else{
			maps.put("data", token);
			maps.put("success", false);
		}
		return JSON.toJSONString(maps);
	}
	@Override
	public String dealTemplate(Long memberId) {
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("memberId", memberId);
		String url = constPool.getSmsHttpUrl();
		String result = smsHttpClient.Post(url+"dealBusiness/dealTemplate", map1);
		//String templateContents = this.querySmsTemplateContent(memberId);
		try {
			JSONObject parseObject = JSONObject.parseObject(result);
			if(!PubMethod.isEmpty(parseObject)){
				JSONArray jsonArray = parseObject.getJSONArray("data");
				int size = jsonArray.size();
				
				Map<String,Object> map = null;
				logger.info("根据memberId:"+memberId+"查询出该人下有多少模板 jsonArray: "+jsonArray+" ,size: "+size);
				for(int i = 0;i < size; i++){
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String templateContent = jsonObject.getString("templateContent");
					Long _id = jsonObject.getLong("id");
					map = new HashMap<String,Object>();
					//如果更改了内容, 则需先通过敏感词
					logger.info("_id: "+_id+" ,templateContent: "+templateContent);
					String validation=null;
					try {
						validation = this.validation(templateContent);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					if(!PubMethod.isEmpty(validation)){
						map.put("auditStatus", (short)2);//如果包含敏感词,审核状态不通过
						logger.info("包含的敏感词为validation: "+validation);
					}else{
						logger.info("不包含敏感词,审核状态为0,待审核");
						map.put("auditStatus", (short)0);//不包含敏感词,待审核
					}
					//map.put("number", i);
					map.put("id", _id);
					map.put("memberId", jsonObject.getLong("memberId"));
					map.put("content", templateContent);
					logger.info("处理的模板内容为templateContent: "+templateContent+" 是否通过敏感词,auditStatus: "+map.get("auditStatus"));
					String result2 = smsHttpClient.Post(url+"addedLogic/updateSmsTemplateContent", map);
					logger.info("更新返回的结果result2: "+result2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//logger.info("程序抛异常了,异常原因: "+e.getStackTrace());
		}
		return null;
	}
	@Override
	public String dealTemplatePhone() {
		Map<String,Object> map = new HashMap<String,Object>();
		String url = constPool.getSmsHttpUrl();
		String result2 = smsHttpClient.Post(url+"dealBusiness/dealTemplatePhone", map);
		logger.info("publicapi中根据memberId处理模板手机号phone返回的result: "+result2);
		return null;
	}

	@Override
	public String isBelongBlackAndWrite(String templateContent) {
		//先处理敏感词过滤，有敏感词则讲敏感词集合返回给手机端进行展示
		Set<String> whiteSet=sensitiveWordService.queryWhiteList();
		Set<String> blackSet=sensitiveWordService.queryBlackList();
		SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
		SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
		boolean blackflag = blackFilter.isContaintSensitiveWord(templateContent, 1);
		boolean whiteflag = whiteFilter.isContaintSensitiveWord(templateContent, 1);

		Map<String, Object> result = new HashMap<>();
		if(!whiteflag){
			Map<String,Object> errorMap = new HashMap<>();
			errorMap.put("success",true);
			errorMap.put("data", new HashSet<>());
			errorMap.put("errorMsg", "通知内容不符合发送要求!!!");
			result.put("data", errorMap);
			result.put("whiteflag", false);
			return JSON.toJSONString(result);
		}else if(blackflag){  //处理黑名单，不包含黑名单词汇
			Set<String> words = blackFilter.getSensitiveWord(templateContent, 1);//获取命中的敏感词
			if(words != null && words.size() >0){
				Map<String,Object> errorMap = new HashMap<>();
				errorMap.put("success",true);
				errorMap.put("data", words);
				errorMap.put("errorMsg", "模板中含有敏感词："+words);
				result.put("data", errorMap);
				result.put("blackflag", false);
				return JSON.toJSONString(result);
			}
		}

		result.put("whiteflag", true);
		result.put("blackflag", true);
		return JSON.toJSONString(result);
	}

	public String validation(String content){
		if(true){//验证黑白词汇，语音不验证
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(content, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(content, 1);
			if(blackflag){
				//this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求!");
				//sensitiveWordService.addWrongNumber(memberId.toString(),phone,content);
				logger.info("添加新模板中有黑名单的词,不能保存......");
				//taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag){
				//this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容不符合发送要求!!");
				//sensitiveWordService.addWrongNumber(memberId.toString(),phone,content);
				logger.info("添加新模板中没有白名单的词,不能保存......");
				//taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return JSON.toJSONString(errorMap);
			}
		}
		return null;
	}
}

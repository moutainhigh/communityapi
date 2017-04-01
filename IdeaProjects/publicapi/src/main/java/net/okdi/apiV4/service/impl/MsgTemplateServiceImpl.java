package net.okdi.apiV4.service.impl;

import java.util.HashMap;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.okdi.api.exception.ServiceException;
import net.okdi.apiV4.service.MsgTemplateService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;

/**
 * @author zhaohu
 *
 */
@Service
public class MsgTemplateServiceImpl extends AbstractHttpClient implements MsgTemplateService  {
	
	Logger logger = Logger.getLogger(MsgTemplateServiceImpl.class);

	@Autowired
    private ConstPool constPool;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	
	@Override
	public String queryTemplateList(String memberId, Short auditStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId",memberId);
		map.put("auditStatus",auditStatus);
		String response = this.Post(constPool.getSmsHttpUrl()+"msgTemplate/queryTemplateList/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MsgTemplateServiceImpl.queryTemplateList.001","数据请求异常");
		}
		return response;
	}
	    
	@Override
	public String deleTemplate(String templateId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("templateId",templateId);
		String response = this.Post(constPool.getSmsHttpUrl()+"msgTemplate/deleTemplate/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MsgTemplateServiceImpl.deleTemplate.001","数据请求异常");
		}
		return response;
	}
	    
	/*@Override
	public String saveTemplate(String templateName, String templateContent,
			String phone, String memberId) {
		//验证繁体字
		char[] charArray = templateContent.toCharArray();
		for (char c : charArray) {
			boolean b = ChineseHelper.isTraditionalChinese(c);
			if(b){
				//改动前
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",true);
				errorMap.put("data", new HashSet<>());
				errorMap.put("errorMsg", "通知内容包含繁体字不符合发送要求");
				return JSON.toJSONString(errorMap);
			
			}
		}
		//先处理敏感词过滤，有敏感词则讲敏感词集合返回给手机端进行展示
		Set<String> whiteSet=sensitiveWordService.queryWhiteList();
		Set<String> blackSet=sensitiveWordService.queryBlackList();
		SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
		SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
		boolean blackflag = blackFilter.isContaintSensitiveWord(templateContent, 1);
		boolean whiteflag = whiteFilter.isContaintSensitiveWord(templateContent, 1);
		//封装和传递模板信息的map集合
		Map<String, Object> map = new HashMap<String, Object>();
		
		//1.先处理白名单，必须包含白名单
		if(!whiteflag){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",true);
			errorMap.put("data", new HashSet<>());
			errorMap.put("errorMsg", "通知内容不符合发送要求!!!");
			return JSON.toJSONString(errorMap);
			
			//2.处理黑名单，不包含黑名单词汇
		}else if(blackflag){
			Set<String> words = blackFilter.getSensitiveWord(templateContent, 1);//获取命中的敏感词
			if(words != null && words.size() >0){
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",true);
				errorMap.put("data", words);
				errorMap.put("errorMsg", "模板中含有敏感词："+words);
				return JSON.toJSONString(errorMap);
				
			}
		}
		
		map.put("templateName",templateName);
		map.put("templateContent",templateContent);
		map.put("phone",phone);
		map.put("memberId",memberId);
		
		String response = this.Post(constPool.getSmsHttpUrl()+"msgTemplate/saveTemplate/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MsgTemplateServiceImpl.saveTemplate.001","数据请求异常");
		}
		return response;
	}*/
	@Override
	public String saveTemplate(String templateName, String templateContent,
			String phone, String memberId) {
		//封装和传递模板信息的map集合
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackWord", "");
		map.put("auditStatus", (short)3);
		//验证繁体字
		char[] charArray = templateContent.toCharArray();
		for (char c : charArray) {
			boolean b = ChineseHelper.isTraditionalChinese(c);
			if(b){
				//改动前
				/*Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",true);
				errorMap.put("data", new HashSet<>());
				errorMap.put("errorMsg", "通知内容包含繁体字不符合发送要求");
				return JSON.toJSONString(errorMap);*/
				map.put("blackWord", c);
				logger.info("通知内容包含繁体字:"+c);
				map.put("auditStatus", (short)0);
			}
		}
		//先处理敏感词过滤，有敏感词则讲敏感词集合返回给手机端进行展示
		Set<String> whiteSet=sensitiveWordService.queryWhiteList();
		Set<String> blackSet=sensitiveWordService.queryBlackList();
		SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
		SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
		boolean blackflag = blackFilter.isContaintSensitiveWord(templateContent, 1);
		boolean whiteflag = whiteFilter.isContaintSensitiveWord(templateContent, 1);
		
		
		//1.先处理白名单，必须包含白名单
		if(!whiteflag){
			//如果不包括白词，我们将blackWord属性值设为空，进行区分， 2016/12/24 11:43
			//map.put("blackWord", "");
			logger.info("通知内容不包含白词1111");
			map.put("auditStatus", (short)0);
			//2.处理黑名单，不包含黑名单词汇
		}else if(blackflag){
			Set<String> words = blackFilter.getSensitiveWord(templateContent, 1);//获取命中的敏感词
			if(words != null && words.size() >0){
				//将敏感词放入到map中，进行传递保存 2016/12/24 11:43
				logger.info("通知内容包含黑词1111:"+words+"");
				map.put("blackWord", words.toString());
				map.put("auditStatus", (short)0);
			}
		}
		map.put("templateName",templateName);
		map.put("templateContent",templateContent);
		map.put("phone",phone);
		map.put("memberId",memberId);
		//String url="http://localhost:8081/sms/msgTemplate/saveTemplate";
		//String response = this.Post(url, map);
		String response = this.Post(constPool.getSmsHttpUrl()+"msgTemplate/saveTemplate/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MsgTemplateServiceImpl.saveTemplate.001","数据请求异常");
		}
		//JSONObject parseObject = JSONObject.parseObject(response);
			//保存后返回的数据response----》{"data":{},"success":true}
			//将审核状态返回给手机端
			Map<String, Object>responsemap= (Map<String, Object>) JSON.parse(response);
			responsemap.put("auditStatus", map.get("auditStatus"));
			//最终的结果是{"auditStatus":0,"data":{},"success":true}
			response = JSON.toJSONString(responsemap);
			logger.info("新版本的短信模板,返回的结果是："+response);
			return response;
	}
	  
	@Override
	public String updateTemplate(String templateId, String templateName,String templateContent) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blackWord", "");
		map.put("auditStatus", (short)3);
		//验证繁体字
		char[] charArray = templateContent.toCharArray();
		for (char c : charArray) {
			boolean b = ChineseHelper.isTraditionalChinese(c);
			if(b){
//				Map<String,Object> errorMap = new HashMap<String,Object>();
//				errorMap.put("success",true);
//				errorMap.put("data", new HashSet<>());
//				errorMap.put("errorMsg", "通知内容包含繁体字不符合发送要求");
//				return JSON.toJSONString(errorMap);
				map.put("auditStatus", (short)0);
				map.put("blackWord", c + "");
			}
		}
		//先处理敏感词过滤，有敏感词则讲敏感词集合返回给手机端进行展示
		Set<String> whiteSet=sensitiveWordService.queryWhiteList();
		Set<String> blackSet=sensitiveWordService.queryBlackList();
		SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
		SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
		boolean blackflag = blackFilter.isContaintSensitiveWord(templateContent, 1);
		boolean whiteflag = whiteFilter.isContaintSensitiveWord(templateContent, 1);
		//1.先处理白名单，必须包含白名单
		if(!whiteflag){
//			Map<String,Object> errorMap = new HashMap<String,Object>();
//			errorMap.put("success",true);
//			errorMap.put("data", new HashSet<>());
//			errorMap.put("errorMsg", "通知内容不符合发送要求!!!");
//			return JSON.toJSONString(errorMap);
			map.put("auditStatus", (short)0);
			//2.处理黑名单，不包含黑名单词汇
		}else if(blackflag){
			Set<String> words = blackFilter.getSensitiveWord(templateContent, 1);//获取命中的敏感词
			if(words != null && words.size() >0){
//				Map<String,Object> errorMap = new HashMap<String,Object>();
//				errorMap.put("success",true);
//				errorMap.put("data", words);
//				errorMap.put("errorMsg", "模板中含有敏感词："+words);
//				return JSON.toJSONString(errorMap);
				map.put("blackWord", words);
				map.put("auditStatus", (short)0);
			}
		}
		
		map.put("templateId",templateId);
		map.put("templateName",templateName);
		map.put("templateContent",templateContent);
//		String url="http://localhost:8081/sms/msgTemplate/updateTemplate";
//		String response = this.Post(url, map);
		String response = this.Post(constPool.getSmsHttpUrl()+"msgTemplate/updateTemplate/", map);
		if(PubMethod.isEmpty(response)){
			throw new ServiceException("net.okdi.apiV4.service.impl.MsgTemplateServiceImpl.updateTemplate.001","数据请求异常");
		}
		//修改后返回的数据response----》{"data":{},"success":true}
		//将审核状态返回给手机端
		//JSONObject parseObject = JSONObject.parseObject(response);
		Map<String, Object>responsemap= (Map<String, Object>) JSON.parse(response);
		responsemap.put("auditStatus", map.get("auditStatus"));
		//最终的结果是{"auditStatus":0,"data":{},"success":true}
		response = JSON.toJSONString(responsemap);
		return response;
	}


}

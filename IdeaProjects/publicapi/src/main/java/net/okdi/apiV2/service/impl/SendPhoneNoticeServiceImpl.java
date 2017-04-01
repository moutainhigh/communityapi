package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;

import net.okdi.apiV2.service.SendPhoneNoticeService;
import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV4.service.MsgTemplateService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.SensitiveWordService;
@SuppressWarnings("all")
@Service
public class SendPhoneNoticeServiceImpl implements SendPhoneNoticeService {
	private static Logger logger = Logger.getLogger(SendPhoneNoticeServiceImpl.class);
	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private ExpGatewayService expGatewayService;
	@Autowired
	private InterceptService interceptService;
	@Autowired
	private TaskMassNoticeService taskMassNoticeService;
	@Autowired
	private SmsSendService smsSendService;
	
	@Autowired
	private MsgTemplateService msgTemplateService;
	@Autowired
	private  ExpGatewayService  ExpGatewayService;
	
	private static Integer ONESENDNUM=1000;//1000;  //群发通知每次提交上限
	@Override
	public String sendPhoneNotice(String accountId ,String phoneAndNum, String noticePhone,
			Long memberId, String content, Short flag, String pickupTime, String pickupAddr, String compName, String md5Hex) {
		
		logger.info("进入语音短信发送"+phoneAndNum+noticePhone+memberId+content+flag);
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString()); //查询错误次数
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
		String memberInfo = this.expGatewayService.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		String smsTemplates=content;
		if(content.substring(0,2).equals("您好")){
			String[] head=content.split("\\s+");
			smsTemplates=content.substring(head[0].length(),content.length()-head[head.length-1].length());
		}else if(content.substring(0, 2).equals("我是")){
			String[] head=content.split(",");
			String[] end=content.split("\\s+");
			smsTemplates=content.substring(head[0].length(),content.length()-end[end.length -1].length());
		}else{
			smsTemplates=content;
		}
		if(content.substring(0,2).equals("您好，")){
			content=content.substring(3,content.length());
		}
		logger.info("发送内容为："+content+",需要验证的内容为："+smsTemplates);
		/**********************************原来这个是验证繁体字黑白词的地方***后来提出来了,2016年12月31日 11:52:23*****************************/
		//短信正常发送的才给存入到缓存中, 发送的条数
		String[] arrs = phoneAndNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-群呼，每次发送数量最多为"+this.ONESENDNUM+"条");//每次最多1000条
			errorMap.put("flag", "");
			taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
			return JSON.toJSONString(errorMap);
			}
			String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length); //每天5000条
			JSONObject json=JSONObject.parseObject(res);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
			res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length);//每小时1000条
			json=JSONObject.parseObject(res);
			logger.info("每小时发送返回的json串-群呼: "+json);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
		Map<String,String> map=new HashMap<String, String>();
		map.put("accountId",accountId);
		map.put("channelNo","07");
		map.put("phoneAndNum", phoneAndNum);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone",noticePhone);
		map.put("flag",String.valueOf(flag));
		map.put("content",content);
		map.put("pickupTime",pickupTime);
		map.put("pickupAddr",pickupAddr);
		map.put("compName",compName);
		String response = smsHttpClient.Post(constPool.getSmsHttpUrl()+"/vlinkCall/sendPhoneNotice",map);
		if(response==null || "".equals(response)){
			return null;
		}
		return response;
	}
	
	@Override
	public String queryMsgTemplate(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, String md5Hex1,String templateId) {
				//加入传过来的有状态码和模板名字
				Map<String, Object> map=new HashMap<>();
				map.put("templateContent", smsTemplate);
				map.put("templateId", templateId);
				map.put("memberId", memberId);
				logger.info("根据模板id"+templateId+"模板内容:"+smsTemplate+"和memberid"+memberId+""+"判断返回的状态");
				//http://sms.okdi.net/sms/
				
				String originaldata = smsHttpClient.Post(constPool.getSmsHttpUrl()+"msgTemplate/queryMsgAuditStatus", map);
				logger.info("查询模板审核状态 *********************originaldata: "+originaldata);
				//解析查询结果的的json数据
				Map<String,Object>returnmap = JSON.parseObject(String.valueOf(JSON.parseObject(originaldata).get("data")));
				/*判断传过来的状态码*****2016/12/27*************************************************************************/
				//获取发送人的电话号码
				/*String memberInfo = ExpGatewayService.getMemberInfoById(memberId);
				Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));*/
				String memberPhone = noticePhone;//memberMap.get("memberPhone").toString();
				//如果状态码不为空则说明该短信模板跟原来的一模一样
				//如果是1，短信直接发送
				//返回controller的map数据
				logger.info("返回的短信审核状态："+returnmap.get("auditStatus"));
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success", true);
				if (!"".equals(returnmap.get("auditStatus"))) {
					//如果返回的状态是0和2，说明短信模板本身是未审核状态，不让发送，直接返回
					logger.info("如果返回的状态是0和2，说明短信模板本身未审核通过，不让发送，直接返回");
					if ("0".equals(returnmap.get("auditStatus")+"")|"2".equals(returnmap.get("auditStatus")+"")) {
						errorMap.put("success",false);
						errorMap.put("cause", "短信内容不合法");
						errorMap.put("flag", "");
						logger.info("短信内容审核不通过："+errorMap);
						return JSON.toJSONString(errorMap);
					}
					//如果状态为3，系统审核通过，再验证一遍
					if ("3".equals(returnmap.get("auditStatus")+"")) {
					//进行过滤验证
					logger.info("11111需要验证的内容："+smsTemplate);
					if(isValidate){
						logger.info("开始验证繁体字.....isValidate: "+isValidate);
						//验证繁体字
						char[] charArray = smsTemplate.toCharArray();
						for (char c : charArray) {
							boolean b = ChineseHelper.isTraditionalChinese(c);
							if(b){
								//Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "通知内容包含繁体字不符合发送要求");
								errorMap.put("flag", "");
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
								return JSON.toJSONString(errorMap);
							}
						}
						logger.info("开始验证黑白词********************: smsTemplate: "+smsTemplate);
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
							//Map<String,Object> errorMap = new HashMap<String,Object>();
							Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
							errorMap.put("success",false);
							errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求!");
							errorMap.put("flag", "");
							sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
							logger.info("首页群发短信,清空缓存..........黑名单******111");
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
							return JSON.toJSONString(errorMap);
						}
						if(flag==1 || flag==5){//新加微信优先
							if(!whiteflag){
								this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
								//Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "通知内容不符合发送要求!!");
								errorMap.put("flag", "");
								sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
								logger.info("首页群发短信,清空缓存..........白名单****2222");
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
								return JSON.toJSONString(errorMap);
							}
						}
						
					}
					}
					//如果状态为空，说明内容发生了变化，我们呢调用msgtemplateservice将模板进行保存
				}else{
					logger.info("短信内容变化了，模板保存");
					logger.info("模板名称为空，模板内容是："+smsTemplate+"，手机号："+memberPhone+"，快递员id："+memberId);
					//保存模板
					String saveTemplatereturn = msgTemplateService.saveTemplate("", smsTemplate, memberPhone, String.valueOf(memberId));
					logger.info("保存模板模板后保存的结果是："+saveTemplatereturn);
					//对内容进行黑白词的判断
					logger.info("需要验证的内容："+smsTemplate);
					if(isValidate){
						//验证繁体字
						char[] charArray = smsTemplate.toCharArray();
						for (char c : charArray) {
							boolean b = ChineseHelper.isTraditionalChinese(c);
							if(b){
								//Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
								errorMap.put("flag", "2");
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
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
							//Map<String,Object> errorMap = new HashMap<String,Object>();
							Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
							errorMap.put("success",false);
							errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
							errorMap.put("flag", "2");
							sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
							logger.info("首页群发短信，包含黑词,清空缓存..........111111黑名单");
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
							return JSON.toJSONString(errorMap);
						}
						if(flag==1 || flag==5){//新加微信优先
							if(!whiteflag){
								this.interceptService.saveIntercept(accountId, "07", msgId, phoneAndWaybillNum, memberId, memberPhone, flag, smsTemplate, isNum);
								//Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
								errorMap.put("flag", "2");
								sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
								logger.info("首页群发短信,没有白词,清空缓存..........22222白名单");
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex1);
								return JSON.toJSONString(errorMap);
							}
						}
						
					}
				}
				return JSON.toJSONString(errorMap);
	}	
	
	/*******************目前这个方法提出来了,还没用到,*用到的是sendPhoneNoticemsService.queryMsgTemplate方法******************************************************/
	public String validationBW(String content, String noticePhone, String md5Hex,String accountId, String phoneAndNum, Long memberId, String memberPhone, Short flag){
		if(true){
			//验证繁体字
			char[] charArray = content.toCharArray();
			for (char c : charArray) {
				boolean b = ChineseHelper.isTraditionalChinese(c);
				if(b){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "通知内容包含繁体字不符合发送要求");
					taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
					return JSON.toJSONString(errorMap);
				}
			}
			
			//验证黑白词汇，语音不验证
			//  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			
			boolean blackflag = blackFilter.isContaintSensitiveWord(content, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(content, 1);
			if(blackflag){//blackflag||!whiteflag//验证黑白词
				this.interceptService.saveIntercept(accountId, "07", "", phoneAndNum, memberId, memberPhone, flag, content, (short)0);
				logger.info("发送内容不符合要求");
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				logger.info("首页群发群呼,清空缓存.......");
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag){
				this.interceptService.saveIntercept(accountId, "07", "", phoneAndNum, memberId, memberPhone, flag, content, (short)0);
				logger.info("发送内容不符合要求-群呼");
				Map<String,Object> errorMap = new HashMap<String,Object>();
				//Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容不符合发送要求!!!");
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				logger.info("首页群发群呼,清空缓存.......");
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return JSON.toJSONString(errorMap);
			}
		}
		int smsLength=content.length();
		int newSmsLength=content.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
			return JSON.toJSONString(errorMap);
		}
		return null;
	}
}

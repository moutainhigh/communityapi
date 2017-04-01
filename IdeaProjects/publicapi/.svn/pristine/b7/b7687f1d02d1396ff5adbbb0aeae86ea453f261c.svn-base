package net.okdi.apiV5.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV5.service.DelaySendNoticeService;
import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.SensitiveWordService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;

@Service
public class DelaySendNoticeServiceImpl extends BaseService_jdw implements DelaySendNoticeService{

	private static Logger logger = Logger.getLogger(DelaySendNoticeServiceImpl.class);
	
	@Autowired
	private SensitiveWordService sensitiveWordService;
	@Autowired
	private ExpGatewayService expGatewayService;
	@Autowired
	private SmsHttpClient smsHttpClient;
	@Autowired
	private ConstPool constPool;
	@Autowired
	private InterceptService interceptService;
	@Autowired

	private OpenApiHttpClient openApiHttpClient;
	
	@Autowired

	private SmsSendService smsSendService;
	@Autowired
	RedisService redisService;
	private static Integer ONESENDNUM=500;  //群发通知每次提交上限
	@Override
	public String queryBalance(String sendNum,String content,String[] arrs,Long memberId,Short flag,Long count) {
		JSONObject jsons=JSONObject.parseObject(sendNum);
		Map<String,Object> errorMap = new HashMap<String,Object>();
		if(jsons.getBooleanValue("success")){							//调用接口成功
			String data=jsons.getString("data");							
			jsons=JSONObject.parseObject(data);						
			Integer one=jsons.getInteger("one");
			Integer two=jsons.getInteger("two");
			Integer three=jsons.getInteger("three");
			//新加的判断短信余额是否足够
			Double double1 = jsons.getDouble("telecomBalance");
			if(double1<=0){
				errorMap.put("success",false);
				errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
				errorMap.put("flag","");
				return JSON.toJSONString(errorMap);
			}
			Integer contentLen=content.length();
			Integer contentLenNew=content.replaceAll("#编号#", "").length();
			Integer len=content.length();
			//List<String> listPhone = new ArrayList<String>();
			StringBuffer buffer = new StringBuffer();
			if(contentLen!=contentLenNew){
				Integer numLen=0;
				for(String str:arrs){//遍历出来长度最大的编号
				buffer.append(str.split("-")[0]).append("-");
					//listPhone.add(str.split("-")[0]);//把遍历出来的手机号放入到List中
					if(((str.split("-")[1]).length())>numLen){
						numLen=(str.split("-")[1]).length();
					}
				}
//				len=numLen+content.length()-3;
				////把"#"换成了"编号"
				len=numLen+content.length()-2;
			}
			//String phones = buffer.substring(0, buffer.length()-1);
			
			//查询当天是否已经发送过了
			//InterceptService.querySmsCount(listPhone);
			Integer feeCount=arrs.length;
			if(flag==2||flag==3){								//用户选择电话优先或者仅电话
				String sendContent=content;
				int num = sendContent.indexOf(",");
				sendContent = sendContent.substring(num+1, sendContent.length()); 
				len=sendContent.length();
				if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag","");
					return JSON.toJSONString(errorMap);
				}else{
					if(len<=one){
						count*=1;
					}else if(one<len&&len<=two){
						count*=2;
					}else if(two<len&&len<=three){
						count*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				if(!"no".equals(jsons.getString("lenPhone"))){
					Integer lenPhone=Integer.valueOf(jsons.getString("lenPhone"));
					if(count>lenPhone){
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "内容不能超过"+three+"个字");
					errorMap.put("flag","");
					return JSON.toJSONString(errorMap);
				} else{
					if(len<=one){
						feeCount*=1;
					}else if(one<len&&len<=two){
						feeCount*=2;
					}else if(two<len&&len<=three){
						feeCount*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "语音通知失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				Integer preferentialNum=0;				
				if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
					preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
				}								
				if(!"no".equals(jsons.getString("lenPhone"))){
					Integer lenPhone=Integer.valueOf(jsons.getString("lenPhone"));
					if(feeCount>(lenPhone+preferentialNum)){
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
			}else if(flag ==1){
				
				//需走余额的条数 与 短信可发条数进行比较
					if(len<=one){
						count*=1;
					}else if(one<len&&len<=two){
						count*=2;
					}else if(two<len&&len<=three){
						count*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
					/*Integer preferentialNum=0;
					if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
						preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
					}*/	
					Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
					if(count>lenSmss){//需要扣费的条数 大于 余额还可以发送多少短信的条数
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
					
				//全部的条数 与 短信可发条数进行比较
				if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag","");
					return JSON.toJSONString(errorMap);
				} else{
					if(len<=one){
						feeCount*=1;
					}else if(one<len&&len<=two){
						feeCount*=2;
					}else if(two<len&&len<=three){
						feeCount*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				Integer preferentialNum=0;
				if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
					preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
				}	
				if(!"no".equals(jsons.getString("lenSms"))){
					Integer lenSms=Integer.valueOf(jsons.getString("lenSms"));
					if(feeCount>(lenSms+preferentialNum)){
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				//********************** 修改日期 2016年7月21日 17:07:45****************************/
				/*if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					return JSON.toJSONString(errorMap);
				}else{
					if(len<=one){
						feeCount*=1;
					}else if(one<len&&len<=two){
						feeCount*=2;
					}else if(two<len&&len<=three){
						feeCount*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						return JSON.toJSONString(errorMap);
					}
				}
				Integer preferentialNum=0;
				if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
					preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
				}	
				if(!"no".equals(jsons.getString("lenSms"))){
					Integer lenSms=Integer.valueOf(jsons.getString("lenSms"));
					if(feeCount>(lenSms+preferentialNum)){
						errorMap.put("success",false);
						errorMap.put("cause", "余额不足，群发通知失败");
						return JSON.toJSONString(errorMap);
					}
				}*/
				
			}else if(flag == 5){

				//需走余额的条数 与 短信可发条数进行比较
					if(len<=one){
						count*=1;
					}else if(one<len&&len<=two){
						count*=2;
					}else if(two<len&&len<=three){
						count*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
					/*Integer preferentialNum=0;
					if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
						preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
					}*/	
					Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
					if(count>lenSmss){//需要扣费的条数 大于 余额还可以发送多少短信的条数if(count>lenSmss){
						errorMap.put("success",false);
						errorMap.put("cause", "余额不足，群发通知失败");
						errorMap.put("flag","1");
						return JSON.toJSONString(errorMap);
					}
					
				//全部的条数 与 短信可发条数进行比较
				if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag","");
					return JSON.toJSONString(errorMap);
				} else{
					if(len<=one){
						feeCount*=1;
					}else if(one<len&&len<=two){
						feeCount*=2;
					}else if(two<len&&len<=three){
						feeCount*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return JSON.toJSONString(errorMap);
					}
				}
				Integer preferentialNum=0;
				if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
					preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
				}	
				if(!"no".equals(jsons.getString("lenSms"))){
					Integer lenSms=Integer.valueOf(jsons.getString("lenSms"));
					if(feeCount>(lenSms+preferentialNum)){ //if(feeCount>(lenSms+preferentialNum)){
						errorMap.put("success",false);
						errorMap.put("cause", "余额不足，群发通知失败");
						errorMap.put("flag","1");
						return JSON.toJSONString(errorMap);
					}
				}
			}
		}else{
			errorMap.put("success",false);
			errorMap.put("cause", "发送短信失败");
			errorMap.put("flag","");
			return JSON.toJSONString(errorMap);
		}
		errorMap.put("success",true);
		errorMap.put("cause", "发送短信成功");
		return JSON.toJSONString(errorMap);
	}
	@Override
	public Map<String,Object> queryAliBalance(String sendNum,String content,String[] arrs,Long memberId,Short flag, Long count) {
		Map<String,Object> receiverPhoneMap= new HashMap<String,Object>();
		JSONObject jsons=JSONObject.parseObject(sendNum);
		Map<String,Object> errorMap = new HashMap<String,Object>();
		if(jsons.getBooleanValue("success")){							//调用接口成功
			String data=jsons.getString("data");							
			jsons=JSONObject.parseObject(data);						
			Integer one=jsons.getInteger("one");
			Integer two=jsons.getInteger("two");
			Integer three=jsons.getInteger("three");
			//新加的判断短信余额是否足够
			Integer contentLen=content.length();
			Integer contentLenNew=content.replaceAll("#编号#", "").length();
			Integer len=content.length();
			StringBuffer sb=new StringBuffer();//保存手机号
			String sbresult="";
			if(contentLen!=contentLenNew){
				Integer numLen=0;
				for(String str:arrs){//遍历出来长度最大的编号
					//listPhone.add(str.split("-")[0]);//把遍历出来的手机号放入到List中
					if(((str.split("-")[1]).length())>numLen){
						numLen=(str.split("-")[1]).length();
						sb.append(str.split("-")[0]+"-");//保存运单号
					}
				}
				////把"#编号#"换成了"取件码"
				len=numLen+content.length()-1;
			}
			Integer feeCount=arrs.length;
			if(flag==10||flag==11){							//用户选择电话优先或者仅电话
				String sendContent=content;
				int num = sendContent.indexOf(",");
				sendContent = sendContent.substring(num+1, sendContent.length()); 
				len=sendContent.length();
				if(len>three){
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag","");
					return errorMap;
				}else{
					if(len<=one){
						feeCount*=1;
					}else if(one<len&&len<=two){
						feeCount*=2;
					}else if(two<len&&len<=three){
						feeCount*=3;
					}else{
						errorMap.put("success",false);
						errorMap.put("cause", "发送短信失败");
						errorMap.put("flag","");
						return errorMap;
					}
				}
				if(!"no".equals(jsons.getString("lenPhone"))){
					Integer lenPhone=Integer.valueOf(jsons.getString("lenPhone"));
					if(feeCount>lenPhone){
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag","");
						return errorMap;
					}
				}
			}else if(flag ==9){
				Long chargeNum=0l;
				sbresult=sb.substring(0, sb.length()-1);
				String result=smsSendService.isExist(memberId,sbresult);
				JSONObject json=JSONObject.parseObject(result);//TODO
				if(json.getBoolean("success")){
					chargeNum=json.getLong("data");//实际扣款的数量
					String str=json.getString("str");//运单号-是否扣费
					String[] receiverPhones=str.split("\\|");
					for(String phoneNum:receiverPhones){
						receiverPhoneMap.put(phoneNum.split("-")[0], phoneNum.split("-")[1]);
					}
					//需走余额的条数 与 短信可发条数进行比较
					if(len>three){
						errorMap.put("success",false);
						errorMap.put("cause", "短信内容不能超过"+three+"个字");
						errorMap.put("flag","");
						return errorMap;
					}else { 
						if(len<=one){
							feeCount*=1;
						}else if(one<len&&len<=two){
							feeCount*=2;
						}else if(two<len&&len<=three){
							feeCount*=3;
						}else{
							errorMap.put("success",false);
							errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
							errorMap.put("flag","");
							return errorMap;
						}
						Integer preferentialNum=0;
						if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
							preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
						}
						Integer lenSms=0;
						if(!"no".equals(jsons.getString("lenSms"))){
							lenSms = Integer.valueOf(jsons.getString("lenSms"));
							if(feeCount>(lenSms+preferentialNum)){
								errorMap.put("success",false);
								errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
								errorMap.put("flag","");
								return errorMap;
							}else{
								//就是先按照一条计算扣款的过了,现在验证实际需要扣款的数量计算
								if(len<=one){
									chargeNum*=1;
								}else if(one<len&&len<=two){
									chargeNum*=2;
								}else if(two<len&&len<=three){
									chargeNum*=3;
								}else{
									errorMap.put("success",false);
									errorMap.put("cause", "发送短信失败");
									errorMap.put("flag","");
									return errorMap;
								}
								if(chargeNum > lenSms){		//查出免费条数
									errorMap.put("success",false);
									errorMap.put("flag","");
									errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
									return errorMap;
								}
							}
						}
					}
				}
			}
		}else{
			errorMap.put("success",false);
			errorMap.put("cause", "发送短信失败");
			errorMap.put("flag","");
			return errorMap;
		}
		receiverPhoneMap.put("cause", "fff");
		return receiverPhoneMap;
	}

	
	@Override
	public String sendTaskPhoneNotice(String accountId ,String phoneNumMsgId, String noticePhone,
			Long memberId, String content, Short flag,Short isNum, String pickupAddr,String pickupTime, String compName,
			String version, String system) {
		logger.info("新版延时发送语音发送accountId="+accountId+" ,phoneNumMsgId="+phoneNumMsgId+" ,accountId= "+accountId+" ,noticePhone="+noticePhone+memberId+content+flag);
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString()); //查询错误次数
		JSONObject jsons=JSONObject.parseObject(resultvalue);
		String success=jsons.getString("success").toString();
		if("false".equals(success)){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("flag","");
			errorMap.put("cause",jsons.getString("message"));
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
		if(true){
			//验证繁体字
			char[] charArray = content.toCharArray();
			for (char c : charArray) {
				boolean b = ChineseHelper.isTraditionalChinese(c);
				if(b){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
					errorMap.put("flag", "2");
					return JSON.toJSONString(errorMap);
				}
			}
			//验证黑白词汇，语音不验证白词库
			//  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			
			boolean blackflag = blackFilter.isContaintSensitiveWord(smsTemplates, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(smsTemplates, 1);
			if(blackflag){//blackflag||!whiteflag by zj 2016年10月19日 11:43:32
				this.interceptService.saveTaskIntercept(accountId, "07", phoneNumMsgId, memberId, memberPhone, flag, content, isNum);
				logger.info("发送内容不符合要求黑词");
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				//errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				errorMap.put("flag", "2");
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag){
				this.interceptService.saveTaskIntercept(accountId, "07", phoneNumMsgId, memberId, memberPhone, flag, content, isNum);
				logger.info("发送内容不符合要求白词");
				Map<String,Object> errorMap = new HashMap<String,Object>();
				//Set<String> words = blackFilter.getSensitiveWord(content, 1);//获取命中的敏感词
				errorMap.put("success",false);
				//errorMap.put("cause", "通知内容不符合发送要求!!!");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				errorMap.put("flag", "2");
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
				return JSON.toJSONString(errorMap);
			}
		}
		int smsLength=content.length();
		int newSmsLength=content.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			errorMap.put("flag", "");
			String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,content);
			return JSON.toJSONString(errorMap);
		}
		//把实际发送数量存入进来
		String[] arrs = phoneNumMsgId.split("\\|");
		if(arrs.length>this.ONESENDNUM){									//群发通知每次最多提交500
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-群呼~，每次发送数量最多为"+this.ONESENDNUM+"条");
			errorMap.put("flag", "");
			return JSON.toJSONString(errorMap);
		}
		
		String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length);	//查询是否超过今日最大发送次数
		JSONObject json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		
		res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length);	//查询是否超过每小时最大发送次数
		json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		/**
		 * String accountId,String channelNo, String phoneAndNum,String content,Long memberId,String memberPhone,Short flag, 
			String pickupTime,String pickupAddr, String compName, String version, String system
		 */
		Map<String,String> map=new HashMap<String, String>();
		map.put("accountId",accountId);
		map.put("channelNo","07");
		map.put("phoneAndNum", phoneNumMsgId);
		map.put("memberId", String.valueOf(memberId));
		map.put("memberPhone",noticePhone);
		map.put("flag",String.valueOf(flag));
		map.put("content",content);
		//, String pickupAddr,String pickupTime, String compName
		map.put("pickupAddr",pickupAddr);
		map.put("pickupTime",content);
		map.put("compName",compName);
		map.put("version",version);
		map.put("system",system);
		String response = smsHttpClient.Post(constPool.getSmsHttpUrl()+"/newVlinkCall/newSendPhoneNotice",map);
		if(response==null || "".equals(response)){
			return null;
		}
		return response;
	}
	
	@Override
	public String sendTaskSms(String accountId,String smsTemplate,String phoneAndNum,Long memberId,
			boolean isOld,boolean isValidate,String voiceUrl,String msgId,Short isNum, Short flag, String pickupAddr,
			String pickupTime, String compName, String version, String system){
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString());
		JSONObject jsons=JSONObject.parseObject(resultvalue);
		String success=jsons.getString("success").toString();
		if("false".equals(success)){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause",jsons.getString("message"));
			errorMap.put("flag","");
			return JSON.toJSONString(errorMap);
		}
		//获取发送人的电话号码
		String memberInfo = this.expGatewayService.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		logger.info("需要验证的内容："+smsTemplate);
		if(isValidate){
			//验证繁体字
			char[] charArray = smsTemplate.toCharArray();
			for (char c : charArray) {
				boolean b = ChineseHelper.isTraditionalChinese(c);
				if(b){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("flag","2");
					errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
					return JSON.toJSONString(errorMap);
				}
			}
			//验证黑白词汇，短信验证黑白词库
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(smsTemplate, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(smsTemplate, 1);
			if(blackflag){
				this.interceptService.saveTaskIntercept(accountId, "07", phoneAndNum, memberId, memberPhone, (short)1, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
				errorMap.put("success",false);
				errorMap.put("flag","2");
				//errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag && PubMethod.isEmpty(msgId)){
				this.interceptService.saveTaskIntercept(accountId, "07", phoneAndNum, memberId, memberPhone, (short)1, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				//Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
				logger.info("通知内容不符合发送要求-白词-派件");
				errorMap.put("success",false);
				errorMap.put("flag","2");
				//errorMap.put("cause", "通知内容不符合发送要求!!!");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				return JSON.toJSONString(errorMap);
			}
		}
		
		int smsLength=smsTemplate.length();
		int newSmsLength=smsTemplate.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			errorMap.put("flag","");
			String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
			return JSON.toJSONString(errorMap);
		}
		
		String channelNO = "03";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		String compId = String.valueOf(memberMap.get("compId"));//快递员所属站点id
		if(PubMethod.isEmpty(compId)){
			compId = "33620";
		}
		String ResultContenNew =  NewSetSmsContentTask(smsTemplate,phoneAndNum,memberPhone);
		Map<String,Object> reMap = new HashMap<String,Object>();
		logger.info("延时发送---派件返回的数据格式smsTemplate:"+ResultContenNew);
		if(!PubMethod.isEmpty(ResultContenNew)){
			logger.info("延时发送---派件返回的数据第一步:::::111111111111");
			JSONArray parseArray = JSONArray.parseArray(ResultContenNew);
			logger.info("延时发送---派件返回的数据第二步:::::222222222222");
			int size = parseArray.size();
			if(size > 0){
				logger.info("延时发送---派件返回的数据第三步:::::333333333333");
				JSONObject jsonObject = parseArray.getJSONObject(0);
				logger.info("延时发送---派件返回的数据第四步:::::444444444444");
				if(!PubMethod.isEmpty(jsonObject.getString("isCharge"))){
					System.out.println("555");
					if("2".equals(jsonObject.getString("isCharge"))){
						logger.info("延时发送---派件返回的数据第五步:::::555555555555");
						if(!jsonObject.getBoolean("success")){
							reMap.put("success",jsonObject.getString("success"));
							reMap.put("cause", jsonObject.getString("cause"));
							reMap.put("flag", "");
							System.out.println("666");
							logger.info("延时发送---派件返回的数据第六步:::::666666666666666: "+reMap);
							return JSON.toJSONString(reMap);//如果success为false的话则提示错误信息,手机号不正确, 否则流程继续
						}
					}
					
				}
			}
			//throw new ServiceException("发送通知异常");	
		}
		//Short flag = 1;
		if(!PubMethod.isEmpty(msgId)){
			flag = 4;
		}
		//把实际发送数量存入进来
		String[] arrs = phoneAndNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){									//群发通知每次最多提交500
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-群呼~，每次发送数量最多为"+this.ONESENDNUM+"条");
			errorMap.put("flag","");
			return JSON.toJSONString(errorMap);
		}
		
		String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length);	//查询是否超过今日最大发送次数
		JSONObject json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		
		res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length);	//查询是否超过每小时最大发送次数
		json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		String doSmsSend = smsHttpClient.newDoSmsSendTask
				(accountId,channelNO, ResultContenNew,memberId,memberPhone, flag,msgId, pickupAddr, pickupTime, compName, version,system);
			if(PubMethod.isEmpty(doSmsSend)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				map.put("flag", "");
				return 	JSON.toJSONString(map);
			}else{
				JSONObject jsonn=JSONObject.parseObject(doSmsSend);
				if(jsonn.getBooleanValue("success")){
					sensitiveWordService.removeWrongNumber(memberId.toString());
				}
			}
			return 	doSmsSend;
	}
	@Override
	public String sendAliTaskSms(String accountId,String smsTemplate,String phoneAndNum,Long memberId,
			boolean isOld,boolean isValidate,String voiceUrl,String msgId,Short isNum, Short flag,String pickupAddr, String pickupTime, String compName, 
			String version, String system){
		String resultvalue=sensitiveWordService.queryWrongNumber(memberId.toString());
		JSONObject jsons=JSONObject.parseObject(resultvalue);
		String success=jsons.getString("success").toString();
		if("false".equals(success)){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause",jsons.getString("message"));
			errorMap.put("flag","");
			return JSON.toJSONString(errorMap);
		}
		//获取发送人的电话号码
		String memberInfo = this.expGatewayService.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		logger.info("阿里需要验证的内容："+smsTemplate);
		if(isValidate){
			//验证繁体字
			char[] charArray = smsTemplate.toCharArray();
			for (char c : charArray) {
				boolean b = ChineseHelper.isTraditionalChinese(c);
				if(b){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("flag","2");
					errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
					return JSON.toJSONString(errorMap);
				}
			}
			//验证黑白词汇，短信验证黑白词库
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(smsTemplate, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(smsTemplate, 1);
			if(blackflag){
				this.interceptService.saveTaskIntercept(accountId, "07", phoneAndNum, memberId, memberPhone, (short)1, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
				errorMap.put("success",false);
				//errorMap.put("cause", "通知内容包含敏感词"+words+"不符合发送要求");
				errorMap.put("flag","2");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				return JSON.toJSONString(errorMap);
			}
			if(!whiteflag && PubMethod.isEmpty(msgId)){
				this.interceptService.saveTaskIntercept(accountId, "07", phoneAndNum, memberId, memberPhone, (short)1, smsTemplate, isNum);
				Map<String,Object> errorMap = new HashMap<String,Object>();
				//Set<String> words = blackFilter.getSensitiveWord(smsTemplate, 1);//获取命中的敏感词
				logger.info("通知内容不符合发送要求-白词-派件-阿里");
				errorMap.put("success",false);
				//errorMap.put("cause", "通知内容不符合发送要求!!!");
				errorMap.put("flag","2");
				errorMap.put("cause", "您的短信模板已进入人工审核状态，人工审核时间为每天早上9：00-晚上7：00自提交5分钟内完成审核，请耐心等待。如有疑问，请加Q"+constPool.getCustomerQq());
				String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				return JSON.toJSONString(errorMap);
			}
		}
		
		int smsLength=smsTemplate.length();
		int newSmsLength=smsTemplate.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			errorMap.put("flag","");
			String result=sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
			return JSON.toJSONString(errorMap);
		}
		
		String channelNO = "03";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		String ResultContenNew =  NewSetAliSmsContentTask(smsTemplate,phoneAndNum,memberPhone);
		logger.info("阿里=延时发送---派件返回的数据格式smsTemplate:"+ResultContenNew);
		//把实际发送数量存入进来
		String[] arrs = phoneAndNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){									//群发通知每次最多提交500
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-群呼~，每次发送数量最多为"+this.ONESENDNUM+"条");
			errorMap.put("flag","");
			return JSON.toJSONString(errorMap);
		}
		
		String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length);	//查询是否超过今日最大发送次数
		JSONObject json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		
		res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length);	//查询是否超过每小时最大发送次数
		json=JSONObject.parseObject(res);
		if(!json.getBoolean("success")){
			return res;
		}
		String doSmsSend = smsHttpClient.newDoSmsSendTask
				(accountId,channelNO, ResultContenNew,memberId,memberPhone, flag,msgId, pickupAddr,pickupTime, compName, version,system);
		if(PubMethod.isEmpty(doSmsSend)){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("success", false);
			map.put("flag", "");
			return 	JSON.toJSONString(map);
		}else{
			JSONObject jsonn=JSONObject.parseObject(doSmsSend);
			if(jsonn.getBooleanValue("success")){
				sensitiveWordService.removeWrongNumber(memberId.toString());
			}
		}
		return 	doSmsSend;
	}


	@Override
	public String queryIsExist(Long memberId, String phoneAndNum) {
		logger.info("queryIsExist==memberId:"+memberId+",phoneAndNum:"+phoneAndNum);
		Map map=new HashMap();
		String receiverPhones="";
		String[] arr=phoneAndNum.split("\\|");
		int i=0;
		for(String receiverPhone:arr){
			String phone = receiverPhone.split("-")[0];
			if(i==0){
				receiverPhones+=phone;
			}else{
				receiverPhones+="-"+phone;
			}
			i++;
		}
		logger.info("拼接的号码receiverPhones: "+receiverPhones+" ==========arr: "+arr);
		map.put("memberId", memberId);
		map.put("receiverPhones", receiverPhones);
		String url = constPool.getSmsHttpUrl()+"smsLogQuery/isExist";
		String result = smsHttpClient.Post(url, map);
		logger.info("url:"+url+",result:"+result);
		return result;
	}
	
	
	@Override
	public boolean getReqOnceKeyByPhone(String phone, String timeStamp){
		//RedisServiceImpl redisServiceImpl = new RedisServiceImpl();
		logger.info("群发通知是否重复提交进来...phone= "+phone+", timeStamp="+timeStamp);
		boolean bool = false;
		boolean byKey = redisService.getByKey("getReqOnceKeyByPhone-"+phone+"-"+timeStamp, phone+"-"+timeStamp);
		logger.info("查询缓存中是否有值byKey: "+byKey);
		if(!byKey){
			redisService.put1("getReqOnceKeyByPhone-"+phone+"-"+timeStamp, phone+"-"+timeStamp, JSON.toJSON(timeStamp));
			bool = true;
		}else {
			Object msecTime = redisService.get("getReqOnceKeyByPhone-"+phone+"-"+timeStamp, phone+"-"+timeStamp, Object.class);
			if(msecTime.equals(timeStamp)){
				//重复提交了
				bool = false;
			}else{
				
				bool = true;
			}
		}
		return bool;
	}


	//群发通知的时候,删除掉原先的缓存
	@Override
	public String removeReqOnceKeyByPhone(String phone, String timeStamp) {
		try {
			if(!PubMethod.isEmpty(timeStamp)){
				redisService.remove("getReqOnceKeyByPhone-"+phone+"-"+timeStamp, phone+"-"+timeStamp);
			}
		} catch (Exception e) {
			//e.printStackTrace();
			logger.info("删除重复提交的缓存失败, 失败原因: "+e.getStackTrace());
			return "false";
		}
		return "true";
	}


	//判断模板是否通过
	@Override
	public String getTemplateContent(String content,Long memberId) {

		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("content", content);
		String methodName="getTemplateContentController/getTemplateContent";
		logger.info("根据content: "+methodName+" ,memberId: "+memberId+" 查询通过模板返回值为result: "+methodName);
		return openApiHttpClient.doPassSendStr(methodName, map);
		
	}
	@Override
	public String queryOnlyRepeatTag(Long memberId, String timeStamp, String accountId, String phone, String sendContent) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("timeStamp", timeStamp);
		map.put("accountId", accountId);
		map.put("phone", phone);
		map.put("sendContent", sendContent);
		String methodName="smsTimedController/queryOnlyRepeatTag";
		String result = openApiHttpClient.doPassSendStr(methodName, map);
		logger.info("根据memberId: "+memberId+" ,timeStamp:"+timeStamp+" 查询短信是否重复提交返回值为result: "+result);
		return result;
	}
	
}























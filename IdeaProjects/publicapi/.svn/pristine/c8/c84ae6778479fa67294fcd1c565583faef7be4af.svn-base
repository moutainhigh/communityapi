package net.okdi.apiV5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV4.service.InterceptService;
import net.okdi.apiV4.service.MsgTemplateService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.apiV5.service.NewExpGatewayService;
import net.okdi.core.base.BaseService_jdw;
import net.okdi.core.base.NewBaseService_jdw;
import net.okdi.core.common.page.Page;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.passport.ShortLinkHttpClient;
import net.okdi.core.passport.SmsHttpClient;
import net.okdi.core.util.NewSensitivewordFilter;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.SensitivewordFilter;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.ExpGatewayService;
import net.okdi.mob.service.RegisterService;
import net.okdi.mob.service.SensitiveWordService;
@SuppressWarnings("all")
@Service
public class NewExpGatewayServiceImpl extends NewBaseService_jdw implements NewExpGatewayService {
	
	private static Logger logger = Logger.getLogger(NewExpGatewayServiceImpl.class);
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
	
	@Autowired
	private MsgTemplateService msgTemplateService;
	private static Integer ONESENDNUM=500;//1000;  //群发通知每次提交上限
	
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 * @param 
	 */
	@Override
	public String sendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			String md5Hex, String version, String system, String timeStamp){//
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
		/**************短信原先是在这验证繁体字,黑白词**************************************************/	
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
		smsTemplate =  SetSmsContentNew(smsTemplate,phoneAndWaybillNum,memberPhone);
		Map<String,Object> reMap = new HashMap<String,Object>();
		logger.info("新版返回的数据格式smsTemplate:"+smsTemplate);
		if(!PubMethod.isEmpty(smsTemplate)){
			logger.info("新版返回的数据第一步:::::111111111111");
			JSONArray parseArray = JSONArray.parseArray(smsTemplate);
			logger.info("新版返回的数据第二步:::::222222222222");
			int size = parseArray.size();
			if(size > 0){
				logger.info("新版返回的数据第三步:::::333333333333");
				JSONObject jsonObject = parseArray.getJSONObject(0);
				logger.info("新版返回的数据第四步:::::444444444444");
				if(!PubMethod.isEmpty(jsonObject.getString("isCharge"))){
					System.out.println("555");
					if("2".equals(jsonObject.getString("isCharge"))){
						logger.info("新版返回的数据第五步:::::555555555555");
						if(!jsonObject.getBoolean("success")){
							reMap.put("success",jsonObject.getString("success"));
							reMap.put("cause", jsonObject.getString("cause"));
							reMap.put("flag", "");
							System.out.println("666");
							logger.info("新版返回的数据第六步:::::666666666666666: "+reMap);
							taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
							return JSON.toJSONString(reMap);//如果success为false的话则提示错误信息,手机号不正确, 否则流程继续
						}
					}
					
				}
			}
			//throw new ServiceException("发送通知异常");	
		}
		
		//正常发送成功的短信才会给存入到黑名单中
		String[] arrs = phoneAndWaybillNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-短信，每次发送数量最多为"+this.ONESENDNUM+"条");//每次最多500条
			errorMap.put("flag", "");
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
			logger.info("新版每小时发送返回的json串-短信: "+json);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
		logger.info("新版开始调用发送短信接口,,,....smsHttpClient.doSmsSend_HJZX........................................");
		String doSmsSend = smsHttpClient.newDoSmsSend_HJZX
				(accountId,channelNO, smsTemplate,memberId,memberPhone,flag,msgId,isNum, pickupTime,pickupAddr, 
				compName, version, system);
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
	
	@Override
	public String newSendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			String md5Hex, String version , String system, String timeStamp){//
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
		/**************短信原先是在这验证繁体字,黑白词**************************************************/	
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
		smsTemplate =  SetSmsContentNew(smsTemplate,phoneAndWaybillNum,memberPhone);
		Map<String,Object> reMap = new HashMap<String,Object>();
		logger.info("新版返回的数据格式smsTemplate:"+smsTemplate);
		if(!PubMethod.isEmpty(smsTemplate)){
			logger.info("新版返回的数据第一步:::::111111111111");
			JSONArray parseArray = JSONArray.parseArray(smsTemplate);
			logger.info("新版返回的数据第二步:::::222222222222");
			int size = parseArray.size();
			if(size > 0){
				logger.info("新版返回的数据第三步:::::333333333333");
				JSONObject jsonObject = parseArray.getJSONObject(0);
				logger.info("新版返回的数据第四步:::::444444444444");
				if(!PubMethod.isEmpty(jsonObject.getString("isCharge"))){
					System.out.println("555");
					if("2".equals(jsonObject.getString("isCharge"))){
						logger.info("新版返回的数据第五步:::::555555555555");
						if(!jsonObject.getBoolean("success")){
							reMap.put("success",jsonObject.getString("success"));
							reMap.put("cause", jsonObject.getString("cause"));
							reMap.put("flag", "");
							System.out.println("666");
							logger.info("新版返回的数据第六步:::::666666666666666: "+reMap);
							return JSON.toJSONString(reMap);//如果success为false的话则提示错误信息,手机号不正确, 否则流程继续
						}
					}
					
				}
			}
			//throw new ServiceException("发送通知异常");	
		}
		
		//正常发送成功的短信才会给存入到黑名单中
		String[] arrs = phoneAndWaybillNum.split("\\|");
		if(arrs.length>this.ONESENDNUM){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "群发通知-短信，每次发送数量最多为"+this.ONESENDNUM+"条");//每次最多1000条
			errorMap.put("flag", "");
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
			logger.info("新版每小时发送返回的json串-短信: "+json);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeRepeatTag(memberId,timeStamp);
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
		logger.info("新版开始调用发送短信接口,,,....smsHttpClient.doSmsSend_HJZX........................................");
		String doSmsSend = smsHttpClient.newDoSmsSend_HJZX
				(accountId,channelNO, smsTemplate,memberId,memberPhone, flag,msgId,isNum, pickupTime,pickupAddr, 
						compName, version, system);
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
	
	@Override
	 public String findMassnotificationrecord(Long memberId,String date,Integer pageSize,Integer pageNo,Short status,Short type,String receiverPhone,String sendContent ,String number, String billNum,String name){
		 
	  		return smsHttpClient.querySmsSend_HJZX2(memberId, date, pageSize, pageNo, status, type, receiverPhone, sendContent ,number, billNum,name);
		 
	 }
	
	/**
	 * 	短信聊天
	 */
	@Override
	public String querySmsChat(String firstMsgId,int pageNo,int pageSize) {
		return smsHttpClient.querySmsChat(firstMsgId,pageNo,pageSize);
	}
	
	
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 */
	@Override
	public String sendSmsTwo(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,
			boolean isValidate,String voiceUrl,String msgId,Short isNum, String version){
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
		String memberInfo = this.getMemberInfoById(memberId);
		Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
		String memberPhone = memberMap.get("memberPhone").toString();
		String smsTemplates="";
		if(smsTemplate.subSequence(0, 2).equals("您好")){
			String[] head=smsTemplate.split("\\s+");
			smsTemplates=smsTemplate.substring(head[0].length(),smsTemplate.length()-head[head.length-1].length());
		}else if(smsTemplate.substring(0, 2).equals("我是")){
			String[] head=smsTemplate.split(",");
			String[] end=smsTemplate.split("\\s+");
			smsTemplates=smsTemplate.substring(head[0].length(),smsTemplate.length()-end[end.length-1].length());
		}else{
			smsTemplates=smsTemplate;
		}
		if(isValidate){//验证黑白词汇，语音不验证
			//2015 08 20 kai.yang start  查询黑名单和白名单的敏感词
			Set<String> blackSet=sensitiveWordService.queryBlackList();
			Set<String> whiteSet=sensitiveWordService.queryWhiteList();
			SensitivewordFilter blackFilter = new SensitivewordFilter("black",blackSet);
			SensitivewordFilter whiteFilter = new SensitivewordFilter("white",whiteSet);
			//2015 08 20 kai.yang end
			boolean blackflag = blackFilter.isContaintSensitiveWord(smsTemplates, 1);
			boolean whiteflag = whiteFilter.isContaintSensitiveWord(smsTemplates, 1);
			if(blackflag||(!whiteflag && PubMethod.isEmpty(msgId))){
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "通知内容不符合发送要求");
				sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
				return JSON.toJSONString(errorMap);
			}
		}
		int smsLength=smsTemplate.length();
		int newSmsLength=smsTemplate.replaceAll("(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?","").length();
		if(smsLength!=newSmsLength){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "通知内容不符合发送要求");
			sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
			return JSON.toJSONString(errorMap);
		}
		String channelNO = "01";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		String compId = String.valueOf(memberMap.get("compId"));//快递员所属站点id
		if(PubMethod.isEmpty(compId)){
			compId = "33620";
		}
		String ResultContenNew =  SetSmsContentNewTwo(smsTemplate,phoneAndWaybillNum,memberPhone,lng,lat);
		if(PubMethod.isEmpty(ResultContenNew)){
			throw new ServiceException("发送通知异常");	
		}
		Short flag = 1;
		if(!PubMethod.isEmpty(msgId)){
			flag = 4;
		}
		String doSmsSend = smsHttpClient.doSmsSend_HJZX
				(accountId,channelNO, ResultContenNew,memberId,memberPhone, flag,voiceUrl,msgId,isNum, "", "","");//最后三个空串是取件时间,取件地址,公司名称
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
	@Override
	public String getMemberInfoById(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("memberInfo/getMemberInfoById",paraMeterMap);	
	}
	
	@Override
	public String queryParcelNumber(String msgId) {
		String flag = "";
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("sendTaskId", Long.valueOf(msgId));
			String url = constPool.getSmsHttpUrl();
			String result = smsHttpClient.Post(url+"parParcelInfo/findNumber", map);
			//String result = openApiHttpClient.doPassSendStr("parParcelInfo/findNumber", map);
			logger.info("短信聊天根据firstMsgId查询包裹编号返回的result: "+result+", url: "+url);
			if(!PubMethod.isEmpty(result)){
				JSONObject parseObject = JSONObject.parseObject(result);
				if(!PubMethod.isEmpty(parseObject)){
					if(parseObject.getBooleanValue("success")){
						flag = parseObject.getString("parNum");
					}else{
						flag = "";
					}
				}else{
					flag = "";
				}
			}else{
				flag = "";
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("短信聊天根据firstMsgId查询包裹编号失败, 失败原因: "+e.getStackTrace());
			flag = "";
		}
		return flag;
		
	}
	
	@Override
	public String newQueryParcelNumber(String parcelId) {
		logger.info("新版根据包裹id查询包裹编号进来parcelId:"+parcelId);
		String flag = "";
		if(PubMethod.isEmpty(parcelId)){
			return flag;	
		}
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("parcelId", Long.valueOf(parcelId));
			String url = constPool.getSmsHttpUrl();
			String result = smsHttpClient.Post(url+"parParcelInfo/newFindNumber", map);
			//String result = openApiHttpClient.doPassSendStr("parParcelInfo/findNumber", map);
			logger.info("新版短信聊天根据firstMsgId查询包裹编号返回的result: "+result+", url: "+url);
			if(!PubMethod.isEmpty(result)){
				JSONObject parseObject = JSONObject.parseObject(result);
				if(!PubMethod.isEmpty(parseObject)){
					if(parseObject.getBooleanValue("success")){
						flag = parseObject.getString("parNum");
					}else{
						flag = "";
					}
				}else{
					flag = "";
				}
			}else{
				flag = "";
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("新版短信聊天根据firstMsgId查询包裹编号失败, 失败原因: "+e.getStackTrace());
			flag = "";
		}
		return flag;
	
	}
	
	
	@Override
	public String queryCountAndCost(String queryDate, String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("queryDate", queryDate);
		map.put("memberId", memberId);
		String response = Post(constPool.getSmsHttpUrl() + "mongoSms/queryCountAndCost", map);
		if (response == null || "".equals(response)){
			return null;
		}
		return response;
	}
	
}
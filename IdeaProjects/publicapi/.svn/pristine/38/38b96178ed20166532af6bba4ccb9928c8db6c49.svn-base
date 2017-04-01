package net.okdi.apiV4.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.apiV4.service.SendSmsAndPhoneService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller("sendSmsAndPhoneController")
@RequestMapping("sendSmsAndPhone")
public class SendSmsAndPhoneController extends BaseController{
	
	Logger logger=Logger.getLogger(SendSmsAndPhoneController.class);
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	@Autowired
	private SendSmsAndPhoneService sendSmsAndPhoneService;
	private static Integer ONESENDNUM=1000;  //群发通知每次提交上限
	/**	 * 
	 * @api {post} /sendSmsAndPhone/send 群呼+短信
	 * @apiPermission user
	 * @apiDescription  群发送至 kai.yang
	 * @apiparam {String} accountId 钱包ID
	 * @apiparam {String} phoneAndNum 手机号和编号 ，电话号-编号-是否关注微信|电话号-编号-是否关注微信 这样  0.已关注 1.未关注
	 * @apiparam {String} noticePhone 通知号码
	 * @apiparam {Long} memberId 当前收派员的memberId
	 * @apiparam {String} content 短信内容
	 * @apiparam {String} flag 7.电话加群呼
	 * @apiGroup 通知记录
	 * @apiSampleRequest /sendSmsAndPhone/send
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   	
	 * @apiVersion 0.2.0
	 */
	@ResponseBody
	@RequestMapping("send")
	public String send(String accountId,String phoneAndNum,String noticePhone,Long memberId,String content,Short flag){
		logger.info("群发通知accountId:"+accountId+",phoneAndNum:"+phoneAndNum+",noticePhone:"+
				noticePhone+",memberId:"+memberId+",content:"+content+",flag:"+flag);
		if(PubMethod.isEmpty(phoneAndNum)){
			return paramsFailure("SendSmsAndPhoneController.send.phoneAndNum","电话号码以及编号不能为空");
		}
		if(PubMethod.isEmpty(noticePhone)){
			return paramsFailure("SendSmsAndPhoneController.send.noticePhone","通知电话不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("SendSmsAndPhoneController.send.memberId","用户Id不能为空");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure("SendSmsAndPhoneController.send.content","发送内容不能为空");
		}
		if(PubMethod.isEmpty(accountId)){
			return paramsFailure("SendSmsAndPhoneController.send.accountId","钱包id不能为空");
		}
		if(PubMethod.isEmpty(flag)){
			return paramsFailure("SendSmsAndPhoneController.send.flag","发送标识不能为空");
		}
		int sub = content.indexOf(",");
		String smsContent=content.substring(sub+1, content.length());
		try {
			Long chargeNum = 0l; //需要收费的总数
			String[] arrs=phoneAndNum.split("\\|");
			Map<String, String> receiverPhoneMap = new HashMap<String, String>();
			JSONObject jsons;
			String sendNum = telephoneRewardService.getSMSOrPhoneNum(memberId,accountId);
			logger.info(memberId+"：查询出查询可用电话、短信和免费短信条数："+sendNum);
			jsons = JSONObject.parseObject(sendNum);
			if(jsons.getBooleanValue("success")){							//调用查询通知可用条数接口成功
				String data=jsons.getString("data");							
				jsons=JSONObject.parseObject(data);						
				Integer one=jsons.getInteger("one");	//一条短信的最大长度
				Integer two=jsons.getInteger("two");	//两条短信的最大长度
				Integer three=jsons.getInteger("three"); //三条短信的最大长度
				Integer smsContentLen=smsContent.length();
				Integer smsContentLenNew=smsContent.replaceAll("#编号#", "").length();
				Integer len=smsContent.length();
				if(smsContentLen!=smsContentLenNew){//判断是否要插入编号
					Integer numLen=0;
					for(String str:arrs){//遍历出来长度最大的编号
						if(((str.split("-")[1]).length())>numLen){
							numLen=(str.split("-")[1]).length();
						}
					}
					//前台传过来的有"#编号#"，我们要替换为"编号"，所以计算长度的时候需要减去两位
					len=numLen+content.length()-2;
				}
				logger.info(memberId+"的短信字数为:"+len+"个");
				Integer feeCount=arrs.length*2; // 因为即发短信又发语音,所以乘2
				
				if(len>three){
					Map<String,Object> errorMap = new HashMap<String,Object>();
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
						Map<String,Object> errorMap = new HashMap<String,Object>();
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
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", "余额不足，群发通知失败");
						errorMap.put("flag", "1");
						return JSON.toJSONString(errorMap);
					}else{
						//掉接口
						StringBuffer sb=new StringBuffer();
						String sbresult="";
						for(String rece:arrs){
							String[] phone=rece.split("-");
							sb.append(phone[0]+"-");
						}
						sbresult=sb.substring(0, sb.length()-1);
						String result=smsSendService.isExist(memberId,sbresult);
						JSONObject json=JSONObject.parseObject(result);
						if(json.getBoolean("success")){
							chargeNum=json.getLong("data"); //需要收费的条数
							String str=json.getString("str");
							String[] receiverPhones=str.split("\\|");
							for(String phoneNum:receiverPhones){
								receiverPhoneMap.put(phoneNum.split("-")[0], phoneNum.split("-")[1]);
							}
							if(len<=one){
								chargeNum*=1;
							}else if(one<len&&len<=two){
								chargeNum*=2;
							}else if(two<len&&len<=three){
								chargeNum*=3;
							}else{
								Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "发送短信失败");
								return JSON.toJSONString(errorMap);
							}
							Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
							if((chargeNum+feeCount/2)>lenSmss){
								Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "余额不足，群发通知失败");
								errorMap.put("flag", "1");
								return JSON.toJSONString(errorMap);
							}
							}
						}
					}
				logger.info(memberId+"的短信条数为:"+feeCount+"条");
			}else{
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "发送短信失败");
				return JSON.toJSONString(errorMap);
			}
			
				String result="";
				Date date=new Date();
				String start=new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(date);
				String end=new SimpleDateFormat("yyyy-MM-dd 07:00:00").format(date);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info("开始时间"+start+"结束时间"+end);
				Date startTime=sim.parse(start);
				Date endTime=sim.parse(end);
				if(date.after(startTime)||date.before(endTime)){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "夜间12点到早晨7点无法使用群发通知功能");
					return JSON.toJSONString(errorMap);
				}
				if((arrs.length*2)>this.ONESENDNUM){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "群发通知，每次发送数量最多为"+this.ONESENDNUM+"条");
					return JSON.toJSONString(errorMap);
				}
					String res=this.smsSendService.memberSendNumQueryOneDay(memberId, arrs.length*2);
					JSONObject json=JSONObject.parseObject(res);
					if(!json.getBoolean("success")){
						return res;
					}
					res=this.smsSendService.memberSendNumQueryOneHour(memberId, arrs.length*2);
					json=JSONObject.parseObject(res);
					if(!json.getBoolean("success")){
						return res;
					}
					String smsAndWaybillNum="";
					int i=0;
					for(int j=0;j<arrs.length;j++){
						String phone=arrs[j].split("-")[0];
						String num=arrs[j].split("-")[1];
						String isWx=arrs[j].split("-")[2];
						if(i==0){
							smsAndWaybillNum+=phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone);
							i++;
						}else{
							smsAndWaybillNum+="|"+phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone);
						}
					}
					String phoneAndWaybillNum="";
					for(int j=0;j<arrs.length;j++){
						String phone=arrs[j].split("-")[0];
						if(j==0){
							arrs[j]=arrs[j]+"-"+receiverPhoneMap.get(phone);
							phoneAndWaybillNum+=arrs[j];
						}else{
							arrs[j]="|"+arrs[j]+"-"+receiverPhoneMap.get(phone);
							phoneAndWaybillNum+=arrs[j];
						}
					}
					result=this.sendSmsAndPhoneService.sendSms(accountId,smsContent,content, phoneAndWaybillNum,smsAndWaybillNum, memberId,false,true,(short)1);
		return result;
	} catch (Exception e) {
		e.printStackTrace();
		logger.info(memberId+"：发送短信失败:"+"群发通知accountId:"+accountId+",phoneAndNum:"+phoneAndNum+",noticePhone:"+
				noticePhone+",memberId:"+memberId+",content:"+content+",flag:"+flag);
		Map<String,Object> errorMap = new HashMap<String,Object>();
		errorMap.put("success",false);
		errorMap.put("cause", "发送短信失败");
		return JSON.toJSONString(errorMap);
	}

	}
}

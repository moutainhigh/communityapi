package net.okdi.apiV5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.apiV5.service.NewExpGatewayService;
import net.okdi.apiV5.service.NewSendPhoneNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/newSendNoticeController")
public class NewSendPhoneNoticeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(NewSendPhoneNoticeController.class);
	
	@Autowired
	private NewSendPhoneNoticeService newSendPhoneNoticemsService;
	@Autowired
	private SendPackageService newSendPackageService;//注入service
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private WalletNewService walletNewService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	@Autowired
	private NewExpGatewayService newExpGatewayService;
	
	@Autowired
	ConstPool constPool;
	
	@Autowired
	TaskMassNoticeService taskMassNoticeService;
	
	
	
	
	private static Integer ONESENDNUM=1000;//1000;  //群发通知每次提交上限
	/**	 * 
	 * @api {post} /newSendNoticeController/newSendPhoneNotice 首页普通单群发通知
	 * @apiPermission user
	 * @apiDescription  群发送至 jiong.zheng
	 * @apiparam {String} accountId 钱包ID
	 * @apiparam {String} phoneAndNum 手机号和编号 ，电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|  这样  0.关注 1.未关注(如果首页群发通知的运单号拼上空"")
	 * @apiparam {String} noticePhone 通知号码
	 * @apiparam {Long} memberId 当前收派员的memberId
	 * @apiparam {String} content 短信内容
	 * @apiparam {Short} flag 1：仅短信  2:电话优先 5 微信优先  (3.仅电话 7.电话加群呼)[弃用]
	 * @apiparam {String} timeStamp 防重复提交的唯一串
	 * @apiparam {String} pickupTime 取件时间
	 * @apiparam {String} pickupAddr 取件地址
	 * @apiparam {String} compName 公司名称
	 * @apiparam {String} templateId 模板id
	 * @apiparam {String} versionNum 版本号 如 5.0.0 ======2017年2月15日 20:03:38 新增字段
	 * @apiparam {String} system 操作系统  安卓 传 Android, 苹果  传 IOS(不能为空)
	 * @apiGroup 新版-短信
	 * @apiSampleRequest /newSendNoticeController/newSendPhoneNotice
	 * @apiSuccess {String} result true
	 * @apiError {String} result false
	 * @apiSuccessExample {json} Success-Response:
	 *     HTTP/1.1 200 OK
	 * 		{"success":"true"}
	 * @apiErrorExample {json} Error-Response:
	 *     HTTP/1.1 200 OK
	 *   	
	 * @apiVersion 5.0.0
	 */
	@ResponseBody
	@RequestMapping(value="/newSendPhoneNotice",method={RequestMethod.POST,RequestMethod.GET})
	public String sendPhoneNotice(String accountId,String phoneAndNum,String noticePhone,Long memberId,String content,Short flag, 
			String timeStamp, String pickupTime, String pickupAddr, String compName,String templateId, String versionNum, String system){
		logger.info("新版群发通知accountId:"+accountId+",phoneAndNum:"+phoneAndNum+",noticePhone:"+
				noticePhone+",memberId:"+memberId+",content:"+content+",flag:"+flag+",pickupTime:"+pickupTime+",pickupAddr:"
				+pickupAddr+",compName: "+compName+",templateId:"+templateId+",version:"+versionNum);
		
		//String pickupTime="01月01日 10:00-10:30";//取件时间 格式:01月01日 10:00-10:30
		//String compName="百世汇通";//快递公司
		//String pickupAddr="北京市海淀区花园路14号环星大厦A座7层......";//取件地址
		if(PubMethod.isEmpty(phoneAndNum)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.phoneAndNum","电话号码以及编号不能为空");
		}
		if(PubMethod.isEmpty(noticePhone)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.noticePhone","通知电话不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.memberId","用户Id不能为空");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.content","发送内容不能为空");
		}
		if(PubMethod.isEmpty(versionNum)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.versionNum","版本号不能为空");
		}

		String[] arrs=phoneAndNum.split("\\|");
		timeStamp=DigestUtils.md5Hex(timeStamp+content);
		if(!PubMethod.isEmpty(timeStamp)){
			String obj = taskMassNoticeService.queryOnlyRepeatTag(memberId,timeStamp, accountId, noticePhone, content, versionNum, system);//只根据phoneAndNum md5加密, 跟内容没关系
			logger.info("新版首页群发验证手机端传过来的唯一串是否重复提交memberId: "+memberId+" ,timeStamp: "+timeStamp+" ,接口返回 obj:"+obj);
			if(!PubMethod.isEmpty(obj)){
				JSONObject parseObject = JSONObject.parseObject(obj);
				String flagTag = parseObject.getString("data");
				if("002".equals(flagTag)){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "请勿重复提交，或查看通知记录是否发送成功！");
					errorMap.put("flag", "");
					return JSON.toJSONString(errorMap);
				}
			}
		}
		//防止重复提交2
		String mds5Str = noticePhone+"-"+phoneAndNum+"-"+content.trim();//发送人电话号码-全部的手机号-去掉空格的内容
		
		String md5Hex = DigestUtils.md5Hex(mds5Str);
		logger.info("新版mds5Str: "+mds5Str +" 普通生成的md5Hex: "+md5Hex);
		boolean bool = taskMassNoticeService.getReqOnceKeyByPhone(noticePhone, md5Hex);
		if(!bool){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "请勿重复提交，或查看通知记录是否发送成功！！");
			errorMap.put("flag", "");
			logger.info("新版 ...=====");
			return JSON.toJSONString(errorMap);
		}
		logger.info("aaaaaaaaaaaaaaaaaaaaaaaaaaa");
		/**start****增加对模板内容的修改的判断**********2016/12/27******************************/
		//根据memerid和content查询短信模板的信息，若果有，判断是状态是人工过还是系统过
		logger.info("新版对短信内容进行过滤，判断审核状态");
		if (!flag.equals((short)2)){//如果群呼 flag=2或者3的时候,模板不保存
			String returnjson=newSendPhoneNoticemsService.queryMsgTemplate
					(accountId,content,phoneAndNum,memberId,null,null,false,true,
							null,null,(short)1 , pickupTime,  pickupAddr,  compName,  flag,  noticePhone,  
							md5Hex,templateId, timeStamp);
			logger.info("新版短信模板审核返回的数据*******************returnjson: "+returnjson);
			///对返回的数据进行解析
			Map<String,Object>errormap = JSON.parseObject(String.valueOf(JSON.parseObject(returnjson)));
			if ("false".equals(errormap.get("success").toString())){
				logger.info("新版过滤返回的结果："+returnjson);
				return returnjson;
			}
		}
		/**stop****增加对模板内容的修改的判断****************************************/
		
		content=content.trim();
		if(PubMethod.isEmpty(accountId)){
			accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
		}
		Long chargeNum = 0l; //需要收费的总数
		logger.info("新版bbbbbbbbbbbbb");
		Map<String, String> receiverPhoneMap = new HashMap<String, String>();
		JSONObject jsons;
		try {
			String sendNum = telephoneRewardService.getSMSOrPhoneNum(memberId,accountId);
			//{"data":{"contentNum":1,"lenPhone":"0","lenSms":"0","one":62,"preferentialNum":"no","telecomBalance":0,"three":193,"two":126},"success":true}
			logger.info(memberId+"：新版查询出查询可用电话、短信和免费短信条数："+sendNum);//250473483296768：查询出查询可用电话、短信和免费短信条数：
			jsons = JSONObject.parseObject(sendNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(memberId+"：新版查询发送人的电话号码失败");
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "发送短信失败");
			errorMap.put("flag", "");
			return JSON.toJSONString(errorMap);
		}
		if(jsons.getBooleanValue("success")){							//调用查询通知可用条数接口成功
			String data=jsons.getString("data");							
			jsons=JSONObject.parseObject(data);
			//使用可用余额进行判断短信是否能发
			Integer one=jsons.getInteger("one");	//一条短信的最大长度
			Integer two=jsons.getInteger("two");	//两条短信的最大长度
			Integer three=jsons.getInteger("three"); //三条短信的最大长度
			Integer contentLen=content.length();
			Integer contentLenNew=content.replaceAll("#编号#", "").length();
			Integer len=content.length();
			if(contentLen!=contentLenNew){//判断是否要插入编号
				Integer numLen=0;
				for(String str:arrs){//遍历出来长度最大的编号
					if(((str.split("-")[1]).length())>numLen){
						numLen=(str.split("-")[1]).length();
					}
				}
				//前台传过来的有"#编号#"，我们要替换为"取件码"，所以计算长度的时候需要减去一位
				len=numLen+content.length()-1;
			}
			logger.info(memberId+"的短信字数为:"+len+"个");
			Integer feeCount=arrs.length;
			if(flag==2){								//用户选择电话优先
				String sendContent=content;
				int num = sendContent.indexOf(",");
				sendContent = sendContent.substring(num+1, sendContent.length()); 
				len=sendContent.length();
				if(len>three){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag", "");
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
						errorMap.put("flag", "");
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
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag", "");
						taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
						taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
						return JSON.toJSONString(errorMap);
					}else{
						StringBuffer sb=new StringBuffer();
						String sbresult="";
						try {
						for(String rece:arrs){
							String[] phone=rece.split("-");
							sb.append(phone[0]+"-");
						}
						sbresult=sb.substring(0, sb.length()-1);
						} catch (Exception e) {
							e.printStackTrace();
						}
						String result=smsSendService.isExist(memberId,sbresult);//recephone-0免费或者1收费
						JSONObject json=JSONObject.parseObject(result);//TODO
						Object success =  json.get("success");
						if("true".equals(success.toString())){
							chargeNum=json.getLong("data");
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
								errorMap.put("flag", "");
								return JSON.toJSONString(errorMap);
							}
							Integer lenSmss=Integer.valueOf(jsons.getString("lenPhone"));
							if(chargeNum>lenSmss){
								Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
								errorMap.put("flag", "");
								taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
								return JSON.toJSONString(errorMap);
						}
						}
					}
				}
				
			}else if(flag==1){
				if(len>three){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag", "");
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
						errorMap.put("flag", "");
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
						errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
						errorMap.put("flag", "");
						taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
						taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
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
						JSONObject json=JSONObject.parseObject(result);//TODO
						if(json.getBoolean("success")){
							chargeNum=json.getLong("data");
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
								errorMap.put("flag", "");
								return JSON.toJSONString(errorMap);
							}
							if(chargeNum>lenSms){ //if(chargeNum>lenSmss){ 修改时间 2016年10月17日 15:15:30
								Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "当前余额不足，推荐您使用“微信优先”免费群发通知，或充值使用短信和群呼。");
								errorMap.put("flag", "");
								taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
								return JSON.toJSONString(errorMap);
							}
							}
						}
					}
					
				}else if(flag==5){
					if(len>three){
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", "短信内容不能超过"+three+"个字");
						errorMap.put("flag", "");
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
							errorMap.put("flag", "");
							return JSON.toJSONString(errorMap);
						}
					}
					Integer preferentialNum=0;
					if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
						preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
					}	
					
					if(!"no".equals(jsons.getString("lenSms"))){
						Integer lenSms=Integer.valueOf(jsons.getString("lenSms"));
						if(feeCount>(lenSms+preferentialNum)){ //if(feeCount>(lenSms+preferentialNum)){ 修改时间 2016年10月17日 15:15:42
							Map<String,Object> errorMap = new HashMap<String,Object>();
							errorMap.put("success",false);
							errorMap.put("cause", "余额不足，群发通知失败");
							errorMap.put("flag", "1");
							taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
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
							JSONObject json=JSONObject.parseObject(result);//TODO
							if(json.getBoolean("success")){
								chargeNum=json.getLong("data");
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
									errorMap.put("flag", "");
									return JSON.toJSONString(errorMap);
								}
								Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
								if(chargeNum>lenSmss){ //if(chargeNum>lenSmss){ 修改时间 2016年10月17日 15:15:30
									Map<String,Object> errorMap = new HashMap<String,Object>();
									errorMap.put("success",false);
									errorMap.put("cause", "余额不足，群发通知失败");
									errorMap.put("flag", "1");
									taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
									taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
									return JSON.toJSONString(errorMap);
								}
								}
							}
						}
				}
				logger.info(memberId+"的短信条数为:"+feeCount+"条");
			}else{
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "发送短信失败");
				errorMap.put("flag", "");
				return JSON.toJSONString(errorMap);
			}
		//*******************发送短信的时间限制******************************
			String smsStartTime=constPool.getSmsStartTime();//短信开始时间
			String smsEndTime = constPool.getSmsEndTime();//短信结束时间
			String smsCause = constPool.getSmsCause();//提示语
			logger.info("新版首页群发通知短信的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
				
			String result="";
			Date date=new Date();
			String start=new SimpleDateFormat(smsStartTime).format(date);//23:59:59
			String end=new SimpleDateFormat(smsEndTime).format(date);
			SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				logger.info("新版开始时间"+start+"结束时间"+end);
				Date startTime=sim.parse(start);
				Date endTime=sim.parse(end);
				if(date.after(startTime)&&date.before(endTime)){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", smsCause);
					errorMap.put("flag", "");
					taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
					taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
					return JSON.toJSONString(errorMap);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			//原先在这里发送条数存库的*********************************************************
			if(PubMethod.isEmpty(flag)){
				return paramsFailure("新版SendPhoneNoticeController.sendPhoneNotice.flag","发送标识不能为空");
			}else if(flag==2){//打电话或者电话不通发短信
				String phoneAndWaybillNum="";
				logger.info("flag=2****arr: "+arrs);
				for(int j=0;j<arrs.length;j++){//电话号-编号-是否关注微信
					String phone=arrs[j].split("-")[0];//手机号
					/******************兼容老版本*************************/
					//电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id 
					String wayBill="";//运单号
					String netName="";//网络名称
					String name="";//姓名
					String parcelId="";//包裹id
					if(arrs[j].split("-").length>3){
						wayBill=arrs[j].split("-")[3];//运单号
						if(arrs[j].split("-").length>4){
							netName=arrs[j].split("-")[4];//网络名称
						}
					}
					if(arrs[j].split("-").length>5){
						name=arrs[j].split("-")[5];//姓名
					}
					logger.info("新版cccccccccccccccccccccccddddddddddddddddddd***wayBill: "+wayBill);
					if(arrs[j].split("-").length>6){
						parcelId = arrs[j].split("-")[6];//包裹id
					}
					/*********************根据parcelId 查询短信记录是否存在,存在拿到firstMsgId*********************/
					String resultP = newSendPhoneNoticemsService.queryFirstMsgIdByParcelId(parcelId,flag);
					JSONObject parseObject = JSONObject.parseObject(resultP);
					String firstMsgId = parseObject.getString("firstMsgId");
					logger.info("语音根据包裹id: "+parcelId+" 查询短信记录中聊天会话firstMsgId: "+firstMsgId);
					String isWx="1";
					/////////////////////////////////////////////
					String str=newSendPackageService.queryOpenIdState(phone);
					logger.info("新版语音根据手机号查询该用户关注微信状态str: "+str+" ,该手机号phone: "+phone);
					JSONObject jsonWx=JSONObject.parseObject(str);
					//目前先去掉   2017年2月18日 16:26:10
					if(jsonWx.getBooleanValue("success")){
						//isWx="1";
						logger.info("新版语音解析得到的json串为jsonWx: "+jsonWx);
						JSONObject jsonObject = jsonWx.getJSONObject("data");
						if(!PubMethod.isEmpty(jsonObject)){
							isWx = jsonObject.getString("isRemove");//0:关注, 1:手动解绑, 2:取消关注解绑
						}
					}
					
					/******************************start*********************************************/
					String[] split = arrs[j].split("-");
					String ephone=split[0];//手机号
					String num=split[1];//编号
					if(j==0){
						phoneAndWaybillNum+=ephone+"-"+num+"-"+isWx+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";
					}else{
						phoneAndWaybillNum+="|"+ephone+"-"+num+"-"+isWx+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";
					}
					/******************************end*********************************************/
				}
				content = content.replaceAll("#编号#", "");
				result = newSendPhoneNoticemsService.sendPhoneNotice(accountId,phoneAndWaybillNum, noticePhone,memberId,content+" 快递员的号码为:"+noticePhone,
						flag, pickupTime,pickupAddr, compName, md5Hex, versionNum, system, timeStamp);
			}else if(flag==1||flag==5){ //仅发短信或者微信优先1仅短信,5微信优先//
				String phoneAndWaybillNum="";
				int i=0;
				Integer contentLen=content.length();
				Integer contentLenNew=content.replaceAll("#编号#", "").length();
				String bb = "0";
				if(contentLen != contentLenNew){//内容不相等, 插入编号了
					bb = "1";
				}
				logger.info("contentLen: "+contentLen+",contentLenNew: "+contentLenNew+",bb:"+bb+" ,arrs: "+arrs);
				for(int j=0;j<arrs.length;j++){
					logger.info("****************arrs: "+arrs+" ,j: "+j+" ,arrs: "+arrs[j]);
					String phone=arrs[j].split("-")[0];
					logger.info("新版aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa*****phone: "+phone);
					String num="0"==bb?"":arrs[j].split("-")[1];//编号
					logger.info("新版bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb*****num: "+num);
					/*************************兼容老版本*******************************/
					String wayBill="";
					String netName="";
					String name="";
					String parcelId="";
					logger.info("新版cccccccccccccccccccccccccccccccccccccccccc*****num: ");
					//电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|
					if(arrs[j].split("-").length>3){
						wayBill=arrs[j].split("-")[3];//运单号
						if(arrs[j].split("-").length>4){
							netName=arrs[j].split("-")[4];//网络名称
						}
					}
					if(arrs[j].split("-").length>5){
						name=arrs[j].split("-")[5];//姓名
					}
					logger.info("新版cccccccccccccccccccccccddddddddddddddddddd***wayBill: "+wayBill);
					if(arrs[j].split("-").length>6){
						parcelId = arrs[j].split("-")[6];//包裹id
					}
					/*********************根据parcelId 查询短信记录是否存在,存在拿到firstMsgId*********************/
					String resultP = newSendPhoneNoticemsService.queryFirstMsgIdByParcelId(parcelId,flag);
					logger.info("返回数据为resultP: "+resultP);
					String firstMsgId = "";
					if(!PubMethod.isEmpty(resultP)){
						JSONObject parseObject = JSONObject.parseObject(resultP);
						firstMsgId = parseObject.getString("firstMsgId");
					}
					
					logger.info("短信根据包裹id: "+parcelId+" 查询短信记录中聊天会话firstMsgId: "+firstMsgId+" ,name:"+name+" ,num: "+num);
					String isWx="1";//默认未关注
					String str=newSendPackageService.queryOpenIdState(phone);
					logger.info("新版ffffffffffffffffffffffffffffffffffff*****num: "+num);
					logger.info("新版根据手机号查询该用户关注微信状态str: "+str+" ,该手机号phone: "+phone+" ,phone: "+phone+" ,num: "+num+" ,wayBill: "+wayBill+" ,netName: "+netName+" ,name:"+name);
					JSONObject jsonWx=JSONObject.parseObject(str);
					logger.info("新版ggggggggggggggggggggggggggggggggggggg*****num: "+num);
					//目前先去掉   2017年2月18日 16:26:10
					if(jsonWx.getBooleanValue("success")){
						//isWx="1";
						logger.info("新版解析得到的json串为jsonWx: "+jsonWx);
						JSONObject jsonObject = jsonWx.getJSONObject("data");
						if(!PubMethod.isEmpty(jsonObject.getString("isRemove"))){
							isWx = jsonObject.getString("isRemove");//0:关注, 1:手动解绑, 2:取消关注解绑
						}
					}
					//电话号-编号-是否关注微信-运单号-网络名称
					if(i==0){
						phoneAndWaybillNum+=phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";
						i++;
					}else{
						phoneAndWaybillNum+="|"+phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";
					}
				}
				result=this.newExpGatewayService.sendSms(accountId,content, phoneAndWaybillNum, memberId, null, null,false,true,null,null,(short)1, 
						pickupTime,pickupAddr,compName,flag, noticePhone, md5Hex, versionNum, system, timeStamp);//
			}
			return result;
	}
	
}

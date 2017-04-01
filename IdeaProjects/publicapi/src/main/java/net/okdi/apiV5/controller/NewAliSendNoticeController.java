package net.okdi.apiV5.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.AlisendNoticeService;
import net.okdi.apiV2.service.SmsSendService;
import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.apiV5.service.NewAlisendNoticeService;
import net.okdi.apiV5.service.NewSendPhoneNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.SmsHttpClient;
import net.okdi.mob.service.ExpGatewayService;

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
@RequestMapping("/newAlisendNoticeController")
public class NewAliSendNoticeController extends BaseController{
	
	private static Logger logger = Logger.getLogger(NewAliSendNoticeController.class);
	
	@Autowired
	private NewSendPhoneNoticeService newSendPhoneNoticemsService;
	@Autowired
	private SendPackageService sendPackageService;//注入service
	@Autowired
	private SmsSendService smsSendService;
	@Autowired
	private WalletNewService walletNewService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	@Autowired
	private ExpGatewayService expGatewayService;
	
	@Autowired
	TaskMassNoticeService taskMassNoticeService;
	
	@Autowired
	private NewAlisendNoticeService newAlisendNoticeService;
	
	private static Integer ONESENDNUM=1000;//1000;  //群发通知每次提交上限
	
	@Autowired
	SmsHttpClient smsHttpClient = new SmsHttpClient();
	
	@Autowired
	ConstPool constPool;
	@Autowired
	private NewSendPhoneNoticeController sendPhoneNoticeController;
	
	/**	 * 
	 * @api {post} /newAlisendNoticeController/newAliSendNotice 首页淘宝单群发通知(待派的群发通知)
	 * @apiPermission user
	 * @apiDescription  群发送至 jiong.zheng
	 * @apiparam {String} accountId 钱包ID
	 * @apiparam {String} aliPhoneAndNum 运单号和编号和netId, 运单号-编号-netId-网络名称-包裹id|运单号-编号-netId-网络名称-包裹id| (阿里的)
	 * @apiparam {String} phoneAndNum 手机号和编号 ，电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|电话号-编号-是否关注微信-运单号-网络名称-姓名-包裹id|  这样  0.关注 1.未关注 (普通的)[如果姓名没有传空,但是 - 要拼上]
	 * @apiparam {String} noticePhone 通知号码
	 * @apiparam {Long} memberId 当前收派员的memberId
	 * @apiparam {String} content 短信内容
	 * @apiparam {String} flag 1：仅短信  5:微信优先 2:群呼优先(flag=2待派里面群呼优先)
	 * @apiparam {String} pickupTime 取件时间
	 * @apiparam {String} timeStamp 防重复提交的唯一串
	 * @apiparam {String} pickupAddr 取件地址
	 * @apiparam {String} compName 公司名称
	 * @apiparam {String} templateId 模板id
	 * @apiparam {String} versionCode 版本号(批注:为了兼容之前版本,所以需要传最新的版本号,目前定义版本号最低传5.0.0,切记!切记!)
	 * @apiparam {String} system 操作系统  安卓 传 Android, 苹果  传 IOS(不能为空)
	 * @apiGroup 新版-短信
	 * @apiSampleRequest /newAlisendNoticeController/AliSendNotice
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
	@RequestMapping(value="/newAliSendNotice",method={RequestMethod.POST,RequestMethod.GET})
	public String AliSendNotice(String accountId,String aliPhoneAndNum,String noticePhone,Long memberId,String content,Short flag, String pickupTime, 
			String pickupAddr, String compName, String phoneAndNum, String templateId, String timeStamp, String versionCode, String system){
		logger.info("阿里-普通短信通知accountId:"+accountId+",phoneAndNum:"+phoneAndNum+",noticePhone:"+
				noticePhone+",memberId:"+memberId+",content:"+content+",flag:"+flag+",pickupTime:"+pickupTime+
				",pickupAddr:"+pickupAddr+",compName: "+compName+" ,aliPhoneAndNum: "+aliPhoneAndNum+" ,templateId: "
				+templateId+ " ,timeStamp: "+timeStamp+" ,versionCode: "+versionCode+" ,system: "+system);
		
		
		if(PubMethod.isEmpty(aliPhoneAndNum) && PubMethod.isEmpty(phoneAndNum)){
			return paramsFailure("net.okdi.apiV2.controller.AliSendNoticeController.AliSendNotice", "录入单或者淘宝单不能同时为空 !!!");
		}
		//防止重复提交1, 如果阿里大于不是空进来验证重复提交的 新版加 2017年2月20日 10:54:00
		if(!PubMethod.isEmpty(aliPhoneAndNum)){
			if(!PubMethod.isEmpty(timeStamp) && !PubMethod.isEmpty(versionCode)){
				logger.info("**********timeStamp: "+timeStamp);
				String obj = taskMassNoticeService.queryOnlyRepeatTag(memberId,timeStamp, accountId, noticePhone, content, versionCode, system);
				logger.info("首页扫码验证手机端传过来的唯一串是否重复提交memberId: "+memberId+" ,timeStamp: "+timeStamp+" ,接口返回 obj:"+obj);
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
			
		}
		
		//防止重复提交2
		String mds5Str = noticePhone+"-"+aliPhoneAndNum+"-"+phoneAndNum+"-"+content.trim();//发送人电话号码-全部的手机号-去掉空格的内容
		String md5Hex = DigestUtils.md5Hex(mds5Str);
		logger.info("mds5Str: "+mds5Str +" aaa生成的md5Hex: "+md5Hex);
		//判断余额是否足够
		Map<String,Object> resultMap = this.isSmsBalance(memberId, accountId, content, (short)9, noticePhone, md5Hex, aliPhoneAndNum, phoneAndNum, timeStamp);
		if(!PubMethod.isEmpty(resultMap)){
			Object object = resultMap.get("cause");
			if(!"fff".equals(object)){
				return JSON.toJSONString(resultMap);
			}
		}
		//验证每小时, 和 每天发送的条数
		String[] aliArr=new String[]{};
		String[] ordiArr=new String[]{};
		if(!PubMethod.isEmpty(aliPhoneAndNum)){
			aliArr = aliPhoneAndNum.split("\\|");
		}
		if(!PubMethod.isEmpty(phoneAndNum)){
			ordiArr = phoneAndNum.split("\\|");
		}
		Integer alllen = aliArr.length+ordiArr.length;
		String hourday = smsSendService.queryHoursAndDaynum(alllen, memberId);
		logger.info("验证每小时,每天发送的数量......返回的数据:hourday"+hourday+" ,alllen: "+alllen+" ,memberId: "+memberId);
		if(!PubMethod.isEmpty(hourday)){
			JSONObject parseObject = JSONObject.parseObject(hourday);
			if("true".equals(parseObject.getString("success"))){
				JSONObject jsonObject = parseObject.getJSONObject("data");
				String hourNum = jsonObject.getString("hourNum");//每小时的
				String dayNum = jsonObject.getString("dayNum");//每天的
				//天
				Map<String,Object> maps=new HashMap<String, Object>();
				if("false".equals(hourNum)){
					maps.put("success",false);
					maps.put("flag","");
					maps.put("cause", "群发通知，1小时内发送数量不可超过 1000 条！");
					return JSON.toJSONString(maps);
				}
				if("false".equals(dayNum)){
					maps.put("success",false);
					maps.put("flag","");
					maps.put("cause", "每日群发通知数量最多为 5000 条，您今日已达上限，请明日继续发送");
					return JSON.toJSONString(maps);
				}
			}
		}
		//调用正常短信的
		if(!PubMethod.isEmpty(phoneAndNum)){
			logger.info("首页群发普通的短信串不为空phoneAndNum : "+phoneAndNum);
			//SendPhoneNoticeController sendPhoneNoticeController = new SendPhoneNoticeController();
			String result = sendPhoneNoticeController.sendPhoneNotice(accountId, phoneAndNum, noticePhone, memberId, content, flag, "", pickupTime, pickupAddr, compName, templateId,versionCode, system);
			JSONObject parseObject = JSONObject.parseObject(result);
			
			if("false".equals(parseObject.getString("success"))){
				logger.info("普通群发短信出错,出错原因result: "+result);
				return result;
			}
		}else{
			logger.info("阿里大于验证模板进来........................................content: "+content+" ,templateId: "+templateId);
			String returnjson = newSendPhoneNoticemsService.queryMsgTemplate(accountId, content, phoneAndNum, memberId,null,null,false,true,
					null,null,(short)1 , pickupTime, pickupAddr, compName, flag, noticePhone, md5Hex, templateId, timeStamp);
			logger.info("返回回来的数据......returnjson: "+returnjson);
			Map<String,Object>errormap = JSON.parseObject(String.valueOf(JSON.parseObject(returnjson)));
			if ("false".equals(errormap.get("success").toString())){
				logger.info("过滤返回的结果："+returnjson);
				return returnjson;
			}
		}
		//在判断阿里串中是否为空aliPhoneAndNum
		if(PubMethod.isEmpty(aliPhoneAndNum)){//能到这里,说明是有普通短信的, 并且还没有报错
			logger.info("阿里大于拼的串为空......aliPhoneAndNum: "+aliPhoneAndNum);
			return "{\"success\":\"true\"}";
		}
		if(flag==1||flag==5||flag==2){//加上flag==2 群呼优先， 为了兼容，待派里面的群呼优先 2017-3-13 11:51:47
			flag = 9;
		}
		logger.info("到这里是有阿里大于运单号的aliPhoneAndNum: "+aliPhoneAndNum+" ,flag: "+flag+" ,开始发送阿里短信....");
		//String pickupTime="01月01日 10:00-10:30";//取件时间 格式:01月01日 10:00-10:30
		//String compName="百世汇通";//快递公司
		//String pickupAddr="北京市海淀区花园路14号环星大厦A座7层......";//取件地址
		/*if(PubMethod.isEmpty(aliPhoneAndNum)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.aliPhoneAndNum","运单号以及编号不能为空");
		}*/
		if(PubMethod.isEmpty(noticePhone)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.noticePhone","通知电话不能为空");
		}
		if(PubMethod.isEmpty(memberId)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.memberId","用户Id不能为空");
		}
		if(PubMethod.isEmpty(content)){
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.content","发送内容不能为空");
		}
		
		String[] arrs=aliPhoneAndNum.split("\\|");
		
		boolean bool = taskMassNoticeService.getReqOnceKeyByPhone(noticePhone, md5Hex);
		if(!bool){
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "请勿重复提交，或查看通知记录是否发送成功！！");
			errorMap.put("flag", "");
			return JSON.toJSONString(errorMap);
		}
		content=content.trim();
		if(PubMethod.isEmpty(accountId)){
			accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
		}
		//*******************发送短信的时间限制******************************
		String smsStartTime=constPool.getSmsStartTime();//短信开始时间
		String smsEndTime = constPool.getSmsEndTime();//短信结束时间
		String smsCause = constPool.getSmsCause();//提示语
		logger.info("阿里大于扫码通知短信的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
		String result="";
		Date date=new Date();
		String start=new SimpleDateFormat(smsStartTime).format(date);
		String end=new SimpleDateFormat(smsEndTime).format(date);
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			logger.info("开始时间"+start+"结束时间"+end);
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
			return paramsFailure("SendPhoneNoticeController.sendPhoneNotice.flag","发送标识不能为空");
		}else if(flag==9){ //阿里大于短信flag=9
			String phoneAndWaybillNum="";
			int i=0;
			Integer contentLen=content.length();
			Integer contentLenNew=content.replaceAll("#编号#", "").length();
			String bb = "0";
			if(contentLen != contentLenNew){//内容不相等, 插入编号了
				bb = "1";
			}
			logger.info("阿里大于contentLen: "+contentLen+",contentLenNew: "+contentLenNew+",bb:"+bb);
			for(int j=0;j<arrs.length;j++){
				arrs[j]=arrs[j]+"-tag";//加上这个是为了手机端如果那个字段传空的,在截取的时候还是能拿到空值,不至于报空指针或者乱七八糟的错误啥的
				logger.info("************arrs[j]: "+arrs[j]);
				String waybill=arrs[j].split("-")[0];//运单号
				String num="0"==bb?"":arrs[j].split("-")[1];
				logger.info("传过来的字符串: "+arrs[j]);
				//运单号-编号-netId-网络名称-包裹id
				String parcelId="";
				if(arrs[j].split("-").length>4){
					parcelId = arrs[j].split("-")[4];
				}
				/*********************根据parcelId 查询短信记录是否存在,存在拿到firstMsgId*********************/
				String resultP = newSendPhoneNoticemsService.queryFirstMsgIdByParcelId(arrs[j].split("-")[4],flag);
				logger.info("返回数据为resultP: "+resultP);
				String firstMsgId = "";
				if(!PubMethod.isEmpty(resultP)){
					JSONObject parseObject = JSONObject.parseObject(resultP);
					firstMsgId = parseObject.getString("firstMsgId");
				}
				if(i==0){ //运单号-编号-netId-网络名称-包裹id
					phoneAndWaybillNum+=waybill+"-"+num+"-"+arrs[j].split("-")[2]+"-"+arrs[j].split("-")[3]+"-"+resultMap.get(waybill)+"-"+parcelId+"-"+firstMsgId+"-tag"; //运单号-编号-netId-网络名称-是否扣费
					i++;
				}else{
					phoneAndWaybillNum+="|"+waybill+"-"+num+"-"+arrs[j].split("-")[2]+"-"+arrs[j].split("-")[3]+"-"+resultMap.get(waybill)+"-"+parcelId+"-"+firstMsgId+"-tag";
				}
			}
			
			String aliResult = newAlisendNoticeService.newSendAliSms(accountId,content, phoneAndWaybillNum, memberId, false,true,null,null,(short)1, 
					pickupTime,pickupAddr,compName,flag, noticePhone, md5Hex, versionCode, timeStamp, system);
			result = aliResult;
			logger.info("阿里大于发送短信返回结果....aliResult: "+aliResult);
		}
		return result;
	}
	public static void main(String[] args) {
		String phoneAndNum="1111111111-333333|2222222-33333";
		//掉接口(杨凯)
		String[] arr=phoneAndNum.split("\\|");
		StringBuffer sb=new StringBuffer();
		String sbresult="";
		for(String rece:arr){
			String[] phone=rece.split("-");
			sb.append(phone[0]+"-");
		}
		sbresult=sb.substring(0, sb.length()-1);
		System.out.println(sbresult);
	}
	
	//验证余额是否足够
	/**
	 * @apiparam {String} aliPhoneAndNum 运单号和编号和netId, 运单号-编号-netId|运单号-编号-netId (阿里的)
	 * @apiparam {String} phoneAndNum 手机号和编号 ，电话号-编号-是否关注微信|电话号-编号-是否关注微信 这样  0.关注 1.未关注 (普通的)
	 */
	public Map<String,Object> isSmsBalance(Long memberId,String accountId, String content, Short flag, String noticePhone, String md5Hex, 
			String aliPhoneAndNum, String phoneAndNum, String timeStamp){
		logger.info("进来验证余额....");
		
		String[] aliArr=new String[]{};
		String[] ordiArr=new String[]{};
		if(!PubMethod.isEmpty(aliPhoneAndNum)){
			aliArr = aliPhoneAndNum.split("\\|");
		}
		if(!PubMethod.isEmpty(phoneAndNum)){
			ordiArr = phoneAndNum.split("\\|");
		}
		//循环遍历最大的编号
		
		Integer contentLen=content.length();
		Integer contentLenNew=content.replaceAll("#编号#", "").length();
		Integer len=content.length();
		StringBuffer sb=new StringBuffer();//保存手机号
		String sbresult="";
		if(contentLen!=contentLenNew){//判断是否要插入编号
			Integer numLen=0;
			if(!PubMethod.isEmpty(aliPhoneAndNum)){
				for(String str:aliArr){//遍历出来长度最大的编号
					if(((str.split("-")[1]).length())>numLen){
						//aliLen=(str.split("-")[1]).length();
						numLen=(str.split("-")[1]).length();
					}
					sb.append(str.split("-")[0]+"-");//保存运单号
				}
			}
			if(!PubMethod.isEmpty(phoneAndNum)){
				for(String str:ordiArr){//遍历出来长度最大的编号
					if(((str.split("-")[1]).length())>numLen){
						//ordiLen=(str.split("-")[1]).length();
						numLen=(str.split("-")[1]).length();
					}
					sb.append(str.split("-")[0]+"-");//保存手机号
				}
			}
			//numLen = aliLen >= ordiLen ?aliLen:ordiLen;//最大的编号的长度
			//前台传过来的有"#编号#"，我们要替换为"取件码"，所以计算长度的时候需要减去一位
			len=numLen+content.length()-1;//得到真实发送的内容
		}
		//录入和扫码共有的数量
		int lenAll = aliArr.length+ordiArr.length;
		Map<String,Object> receiverPhoneMap = new HashMap<String,Object>();
		JSONObject jsons;
		Long chargeNum = 0l; //需要收费的总数
		try {
			String sendNum = telephoneRewardService.getSMSOrPhoneNum(memberId,accountId);
			logger.info(memberId+"：ali查询出查询可用电话、短信和免费短信条数："+sendNum);
			jsons = JSONObject.parseObject(sendNum);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(memberId+"：ali查询发送人的电话号码失败");
			Map<String,Object> errorMap = new HashMap<String,Object>();
			errorMap.put("success",false);
			errorMap.put("cause", "发送短信失败");
			errorMap.put("flag", "");
			return errorMap;
		}
		if(jsons.getBooleanValue("success")){							//调用查询通知可用条数接口成功
			logger.info("阿里大于群呼通知jsons: "+jsons);
			String data=jsons.getString("data");							
			jsons=JSONObject.parseObject(data);						
			Integer one=jsons.getInteger("one");	//一条短信的最大长度
			Integer two=jsons.getInteger("two");	//两条短信的最大长度
			Integer three=jsons.getInteger("three"); //三条短信的最大长度
			
			logger.info(memberId+"的短信字数为:"+len+"个");
			Integer feeCount=lenAll;//扫码和录入的个数总和
			if(flag==10){								//用户选择电话优先或者仅电话
				logger.info("阿里大于群呼通知feeCount: "+feeCount);
				String sendContent=content;
				int num = sendContent.indexOf(",");
				sendContent = sendContent.substring(num+1, sendContent.length()); 
				len=sendContent.length();
				if(len>three){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag", "");
					return errorMap;
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
						return errorMap;
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
						errorMap.put("cause", "当前余额不足，群呼失败 !");
						errorMap.put("flag", "1");
						taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
						taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
						return errorMap;
					}else{
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
							return errorMap;
						}
						Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
						if(chargeNum>lenSmss){
							Map<String,Object> errorMap = new HashMap<String,Object>();
							errorMap.put("success",false);
							errorMap.put("cause", "当前余额不足，群呼失败 !");
							errorMap.put("flag", "1");
							taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
							return errorMap;
						}
					}
				}
				
			}else if(flag==9){
				logger.info("阿里大于短信通知len: "+len+" ,three: "+three+" ,feeCount: "+feeCount);
				if(len>three){
					Map<String,Object> errorMap = new HashMap<String,Object>();
					errorMap.put("success",false);
					errorMap.put("cause", "短信内容不能超过"+three+"个字");
					errorMap.put("flag", "");
					return errorMap;
				}else{
					sbresult=sb.substring(0, sb.length()-1);
					String result=smsSendService.isExist(memberId,sbresult);
					JSONObject json=JSONObject.parseObject(result);//TODO
					if(json.getBoolean("success")){
						chargeNum=json.getLong("data");//实际扣款的数量
						String str=json.getString("str");//手机号-是否扣费
						String[] receiverPhones=str.split("\\|");
						for(String phoneNum:receiverPhones){
							receiverPhoneMap.put(phoneNum.split("-")[0], phoneNum.split("-")[1]);
						}
					Integer preferentialNum=0;
					if(!"no".equals(jsons.getString("preferentialNum"))){		//查出免费条数
						preferentialNum=Integer.valueOf(jsons.getString("preferentialNum"));
					}	
					logger.info("阿里大于免费条数preferentialNum: "+preferentialNum+" ,jsons: "+jsons);
					if(!"no".equals(jsons.getString("lenSms"))){
						logger.info("111111111111111111111111111111111");
						Integer lenSms=Integer.valueOf(jsons.getString("lenSms"));
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
							return errorMap;
						}
						if(feeCount>(lenSms+preferentialNum)){//先只按一条计算扣费, 短信条数是否足够
							Map<String,Object> errorMap = new HashMap<String,Object>();
							errorMap.put("success",false);
							errorMap.put("cause", "当前余额不足, 发送短信失败 !");
							errorMap.put("flag", "1");
							taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
							taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
							return errorMap;
						}else{
							//在详细判断需要扣费的条数与短信剩余的条数进行比较, 是否足够
							Integer lenSmss=Integer.valueOf(jsons.getString("lenSms"));
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
								return errorMap;
							}
							if(chargeNum>lenSmss){ //if(chargeNum>lenSmss){ 修改时间 2016年10月17日 15:15:30
								Map<String,Object> errorMap = new HashMap<String,Object>();
								errorMap.put("success",false);
								errorMap.put("cause", "当前余额不足，发送短信失败 !");
								errorMap.put("flag", "");
								taskMassNoticeService.removeRepeatTag(memberId, timeStamp);
								taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
								return errorMap;
							}
						}
					}
				}
				logger.info(memberId+"的短信条数为:"+feeCount+"条");
				}
			}
			
			}else{
				Map<String,Object> errorMap = new HashMap<String,Object>();
				errorMap.put("success",false);
				errorMap.put("cause", "发送短信失败");
				errorMap.put("flag", "");
				return errorMap;
			}
		receiverPhoneMap.put("cause", "fff");
		return receiverPhoneMap;
	}
	
	
}

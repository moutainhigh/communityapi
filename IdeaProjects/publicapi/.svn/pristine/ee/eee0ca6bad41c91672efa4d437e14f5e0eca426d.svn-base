package net.okdi.apiV5.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV2.service.TelephoneRewardService;
import net.okdi.apiV3.service.WalletNewService;
import net.okdi.apiV4.service.SendPackageService;
import net.okdi.apiV4.service.TaskMassNoticeService;
import net.okdi.apiV5.service.NewAlisendNoticeService;
import net.okdi.apiV5.service.NewExpGatewayService;
import net.okdi.apiV5.service.NewSendPhoneNoticeService;
import net.okdi.core.base.BaseController;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.CommonService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 接单王的核心业务，包括：站点信息，网点判断是否重名，收派员,站长,后勤更新角色,上传照片，查询任务，签收包裹，提货，派件等
 * @author amssy
 * @version V1.0
 */
@Controller
@RequestMapping("/newExpGateway")
public class NewExpGatewayController extends BaseController{

	Logger logger = Logger.getLogger(NewExpGatewayController.class);
	@Autowired
	NewExpGatewayService newExpGatewayService;
	@Autowired
	CommonService commonService;
	@Autowired
	private WalletNewService walletNewService;
	@Autowired
	private TelephoneRewardService telephoneRewardService;
	@Autowired
	private SendPackageService sendPackageService;//注入service
	@Autowired
	TaskMassNoticeService taskMassNoticeService;

	@Autowired
	ConstPool constPool;
	@Autowired
	private NewAlisendNoticeService newAlisendNoticeService;
	@Autowired
	private NewSendPhoneNoticeService newSendPhoneNoticeService;
	
		/**
		 * @api {post} /newExpGateway/newSendSmsNew 普通短信聊天
		 * @apiPermission user
		 * @apiDescription  短信聊天 panke.sun
		 * @apiparam {String} phoneAndWaybillNum 客户的手机号-运单号-包裹编号-网络名称-包裹id, 用"—"拼接 (如果为空, 也许拼上 - 如--------- 这样) (不能为空)
		 * @apiparam {String} smsTemplate 短信模板 (不能为空)
		 * @apiparam {Long} memberId 当前收派员的memberId (不能为空)
		 * @apiparam {String} msgId 短信的firstMsgId 在待派中是待派包裹的sendTaskId字段,在通知记录,或者通知异常(就是短信记录,都是firstMsgId这个字段)
		 * @apiparam {String} memberPhone 快递员手机号(不能为空)
		 * @apiparam {String} accountId 钱包id (不能为空)
		 * @apiparam {String} pickupTime 取件时间
		 * @apiparam {String} pickupAddr 取件地址
		 * @apiparam {String} compName 公司名称
		 * @apiparam {String} parcelId 包裹id (不能为空)
		 * @apiparam {String} netName 网络名称
		 * @apiparam {String} version 版本号(不能为空) 如 5.0.0 2017-2-16 21:39:24
		 * @apiparam {String} system 操作系统  安卓 传 Android, 苹果  传 IOS(不能为空)
		 * @apiGroup 新版-短信
		 * @apiSampleRequest /newExpGateway/newSendSmsNew
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
		@RequestMapping(value = "/newSendSmsNew", method = { RequestMethod.POST,RequestMethod.GET})
		public String sendSmsNew(String memberPhone,String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String msgId, 
				String pickupTime, String pickupAddr, String compName, String version,String parcelId,String netName, String system){
			logger.info("短信聊天请求方法sendSmsNew参数：accountId="+accountId+",smsTemplate="+smsTemplate+",phoneAndWaybillNum="+phoneAndWaybillNum+"," +
					"memberId="+memberId+",msgId="+msgId+",version:"+version+",parcelId"+parcelId+" ,memberPhone: "+memberPhone+" ,system: "+system);
			try{
				if(PubMethod.isEmpty(smsTemplate) || PubMethod.isEmpty(phoneAndWaybillNum) || PubMethod.isEmpty(memberId) ){
					return paramsFailure();
				}
				if(PubMethod.isEmpty(accountId)){
					accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
				}
				String isWx="1";
				if(PubMethod.isEmpty(memberPhone)){
					String memberInfo = this.newExpGatewayService.getMemberInfoById(memberId); 
					Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
					memberPhone = memberMap.get("memberPhone").toString();
				}
				String sendNum = telephoneRewardService.getSMSOrPhoneNum(accountId, memberPhone);//郑炯的查询可用电话、短信和免费短信条数
				JSONObject jsons=JSONObject.parseObject(sendNum);
				String dataAndStr=taskMassNoticeService.queryIsExist(memberId,phoneAndWaybillNum.split("-")[0]);
				JSONObject jsonAndMoile=JSONObject.parseObject(dataAndStr);
				logger.info("dataAndStr:"+dataAndStr);//{"data":1,"str":"15810885211-1","success":true}
				Short isCharge=0;
				Long count=0l;
				if(jsonAndMoile.getBoolean("success")){
					count=jsonAndMoile.getLong("data");
					if(count>0){
						isCharge=1;
					}
				}
				String str=sendPackageService.queryOpenIdState(phoneAndWaybillNum.split("-")[0]);
				logger.info("聊天---根据手机号查询该用户关注微信状态str: "+str+" ,该手机号phone: "+memberPhone);
				JSONObject jsonWx=JSONObject.parseObject(str);
				if(jsonWx.getBooleanValue("success")){
					//isWx="1";
					logger.info("聊天---解析得到的json串为jsonWx: "+jsonWx);
					JSONObject jsonObject = jsonWx.getJSONObject("data");
					if(!PubMethod.isEmpty(jsonObject.getString("isRemove"))){
						isWx = jsonObject.getString("isRemove");//0:关注, 1:手动解绑, 2:取消关注解绑
					}
				}
			if (jsons.getBooleanValue("success")) { // 调用接口成功
				String data = jsons.getString("data");
				jsons = JSONObject.parseObject(data);
				Integer one = jsons.getInteger("one");
				Integer len = smsTemplate.length();
				Integer feeCount = 1;
				if (len > one) {
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("cause", "短信内容不能超过" + one + "个字");
					errorMap.put("flag", "");
					return JSON.toJSONString(errorMap);
				}
				Integer preferentialNum = 0;
				if (!"no".equals(jsons.getString("preferentialNum"))) { // 查出免费条数
					preferentialNum = Integer.valueOf(jsons.getString("preferentialNum"));
				}
				if (!"no".equals(jsons.getString("lenSms"))) {
					Integer lenSms = Integer.valueOf(jsons.getString("lenSms"));
					Integer all=0;
					if(isCharge==0){
						all=lenSms + preferentialNum;
					}else{
						all=lenSms;
					}
					if (feeCount > all) {
//					if (feeCount > lenSms) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("success", false);
						errorMap.put("cause", "余额不足，群发通知失败");
						errorMap.put("flag", "1");
						return JSON.toJSONString(errorMap);
					}
				}

			} else {
				Map<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("success", false);
				errorMap.put("cause", "发送短信失败");
				errorMap.put("flag", "");
				return JSON.toJSONString(errorMap);
			}
			String smsStartTime=constPool.getSmsStartTime();//短信开始时间
			String smsEndTime = constPool.getSmsEndTime();//短信结束时间
			String smsCause = constPool.getSmsCause();//提示语
			logger.info("聊天通知短信的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
			
				Date date=new Date();
				String start=new SimpleDateFormat(smsStartTime).format(date);
				String end=new SimpleDateFormat(smsEndTime).format(date);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date startTime=sim.parse(start);
					Date endTime=sim.parse(end);
					if(date.after(startTime)&&date.before(endTime)){
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", smsCause);
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("success", false);
					return 	JSON.toJSONString(map);
				}
				logger.info("最初的格式phoneAndWaybillNum: "+phoneAndWaybillNum);
				//根据firstMsgId查询包裹的编号 .................................................................................
				String number = newExpGatewayService.newQueryParcelNumber(parcelId);
				String[] strings = (phoneAndWaybillNum+"-tag").split("-");//加上-tag是为了让切的时候直接拿到数据或者空的,不需要那么多判断了
				/*********************根据parcelId 查询短信记录是否存在,存在拿到firstMsgId*********************/
				Short flag=1;
				String resultP = newSendPhoneNoticeService.queryFirstMsgIdByParcelId(parcelId,flag);
				logger.info("根据包裹id查询返回数据resultP: "+resultP);
				JSONObject parseObject = JSONObject.parseObject(resultP);
				String firstMsgId = "";
				String name="";
				if(!PubMethod.isEmpty(parseObject.getString("firstMsgId"))){
					name = parseObject.getString("name");
					firstMsgId = parseObject.getString("firstMsgId");
				}else{
					//这是做一个兼容,就是如果没有传包裹id,那就一直生成新的firstMsgId,但在同一个通知记录中的详情中一直聊天就看不到,所以需要用短信上面的firstMsgId
					firstMsgId = msgId;
				}
				//手机端传过来的     客户的手机号-运单号-包裹编号-网络名称-包裹id
				//13681296635--1--
				//自己拼的   phone+"-"+isWx+"-"+num+"-"+receiverPhoneMap.get(phone)+"-"+wayBill+"-"+netName+"-"+name+"-"+parcelId+"-"+firstMsgId;
				phoneAndWaybillNum = strings[0]+"-"+isWx+"-"+number+"-"+isCharge+"-"+strings[1]+"-"+strings[3]+"-"+name+"-"+parcelId+"-"+firstMsgId+"-tag";//客户的手机号-运单号-包裹编号-网络名称-包裹id-编号-是否关注微信-是否扣费-姓名-firstMsgId
				logger.info("新版短信聊天拼装之后的数据phoneAndWaybillNum: "+phoneAndWaybillNum+" ,firstMsgId: "+firstMsgId+" ,name: "+name);
				return newExpGatewayService.newSendSms(accountId,smsTemplate, phoneAndWaybillNum, memberId,false,true,null,
						firstMsgId,(short)0, pickupTime, pickupAddr, compName, (short)4, memberPhone, "", version,system, "");//
			}catch(Exception e){
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}
		}
		/**
		 * @api {post} /newExpGateway/newAliSendSmsNew 阿里短信聊天
		 * @apiPermission user
		 * @apiDescription  短信聊天 jiong.zheng
		 * @apiparam {String} phoneAndWaybillNum 客户的运单号，客户的运单号-包裹编号-网络名称-包裹id  这样, 用"—"拼接 (如果为空, 也要拼上 - 如--------- 这样) (不能为空)
		 * @apiparam {String} smsTemplate 短信模板 (不能为空)
		 * @apiparam {Long} memberId 当前收派员的memberId (不能为空)
		 * @apiparam {String} msgId 短信的firstMsgId 在待派中是待派包裹的sendTaskId字段,在通知记录,或者通知异常(就是短信记录,都是firstMsgId这个字段)
		 * @apiparam {String} memberPhone 快递员手机号 (不能为空)
		 * @apiparam {String} accountId 钱包id (不能为空)
		 * @apiparam {String} pickupTime 取件时间
		 * @apiparam {String} pickupAddr 取件地址
		 * @apiparam {String} compName 公司名称
		 * @apiparam {String} netId 网络id(不能为空)
		 * @apiparam {String} netName 网络名称
		 * @apiparam {String} parcelId 包裹id(不能为空)
		 * @apiparam {String} versionCode 版本号 如5.0.0 (不能为空)
		 * @apiparam {String} system 操作系统  安卓 传 Android, 苹果  传 IOS(不能为空)
		 * @apiGroup 新版-短信
		 * @apiSampleRequest /expGateway/aliSendSmsNew
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
		@RequestMapping(value = "/newAliSendSmsNew", method = { RequestMethod.POST,RequestMethod.GET})
		public String aliSendSmsNew(String memberPhone, String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,
				String msgId, String pickupTime, String pickupAddr, String compName, String netId, String netName,
				String parcelId, String versionCode, String system){
			logger.info("短信聊天请求方法aliSendSmsNew参数：accountId="+accountId+",smsTemplate="+smsTemplate+",phoneAndWaybillNum="+phoneAndWaybillNum+"," +
					"memberId="+memberId+",msgId="+msgId+" ,netId: "+netId +" ,netName: "+netName);
			try{
				if(PubMethod.isEmpty(smsTemplate) || PubMethod.isEmpty(phoneAndWaybillNum) || PubMethod.isEmpty(memberId)){
					return paramsFailure();
				}
				if(PubMethod.isEmpty(accountId)){
					accountId = this.walletNewService.getAccountId(memberId);//根据memberId查询accountId
				}
				if(PubMethod.isEmpty(memberPhone)){
					String memberInfo = this.newExpGatewayService.getMemberInfoById(memberId); 
					Map<String,Object> memberMap =  JSON.parseObject(String.valueOf(JSON.parseObject(memberInfo).get("data")));
					memberPhone = memberMap.get("memberPhone").toString();
				}
				String sendNum = telephoneRewardService.getSMSOrPhoneNum(accountId, memberPhone);//郑炯的查询可用电话、短信和免费短信条数
				JSONObject jsons=JSONObject.parseObject(sendNum);
				String dataAndStr=taskMassNoticeService.queryIsExist(memberId,phoneAndWaybillNum.split("-")[0]);
				JSONObject jsonAndMoile=JSONObject.parseObject(dataAndStr);
				logger.info("阿里聊天dataAndStr:"+dataAndStr);//{"data":1,"str":"15810885211-1","success":true}
				Long chargeNum=0l;
				if(!PubMethod.isEmpty(jsonAndMoile.getLong("data"))){
					chargeNum = jsonAndMoile.getLong("data");
				}
				Short isCharge=0;
				Long count=0l;
				if(jsonAndMoile.getBoolean("success")){
					count=jsonAndMoile.getLong("data");
					if(count>0){
						isCharge=1;
					}
				}
				if (jsons.getBooleanValue("success")) { // 调用接口成功
					String data = jsons.getString("data");
					jsons = JSONObject.parseObject(data);
					Integer one = jsons.getInteger("one");
					Integer len = smsTemplate.length();
					Integer feeCount = 1;
					if (len > one) {
						Map<String, Object> errorMap = new HashMap<String, Object>();
						errorMap.put("success", false);
						errorMap.put("cause", "短信内容不能超过" + one + "个字");
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
					Integer preferentialNum = 0;
					if (!"no".equals(jsons.getString("preferentialNum"))) { // 查出免费条数
						preferentialNum = Integer.valueOf(jsons
								.getString("preferentialNum"));
					}
					if (!"no".equals(jsons.getString("lenSms"))) {
						Integer lenSms = Integer.valueOf(jsons.getString("lenSms"));
						Integer all=0;
						if(isCharge==0){
							all=lenSms + preferentialNum;
						}else{
							all=lenSms;
						}
						if (feeCount > all) {
//						if (feeCount > lenSms) {
							Map<String, Object> errorMap = new HashMap<String, Object>();
							errorMap.put("success", false);
							errorMap.put("cause", "余额不足，群发通知失败");
							errorMap.put("flag", "1");
							return JSON.toJSONString(errorMap);
						}
					}
					
				} else {
					Map<String, Object> errorMap = new HashMap<String, Object>();
					errorMap.put("success", false);
					errorMap.put("cause", "发送短信失败");
					errorMap.put("flag", "");
					return JSON.toJSONString(errorMap);
				}
				//*******************发送短信的时间限制******************************
				String smsStartTime=constPool.getSmsStartTime();//短信开始时间
				String smsEndTime = constPool.getSmsEndTime();//短信结束时间
				String smsCause = constPool.getSmsCause();//提示语
				logger.info("普通聊天的开始时间smsStartTime: "+smsStartTime+" ,结束时间smsEndTime: "+smsEndTime+" ,提示语smsCause: "+smsCause);
				Date date=new Date();
				String start=new SimpleDateFormat(smsStartTime).format(date);
				String end=new SimpleDateFormat(smsEndTime).format(date);
				SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					Date startTime=sim.parse(start);
					Date endTime=sim.parse(end);
					if(date.after(startTime) && date.before(endTime)){
						Map<String,Object> errorMap = new HashMap<String,Object>();
						errorMap.put("success",false);
						errorMap.put("cause", smsCause);
						errorMap.put("flag", "");
						return JSON.toJSONString(errorMap);
					}
				} catch (ParseException e) {
					e.printStackTrace();
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("success", false);
					return 	JSON.toJSONString(map);
				}
				String resultP = newSendPhoneNoticeService.queryFirstMsgIdByParcelId(parcelId,(short)9);
				JSONObject parseObject = JSONObject.parseObject(resultP);
				String firstMsgId = "";
				String name="";
				if(!PubMethod.isEmpty(parseObject.getString("firstMsgId"))){
					name = parseObject.getString("name");
					firstMsgId = parseObject.getString("firstMsgId");
				}else{
					//这是做一个兼容,就是如果没有传包裹id,那就一直生成新的firstMsgId,但在同一个通知记录中的详情中一直聊天就看不到,所以需要用短信上面的firstMsgId
					firstMsgId = msgId;
				}
				logger.info("最初的格式phoneAndWaybillNum: "+phoneAndWaybillNum);
				
				//根据包裹id 查询包裹编号
				String number = newExpGatewayService.newQueryParcelNumber(parcelId);
				logger.info("包裹id为parcelId: "+parcelId+" ,查询到的包裹编号为number: "+number);
				//运单号，订单号，编号
				String[] arr = (phoneAndWaybillNum+"-tag").split("-");
				//waybill+"-"+num+"-"+arrs[j].split("-")[2]+"-"+arrs[j].split("-")[3]+"-"+resultMap.get(waybill)+"-"+arrs[j].split("-")[4]+"-"+firstMsgId; //运单号-编号-netId-网络名称-是否扣费
				//客户的运单号-包裹编号-网络名称-包裹id
				String phoneAndWaybillNumNew = arr[0]+"-"+arr[1]+"-"+netId+"-"+netName+"-"+isCharge+"-"+parcelId+"-"+firstMsgId+"-tag";//运单号-编号-netId-网络名称-是否扣费
				logger.info("阿里大于短信聊天拼装之后的数据phoneAndWaybillNum: "+phoneAndWaybillNum);
				//phoneAndWaybillNum+"-"+isWx+"-"+isCharge
				/**
				 * String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			String md5Hex, String versionCode, String timeStamp, String system
				 */
				return newAlisendNoticeService.newSendAliSms(accountId,smsTemplate, phoneAndWaybillNumNew, 
						memberId,false,true,null,msgId,(short)0, pickupTime, pickupAddr, compName, (short)4, memberPhone, "",versionCode,"", system);
			}catch(Exception e){
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("success", false);
				return 	JSON.toJSONString(map);
			}
		}
	
		
	}
package net.okdi.mob.service.impl;

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
import net.okdi.core.base.BaseService_jdw;
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
public class ExpGatewayServiceImpl extends BaseService_jdw implements ExpGatewayService {
	
	private static Logger logger = Logger.getLogger(ExpGatewayServiceImpl.class);
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
	/*
	 * 2015 08 20 kai.yang 注释
	 * private SensitivewordFilter blackFilter = new SensitivewordFilter("black");
	 * private SensitivewordFilter whiteFilter = new SensitivewordFilter("white");
	 */
	/**
	 * 注册售派员
	 */
	@Override
	public String saveCompPersInfo(Long memberId ,Long compId,String memberName,short roleId,String memberPhone,String applicationDesc) {
		Map<String,Object> paraMap = SetCompPersmap(memberId,compId,memberName, roleId,memberPhone,applicationDesc);
		return openApiHttpClient.doPassSendStr("memberInfo/memberInfoToComp",paraMap);
	}
	/**
	 * 查询公司详细信息
	 */
	@Override
	public String getCompInfo(Long loginCompId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("loginCompId",loginCompId);
		return openApiHttpClient.doPassSendStr("compInfo/queryCompBasicInfo",paraMeterMap);
	}
	
	
	/**
	 * 获取人员未取任务列表     
	 */
	public String getMemberTasks(String senderName, String startTime, String endTime, String senderPhone,
			String spacetime, Long operatorCompId, Page page){ 
		Map<String, Object>  paraMeterMap  = SetMemberTaskMap(senderName, startTime, endTime, senderPhone, spacetime, operatorCompId, page);
		return openApiHttpClient.doPassSendStr("task/query/untake",paraMeterMap);
	}

	//翟士贺后给接口 /compInfo
	@Override	  
	public String getPromptCompForMobile(Long netId, String compTypeNum,
			Long addressId, Short roleId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("compTypeNum",compTypeNum);
		paraMeterMap.put("addressId",addressId);
		paraMeterMap.put("roleId",roleId);									    
		return openApiHttpClient.doPassSendStr("compInfo/getPromptCompForMobile",paraMeterMap);
	}

	
	@Override
	public String getSameCompNameForMobile(Long netId, String compName) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("compName",compName);
		return openApiHttpClient.doPassSendStr("compInfo/getSameCompNameForMobile",paraMeterMap);
	}

	@Override
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum,
			String compName, String compTelephone, Long addressId,
			String address) {
		Map<String,Object> paraMeterMap = SetsaveCompMap(memberId, netId, compTypeNum, compName, compTelephone, addressId, address);
		Map<String,Object> map =  openApiHttpClient.doPassSendObj("compInfo/saveCompInfo",paraMeterMap);
		return saveCompInfoMap(map);
	}

	@Override
	public String saveOrUpdateCompBasicInfo(Map<String,Object> paraMeterMap) {
//		Map<String,Object> paraMeterMap = saveOrUpdateCompMap(loginMemberId, loginCompId, netId, compTypeNum, useCompId, compName, compTelephone, addressId, address, longitude, latitude, compRegistWay);
		Map<String,Object> map =   openApiHttpClient.doPassSendObj("compInfo/saveOrUpdateCompBasicInfo",paraMeterMap);
		return saveCompInfoMap(map);
	}

	
	@Override
	public String querySameComp(Long loginCompId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("loginCompId",loginCompId);
		return openApiHttpClient.doPassSendStr("compInfo/queryCompInfo",paraMeterMap);
	}

	@Override
	public String updateOnlineMember(Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("roleId",roleId);
		paraMeterMap.put("employeeWorkStatusFlag",employeeWorkStatusFlag);
		paraMeterMap.put("areaColor",areaColor);
		return  openApiHttpClient.doPassSendStr("memberInfo/doEditMemberInfo",paraMeterMap);
	}

	@Override
	public String doParTaskrecord(Long fromCompId, Long fromMemberId,
			Long toCompId, Long toMemberId, Long coopNetId, String appointTime,
			String appointDesc, Long actorMemberId, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long createUserId,
			BigDecimal contactAddrLongitude, BigDecimal contactAddrLatitude) {
			Map<String, Object>  paraMeterMap  =  doParTaskrecordMap(fromCompId, fromMemberId, toCompId, toMemberId, coopNetId, appointTime, appointDesc, actorMemberId, contactName, contactMobile, contactTel, contactAddressId, contactAddress, customerId, createUserId, contactAddrLongitude, contactAddrLatitude);
			return openApiHttpClient.doPassSendStr("task/create/order",paraMeterMap);
	}

	@Override
	public String toMember(String id, Long formCompId, Long fromMemberId,
			Long toMemberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("id",id);
		paraMeterMap.put("formCompId",formCompId);
		paraMeterMap.put("fromMemberId",fromMemberId);
		return openApiHttpClient.doPassSendStr("task/distribute/member",paraMeterMap);		
	}
	@Override
	public String memberToMember(String id, Long fromMemberId, Long toMemberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("id",id);
		paraMeterMap.put("fromMemberId",fromMemberId);
		paraMeterMap.put("toMemberId",toMemberId);
		paraMeterMap.put("currentMemberId",fromMemberId);
		return openApiHttpClient.doPassSendStr("task/distribute/memToMem",paraMeterMap);		
	}

 	@Override
	public String cancelMember(String taskId, Long memberId,
			Byte taskTransmitCause, String disposalDesc,String expFlag) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("taskId",taskId);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("taskTransmitCause",taskTransmitCause);
		paraMeterMap.put("disposalDesc",disposalDesc);
		String doPassSendStr = openApiHttpClient.doPassSendStr("task/cancel/member",paraMeterMap);
		if(!PubMethod.isEmpty(expFlag) && "1".equals(expFlag)){
			 paraMeterMap  = new HashMap<String, Object>();
			 paraMeterMap.put("taskid", taskId);
			 openApiHttpClient.doExpBack("",paraMeterMap);
		}
		return 	doPassSendStr;
 	}

	@Override
	public String queryRefuseTask(String senderName, String startTime,
			String endTime, String senderPhone, Long operatorCompId, Page page) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("senderName",senderName);
		paraMeterMap.put("startTime",startTime);
		paraMeterMap.put("endTime",endTime);
		paraMeterMap.put("senderPhone",senderPhone);
		paraMeterMap.put("operatorCompId",operatorCompId);
		paraMeterMap.put("page",page);
		return openApiHttpClient.doPassSendStr("task/query/refusetask",paraMeterMap);		
	}

	@Override
	public String finishTask(Long id, Long memberId, Long compId,Long netId,Long takeTaskId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("id",id);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("netId",netId);
		paraMeterMap.put("takeTaskId",takeTaskId);
//		try {
//			Map<String,Object> resultMap =  openApiHttpClient.doPassSendObj("parcelInfo/queryByTakeTaskStatus",paraMeterMap);
//			if(!PubMethod.isEmpty(resultMap)){
//				if(!resultMap.get("status").equals("001")){
//					return jsonFailure(String.valueOf(resultMap.get("message")));
//				}
//			}
//		} catch (Exception e) {
//			throw new ServiceException("查询任务状态异常");	
//		}
//		try {
//			openApiHttpClient.doPassSendStr("parcelInfo/deleteParcelByTakeTaskId",paraMeterMap);
//		} catch (Exception e) {
//			throw new ServiceException("删除没有运单包裹异常");	
//		}
		return openApiHttpClient.doPassSendStr("task/finish/task",paraMeterMap);	
	}
	@Override
	public String finishTask(Long taskId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("takeTaskId",taskId);
//		try {
//			Map<String,Object> resultMap =  openApiHttpClient.doPassSendObj("parcelInfo/queryByTakeTaskStatus",paraMeterMap);
//			if(!PubMethod.isEmpty(resultMap)){
//				if(!resultMap.get("status").equals("001")){
//					return jsonFailure(String.valueOf(resultMap.get("message")));
//				}
//			}
//		} catch (Exception e) {
//			throw new ServiceException("查询任务状态异常");	
//		}
//		try {
//			openApiHttpClient.doPassSendStr("parcelInfo/deleteParcelByTakeTaskId",paraMeterMap);
//		} catch (Exception e) {
//			throw new ServiceException("删除没有运单包裹异常");	
//		}
		return openApiHttpClient.doPassSendStr("task/finish/task",paraMeterMap);	
	}
	/**
	 * <h2>登陆之后保存收派员位置信息</h2>
	 * @Method: saveOnLineMember 
	 * @Description: 登陆之后保存收派员位置信息
	 * @param  ID(可以为空)
	 * @param netId 网络id
	 * @param netName 网络名称
	 * @param compId 网点id
	 * @param compName 网点名称
	 * @param memberId 人员id
	 * @param memberName 人员名称
	 * @param memberMobile 人员电话
	 * @param lng 经度
	 * @param lat 纬度
	 * @param memo 描述
	 * @return{"data":{},"success":true}
	 * @author xiangwei.liu
	 * @date 2014-11-3 下午6:56:51
	 * @since jdk1.6
	 */
	@Override
	public String saveOnLineMember(Long id,Long netId,String netName,Long compId,
			 String compName,Long memberId,String memberName,String memberMobile,
			 Double lng,Double lat,String memo) {
		Map<String,Object> paraMeterMap = saveOnLineMemberMap(id, netId, netName, compId, compName, memberId, memberName, memberMobile, lng, lat, memo);
		return openApiHttpClient.doPassSendStrToCallCourier("Courier/saveOnLineMember",paraMeterMap);	
	}

	@Override
	public String updateAddrByMember(Long memberId, Double lng, Double lat) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("lng",lng);
		paraMeterMap.put("lat",lat);
		return openApiHttpClient.doPassSendStr("Courier/updateOnLineMember",paraMeterMap);	
	}

	@Override
	public String deleteOnLineMember(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("Courier/deleteOnLineMember",paraMeterMap);
	}

	@Override
	public String deleteMemberInfo(Long memberId, Long compId,
			String memberName, String memberPhone,Long userId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("memberName",memberName);
		paraMeterMap.put("memberPhone",memberPhone);
		paraMeterMap.put("userId",userId);
		return openApiHttpClient.doPassSendStr("memberInfo/deleteMemberInfo",paraMeterMap);	
	}
	
	@Override
	public String checkTel(String associatedNumber, Long compId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("associatedNumber",associatedNumber);
		paraMeterMap.put("compId",compId);
		return openApiHttpClient.doPassSendStr("memberInfo/checkTel",paraMeterMap);	
	}

	@Override
	public String getMemberInfoById(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("memberInfo/getMemberInfoById",paraMeterMap);	
	}
	
	//站点中存有URL
	//后的站点下人员
	@Override
	public Map<String,Object> getStationMember(Long logMemberId, Long compId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("logMemberId",logMemberId);
		paraMeterMap.put("compId",compId);
		Map<String, Object>  ResultMap  =openApiHttpClient.doPassSendObj("memberInfo/queryMemberInfo",paraMeterMap);
		ResultMap.put("BasicUrl",constPool.getReadPath()+constPool.getHead());
		return ResultMap;	
	}
	
	@Override
	public String queryTaskDetail(Long id) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("id",id);
		Map<String, Object>  ResultMap  =openApiHttpClient.doPassSendObj("task/query/taskdetail",paraMeterMap);
		return queryTaskDetailHandle(ResultMap);	
	}
	
	 /** @param taskStatus任务状态0:全部任务,1:未完成任务,2:已完成任务
	   * @param taskType 0:取件 1:派件 2:自取件 3:全部
	   */
	@Override
	public String getStatusBytasks(Long memberId, Integer status,String taskType) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		Map<String, Object>  reviseResultMap  = new HashMap<String, Object>();
		
		paraMeterMap.put("status",status);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("taskType",taskType);
		if(!"".equals(taskType) && "1".equals(taskType)){
			return reviseResult(querySendTaskList(memberId));
//			return  JSON.toJSONString(querySendTaskList(memberId));
		}
		if(!"".equals(taskType) && "3".equals(taskType)){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			paraMeterMap.put("taskType",0);
			List takeTask = SendMap(openApiHttpClient.doPassSendObj("task/query/tasknopage",paraMeterMap));
			Map<String,Object> map =  querySendTaskList(memberId);
			List senTask = SendMap(map);
			if(null != senTask){
				takeTask.addAll(senTask);
			}
			resultMap.put("resultlist",takeTask);
			reviseResultMap.put("data",resultMap);
			return reviseResult(reviseResultMap);
//			return jsonSuccess(resultMap);
		}
		paraMeterMap.put("taskType","0");
		Map<String,Object> resultmap =  openApiHttpClient.doPassSendObj("task/query/tasknopage",paraMeterMap);	
		return reviseResult(resultmap);
	}

	@Override
	public String getAuditInfo(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
  		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("memberInfo/getAuditInfo",paraMeterMap);	
	}

	@Override
	public String updateContacts(Long taskId, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId) {
			Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
			paraMeterMap.put("taskId",taskId);
			paraMeterMap.put("contactName",contactName);
			paraMeterMap.put("contactMobile",contactMobile);
			paraMeterMap.put("contactTel",contactTel);
			paraMeterMap.put("contactAddressId",contactAddressId);
			paraMeterMap.put("contactAddress",contactAddress);
			paraMeterMap.put("customerId",customerId);
		return openApiHttpClient.doPassSendStr("task/updateContacts",paraMeterMap);
	}

	@Override
	public String doSmsSend(String channelNO,Long extraCommonParam, String  usePhones,
			String  BasicContent,String netNames,String waybillNum,String memberPhone,String lat,String lng,String compId,String version) {
					String ResultConten =  SetSmsContent( usePhones, BasicContent, netNames,
						 waybillNum, memberPhone,lat,lng);
					String doSmsSend = smsHttpClient.doSmsSendMoreByFree(ResultConten,"smsGateway/doSmsSendMore_LXHL",memberPhone);
					Map<String,Object> resultMap =  JSON.parseObject(doSmsSend);
					if(!PubMethod.isEmpty(resultMap)){
						resultMap.put("status","0");
					}
					try {
						Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
						paraMeterMap.put("memberPhone" , memberPhone);
						paraMeterMap.put("receiver_mobile" , usePhones);
						paraMeterMap.put("sms_source" , (short)0);
						paraMeterMap.put("phone_type" , (short)0);
						paraMeterMap.put("sendSuccess" , (short)1);
						paraMeterMap.put("sendVersion" , version);
						openApiHttpClient.doPassSendStr("sms/sendSmsAudit",paraMeterMap);
					} catch (Exception e) {
						e.printStackTrace();
						return JSON.toJSONString(resultMap);
					}
					return JSON.toJSONString(resultMap);
	}
	
	
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 * @param 
	 */
	@Override
	public String sendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, String md5Hex){//
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
			taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
			sensitiveWordService.addWrongNumber(memberId.toString(),memberPhone,smsTemplate);
			return JSON.toJSONString(errorMap);
		}
		String channelNO = "03";//渠道标识 00:管理后台,01:客服系统,02:加盟公司系统,03:收派员系统,04:代收点系统,05:手机客户端,06:好递发货王,09:对外调用,10:好递生活,11:好递商铺
		String compId = String.valueOf(memberMap.get("compId"));//快递员所属站点id
		if(PubMethod.isEmpty(compId)){
			compId = "33620";
		}
		smsTemplate =  SetSmsContentNew(smsTemplate,phoneAndWaybillNum,memberPhone,lng,lat);
		Map<String,Object> reMap = new HashMap<String,Object>();
		logger.info("返回的数据格式smsTemplate:"+smsTemplate);
		if(!PubMethod.isEmpty(smsTemplate)){
			logger.info("返回的数据第一步:::::111111111111");
			JSONArray parseArray = JSONArray.parseArray(smsTemplate);
			logger.info("返回的数据第二步:::::222222222222");
			int size = parseArray.size();
			if(size > 0){
				logger.info("返回的数据第三步:::::333333333333");
				JSONObject jsonObject = parseArray.getJSONObject(0);
				logger.info("返回的数据第四步:::::444444444444");
				if(!PubMethod.isEmpty(jsonObject.getString("isCharge"))){
					System.out.println("555");
					if("2".equals(jsonObject.getString("isCharge"))){
						logger.info("返回的数据第五步:::::555555555555");
						if(!jsonObject.getBoolean("success")){
							reMap.put("success",jsonObject.getString("success"));
							reMap.put("cause", jsonObject.getString("cause"));
							reMap.put("flag", "");
							System.out.println("666");
							logger.info("返回的数据第六步:::::666666666666666: "+reMap);
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
			logger.info("每小时发送返回的json串-短信: "+json);
			if(!json.getBoolean("success")){
				taskMassNoticeService.removeReqOnceKeyByPhone(noticePhone, md5Hex);
				return res;
			}
		logger.info("开始调用发送短信接口,,,....smsHttpClient.doSmsSend_HJZX........................................");
		String doSmsSend = smsHttpClient.doSmsSend_HJZX
				(accountId,channelNO, smsTemplate,memberId,memberPhone, flag,voiceUrl,msgId,isNum, pickupTime,pickupAddr, compName);
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
	@Override
	public String doExpSmsSend(String userPhone , String content , String extraCommonParam) {
		 taskExecutor.execute(new ErpSmsThread(userPhone , content ));
		 return jsonSuccess(null);
	}
	
	 class ErpSmsThread extends Thread {
		 private String userPhone = null;
		 private String content = null;
		 private String extraCommonParam = null;
			public ErpSmsThread(String user_Phone, String content_ ) {
			 this.userPhone = user_Phone;
			 this.content = content_;
			 extraCommonParam = extraCommonParam;
			}
		    @Override
			public void run() {
			 Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
			 paraMeterMap.put("channelNo","00");
			 paraMeterMap.put("channelId","");
			 paraMeterMap.put("usePhone",userPhone);
			 paraMeterMap.put("content",content);
			 paraMeterMap.put("extraCommonParam","");
			 String customerGateway = smsHttpClient.customerGateway(paraMeterMap, "smsGateway/doSmsSend");
		 } 
	 }

	@Override
	public Map<String,Object> getValidationStatus(Long memberId) {
		
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendObj("memberInfo/getValidationStatus",paraMeterMap);
	}

	@Override
	public String ifExitJDW(Long memberId, String contactPhone) {
		Map<String,Object>  paraMeterMap = new HashMap<String, Object>();
		Map<String,Object>  ResultMap = new HashMap<String,Object>();
		paraMeterMap.put("memberId",memberId);
		String [] str = contactPhone.split(",");
			for(int i =0; i < str.length; i++){
				//因为验证的时候contactName,后台有改动所以变量名称没换……
				paraMeterMap.put("contactName",str[i]);
				Map<String,Object> dataMap = openApiHttpClient.doPassSendObj("contact/ifExitJDW",paraMeterMap);
				Map<String,Object> isExistMap = JSONObject.parseObject(dataMap.get("data").toString());
				if(isExistMap.get("isExist").toString().equals("true")){
					ResultMap.put("success",false);
					ResultMap.put("msg","您输入的号码以重复");
					return JSON.toJSONString(ResultMap);
				}
			}
			ResultMap.put("success",false);
			ResultMap.put("msg","您输入不重复");
		return JSON.toJSONString(ResultMap);  
	}
//	@return {"data":2,"success":true} <dd>flag = 2  数据库中不存在这个手机号 flag = 1 本站点存在 flag = 5 非本站点存在</dd>
	@Override
	public String addMemberInfo(Long compId, String associatedNumber,
			String memberName, Short roleId, String areaColor, Long userId,
			Short memberSource) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> paraMeterMap =  addMemberInfoMap(compId, associatedNumber, memberName, roleId, areaColor, userId, memberSource);
		Map<String,Object> checkTelMap =  openApiHttpClient.doPassSendObj("memberInfo/checkTel",paraMeterMap);
		String flag =  checkTelMap(checkTelMap);
		if(!PubMethod.isEmpty(flag) && "2".equals(flag)){
			String	ResultStr = openApiHttpClient.doPassSendStr("memberInfo/addMemberInfo",paraMeterMap);
			return ResultStr;
		}
		if(!PubMethod.isEmpty(flag) && !"2".equals(flag) && !"1".equals(flag) && !"5".equals(flag)){
			return flag;
		}
		if("1".equals(flag)){
			map.put("success",false);
			map.put("data",jsonFailure(new ServiceException("本站点存在该手机号")));
			return  JSON.toJSONString(map);
		}
		if("5".equals(flag)){
			map.put("success",false);
			map.put("data",jsonFailure(new ServiceException("本站点存在该手机号")));
			return  JSON.toJSONString(map);
		}
		   return flag;
	}
	@Override
	public String doEditComp(Long compIdOld, Long compIdNew) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("compIdOld",compIdOld);
		paraMeterMap.put("compIdNew",compIdNew);
		String  ResultStr = openApiHttpClient.doPassSendStr("memberInfo/doEditComp",paraMeterMap);
		return ResultStr;
	}
	@Override
	public String adviceInfo(Long compId,Long memberId,String channelNo,String content,String adviceUser
			,String tel,String memo,String loginId,String compName) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("adviceUser",adviceUser);
		paraMeterMap.put("createTime","");
		paraMeterMap.put("memo",memo);
		paraMeterMap.put("loginId",loginId);
		paraMeterMap.put("compName",compName);
		paraMeterMap.put("compId",compId);
		paraMeterMap.put("channelNo",channelNo);
		paraMeterMap.put("content",content);
		paraMeterMap.put("tel",tel);
		
		ShortLinkHttpClient slh = new ShortLinkHttpClient();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		paraMeterMap.put("createTime",sdf.format(new Date()));
		String url = constPool.getCustomerUrl()+"userFeedbackAction!addUserFeedback.aspx";
		Map<String,String> map = fromObj2Str(paraMeterMap);
		String ResultStr = slh.doPost(url, map);
		System.out.println(ResultStr);
		//String  ResultStr = smsHttpClient.customerGateway(paraMeterMap,"customerGateway/doAdvice");
		return ResultStr;
	}
	private Map<String,String> fromObj2Str(Map<String,Object> m){
		Iterator<String> it = m.keySet().iterator();
		Map<String,String> resultMap = new HashMap<String,String>();
		while(it.hasNext()){
			String next = it.next();
			if(m.get(next)!=null){
				resultMap.put(next, m.get(next).toString());
			}
		}
		return resultMap;
	}
	
	@Override
	public Integer QueryExistByCount(Long memberId) {
		return null;//additionalMemberInfo.QueryExistByCount(memberId);
	}
	@Override
	public String mobileRegistration(Long memberId, String memberName,
			String memberPhone, String idNum, Long compId, Short roleId,
			String applicationDesc, Short flag,String addressName,String memberSourceFlag
			,Long netId,String compName , String compTelephone , Long addressId , String address
			 ,Double longitude , Double latitude,String compTypeNum,short existAdd,String stationPhone) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		//选择站点的时候,是有compId的,如果是要添加一个站点时候,是会初始化一条站长记录的,归属默认通过
		if(flag == 1 && roleId == 1){
			paraMeterMap.put("loginMemberId",memberId);
			paraMeterMap.put("netId",netId);
			paraMeterMap.put("compTypeNum",compTypeNum);
			paraMeterMap.put("useCompId",compId);
			paraMeterMap.put("compName",compName);
			paraMeterMap.put("compTelephone",memberPhone);
			paraMeterMap.put("addressId",addressId);
			paraMeterMap.put("address",address);
			paraMeterMap.put("longitude",longitude);
			paraMeterMap.put("latitude",latitude);
			paraMeterMap.put("compRegistWay",6);
			String saveOrUpdateCompId = saveOrUpdateCompBasicInfo(paraMeterMap);
			compId = Long.parseLong(saveOrUpdateCompId);
		} else {
			if(existAdd == 1 && flag == 1 && roleId != 1){
				 String saveCompId =  saveCompInfo(memberId, netId, compTypeNum, compName, compTelephone, addressId, address);
				 compId = Long.parseLong(saveCompId);
			}
			if(flag == 2){
				registerService.userUpdate(memberId, memberPhone, idNum, "", memberName, addressName, "", "", "", "",null);
			}
			paraMeterMap = new HashMap<String,Object>();
			paraMeterMap.put("memberId",memberId);
			paraMeterMap.put("memberName",memberName);
			paraMeterMap.put("memberPhone",memberPhone);
			paraMeterMap.put("idNum",idNum);
			paraMeterMap.put("compId",compId);
			paraMeterMap.put("roleId",roleId);
			paraMeterMap.put("memberSourceFlag",memberSourceFlag);
			paraMeterMap.put("applicationDesc",applicationDesc);
			paraMeterMap.put("flag",flag);
			paraMeterMap.put("stationPhone",stationPhone);
			String  ResultStr = openApiHttpClient.doPassSendStr("memberInfo/mobileRegistration",paraMeterMap);
		}
		Map<String,Object> map   = new HashMap<String,Object>();
		map.put("compId", compId);
		return jsonSuccess(map);
	}
	
	@Override
	public String findParcelDetailByWaybillNumAndNetId(String wayBillNum,
			Long netId) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("wayBillNum",wayBillNum);
		paraMeterMap.put("netId",netId);
		Map<String,Object> map  = openApiHttpClient.doPassSendObj("parcelInfo/findParcelDetailByWaybillNumAndNetId",paraMeterMap);
		
		return findParcelDetailMap(map);
	}
	@Override
	public String saveParcelInfo(HttpServletResponse response ,short ParceTypeFlag,String parmJSON,Long actualAcount,
			Long memberId) {
		    String ResultStr  =  saveParcelMap(response, ParceTypeFlag, parmJSON, actualAcount,  memberId);
			return ResultStr;
	}
	@Override
	public String deleteParcelInfoByParcelId(Long id, String expWayBillNum,
			Long netId) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("id",id);
		paraMeterMap.put("expWayBillNum",expWayBillNum);
		paraMeterMap.put("netId",netId);
		String doPassSendStr = openApiHttpClient.doPassSendStr("parcelInfo/deleteParcelInfoByParcelId",paraMeterMap);
		return doPassSendStr;
	}
	
	
	@Override
	public String finishTask(Long taskId, String parcelIds,
			Double totalCodAmount, Double totalFreight, Short type,
			Long memberId) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("taskId",taskId);
		paraMeterMap.put("parcelIds",parcelIds);
		paraMeterMap.put("totalCodAmount",totalCodAmount);
		paraMeterMap.put("totalFreight",totalFreight);
		paraMeterMap.put("type",type);
		paraMeterMap.put("memberId",memberId);
		String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/finishTask",paraMeterMap);
		return doPassSendStr;
	}
	@Override
	public Map<String,Object>  querySendTaskList(Long memberId) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendObj("SendTaskController/querySendTaskList",paraMeterMap);
	}
	@Override
	public String ifHasPickUp(String expWayBillNum,Long netId) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("expWayBillNum",expWayBillNum);
		paraMeterMap.put("netId",netId);
		String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/ifHasPickUp",paraMeterMap);
		return doPassSendStr;
	}
	@Override
	public String changSendPerson(String parcelIds, Long memberId,Long oldMemberId,String memberPhone) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("parcelIds",parcelIds);
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("oldMemberId",oldMemberId);
		String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/changSendPerson",paraMeterMap);
       if(!PubMethod.isEmpty(doPassSendStr)){
    	   Map resultMap = JSON.parseObject(doPassSendStr);
    	   if(resultMap.get("success").toString().equals("false") ){
    		   if(!PubMethod.isEmpty(resultMap.get("data")) &&   resultMap.get("data").toString().equals("3")){
    			   return doPassSendStr;
    		   }
    	   }
       }
		try {
			Map<String,Object> map = new HashMap<String,Object>();
//			收派员ID 收派员手机号 包裹数量
			map.put("memberId", memberId);
			map.put("mob", memberPhone);
			map.put("parNum",parcelIds.split(",").length);
			String SendSMG = openApiHttpClient.doPassSendStr("notice/parNoticeToExp",map);
        } catch(Exception e){
			throw new ServiceException("转单发送消息异常");	
		}
		return doPassSendStr;
	}
	
	@Override
	 public String createSendTaskBat(String parcelId,Long memberId,String memberPhone,Long sendTaskId){
				Map<String,Object> parMap = new HashMap<String,Object>();
		        parMap.put("parcelid", parcelId);
		        parMap.put("memberId", memberId);
		        String  resultStr = openApiHttpClient.doPassSendStr("SendTaskController/ifHasPickUpByParcelIdAndMemberId",parMap);
		        if(!PubMethod.isEmpty(resultStr)){
		        	Map map =  JSON.parseObject(resultStr);
		        	if(!PubMethod.isEmpty(map.get("success")) &&  map.get("success").toString().equals("false")){ 
		        		return jsonFailur("3");
		        	}
		        }
		        Map<String,Object> paraMeterMap =  createSendTaskBatch( parcelId, memberId, memberPhone);
		        //提货创建派件任务,一期同一包裹,当a创建派件任务后b不会再创建派件任务
//		        if(PubMethod.isEmpty(sendTaskId)){
//		        	String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/createSendTaskBatch",paraMeterMap);
//		        	return doPassSendStr;
//		        }
		        String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/createSendTaskOrUpdate",paraMeterMap);
		        return doPassSendStr;
		        //改动同一包裹当a创建派件任务后,b可以在创建,b创建成功,a派件任务消失
//		        String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/createSendTaskOrUpdate",paraMeterMap);
	 }
	@Override
	public String createSendTask(String parcelId,Long coopCompId,
			Long coopNetId, Integer parEstimateCount, Double parEstimateWeight,
			Date appointTime, String appointDesc,Long actorMemberId, String actorPhone, String contactName,
			String contactMobile, String contactTel, Long contactAddressId,
			String contactAddress, Long customerId, Long contactCasUserId,
			Long contactCompId, Long createUserId,
			Integer taskFlag, Double contactAddrLongitude,
			Double contactAddrLatitude) {
		String doPassSendStr ="";
		Map<String,Object> paraMeterMap = createSendTaskMap(parcelId, coopCompId, coopNetId, parEstimateCount, parEstimateWeight, appointTime, appointDesc,  actorMemberId, actorPhone, contactName, contactMobile, contactTel, contactAddressId, contactAddress, customerId, contactCasUserId, contactCompId,  createUserId, taskFlag,  contactAddrLongitude, contactAddrLatitude);
		try {
			 doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/createSendTask",paraMeterMap);
		} catch(Exception e){
			throw new ServiceException("创建派件任务异常");	
		}
        return doPassSendStr;
	}
	
	@Override
	public String queryParcelListBySendMemberId(Long memberId,Integer currentPage,Integer pageSize) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		paraMeterMap.put("memberId",memberId);
		paraMeterMap.put("currentPage",currentPage);
		paraMeterMap.put("pageSize",pageSize);
		 String doPassSendStr = openApiHttpClient.doPassSendStr("parcelInfo/queryParcelListBySendMemberId",paraMeterMap);
		return doPassSendStr;
	}
	@Override
	public String settleAccounts(String parcelIds, Double totalCodAmount,
			Double totalFreight, Long memberId, short type) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("parcelIds",parcelIds);
		  paraMeterMap.put("totalCodAmount",totalCodAmount);
		  paraMeterMap.put("totalFreight",totalFreight);
		  paraMeterMap.put("memberId",memberId);
		  paraMeterMap.put("type",type);
		  String doPassSendStr = openApiHttpClient.doPassSendStr("expressPrice/settleAccounts",paraMeterMap);
		  return doPassSendStr;
	}
	@Override
	public String queryTakeTaskList(Long actualTakeMember) {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
 		  paraMeterMap.put("actualTakeMember",actualTakeMember);
		  return openApiHttpClient.doPassSendStr("parcelInfo/queryTakeTaskList",paraMeterMap);
	}
	@Override
	public Map<String,Object> queryTakeByWaybillNum(Long actualTakeMember, Long receiptId) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("actualTakeMember",actualTakeMember);
		  paraMeterMap.put("receiptId",receiptId);
		  return openApiHttpClient.doPassSendObj("parcelInfo/queryTakeByWaybillNum",paraMeterMap);
		
	}
	@Override
	public Map<String,Object> modyfyTaskInfo(Long memberId, Long parceId, Long TaskId,
			Long AddressId, String takePersonName, String takePersonMoble,
			String takePersonAddress) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("memberId",memberId);
		  paraMeterMap.put("parceId",parceId);
		  paraMeterMap.put("TaskId",TaskId);
		  paraMeterMap.put("AddressId",AddressId);
		  paraMeterMap.put("takePersonName",takePersonName);
		  paraMeterMap.put("takePersonMoble",takePersonMoble);
		  paraMeterMap.put("takePersonAddress",takePersonAddress);
		  return openApiHttpClient.doPassSendObj("parcelInfo/modyfyTaskInfo",paraMeterMap);
			 
	}
	@Override
	public String getExpressPrice(Long netId, Long sendAddressId,
			Long receiveAddressId, Double weight) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("netId",netId);
		  paraMeterMap.put("sendAddressId",sendAddressId);
		  paraMeterMap.put("receiveAddressId",receiveAddressId);
		  paraMeterMap.put("weight",weight);
		  return openApiHttpClient.doPassSendStr("expressPrice/getExpressPrice",paraMeterMap);
	}
	@Override
	public String cancelSendTask(Long taskId, Long memberId, Long parcelId , Long Id ,String cancelType,Long compId,String textValue) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("taskId",taskId);
		  paraMeterMap.put("memberId",memberId);
		  paraMeterMap.put("parcelId",parcelId);
		  paraMeterMap.put("Id",Id);
		  paraMeterMap.put("cancelType",cancelType);
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("textValue",textValue);
	  return openApiHttpClient.doPassSendStr("SendTaskController/cancelSendTask",paraMeterMap);
	}
	@Override
	public String getStationHostPhone(Long logMemberId, Long compId) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("logMemberId",logMemberId);
		  return openApiHttpClient.doPassSendStr("memberInfo/getMasterPhone",paraMeterMap);
	}
	@Override
	public String queryCompInfo(Long loginCompId) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("loginCompId",loginCompId);
		  return openApiHttpClient.doPassSendStr("compInfo/queryCompInfo",paraMeterMap);
	}
	@Override
	public String finishTaskPersonal(Long taskId) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("taskId",taskId);
		  return openApiHttpClient.doPassSendStr("task/finish/taskPersonal",paraMeterMap);
	}
	@Override
	public String queryAlreadySignList(Long sendMemberId, Integer currentPage,
			Integer pageSize) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("sendMemberId",sendMemberId);
		  paraMeterMap.put("currentPage",currentPage);
		  paraMeterMap.put("pageSize",pageSize);
		  return openApiHttpClient.doPassSendStr("parcelInfo/queryAlreadySignList",paraMeterMap);
	}
	@Override
	public String cancelParcelBatche(Long memberId,
			String parcelId, Long compId, String compName) {
		 Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("memberId",memberId);
		  paraMeterMap.put("parcelId",parcelId);
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("compName",compName);
		  return openApiHttpClient.doPassSendStr("parcelInfo/cancelParcelBatche",paraMeterMap);
	}
	
	@Override
	public String scannerSendTaskSign(Long id,String expWaybillNum, Long compId,
			Long netId, String addressAddressName, String addressAddressMobile,
			Long addresseeAddressId, String addresseeAddress, Double freight,
			Double codAmount,Long memberId,Long sendTaskId) {
		 Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("id",id);
		  paraMeterMap.put("expWaybillNum",expWaybillNum);
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("netId",netId);
		  paraMeterMap.put("addresseeName",addressAddressName);
		  paraMeterMap.put("addresseeMobile",addressAddressMobile);
		  paraMeterMap.put("addresseeAddressId",addresseeAddressId);
		  paraMeterMap.put("addresseeAddress",addresseeAddress);
		  paraMeterMap.put("freight",freight);
		  if(null !=  freight && freight > 0){
				// freight_payment_status '费用结算状态 1 运费已收 0 运费未收',
			    paraMeterMap.put("freightPaymentStatus", "1");
			    paraMeterMap.put("freightPaymentMethod", "2");
			} else {
				paraMeterMap.put("freightPaymentStatus", "0");
				paraMeterMap.put("freightPaymentMethod", "0");
			}
		  paraMeterMap.put("freight",freight);
		  paraMeterMap.put("codAmount",codAmount);
		  paraMeterMap.put("parcelEndMark","0");
		  paraMeterMap.put("parcelStatus","11");
		  paraMeterMap.put("actualSendMember",memberId);
		  String  ResultStr = openApiHttpClient.doPassSendStr("parcelInfo/saveParcelInfo",paraMeterMap);
		  String parceId = "";
		  if(!PubMethod.isEmpty(ResultStr)){
				Map<String, Object> castFunction = castFunction(ResultStr);
				String existBoolean  = castFunction.get("success").toString();
				if("".equals(existBoolean) || existBoolean.equals("false")){
				     throw new ServiceException("保存包裹信息异常");
				}
			    parceId = castFunction(castFunction.get("data").toString()).get("id").toString();
			} else {
				    throw new ServiceException("保存包裹信息异常");
			}
		  if(!PubMethod.isEmpty(parceId) ){
			  Map<String,Object> paraMeter = new HashMap<String,Object>();
			  paraMeter.put("taskId",sendTaskId);
			  paraMeter.put("parcelIds",parceId);
			  paraMeter.put("totalCodAmount",codAmount);
			  paraMeter.put("totalFreight",freight);
			  paraMeter.put("type",(short)1);
			  paraMeter.put("memberId",memberId);
			  paraMeter.put("expWaybillNum",expWaybillNum);
			  openApiHttpClient.doPassSendStr("SendTaskController/finishTask",paraMeter);
		  } 
		  
		  
		return jsonSuccess(null);
	}
	@Override
	public String scannerSendTaskException(Long parceId,String expWaybillNum, Long compId,
			Long netId, String addressAddressName, String addressAddressMobile,
			Long addresseeAddressId, String addresseeAddress, Double freight,
			Double codAmount, Long memberId, String disposalDesc,Long sendTaskId) {
	try {
		Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("id",parceId);
		  paraMeterMap.put("expWaybillNum",expWaybillNum);
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("netId",netId);
		  paraMeterMap.put("addresseeName",addressAddressName);
		  paraMeterMap.put("addresseeMobile",addressAddressMobile);
		  paraMeterMap.put("addresseeAddressId",addresseeAddressId);
		  paraMeterMap.put("addresseeAddress",addresseeAddress);
		  paraMeterMap.put("freight",freight);
		  paraMeterMap.put("codAmount",codAmount);
		  paraMeterMap.put("parcelEndMark","0");
		  paraMeterMap.put("parcelStatus","10");
		  paraMeterMap.put("actualSendMember",memberId);
		  paraMeterMap.put("disposalDesc",disposalDesc);
		  String  ResultStr = openApiHttpClient.doPassSendStr("parcelInfo/saveParcelInfo",paraMeterMap);
		  if(!PubMethod.isEmpty(ResultStr)){
				Map<String, Object> castFunction = castFunction(ResultStr);
				String existBoolean  = castFunction.get("success").toString();
				if("".equals(existBoolean) || existBoolean.equals("false")){
				     throw new ServiceException("保存包裹信息异常");
				}
			    parceId = Long.parseLong(castFunction(castFunction.get("data").toString()).get("id").toString());
			} else {
				     throw new ServiceException("保存包裹信息异常");
			}
		  if(!PubMethod.isEmpty(sendTaskId)){
			  this.cancelSendTask(sendTaskId, memberId, parceId, null, "3", compId, disposalDesc);
		  }
		  return ResultStr;
	} catch (Exception e) {
		 e.printStackTrace();
		return e.getMessage();
	}
}
	
	@Override
	public String scanSemdTaskCreate(Long parcelId, String expWaybillNum,
			Long compId, Long netId, String addressAddressName,
			String addressAddressMobile, Long addresseeAddressId,
			String addresseeAddress, Double freight, Double codAmount,
			Long memberId,String memberPhone,String sendTaskId) {
		  Map<String,Object> paraMeterMap = new HashMap<String,Object>();
		  paraMeterMap.put("id",parcelId);
		  paraMeterMap.put("expWaybillNum",expWaybillNum);
		  paraMeterMap.put("compId",compId);
		  paraMeterMap.put("netId",netId);
		  paraMeterMap.put("addresseeName",addressAddressName);
		  paraMeterMap.put("addresseeMobile",addressAddressMobile);
		  paraMeterMap.put("addresseeAddressId",addresseeAddressId);
		  paraMeterMap.put("addresseeAddress",addresseeAddress);
		  paraMeterMap.put("freight",freight);
		  if(null !=  freight && freight > 0){
				// freight_payment_status '费用结算状态 1 运费已收 0 运费未收',
			    paraMeterMap.put("freightPaymentStatus", "1");
			    paraMeterMap.put("freightPaymentMethod", "2");
			} else {
				paraMeterMap.put("freightPaymentStatus", "0");
				paraMeterMap.put("freightPaymentMethod", "0");
			}
		  paraMeterMap.put("codAmount",codAmount);
		  paraMeterMap.put("parcelEndMark","0");
		  paraMeterMap.put("parcelStatus","10");
		  paraMeterMap.put("actualSendMember",memberId);
		  String  ResultStr = openApiHttpClient.doPassSendStr("parcelInfo/saveParcelInfo",paraMeterMap);
		  String parceId = "";
		  try {
			  if(!PubMethod.isEmpty(ResultStr)){
					Map<String, Object> castFunction = castFunction(ResultStr);
					String existBoolean  = castFunction.get("success").toString();
					if("".equals(existBoolean) || existBoolean.equals("false")){
					     throw new ServiceException("保存包裹信息异常");
					}
				    parceId = castFunction(castFunction.get("data").toString()).get("id").toString();
				} else {
					    throw new ServiceException("保存包裹信息异常");
				}
		  } catch (Exception e) {
			  throw new ServiceException("保存包裹信息异常"+e.getMessage());
		  }
		  try {
			  Map<String,Object> map  = new HashMap<String,Object>();
			  map.put("parcelId",parceId);
			  map.put("memberId",memberId );
			  map.put("memberPhone",memberPhone);
			  String doPassSendStr = openApiHttpClient.doPassSendStr("SendTaskController/createSendTaskOrUpdate",map);
		} catch (Exception e) {
			throw new ServiceException("创建派件任务异常"+e.getMessage());
		}
		return jsonSuccess(null);
	}
	@Override
	 public String  verifyRelation(String phone){
			Map<String,Object> paraMeterMap = new HashMap<String,Object>();
			  paraMeterMap.put("phone",phone);
			  String  ResultStr = openApiHttpClient.doPassSendStr("memberInfo/verifyRelationForOperate",paraMeterMap);
			  return ResultStr;
	}
	
	/**
	 * @param String smsTemplate 发送内容
	 * @param String phoneAndWaybillNum 发送号码
	 * @param Long memberId 发送人id
	 */
	@Override
	public String sendSmsTwo(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,String voiceUrl,String msgId,Short isNum){
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
	public String queryInviteInfo(String phone, String firstMsgId) {
		String result = null;
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("phone", phone);
			map.put("firstMsgId", firstMsgId);
			result = openApiHttpClient.doPassSendStr("messageInfo/queryInviteInfo", map);
			logger.info("通知记录详情页的手机号是否被邀请过,查询人员信息返回的数据 result: "+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.info("通知记录详情页的手机号是否被邀请过,查询人员信息失败, "+e.getStackTrace());
		}
		return result;
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
						flag = parseObject.getString("data");
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
	@Override
	public String newQuerySmsChat(Long memberId, String firstMsgId, Integer pageNo,Integer pageSize) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("memberId", memberId);
			map.put("pageNo", pageNo);
			map.put("pageSize", pageSize);
			map.put("firstMsgId", firstMsgId);
			String response = Post(constPool.getSmsHttpUrl()+"mongoCallBack/newQuerySmsChat", map);
			if (response == null || "".equals(response))
				return null;
			return response;
		}
}
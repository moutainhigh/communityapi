package net.okdi.apiV3.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import net.okdi.apiV3.service.SmallBellService;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;
import net.okdi.httpClient.SmsHttpClient;

@Service
public class SmallBellServiceImpl extends AbstractHttpClient implements SmallBellService{
	
	public static final Log logger = LogFactory.getLog(SmallBellServiceImpl.class);
	@Autowired
	private ConstPool constPool;
	@Autowired
	SmsHttpClient smsHttpClient;
	@Override
	public String query(Long memberId, Integer pageSize, Integer pageNo) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("pageNo",pageNo+"");
		map.put("pageSize",pageSize+"");
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/queryTaskRemind",map);
//		return smsHttpClient.Post(constPool.getSmsHttpUrl()+"/smallBell/query",map);
	}
	@Override
	public String queryTaskRob(Long memberId, Integer pageSize, Integer pageNo) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("pageNo",pageNo+"");
		map.put("pageSize",pageSize+"");
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/queryTaskRob",map);
	}

    @Override
    public String queryUnReadTaskRob(Long memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        return smsHttpClient.Post(constPool.getOpenApiUrl() + "/taskRemind/queryUnReadTaskRob", params);
    }

    @Override
    public String deleteTaskRob(Long id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/deleteTaskRob",params);
    }

    @Override
	public String resend(Long memberId, String msgIds) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("msgIds",msgIds);
		return smsHttpClient.Post(constPool.getSmsHttpUrl()+"/newSmallBell/newResend",map);
	}

	@Override
	public String delete(Long memberId, String msgId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("msgId",msgId);
		smsHttpClient.Post(constPool.getSmsHttpUrl()+"/mongoSms/removeMsgLoger",map);
		smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/removeMsgLoger",map);
		return "true";
	}
	@Override
	public String taskInfo(String memberId, String taskId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		map.put("taskId",taskId);
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/take/queryInfo",map);
	}
	@Override
	public String createTaskIgomo(Long fromCompId, Long coopNetId,String appointDesc,Long actorMemberId,String contactName,String contactMobile
			,String contactAddress,BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,
			Long memberId,String parcelStr,Byte taskFlag,Integer howFast,Byte parEstimateCount,Long assignNetId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("fromCompId",fromCompId==null?"":fromCompId+"");
		map.put("coopNetId",coopNetId==null?"":coopNetId+"");
		map.put("appointDesc",appointDesc==null?"":appointDesc);
		map.put("actorMemberId",actorMemberId==null?"":actorMemberId+"");
		map.put("contactName",contactName==null?"":contactName);
		map.put("contactMobile",contactMobile==null?"":contactMobile);
		map.put("contactAddress",contactAddress==null?"":contactAddress);
		map.put("contactAddrLongitude",contactAddrLongitude==null?"":contactAddrLongitude+"");
		map.put("contactAddrLatitude",contactAddrLatitude==null?"":contactAddrLatitude+"");
		map.put("actorPhone",actorPhone==null?"":actorPhone);
		map.put("openId",openId==null?"":openId);
		map.put("taskSource",taskSource==null?"":taskSource+"");
		map.put("memberId",memberId==null?"":memberId+"");
		map.put("parcelStr",parcelStr==null?"":parcelStr);
		map.put("taskFlag",taskFlag==null?"":taskFlag+"");
		map.put("howFast",howFast==null?"":howFast+"");
		map.put("parEstimateCount",parEstimateCount==null?"":parEstimateCount+"");
		map.put("assignNetId",assignNetId==null?"":assignNetId+"");
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/task/createTaskIgomo",map);
	}
	@Override
	public Long queryCount(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/smallBell/queryCount",map);
//		String results=smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/unReadCount",map);
		Long smsNum=0l;
		if(!PubMethod.isEmpty(result)){
			smsNum+=this.getNum(result);			
		}
//		if(!PubMethod.isEmpty(results)){
//			smsNum+=this.getNum(results);			
//		}
		return smsNum;
	}
	@Override
	public Long queryRobCount(Long memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId+"");
		String results=smsHttpClient.Post(constPool.getOpenApiUrl()+"/taskRemind/unReadCount",map);
		Long smsNum=0l;
		if(!PubMethod.isEmpty(results)){
			smsNum+=this.getNum(results);			
		}
		return smsNum;
	}
	
	@Override
	public String wechatRob(Long memberId, String netName, String netId, String phone, String mname, Long taskId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId", String.valueOf(memberId));
		map.put("netName", netName);
		map.put("netId", netId);
		map.put("phone", phone);
		map.put("mname", mname);
		map.put("taskId", String.valueOf(taskId));
		return smsHttpClient.Post(constPool.getOpenApiUrl()+"/task/rob", map);
	}
	
	private Long getNum(String result){
		Long num=0l;
		JSONObject json=JSONObject.parseObject(result);
		if(json.getBooleanValue("success")){
			if(!PubMethod.isEmpty(json.get("data"))){
				num=json.getLong("data");
			}
		}
		return num;
	}
	@Override
	public String deleteMessage(String msgId, String memberId) {
		Map<String,String> map=new HashMap<String, String>();
		map.put("memberId",memberId);
		map.put("msgId", msgId);
		logger.info("deleteMessage的参数是memberId："+memberId+",msgId:"+msgId);
		
		String result=smsHttpClient.Post(constPool.getSmsHttpUrl()+"/smallBell/deleteMessage", map);
		logger.info("返回的结果是："+result);
		return result;
		
	}
	
}

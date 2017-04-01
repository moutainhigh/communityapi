package net.okdi.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.okdi.api.dao.SmsInterceptionLogMapper;
import net.okdi.api.dao.SmsInterceptionMapper;
import net.okdi.api.entity.SmsInterception;
import net.okdi.api.entity.SmsInterceptionLog;
import net.okdi.api.service.MemberInfoService;
import net.okdi.api.service.SmsInterceptionLogService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class SmsInterceptionLogServiceImpl implements SmsInterceptionLogService{

	@Autowired
	SmsInterceptionLogMapper smsInterceptionLogMapper;
	
	@Autowired
	SmsInterceptionMapper smsInterceptionMapper;
	
	
	@Autowired
	private MemberInfoService memberInfoService;
	
	@Autowired
	private RedisService redisService;

	/**
     * 	查询短信拦截列表 
     * @author chunyang.tan
     * @param sendPhone	发送人号码
     * @param createTime	创建时间
     * @param sendContent	发送内容
     * @return
     */
	@Override
	public Map<String,Object> querySmsInterceptionLog(String sendPhone,String startTime, String endTime,String sendContent, Integer pageNo, Integer pageSize) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sendPhone", sendPhone);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		param.put("sendContent", sendContent);
		param.put("pageNo", (pageNo-1)*pageSize);
		param.put("pageSize", pageSize);
		List<SmsInterceptionLog> listSmsInterceptionLog = smsInterceptionLogMapper.querySmsInterceptionLog(param);
		int count = smsInterceptionLogMapper.querySmsInterceptionLogCount(param);
//		if(listSmsInterceptionLog!=null && listSmsInterceptionLog.size()!=0)
//		{
//			redisService.put("smsInterceptionLog", "1", listSmsInterceptionLog);
//		}
		Integer totalPage = null;
		if(listSmsInterceptionLog.size()%pageSize==0)
		{
			totalPage = listSmsInterceptionLog.size()/pageSize;
		}else
		{
			totalPage = listSmsInterceptionLog.size()/pageSize+1;
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", count);
		map.put("pageNo", pageNo);
		map.put("pageSize", pageSize);
		map.put("listSmsInterceptionLog", listSmsInterceptionLog);
		map.put("totalPage", totalPage);
		return map;
	}
	
	/**
	 * 	账户查询
	 * @author chunyang.tan
	 * @param memberId	发送人id
	 * @param sendPhone	发送人电话
	 * @return
	 */
	@Override
	public SmsInterception queryAccountInquiry(String memberId) {
		SmsInterception smsInterception = smsInterceptionMapper.queryAccountInquiry(memberId);
		if(smsInterception!=null)
		{
			redisService.put("smsInterception", memberId, smsInterception);
		}
		return smsInterception;
	}
	@Override
	public int insert(SmsInterceptionLog smsIntLog) {
		return this.smsInterceptionLogMapper.insert(smsIntLog);
	}
	/**
	 * @author AiJun.Han
	 * 查询电话号码的短信拦截次数与状态
	 */
	@Override
	public Map queryIfHas(String sendPhone){
		Map<String,Object> map=new HashMap<String,Object>();
		SmsInterceptionLog smsInterceptionLog=this.smsInterceptionLogMapper.queryIfHas(sendPhone);
		
		if(!PubMethod.isEmpty(smsInterceptionLog)){
			SmsInterception smsInterception=this.smsInterceptionMapper.queryAccountInquiry(String.valueOf(smsInterceptionLog.getMemberId()));
			map.put("wrongNumber",smsInterception.getWrongNumber());
			map.put("disableType",smsInterception.getDisableType());
			map.put("memberId",smsInterceptionLog.getMemberId());
		}else{
			map.put("wrongNumber","");
			map.put("disableType","");
			map.put("memberId","");
		}
		return map;
	}
	
	/**
	 * @author AiJun.Han
	 * 添加禁用号码
	 */
	@Override
	public String insertTelPhone(String sendPhone,String disableType){
		SmsInterceptionLog smsInterceptionLog=this.smsInterceptionLogMapper.queryIfHas(sendPhone);
		//先创建拦截日志
		SmsInterceptionLog smsIntLog=new SmsInterceptionLog();
		Long id=IdWorker.getIdWorker().nextId();
		smsIntLog.setId(id);
		smsIntLog.setMemberId(this.memberInfoService.getMemberIdByMemberPhone(sendPhone));
		smsIntLog.setSendPhone(sendPhone);
		smsIntLog.setSendContent("");
		Date today=new Date(); //当前时间
		smsIntLog.setCreateTime(today);
		this.smsInterceptionLogMapper.insert(smsIntLog);
		
		//存在记录更新Interception
		if(!PubMethod.isEmpty(smsInterceptionLog)){
			SmsInterception record=new SmsInterception();
			record.setDisableType(Integer.valueOf(disableType));
			record.setDisableTime(new Date());
			record.setMemberId(smsInterceptionLog.getMemberId());
			this.smsInterceptionMapper.updateErrorType(record);
		}
		//不存在创建Interception
		else{
			SmsInterception sms=new SmsInterception();
			sms.setId(IdWorker.getIdWorker().nextId());
			sms.setSendPhone(sendPhone);
			sms.setMemberId(this.memberInfoService.getMemberIdByMemberPhone(sendPhone));
			sms.setDisableType(Integer.valueOf(disableType));
			sms.setDisableTime(new Date());
			sms.setCreateTime(new Date());
			sms.setWrongNumber(0);
			this.smsInterceptionMapper.insert(sms);
		}
		
		
		return "1";
	}
	
	@Override
	public Map getMemberId(String sendPhone){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("memberId",this.memberInfoService.getMemberIdByMemberPhone(sendPhone));
		return map;
	}
}

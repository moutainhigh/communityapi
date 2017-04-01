package net.okdi.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.okdi.api.dao.SmsInterceptionMapper;
import net.okdi.api.entity.SmsInterception;
import net.okdi.api.service.SmsInterceptionService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

@Service
public class SmsInterceptionServiceImpl implements SmsInterceptionService{

	@Autowired
	RedisService redisService;
	@Autowired
	private SmsInterceptionMapper smsInterceptionMapper;
    /**
     * 用户第一次发送短信，给用户添加一条记录
     */
    public int insert(SmsInterception record){
    	return this.smsInterceptionMapper.insert(record);
    }

    /**
     * 判断用户是否已存在
     */
    public int selectByPrimaryKey(Long id){
    	return this.smsInterceptionMapper.selectByPrimaryKey(id);
    }

    /**
     * 更新累计错误次数，+1
     */
    public int updateByPrimaryKey(Long memberId){
    	return this.smsInterceptionMapper.updateByPrimaryKey(memberId);
    }
    
    /**
     * 更新错误类型
     * @param memberId 发送人id
     * @param disableType 禁用类型 1.正常 2.禁用24小时 3.禁用三天 4.长期禁用
     */
    public int updateErrorType(Long memberId,int disableType){
		if(PubMethod.isEmpty(memberId)){
			throw new ServiceException("openapi.SmsInterceptionServiceImpl.updateErrorType.001","查询用户id异常，memeberId参数非空异常");			
		}
		if(PubMethod.isEmpty(disableType)){
			throw new ServiceException("openapi.SmsInterceptionServiceImpl.updateErrorType.002","更新禁用类型disableType异常，disableType参数非空异常");			
		}
		SmsInterception sms=new SmsInterception();
		sms.setMemberId(memberId);
		sms.setDisableType(disableType);
		int result=0;
		if (disableType == 1) {
			sms.setDisableTime(null);
//			result=this.smsInterceptionMapper.updateErrorType(memberId, disableType,new Date());
			result=this.smsInterceptionMapper.updateErrorType(sms);
			this.redisService.remove("addWrongNumber", memberId.toString());
		} else if (disableType == 2 || disableType == 3 || disableType == 4) {
			Date today=new Date();
			sms.setDisableTime(today);
			result=this.smsInterceptionMapper.updateErrorType(sms);
//			result=this.smsInterceptionMapper.updateErrorType(memberId, disableType,new Date());
			Map map=this.redisService.get("addWrongNumber",memberId.toString(),Map.class);
			if(PubMethod.isEmpty(map)){
				map=new HashMap();
				map.put("errorNum",0);		
			}
			map.put("errorTime", today);
			map.put("disableType", disableType);
			this.redisService.put("addWrongNumber",memberId.toString(),map);
		}
		this.redisService.remove("smsInterception",memberId.toString());
    	return result;
    }
    /**
     * 短信错误次数达到三次，添加禁用时间，累计错误次数+1
     */
    public int updateErrorNumAndTime(Long memberId,Date disableTime){
		return this.smsInterceptionMapper.updateErrorNumAndTime(memberId,disableTime);
    }
}
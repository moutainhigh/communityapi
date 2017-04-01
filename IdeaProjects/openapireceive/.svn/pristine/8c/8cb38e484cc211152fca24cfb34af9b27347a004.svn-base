package net.okdi.api.service;

import java.util.Map;

import net.okdi.api.entity.SmsInterception;
import net.okdi.api.entity.SmsInterceptionLog;

public interface SmsInterceptionLogService {

	/**
     * 	查询短信拦截列表 
     * @author chunyang.tan
     * @param sendPhone	发送人号码
     * @param createTime	创建时间
     * @param sendContent	发送内容
     * @return
     */
	public Map<String,Object> querySmsInterceptionLog(String sendPhone,String startTime, String endTime,String sendContent, Integer pageNo, Integer pageSize);
	
	/**
	 * 	账户查询
	 * @author chunyang.tan
	 * @param memberId	发送人id
	 * @param sendPhone	发送人电话
	 * @return
	 */
	public SmsInterception queryAccountInquiry(String memberId);
	/**
	 * 插入一条错误短信
	 * @author chunyang.tan
	 * @param sendPhone	发送人号码
	 * @param createTime	创建时间
	 * @param sendContent	发送内容
	 * @return
	 */
	public int insert(SmsInterceptionLog smsIntLog);

	Map queryIfHas(String sendPhone);

	

	String insertTelPhone(String sendPhone, String disableType);

	Map getMemberId(String sendPhone);
}

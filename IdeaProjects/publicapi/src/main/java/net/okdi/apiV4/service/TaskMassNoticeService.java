package net.okdi.apiV4.service;

import java.util.Map;

public interface TaskMassNoticeService {

	String queryBalance(String sendNum,String content,String[] arrs,Long memberId,Short flag, Long count);
	
	public String sendTaskPhoneNotice(String accountId,String phoneAndNum,String noticePhone,Long memberId,String content,Short flag,Short isNum, String pickupAddr,String pickupTime, String compName);
	
	 public String sendTaskSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,
			 boolean isValidate,String voiceUrl,String msgId, Short isNum, Short flag, String pickupAddr,String pickupTime, String compName);
	 public String sendAliTaskSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,
			 boolean isValidate,String voiceUrl,String msgId, Short isNum, Short flag,String pickupAddr, String pickupTime, String compName);
	 
	 String queryIsExist(Long memberId,String phoneAndNum);
	 
	 
	 public boolean getReqOnceKeyByPhone(String phone, String timeStamp);

	public String removeReqOnceKeyByPhone(String phone, String timeStamp);

	String getTemplateContent(String content, Long memberId);

	public Map<String,Object> queryAliBalance(String sendNum, String content, String[] arrs,
			Long memberId, Short flag, Long count);

	public String queryOnlyRepeatTag(Long memberId, String timeStamp, String accountId, String phone, String sendContent, String version, String system);

	String removeRepeatTag(Long memberId, String timeStamp);
	 
	 
}

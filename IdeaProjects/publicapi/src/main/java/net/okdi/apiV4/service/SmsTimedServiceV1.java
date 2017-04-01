package net.okdi.apiV4.service;


public interface SmsTimedServiceV1 {
	
	String updateSmsTimedInfo(Long id, String number, String phone, Integer flag);
	
	String deleteSmsTimedInfo(Long id);
	
	String sendSmsTimed(Long id);

	String timeDelaySendSms(String groupName, Long warnTime, Long sendTime,
                            Short flag, String content, Long memberId, String memberPhone,
                            String accountId, String phoneAndNum, String type);

	String aliTimeDelaySendSms(String groupName, Long warnTime, Long sendTime,
                               Short flag, String content, String templateId, Long memberId, String memberPhone,
                               String accountId, String phoneAndNum, String aliParams, String type, String pickupAddr, String version, String system);

	String timeDelaySendSmsList(Long memberId);

	String DelaySendSmsDetial(Long groupId);

	String updateSmsWarnTime(Long groupId, Long warnTime, String type);
	
	String deleteSmsList(String ids);
}

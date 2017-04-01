package net.okdi.apiV5.service;

import java.util.Map;

public interface NewSendPhoneNoticeService {
	/**
	 * @param phoneAndNum  String 客户电话以及编号  例子：13521285623-1|13526354248-2
	 * @param noticePhone  String 通知电话
	 * @param memberId 	   Long   用户id
	 * @param content      String 短信内容
	 * @param flag         Short  是否插入编号 0.插入 1.不插入
	 * @return
	 */
	public String sendPhoneNotice(String accountId,String phoneAndNum,String noticePhone,Long memberId,String content,Short flag,
			String pickupTime,String pickupAddr, String compName, String md5Hex, String version, String system, String timeStamp);

	/** 通过map 包含了用户id和短信内容content拿到该条短信的审核状态
	 * @param content 
	 * @param accountId 
	 * @param map map包含了用户id和短信内容content
	 * @param md5Hex1 
	 * @param noticePhone 
	 * @param flag 
	 * @param compName 
	 * @param pickupAddr 
	 * @param pickupTime 
	 * @param s 
	 * @param object4 
	 * @param object3 
	 * @param c 
	 * @param b 
	 * @param object2 
	 * @param object 
	 * @param memberId 
	 * @return
	 */
	

	public String queryMsgTemplate(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			String voiceUrl,String msgId,Short isNum ,String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, String md5Hex1,String templateId, String timeStamp);

	public String queryFirstMsgIdByParcelId(String parcelId, Short flag);
}

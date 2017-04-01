package net.okdi.apiV5.service;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.okdi.core.common.page.Page;

public interface NewExpGatewayService {
		/**
		 * @param String smsTemplate 发送内容
		 * @param String phoneAndWaybillNum 发送号码
		 * @param Long memberId 发送人id
		 */
	 public String sendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
			 String voiceUrl,String msgId,Short isNum, String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			 String md5Hex, String version, String system, String timeStamp);//
	 
	 /**聊天发短信的接口增加参数 panke.sun
	 * @param accountId
	 * @param smsTemplate
	 * @param phoneAndWaybillNum
	 * @param memberId
	 * @param lng
	 * @param lat
	 * @param isOld
	 * @param isValidate
	 * @param voiceUrl
	 * @param msgId
	 * @param isNum
	 * @param pickupTime
	 * @param pickupAddr
	 * @param compName
	 * @param flag
	 * @param noticePhone
	 * @param md5Hex
	 * @param version
	 * @param name
	 * @param firstMsgId
	 * @param netName
	 * @return
	 */
	public String newSendSms(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,boolean isOld,boolean isValidate,
			 String voiceUrl,String msgId,Short isNum, String pickupTime, String pickupAddr, String compName, Short flag, String noticePhone, 
			 String md5Hex, String version, String system, String timeStamp);//
	 
	 public String findMassnotificationrecord(Long memberId,String queryTime,Integer pageSize,Integer pageNo,Short status,Short type,String receiverPhone,
			 String sendContent, String number, String billNum,String name);

	 
	 
	 /**
		 * 	短信聊天 
		 * @author chunyang.tan add
		 * @param String firstMsgId 关联主表id
		 * @return
		 */
		public String querySmsChat(String firstMsgId,int pageNo,int pageSize);
		public String sendSmsTwo(String accountId,String smsTemplate,String phoneAndWaybillNum,Long memberId,String lng,String lat,boolean isOld,boolean isValidate,
				 String voiceUrl,String msgId,Short isNum, String version);
		public String getMemberInfoById(Long memberId);

		public String queryCountAndCost(String queryDate, String memberId);

		public String queryParcelNumber(String msgId);
		public String newQueryParcelNumber(String parcelId);
}
package net.okdi.api.service;



/**
 * @author feng.wang
 * @version V1.0
*/

public interface SmsService {

	/** 
	 * 呼叫快递哥发送短信
	 * @param mobile  快递哥手机号
	 * @return
	*/
	public String callExp(String mobile);


}

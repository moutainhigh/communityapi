package net.okdi.apiV3.service;

public interface WechatMpService {

	/**
	 * 微信公众号发送模板消息
	 * 
	 * @param openId
	 *            接收人(关注公众号的微信用户)id
	 * @param site
	 *            站点
	 * @param courierName
	 *            快递员
	 * @param phone
	 *            快递员电话
	 */
	String send(String openId, String site, String courierName, String phone, String status, String reason ,String taskId);

}

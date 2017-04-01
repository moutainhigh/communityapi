package net.okdi.apiV2.service;

import java.util.Map;

public interface VlinkCallService {
	/**
	 * 发送手机验证码功能
	 * @param phone 发送手机号
	 * @param type： findpwd找回密码 reg注册 默认reg
	 * @return {"data":{"result":"OverMaxLimited"},"success":true}
	 * 返回json数据{"result":"OverMaxLimited"} result:OverMaxLimited 超过当日限制发送次数 SendFalse发送短信失败 SendTrue发送成功
	 */
	public Map<String, Object> sendSmsCode(String phone, String type,String verifyCode);
}

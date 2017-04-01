package net.okdi.api.service;

import java.util.Map;

import net.okdi.api.entity.MemberInfo;
import net.okdi.core.base.BaseService;
import net.okdi.core.base.BaseServiceImpl;

public interface SensitiveWordService extends BaseService<MemberInfo> {

	/**
	 * 查询黑名单库
	 */
	Map<String, Object> queryBlackList();
	
	/**
	 * 查询白名单库
	 */
	Map<String, Object> queryWhiteList();
	/**
	 * 添加关键字
	 */
	Map<String, Object> insertSensitiveWord(String sensitiveWord,String type);
	/**
	 * 删除关键字
	 */
	Map<String, Object> deleteSensitiveWord(String sensitiveWord,String type);
	/**
	 * 模糊查询关键字
	 */
	Map<String, Object> likeSensitiveWord(String sensitiveWord,String type);
	/**
	 * 记录错误次数 错一次+1  错误三次以后就短时间内不让发短信
	 */
	String addWrongNumber(Long memberId,String phone,String sendContent);
	/**
	 * 查询错误次数，判断是否可以发短信
	 * @param memberId 用户id
	 * @param phone 发送号码
	 * @param sendContent 发送内容
	 */
	String queryWrongNumber(Long memeberId);
	/**
	 * 移除错误次数
	 */
	void  removeWrongNumber(String memeberId);
}

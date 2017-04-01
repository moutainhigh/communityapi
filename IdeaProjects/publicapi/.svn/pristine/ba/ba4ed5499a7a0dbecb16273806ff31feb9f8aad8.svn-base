package net.okdi.mob.service;

import java.util.List;
import java.util.Set;

public interface SensitiveWordService {

	/**
	 * 查询黑名单库
	 */
	Set<String> queryBlackList();
	
	/**
	 * 查询白名单库
	 */
	Set<String> queryWhiteList();

	/**
	 * 记录错误次数 错一次+1
	 * @param memberId 用户id
	 * @param phone 发送号码
	 * @param sendContent 发送内容
	 */
	String addWrongNumber(String memeberId,String phone,String sendContent);

	/**
	 * 查询错误次数，判断是否可以发短信
	 * @param memberId 用户id
	 * @param phone 发送号码
	 * @param sendContent 发送内容
	 */
	String queryWrongNumber(String memeberId);

	/**
	 * 移除错误次数
	 */
	void removeWrongNumber(String memeberId);

	List<String> queryBlackCollen();
}

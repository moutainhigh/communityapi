package net.okdi.apiV4.service;

public interface WeiChatPubNoService {
	/**
	 * 获取二维码的url
	 * @param memberId
	 * @return url
	 */
	String findWeichatkeyBymemberId(String memberId);
}

package net.okdi.apiV4.service;

public interface InterceptService {

	void saveIntercept(String accountId,String channelNo,String msgId,String phoneAndContent,Long memberId,String memberPhone,
			Short flag,String content,Short isNum);

	void saveTaskIntercept(String accountId,String channelNo,String phoneAndContent,Long memberId,String memberPhone,
			Short flag,String content,Short isNum);
}

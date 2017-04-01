package net.okdi.apiV2.service;

import java.util.Map;

import net.okdi.apiV2.entity.TelephoneRelation;

public interface TelephoneRewardService {

	String loginReceiveRewardNum(String phone);

	TelephoneRelation queryRewardNumDetail(String phone);

	String calculateMinusRewardNumber(String phone, String id, String num);

	Map<String, Object> getSMSOrPhoneNum(String accountId, String phone);

	String registerGetReward(String memberId, String accountId);

	
	
	public Map getStrategy(String memberId);

	String queryIsGetReward(String phone);

	String queryIsGetRegister(String memberId);

	String queryPhoneByMemberId(String memberId);

	//String updateLoginAndRegStatus(String phone, String memberId);

	String updateLoginStatus(String phone);

	String updateRegisterStatus(String memberId);


}

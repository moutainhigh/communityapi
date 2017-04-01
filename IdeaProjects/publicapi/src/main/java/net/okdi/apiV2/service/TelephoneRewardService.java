package net.okdi.apiV2.service;

import java.util.Map;

public interface TelephoneRewardService {

	public String loginReceiveRewardNum(String phone);

	public String getSMSOrPhoneNum(String accountId, String phone);

	//public String queryPhoneByMemberId(String memberId);

	public Map<String,String> queryIsGetReward(String phone);

	public Map<String,String> queryIsGetRegister(String memberId);

	//public void updateLoginAndRegStatus(String phone, String memberId);

	public void updateLoginStatus(String phone);

	public void updateRegisterStatus(String memberId);

	public String isReceiveLoginReward(String phone);
	
	public String getSMSOrPhoneNum(Long memberId,String accountId);

	public String queryAuditItemByPhone(String phone);

}

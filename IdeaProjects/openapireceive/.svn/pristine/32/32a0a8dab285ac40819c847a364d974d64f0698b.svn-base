package net.okdi.api.service;

import java.util.List;

import net.okdi.api.entity.MobMemberLogin;
import net.okdi.core.base.BaseService;

public interface MobMemberLoginService extends BaseService<MobMemberLogin> {

	List<MobMemberLogin> findByMobMemberLogin(MobMemberLogin login);
	void updateMobMemberLogin(MobMemberLogin mobMemberLogin);
	void insertMobMemberLogin(MobMemberLogin mobMemberLogin);
	void deleteMobMemberLogin(MobMemberLogin mobMemberLogin);
	
	void saveOrUpdateMobMemberLogin(String channelNo, Long memberId, String deviceType, String deviceToken, String version,
			String address);
	
	
	/**
	 * 查看收派员是否在线
	 */

	String isOnlineMember(String channelNo, Long memberId);
	
	boolean valOnePhoneLogin(String channelNo, Long memberId, String deviceToken);
	
	void pushExt(String pushType,String channelNo,String memberId, String type, String title, String content,
			String extraParam, String msgType);
	
	void pushExtForSendTS(String pushType,String channelNo,String memberId, String type, String title, String content,
			String extraParam, String msgType,String deviceType,String deviceToken);
    
	void initBadge(String deviceToken);
	String isDeliveryOnlineMember(String channelNo, Long memberId);
	
	public void customerReply(Long memberId,String mob);

	public void taskPush(Long memberId,String mob);
	
	void robPush(Long memberId, String mob, String extMsg);
}

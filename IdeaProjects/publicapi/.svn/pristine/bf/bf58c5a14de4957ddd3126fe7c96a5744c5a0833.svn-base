package net.okdi.apiV1.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface ExpressUserService {

	String updateMemberInfoAndAudit(String memberName, String idNum,String memberId, String compId, String roleType,String gender, String birthday,String address);

	String isIdNumRepeat(String idNum, String memberPhone);

	String guishuAudit(String memberId, String status);

	String queryNetInfo();

	String upLoadPhotoInfo(String type, String memberId,MultipartFile[] uploadImage);

	String updateMemberInfoAndAuditJump(String memberId, String compId, String roleType,String memberName);

	String saveExpressAudit(String memberId, String expressNumStr, String compId, String roleType);

	String queryExpressAuditStatusByMemberId(String memberId);

	String saveExpressAuditJump(String memberId, String compId, String roleType);

	String queryRealNameAuditInfo(String memberId);
	
	/**
	 * 完善个人信息
	 * @param memberId
	 * @param nickName
	 * @param gender
	 * @param birthday
	 * @return
	 */
	String completeMemberInfo(String memberId, String nickName, String gender, String birthday);
	
    /**
     * 微信登录
     * @param memberId
     * @param weNumber
     * @return
     */
    String isBoundWechat(String weNumber);
 
    /**
	 * 解绑微信
	 * @param memberId
	 * @param weNumber
	 * @return
	 */	
    String unlockWechat(String memberId);
    /**
     * 是否绑定微信
     * @param memberId
     * @return
     */
	String isBoundWx(String memberId);
	/**
	 * 绑定微信
	 * @param memberId
	 * @param weNumber
	 * @param weName
	 * @return
	 */
	String insertWeChat(String memberId, String weNumber, String weName);
	/**
	 * 已注册绑定微信
	 * @param numberPhone
	 * @param weNumber
	 * @param weName
	 * @return
	 */
	String insertPhoneWeChat(String memberPhone, String weNumber, String weName);

  
	
}

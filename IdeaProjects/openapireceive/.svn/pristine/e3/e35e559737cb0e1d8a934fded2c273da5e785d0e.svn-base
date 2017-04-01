package net.okdi.apiV1.service;

import java.util.HashMap;
import java.util.List;

import net.okdi.core.common.page.Page;

public interface ExpressUserService {

	void updateMemberInfoAndAudit(String memberName, String idNum,String memberId, String compId, String roleType);

	Page queryUserAuditList(Integer auditStatus, String memberName, String memberPhone, String idNum, String roleId, String netId, String startTime,String endTime,Integer currentPage,Integer pageSize, String realNameStatus, String expressStatus, String provinceId,String sureStatusString ,String auditItem);

	HashMap<String, Object> queryUserDetail(String memberId, String compId);

	HashMap<String, Object> isIdNumRepeat(String idNum, String memberPhone);

	void guishuAudit(String memberId,String status);

	HashMap<String, Object> checkMemberInfo(String memberId, String compId, String status,String remark, String reasonNum, String auditType,String mob);

	HashMap<String, Object> forceCheckMemberInfo(String memberId, String compId,String roleType,String status,String remark, String auditType,String mob);

	
	List<HashMap<String, String>> queryNetInfo();

	void updateMemberInfoAndAuditJump(String memberId, String compId, String roleType,String memberName);

	List<HashMap<String, String>> queryProvinceInfo();

	void saveExpressAudit(String memberId, String expressNumStr, String compId, String roleType);

	HashMap<String, Object> queryExpressAuditStatusByMemberId(String memberId);

	void saveExpressAuditJump(String memberId, String compId, String roleType);

	HashMap<String, Object> queryRealNameAuditInfo(String memberId);

	HashMap<String, Object> queryAddressAuditInfo(String memberId);

	void sendMsgToInviter(String memberId, String memberPhone);

	void sendMsgToInvitee(String memberId, String memberPhone);

	HashMap<String, Object> queryMemberInfoByMemberPhones(String memberPhones);

	public List queryRepeatOrder(String orderArr,String netId,String phone);

	void waitSureOper(String memberId, String compId,String roleType);

	void sureOper(String memberId, String compId, String roleType);
	

}

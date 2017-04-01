package net.okdi.apiV1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;

import org.apache.ibatis.annotations.Param;


public interface BasEmployeeAuditMapperV1 {

	void insertAuditInfo(BasEmployeeAudit auditShenfen);

	void passCheckSM(HashMap<String, Object> map);

	void refuseCheckSM(HashMap<String, Object> map);

	List<BasEmployeeAudit> queryShenFenAuditInfo(HashMap<String, String> param);

	void guishuAudit(HashMap<String, Object> param);

	void updateAuditShenFen(HashMap<String, String> param);

	BasEmployeeAudit queryRealNameStatus(@Param("memberId")String memberId);

	BasEmployeeAudit queryExpressStatus(@Param("memberId")String memberId);

	void passCheckKD(HashMap<String, Object> param);
	
	void insertBasEmployeeAudit(HashMap<String, Object> param);

	void refuseCheckKD(HashMap<String, Object> param);

	void updateAuditShenFen2(HashMap<String, String> param);

	void saveExpressAuditJump(HashMap<String, Object> param);

	void updateExpressAudit(HashMap<String, Object> param);

	void saveExpressAudit(HashMap<String, Object> param);

	HashMap<String, Object> queryRealNameAuditInfo(@Param("memberId")String memberId);

	void updateExpressAudit2(HashMap<String, Object> param);

	Map<String, Object> queryRoleInfoAndRaltion(@Param("memberId")Long memberId);

	HashMap<String, Object> queryAddressAuditInfo(String memberId);

	String querAuditFlag(@Param("memberId")Long memberId, @Param("auditItem")Long auditItem);

	BasEmployeeAudit queryBasEmployeeAudit(@Param("memberId")Long memberId,@Param("auditItem")Long auditItem);

	int inserRelation(BasEmployeeAudit basEmployeeAudit);
	
	int getRelation(BasEmployeeAudit basEmployeeAudit);

	void deleteWrongData(@Param("auditId")Long auditId);

	List<BasEmployeeAudit> queryWrongDataByMobile(@Param("mobile")String mobile);

	void deleExpressAudit(HashMap<String, Object> param);

	BasEmployeeAudit querySureExpress(String memberId);
	
	List<Map<String, Object>> queryRepeatOrderByOrderAndNetId(@Param("order")String order,@Param("netId")String netId,@Param("phone")String phone );

	void waitSureOper(HashMap<String, Object> paramMap);

	void updateSureStatus(HashMap<String, Object> paramMap);

	void saveNewSureOper(HashMap<String, Object> paramMap);
	
}
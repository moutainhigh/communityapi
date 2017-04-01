package net.okdi.apiV2.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasEmployeeAudit;


public interface BasEmployeeAuditMapperV2 {
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.queryRealNameStatus 
	 * @Description: TODO(获取快递员的身份和快递认证状态) 
	 * @param @param memberId
	 * @param @return   
	 * @return BasEmployeeAudit  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	BasEmployeeAudit queryRealNameStatus(String memberId);
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.queryExpressStatus 
	 * @Description: TODO(查询快递认证状态信息) 
	 * @param @param memberId
	 * @param @return   
	 * @return BasEmployeeAudit  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	BasEmployeeAudit queryExpressStatus(String memberId);
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.insertAuditInfo 
	 * @Description: TODO(保存身份认证信息) 
	 * @param @param auditShenfen   
	 * @return void  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	void insertAuditInfo(BasEmployeeAudit auditShenfen);


	
	/**aijun.Han add**/
	BasEmployeeAudit queryAuditComp(String memberId);
	/**aijun.Han end**/
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.queryCountInCompInfo 
	 * @Description: TODO(查询此站点下的收派员和后勤人员数量，不包括站长角色) 
	 * @param @param compId
	 * @param @return   
	 * @return int  返回值类型
	 * @throws 
	 * @date 2015-12-5
	 * @auth zhaohu
	 */
	int queryCountInCompInfo(String compId);
	
	int ifCourie(String memberId);
	
	int ifOwnershipAudit(String memberId);
	
	Integer ifIsAssistantOrManager(String memberId);
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.queryCountInShop 
	 * @Description: TODO(查询此店长下的店员数量，不包括店长角色) 
	 * @param @param compId
	 * @param @return   
	 * @return int  返回值类型
	 * @throws 
	 * @date 2015-12-30
	 * @auth zhaohu
	 */
	int queryCountInShop(String compId);
	/**
	 * @MethodName: net.okdi.apiV2.dao.BasEmployeeAuditMapperV2.java.queryLeaderCountInComp 
	 * @Description: TODO(查询站点下的站长数量) 
	 * @param @param compId
	 * @param @return   
	 * @return int  返回值类型
	 * @throws 
	 * @date 2016-1-3
	 * @auth zhaohu
	 */
	int queryLeaderCountInComp(String compId);
	HashMap<String, Object> querySiteInfoByMemberId(String memberId);
	//查询审核所属公司
	Map<String, Object> queryCompIdByMemberId(String memberId);
	BasEmployeeAudit queryGsAuditInfo(String memberId);
	void updateGSAuditInfo(String memberId, String compId, String roleId,Date date,String status);
	void updateEmployeeRelation(String memberId,String compId,String roleId);
	void deleteKdAuditInfo(String memberId);
	
	
}
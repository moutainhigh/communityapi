package net.okdi.apiV2.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;

public interface BasCompInfoMapperV2{

	List<BasCompInfo> queryCompInfoByNetIdAndAddressId(HashMap<String, Object> paramMap);
	String queryNetName(String compId);
	String queryCompName(String compId);
	
	Long queryRelationCompIdOfBasCompinfo(Long compId);
	
	void  addGSAudit(@Param("id")Long id, @Param("memberId")Long memberId, @Param("compId")Long compId,  @Param("applicationRoleType")int applicationRoleType,
			@Param("applicationTime")Date applicationTime, @Param("auditItem")int auditItem, @Param("auditOpinion")int auditOpinion,@Param("showCompName")String showCompName);
	
	void  addSFAudit(@Param("id")Long id, @Param("memberId")Long memberId, @Param("compId")Long compId,  @Param("applicationRoleType")int applicationRoleType,
			@Param("applicationTime")Date applicationTime, @Param("auditItem")int auditItem, @Param("auditOpinion")int auditOpinion,@Param("showCompName")String showCompName);
	
	void  addKDAudit(@Param("id")Long id, @Param("memberId")Long memberId, @Param("compId")Long compId,  @Param("applicationRoleType")int applicationRoleType,
			@Param("applicationTime")Date applicationTime, @Param("auditItem")int auditItem, @Param("auditOpinion")int auditOpinion,@Param("showCompName")String showCompName);
	
	void updateSFAuditInfo(@Param("memberId")Long memberId, @Param("compId")Long compId,@Param("applicationRoleType")int applicationRoleType);
	void updateKDAuditInfo(@Param("memberId")Long memberId, @Param("compId")Long compId,@Param("applicationRoleType")int applicationRoleType);
	
	public BasCompInfo queryVirtualCompInfo(@Param("netId")Long netId,@Param("compTypeNum")String compTypeNum);
	
	void updateMemberNameOfmemberInfo(@Param("memberName")String memberName,@Param("memberId")Long memberId);
	public Long queryRelationCompId(@Param("compId")Long loginCompId);

	public BasCompInfo findById(@Param("compId")Long loginCompId) throws DataAccessException;

	public int insert(BasCompInfo compInfo);

	public  int updateCompTypeNum(@Param("relationCompId")Long relationCompId,@Param("compId")Long loginCompId);

	public int saveEmployeeaudit(@Param("id")long id, @Param("memberid")Long memberid, @Param("roleid")Long roleid, @Param("compId")Long compId,@Param("auditItem")int audit_item,@Param("auditOpinion")int audit_opinion, @Param("application_time")Date application_time);

	List<String> queryRepeatOrderOfRel(@Param("order")String order,@Param("netId")String netId,@Param("phone")String phone );
	
	
	List<HashMap<String, Object>> isRepeatCompInfoName(HashMap<String, Object> paramMap);
	

	public Map<String,Object> queryResponsibles(@Param("memberId")Long memberId,@Param("roleId")int roleId);
	
	public Map<String, Object> queryPushMessage(@Param("compId")Long compId);
	void updateGSAuditInfo(@Param("memberId")Long memberId, @Param("compId")Long compId,@Param("applicationRoleType")int applicationRoleType);
	
	String queryCompStatusByCompId(@Param("compId")String compId);
	public List<Map<String, Object>> queryAllBasCompInfo();
	public List<String> queryCourierMemberId(@Param("list")List list);
	public List<String> queryCourierMemberIdByTime(@Param("list6")List list6, @Param("startMonth")String startMonth, @Param("endMonth")String endMonth);
	List<HashMap<String, Object>> queryCompAuditInfo(String compId);
	void saveSfAuditInfo(@Param("id")long id, @Param("memberid")Long memberid, @Param("roleid")Long roleid, @Param("compId")Long compId,@Param("auditItem")int audit_item,@Param("auditOpinion")int audit_opinion, @Param("application_time")Date application_time);
	
	
	
	
	
	
	
	
	
	
	
}
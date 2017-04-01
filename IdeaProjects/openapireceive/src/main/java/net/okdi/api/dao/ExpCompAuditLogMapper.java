package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ExpCompAuditLog;
import net.okdi.api.entity.ExpCompRelation;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpCompAuditLogMapper extends BaseDao {


	Map<String, Object> queryCompInfo(@Param("compName")String compName, @Param("memberPhone")String memberPhone,@Param("compAddress")String compAddress,@Param("netId")Long netId);

	ExpCompAuditLog queryCompRelationInfo(@Param("compId")Long compId, @Param("userId")Long userId);

	void updateCompRelation(Long logId);

	ExpCompAuditLog isAudit(@Param("loginCompId")Long loginCompId, @Param("compId")Long compId);

	int doAddAuditLog(ExpCompAuditLog expCompAuditLog);

	List<Map<String, Object>> selectLog(Long parentCompId);

	int auditAgress(ExpCompAuditLog expCompAuditLog);

	int auditRefuse(ExpCompAuditLog expCompAuditLog);


	int relieveLog(ExpCompRelation expCompRelation);

	ExpCompAuditLog queryAuditLog(Long compId);

	ExpCompAuditLog queryAuditLogByCompId(@Param("parentCompId")Long parentCompId,@Param("compId")Long compId);

	Map<String, Object> getCompInfo(@Param("compName")String compName, @Param("memberPhone")String memberPhone,@Param("compAddress")String compAddress,@Param("netId")Long netId);

	ExpCompAuditLog queryStatus(Long parentCompId, Long compId);

	int updateAuditStatus(ExpCompAuditLog expCompAuditLog);

	int doUpdateAuditStatus(@Param("parentCompId")Long parentCompId, @Param("compId")Long compId);

	int doInsertAuditLog(ExpCompAuditLog expCompAuditLog);

	void deleteCompRelation(@Param("compId")Long compId);

}
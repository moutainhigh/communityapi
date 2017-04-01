package net.okdi.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface HandleDateMapper {

	List<Map<String,Object>> queryAllRelationData();

	void insert(@Param("id")Long id,@Param("memberId")Long memberId, @Param("compId")Long compId, @Param("roleId")Long roleId, @Param("auditTime")Date auditTime);

	Map<String, Object>  queryPasswordByMobile(@Param("mobile")String mobile);

	
}
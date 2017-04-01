package net.okdi.api.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.ExpCompRelation;
import net.okdi.core.base.BaseDao;

public interface ExpCompRelationMapper extends BaseDao{



	List<ExpCompRelation> queryCompRelationByBusinessId(String compId);

	void deleteCompRelation(@Param("compId")Long compId, @Param("parentCompId")Long parentCompId);

	List<Map<String, Object>> getMemInfo(Long parentId);

	ExpCompRelation queryCompRelationByBusinessId(Long loginCompId,Long compId);

	void doAddRelation(Long relationId, Long netId, Long compId, Long parentCompId, Date createTime);

	List<Map<String, Object>> selectRelation(Long parentCompId);

	ExpCompRelation isConsuming(Long loginCompId, Long compId);

	void insert(ExpCompRelation expCompRelation);

	int deleteById(Long relationId);

	int editAreaColor(@Param("relationId")Long relationId, @Param("areaColor")String areaColor);

	ExpCompRelation queryRelationByCompId(Long compId);

	ExpCompRelation queryCompRelationByBusinessId(Long compId);


 
}
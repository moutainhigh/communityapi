package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.BasCompEmployeeResume;
import net.okdi.core.base.BaseDao;


public interface BasCompEmployeeResumeMapper extends BaseDao {

	/**
	 * Description（方法描述）:查询人员履历信息
	 * Author（开发人员）:ＨaiＦｅｎｇ.Ｈｅ
	 * Receive parameters(接收参数):
	 * Return parameters(返回参数):
	 */
	List<Map<String,Object>> queryMemberResume(Long memberId);

	int doAddResum(Map<String, Object> map);

	void deleteRes(Long memberId);
	
	Long queryMemberResumeCount(Long memberId);

	void editCompName(@Param("compId")Long compId, @Param("compName")String compName);
	
}
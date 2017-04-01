package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;
import net.okdi.api.entity.BasEmployeeAuditExample;
import net.okdi.api.vo.VO_MemberInfo;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;

public interface BasEmployeeAuditMapper extends BaseDao {
	int countByExample(BasEmployeeAuditExample example);

	int deleteByExample(BasEmployeeAuditExample example);

	int deleteByPrimaryKey(Long id);

	int insert(BasEmployeeAudit record);

	int insertSelective(BasEmployeeAudit record);

	List<BasEmployeeAudit> selectByExample(BasEmployeeAuditExample example);

	BasEmployeeAudit selectByPrimaryKey(Long id);

	int updateByExampleSelective(@Param("record") BasEmployeeAudit record,
			@Param("example") BasEmployeeAuditExample example);

	int updateByExample(@Param("record") BasEmployeeAudit record, @Param("example") BasEmployeeAuditExample example);

	int updateByPrimaryKeySelective(BasEmployeeAudit record);

	int updateByPrimaryKey(BasEmployeeAudit record);

	/**
	 * 
	 * @Method: doUpdateComp
	 * @Description: TODO
	 * @param @param map
	 * @param @return
	 * @return int
	 * @throws
	 * @since jdk1.6
	 */
	int doUpdateComp(Map<String, Object> map);

	int updateStatus(Map<String, Object> map);

	void doAddAudit(Map<String, Object> map);

	void saveAudit(Map<String, Object> map);

	/**
	 * 
	 * @Method: removeMemberInfo
	 * @Description: 拒绝之后的人员删除操作
	 * @param logId
	 *            日志id
	 * @author haifeng.he
	 * @date 2014-11-6 下午4:23:53
	 * @since jdk1.6
	 */
	int  removeMemberInfo(Long logId);

	/**
	 * 
	 * @Method: getAuditCount
	 * @Description: //判断该memberid是否与其他的站点现在处于待审核状态
	 * @param memberId
	 *            人员id
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-6 下午5:33:20
	 * @since jdk1.6
	 */
	int getAuditCount(Long memberId);

	void doEditAudit(Map<String, Object> map);

	int getVerifyAudit(Long memberId);

	void doInsertAudit(Map<String, Object> map);

	void doUpdatetAudit(Map<String, Object> map);

	List<Map<String, Object>> loadMemberInfo(Long compId);

	int mobileRegisterFirstStep(VO_MemberInfo vo);

	int mobileRegisterFirstStepEdit(VO_MemberInfo vo);

	void mobileRegisterSecondStep(VO_MemberInfo vo);
	
	void mobileRegisterSecondStepEdit(VO_MemberInfo vo);

	int editAudit(Map<String, Object> map);

	int editRelation(Map<String, Object> map);

	int deleteRelationLog(Long memberId);

	void doSaveAudit(Map<String, Object> map);

	BasEmployeeAudit queryRecorde(String associatedNumber);

	void doAddRelation(Map<String, Object> map);

	void editAuditInfo(@Param("memberId")Long memberId,@Param("roleId")Short roleId,@Param("compId")Long compId);

	BasEmployeeAudit queryIsExit(Map<String, Object> map);

	void deleteRelationLogByMemberId(@Param("memberId")Long memberId);
	int updateComp(@Param("compId")Long compId,@Param("memberId")Long memberId);
	public void updateAuditOpinion(Map<String,Object> param);
	long findBasEmployeeAuditByMemberId(long memberId);

	void updateSFAuditInfo(Map<String, Object> map);

	void updateKDAuditInfo(Map<String, Object> map);
	
	int  queryRolebyMemberId(Long memberId);
	
	Map queryComIdAndNetIdByMemberId(Long memberId);
}
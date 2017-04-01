package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeRelation;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;


public interface BasEmployeeRelationMapper extends BaseDao{
	/**
	 * 
	 * @Method: queryCountById
	 * @Description: 查询bas_employee_relation关系表中是否存在memberId与comId的记录(-1 后勤0
	 *               收派员1 站长)
	 * @param memberId网点id
	 * @param roleId角色id
	 * @param compId网点id
	 * @return
	 * @since jdk1.6
	 */
	List<BasEmployeeRelation> queryCountById(Map<String, Object> map);

	
	/**
	 * 
	 * @Method: insertCompInfo 
	 * @Description: 添加公司人员关系表
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:09:51
	 * @since jdk1.6
	 */
	void insertCompInfo(Map<String, Object> map);


	/**
	 * 
	 * @Method: updateCompInfo 
	 * @Description: 如果关系表中有该member的关联关系,则更新该条数据关系
	 * @param basEmployeeRelation
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:10:00
	 * @since jdk1.6
	 */
	int updateCompInfo(BasEmployeeRelation basEmployeeRelation);


	/**
	 * 
	 * @Method: doEditComp 
	 * @Description: 修改人员所在站点 从旧站点修改到新站点
	 * @param map
	 * @return
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:05:16
	 * @since jdk1.6
	 */
	int doEditComp(Map<String, Object> map);
	/**
	 * 
	 * @Method: doEditMemberInfo 
	 * @Description: 修改右侧人员信息
	 * @param map
	 * @author haifeng.he
	 * @date 2014-11-10 下午8:05:26
	 * @since jdk1.6
	 */
	int   doEditMemberInfo(Map<String, Object> map);
	/**
	 * 
	 * @Method: deleteMemberInfo 
	 * @Description: 站点添加的收派员的删除操作(先删除关系表 同事日志表中写一条日志记录 )
	 * @param @param memberId
	 * @param @return
	 * @return int
	 * @throws
	 */
	int deleteMemberInfo(Long memberId);
	/**
	 * 
	 * @Method: queryMemberIdByCompId 
	 * @Description: 通过compId获取关系表中的memberId
	 * @param  compId 网点id
	 * @return BasEmployeeRelation
	 * @throws 
	 * @since jdk1.6
	 */
	BasEmployeeRelation queryMemberIdByCompId(Long compId);
	/**
	 * 
	 * @Method: doAddRelation 
	 * @Description: //插入关系表
	 * @param map
	 * @throws 
	 * @since jdk1.6
	 */
	int  doAddRelation(Map<String, Object> map);


	BasEmployeeRelation getMemInfo(Long compId);


	List<Map<String,Object>> getMemInfoByCompId(Long parentId);

	/**
	 * 
	 * @Method: queryMemberForComp 
	 * @Description: 查询站点或营业分部下的人员列表
	 * @param compId
	 * @param  roleId
	 * @return List<Map<String,Object>>
	 * @throws 
	 * @since jdk1.6
	 */
	List<Map<String, Object>> queryMemberForComp(Map<String, Object> map);
	/**
	 * 
	 * @Method: queryCompIdByMemberId 
	 * @Description: 查询收派员所属站点或营业分部
	 * @param memberId
	 * @return
	 * @author xpf
	 * @date 2014-10-28 下午5:38:26
	 * @since jdk1.6
	 */
	BasEmployeeRelation queryCompIdByMemberId(Long memberId);
	
	
	public Integer countMemberNum(Map<String, Object> paras);
	
	public List<Map<String, Object>> countMemberNumList(Map<String, Object> paras);
	
	public int getTotalNum();
	
	public int getCurrentMonthNum();
	
	public List<Map<String, Object>> getMonthOfDaysNum();
	long findByMemberId(long memberId);
	
	/**
	 * 
	 * @Method: doEditMemberInfo 
	 * @Description: 修改人员认证角色
	 * @param map
	 * @author yangkai
	 * @date 2015-10-23 下午15:41:26
	 * @since jdk1.7
	 */
	int   editBasEmployeeaudit(Map<String, Object> map);
	
	public Map<String,Object> findMemberInfoById(@Param("memberId")Long memberId);
	public void updateBascompbusiness(@Param("map")Map<String,Object>map);


	Long findCompIdByMemberId(Long actorMemberId);
}
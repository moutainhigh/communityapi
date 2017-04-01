package net.okdi.apiV1.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.MemberInfo;
import net.okdi.apiV1.vo.VoUserAudit;

public interface MemberInfoMapperV1 {

    int deleteByPrimaryKey(Long memberId);

    int insert(MemberInfo record);

    int insertSelective(MemberInfo record);


    MemberInfo selectByPrimaryKey(String memberId);


    int updateByPrimaryKeySelective(MemberInfo record);

    int updateByPrimaryKey(MemberInfo record);

	void updateMemberInfo(HashMap<String,String> map);

	List<VoUserAudit> queryUserAuditList(@Param("map")HashMap<String, Object> params);

	HashMap<String, Object> queryCompInfo(String compId);

	HashMap<String, Object> queryMemberinfo(String memberId);

	List<MemberInfo> isIdNumRepeat(@Param("idNum")String idNum, @Param("memberPhone")String memberPhone);

	Integer queryCount(@Param("map") HashMap<String, Object> params);
	HashMap<String, Object>  selectMumberByMumberId(String memberId);

	public List<Map<String ,Object>>  queryAllAudit(@Param("map")Map<String,Object>map);
	public Integer  queryAllAuditCount(@Param("map")Map<String,Object>map);

	 /**
	  *查询身份信息
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public Map<String ,Object> queryByMemberId(@Param("memberId")String memberId,@Param("memStatus")String memStatus);
	 /**
	  *查询归属信息
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public Map<String ,Object> queryByMemberId2(@Param("memberId")String memberId ,@Param("memStatus")String memStatus);
	public Map<String ,Object> queryAuditPeopleByMemberId(@Param("memberId")String memberId);
	 /**
	  * 归属信息-店长（已认证）
	  * 归属信息-店员（已认证）
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public Map<String,Object> findShopowner(@Param("memberId")String memberId,@Param("roleId")String roleId);
	 /**
	  * 归属信息-收派员、后勤（等待认证）
 	  * 归属信息-收派员、后勤（已认证）
	  * 归属信息-收派员、后勤（未认证）
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public Map<String,Object> findToreceiveAndLogistic(@Param("memberId")String memberId,@Param("roleId")String roleId);
	 /**
	  *  
	  * 归属信息-站长（认证失败）
      * 归属信息-站长（已认证）
	  *	归属信息-站长（等待认证）
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public List<Map<String,Object>> findStationmaster(@Param("memberId")String memberId,@Param("roleId")String roleId);
	 /**
	  *查询站长归属信息(按照站点归属来查)
	  * @author xuanhua.hu
	  * @version 1.0.0
	  * @return {@link String}
	  * @param memberId
	  * @return
	  */
	public List<Map<String,Object>> findbasCompaudit(@Param("memberId")String memberId);
	
	
	//归属
	public List<Map<String, Object>> queryAuditItemAllByGui(@Param("map")Map<String,Object> map);

	//身份
	public List<Map<String, Object>> queryAuditItemAllByShen(@Param("map")Map<String,Object> map);
	//查询memberId
	public String queryMemberIdAllByCondit(@Param("map")Map<String,Object> map);

	MemberInfo queryMemberInfoByMemberPhone(@Param("memberPhone")String mobile);
	//查询列表中的数据
	public List<Map<String, Object>> queryAuditItemAll(@Param("map")Map<String,Object> map);
	//查询归属审核状态
	List<Map<String, Object>> queryAttributionAuditState(@Param("map")Map<String, Object> map);
	/**
	 * 通过手机号集合查询memberid集合
	 */
	List<MemberInfo> queryMemberInfoByMemberPhones(HashMap<String, Object> param);

	Map querySiteNameByMemberId(String memberId);

	MemberInfo queryMemberInfoByMemberId(@Param("memberId")Long memberId);


 }

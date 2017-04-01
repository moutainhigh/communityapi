package net.okdi.apiV2.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CollectionMapper {

	/**
	 * 查询人的基本信息
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Map<String,Object>}
	 * @param memberId
	 * @return
	 */
	public Map<String, Object> getMeberInfoByMemberId(
			@Param("memberId") String memberId);

	/**
	 * 查询是否已经存在代收点
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param compName
	 * @param compAddress
	 * @return
	 */
	public Map<String,Object> isHasCollection(@Param("compName") String compName,
			@Param("compAddress") String compAddress);
	/**
	 * 查询是否有店长
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param compId
	 * @return
	 */
	public Integer findType(@Param("compId") String compId);

	/**
	 * 更新身份认证信息
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param map
	 * @return
	 */
	public Integer updateIdentityAudit(@Param("map") Map<String, Object> map);

	/**
	 * 查找店长
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Map<String,Object>}
	 * @param compId
	 * @return
	 */
	public Map<String, Object> findShopkeeperByCompId(
			@Param("compId") String compId);

	/**
	 * 店长更新代收点店员的归宿信息
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param compId
	 * @return
	 */
	public Integer updateDestinationbyCompId(@Param("compId") String compId);

	/**
	 * 查询身份证号是否被注册
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param idNum
	 * @return
	 */
	public Map<String, Object> findIdentity(@Param("idNum") String idNum);
	public Map<String, Object> findIdentityNew(@Param("idNum") String idNum,@Param("memberPhone") String memberPhone);

	/**
	 * 更新人员信息（更新名字，更身份证号进去）
	 * 
	 * @author xuanhua.hu@amssy.com
	 * @version 1.0.0
	 * @return {@link Integer}
	 * @param idNum
	 * @param memberName
	 * @param memberId
	 * @return
	 */
	public Integer updateIdentity(@Param("idNum") String idNum,@Param("memberName") String memberName,
			@Param("memberId") String memberId);
	public void updateIdentityAudit2(@Param("map") Map<String, Object> map);
	public String selectPhoneByMemberId(@Param("memberId")String memberId);
	public void Delbasemployeerelation(@Param("memberId")String memberId);
	public void delBascompbusiness(@Param("memberId")String memberId,@Param("compId")String compId);
	public void delAutidBymemberId(@Param("memberId")String memberId);
	public void updatebasemployeerelation(@Param("compId") String compId,
			@Param("roleId") String roleId,
			@Param("memberId") String memberId);

	public HashMap<String, Object> queryMemberInfo(String memberPhone);

	public HashMap<String, Object> querySfAuditInfo(String memberPhone);

	 
}

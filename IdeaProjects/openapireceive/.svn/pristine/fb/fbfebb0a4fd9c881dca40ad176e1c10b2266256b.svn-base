package net.okdi.apiV1.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.core.base.BaseDao;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;


public interface BasCompInfoMapperV1 extends BaseDao{
	
	public List<Map<String, Object>> queryDeviceInfos(@Param("startSize")int startSize,@Param("size") int size);
	/**
	 * 按条件查询
	 * @return
	 */
	public List<Map<String, Object>> queryDeviceInfosByCon(@Param("userType")String[] userTypeParam,@Param("platform")String platform,@Param("startTime")String startTime,@Param("endTime")String endTime,@Param("useType")String useType,@Param("list")List<String> list );
	/**
	 * @author jingguoqiang
	 */
	public int insert(BasCompInfo compInfo);
	/**
	 * @author jingguoqiang
	 */
	public String queryCompAuditDesc(Long compId);
	/**
	 * @author jingguoqiang
	 */
	public String queryJoinState1(String member_phone);
	/**
	 * @author jingguoqiang
	 */
	public Map<String, Object> queryJoinState2(String comp_id);
	/**
	 * @author jingguoqiang
	 */
	public String queryJoinState3(String comp_id);
	/**
	 * @author jingguoqiang
	 */
	public BasCompInfo findById(@Param("compId")Long loginCompId) throws DataAccessException;
	
	public Long queryRelationCompId(@Param("compId")Long loginCompId);
	/**
	 * @author jingguoqiang
	 */
	public  int updateCompTypeNum(@Param("relationCompId")Long relationCompId,@Param("compId")Long loginCompId);
	/**
	 * @author jingguoqiang
	 */
	public  int updateCompType(@Param("compId")Long compId, @Param("compMess")String compMess,@Param("compStatus")String compStatus);
	
	public String queryCompStatus(@Param("compId")Long compId);
	/**
	 * @author jingguoqiang
	 */
	public void auditSite(@Param("compId")Long compId, @Param("compStatus")String compStatus);
	/**
	 * @author jingguoqiang
	 */
	public String queryCompName(@Param("compId")Long compId);
	
	public Map<String, Object> queryFormMemberId(@Param("compId")Long compId);
	
	public Map<String, Object> queryPushMessage(@Param("compId")Long compId);
	/**
	 * @author jingguoqiang
	 */
	public void updateCompStatus(@Param("compStatus")int compStatus,@Param("compId")Long compId);
	
	public void updateBasEmployeeaudit(@Param("auditComp")Long auditComp, @Param("memberId")String memberId,@Param("auditItem")String auditItem);
	
	public void performAudit(@Param("id")Long id,@Param("compId")Long compId,@Param("compStatus")String compStatus,@Param("auditDesc")String auditDesc,@Param("auditTime")Date auditTime);
	
	public void updateMemberInvitationRegister(@Param("fromMemberId")Long fromMemberId,@Param("toMemberPhone")String toMemberPhone,@Param("toMemberNetId")Long toMemberNetId);
	/**
	 * @author jingguoqiang
	 */
	public int applyJoin(@Param("id")Long id,@Param("member_id")String member_id,@Param("audit_comp")String audit_comp,@Param("application_role_type")String application_role_type,
			@Param("application_time")Date application_time,@Param("audit_item")String audit_item,@Param("audit_opinion")String audit_opinion);
	/**
	 * 
	 * @Description: 获取同网络下重名网点信息
	 * @author 翟士贺
	 * @date 2014-10-18下午3:16:12
	 * @param paras
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getSameCompNameForMobile(Map<String, Object> paras);

	/**
	 * 
	 * @Description: 获取公司的详细信息
	 * @author zhengjiong
	 * @date 2015-10-13
	 * @param paras
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryOpenSiteDetail(@Param("comid")Long comid);
	
	/**
	 * 
	 * @Description: 获取公司的详细信息
	 * @author xuanhua.hu
	 * @date 2015-10-16
	 * @param paras
	 * @return Map<String,Object>
	 */
	public List<Map<String, Object>> selectCompinfoByName(@Param("comp_name")String comp_name);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长填写代收点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public int insertBasCompinfo(@Param("map")Map<String,Object>map);
	public int insertBasCompinfoNew(@Param("map")Map<String,Object>map);
	public List<Map<String,Object>> queryCompInfoByLongitudeAndLatitude(Map<String, Object> map);
	public List<Map<String,Object>> queryWechatCompInfoByLongitudeAndLatitude(Map<String, Object> map);
	public List<Map<String, Object>> queryCompInfoByRoleId(Map<String, Object> mapParam);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店员通过手机号邀请店长入住</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public Map<String, Object> findShopowner(@Param("memberTelephone")String memberTelephone);


	public List<Map<String, Object>> queryCollectionByRoleId(Map<String, Object> mapParam);

	public List<Map<String, Object>> queryCompInfoByCompName(@Param("addressId")Long addressId, @Param("compName")String compName, @Param("roleId")String roleId,@Param("readPath")String readPath);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店员根据 compid 查询详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public Map<String, Object> queryOpenSite(@Param("compid")Long compid);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入一条 到bas_employee_relation 表中</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public int saveEmployeeRelation(@Param("eid")long eid, @Param("memberid")Long memberid, @Param("compid")Long compid,
			@Param("roleid")Long roleid);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>更新原先bas_cominfo 表中的领用关系</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public int updateBaseCompinfo(@Param("id")Long id, @Param("compid")Long compid, @Param("date")String date);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>插入 bas_employeeaudit 表中收派员数据</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @return null
	 * @since v1.0
	 */
	public int saveEmployeeaudit(@Param("id")long id, @Param("memberid")Long memberid, @Param("roleid")Long roleid, @Param("compId")Long compId,@Param("auditItem")int audit_item,@Param("auditOpinion")int audit_opinion, @Param("application_time")Date application_time);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员申请加入</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return null
	 * @since v1.0
	 */
	public int saveMemberInvitationRegister(@Param("id")long id, @Param("createTime")String createTime,
			@Param("fromMemberId")String fromMemberId, @Param("fromMemberPhone")String fromMemberPhone,
			@Param("fromMemberRoleid")Integer fromMemberRoleid, @Param("toMemberPhone")String toMemberPhone, 
			@Param("toRoleId")Integer toRoleId,@Param("belongToNetId")Long belongToNetId, @Param("isRegister")int isRegister);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点审核不通过</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-19</dd>
	 * @return null
	 * @since v1.0
	 */
	public int siteAuditNotThrough(@Param("isn")Integer isn, @Param("auditOpinion")Integer auditOpinion, @Param("keyId")Long keyId, @Param("refuseDesc")String refuseDesc, @Param("auditTime")String date);
	//根据memberId查询收派员手机号
	public List queryMemberPhoneByMemberId(@Param("memberId")Long memberId);
	public List<Map<String, Object>> queryMemberInfoByCompId(@Param("compId")String compId);
	//先去判断是否邀请过店长
	public Long queryManagerIsInviteByPhone(@Param("fromMemberId")String fromMemberId,
			@Param("fromMemberPhone")String fromMemberPhone, @Param("toMemberPhone")String toMemberPhone);
	//更改邀请过的时间
	public void ManagerIsInviteByPhone(@Param("fromMemberId")String fromMemberId,@Param("fromMemberPhone")String fromMemberPhone, 
			@Param("toMemberPhone")String toMemberPhone, @Param("modityTime")String modityTime);
	//查询是否已经申请过
	public Map queryEmployeeauditByPhone(@Param("memberid")Long memberid, @Param("auditItem")int auditItem);
	public BasCompInfo queryVirtualCompInfo(@Param("netId")Long netId,@Param("compTypeNum")String compTypeNum);

	public void updateEmployeeAudit(@Param("memberId")Long memberId, @Param("compId")Long compId, @Param("roleId")String roleId,@Param("auditOpinion")String auditOpinion, @Param("auditUser")Long auditUser);
	//店长拒绝店员 删除店员的这条记录
	public void removeEmployeeAudit(@Param("memberId")Long memberId, @Param("compId")Long compId, @Param("roleType")String roleType, @Param("auditItem")String auditItem);
	public void deleteRelation(@Param("memberId")String member_id);
	public int queryCountSF(String member_id);
	public int queryCountKD(String member_id);
	public void updateSFAuditInfo(@Param("member_id")String member_id, @Param("audit_comp")String audit_comp);
	public void updateKDAuditInfo(@Param("member_id")String member_id, @Param("audit_comp")String audit_comp);
	
	public List<Map<String, Object>> queryCompInfo(Map<String, Object> mapParam);
	public int queryCountGS(String member_id);
	
	public List queryPhones(@Param("phone")String phone, @Param("currentPage")Integer currentPage,@Param("pageSize")Integer pageSize);
	
	public int queryPhonesTotal(@Param("phone")String phone);
	
	public List exportPhones(@Param("phone")String phone);
	
}
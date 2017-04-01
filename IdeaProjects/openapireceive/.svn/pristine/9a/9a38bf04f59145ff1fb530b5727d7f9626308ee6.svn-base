package net.okdi.apiV1.service;

import java.util.List;
import java.util.Map;

public interface OutletsService {

	public Map<String,Object> queryOpenSiteDetail(Long comid);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长填写代收点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-13</dd>
	 * @return null
	 * @since v1.0
	 */
	public Long insertBasCompinfo(String comp_name, String comp_address,
			String responsible_telephone, String phone,String roleid
			,String longitude, String latitude,String member_id,String responsible, String agentType);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长收派员入驻店铺</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return int
	 * @since v1.0
	 */
	public String saveSiteOpen(Long compid, Long roleid, Long memberid, String managerId, String mobile);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员根据短信提醒站长入驻店铺</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return int
	 * @since v1.0
	 */
	public void invite(String fromMemberId, String fromMemberPhone,
			Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId, Integer flag);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员根据站长手机号查询站点</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return int
	 * @since v1.0
	 */
	public Map<String,Object> findShopowner(String responsible_telephone);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>收派员申请加入代收点</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return int
	 * @since v1.0
	 */
	public void apply(Long roleid, Long memberid, Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>站点审核不通过</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return Map<String, Object>
	 * @since v1.0
	 */
	public void siteAuditNotThrough(Integer isn, Long keyId, String refuseDesc, String memberId, String mobile);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据memberid查询该收派员身份证下的注册手机号</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-15</dd>
	 * @return Map<String, Object>
	 * @since v1.0
	 */
	//根据memberId查询出收派员手机号
	public List queryMemberPhoneByMemberId(Long memberId);
	//收派员审核通过
	public void siteAuditThrough(Long keyId, String remark, String memberId, String mobile);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>代收点店员审核</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-19</dd>
	 * @return List
	 * @since v1.0
	 */
	public Map<String, Object> queryAllAudit(String memberStatus, String currentPage,
			String pageSize, String memberName, String mobile, String idNum,
			String roleId, String registerStartTime, String registerEndTime, String provinceId);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据店员id(memberId) 查询店员的基本信息以及身份认证信息和归属认证信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-19</dd>
	 * @return Map
	 * @since v1.0
	 */
	public Map<String, Object> queryByMemberId(String memberId,String memStatus);//查询人员信息
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据店员id(memberId) 查询店员的图片信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-19</dd>
	 * @return Map
	 * @since v1.0
	 */
	public List<Map<String, Object>> queryImageByMemberId(String memberId);//查询图片
		
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>根据店员id(memberId) 查询审批人姓名</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-19</dd>
	 * @return Map
	 * @since v1.0
	 */
	public Map<String, Object> queryAuditPeopleByMemberId(String memberId);//查询人员信息
	//导出Excel表中的数据
	public List<Map<String,Object>> exportExcelData(String memberStatus,
			String memberName, String mobile, String idNum, String roleId,
			String registerStartTime, String registerEndTime, String province);
	//店长审核店员
	public String managerAauditShopAssistant(Long memberId, Long compId,
			String roleId,String moblie, String auditOpinion, Long auditUser);
	//查询归属审核状态
	public Map<String, Map<String, Object>> queryAttributionAuditState(
			String memberStatus, String memberName, String mobile,
			String idNum, String roleId, String registerStartTime,
			String registerEndTime, String province);
}

package net.okdi.apiV1.service;

import java.util.Map;

public interface OutletsService {

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长店员查询代收点详细信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @param comid 公司id
	 * @return null
	 * @since v1.0
	 */
	public String querySiteDetailed(Long comid);

	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长填写代收点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-13</dd>
	 * @return null
	 * @since v1.0
	 */
	public String insertBasCompinfo(String comp_name, String comp_address,
			String responsible_telephone, String phone, String roleid,
			String longitude, String latitude,String member_id,String responsible, String agentType); 
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店员邀请店长加入</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-13</dd>
	 * @param responsible_telephone 店长手机号
	 * @return null
	 * @since v1.0
	 */
	public String invite(String fromMemberId, String fromMemberPhone,
			Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId, Integer flag);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd> 收派员查找店长</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-13</dd>
	 * @param responsible_telephone 店长手机号
	 * @return null
	 * @since v1.0
	 */
	public String findShopowner(String memberTelephone);
	
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>申请加入代收点</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>xuanhua.hu</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-13</dd>
	 * @param member_id 店长手机号
	 * @return null
	 * @since v1.0
	 */
	public String apply(Long roleid, Long memberid, Long compId);
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>店长店员申请入住</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>jiong.zheng</dd>
	 * <dt><span class="strong">时间:</span></dt><dd>2015-10-14</dd>
	 * @param compid 公司id, roleid 角色id
	 * @return null
	 * @since v1.0
	 */
	public String saveSite(Long compid, Long roleid, Long memberid, String managerId, String mobile);

	//店长审核店员
	public String managerAauditShopAssistant(Long memberId, Long compId,
			String roleId, String moblie, String auditOpinion, Long auditUser);
}

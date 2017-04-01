package net.okdi.apiV4.service;

import org.apache.ibatis.annotations.Param;

public interface CollectionNewService {
	public String getMemberInfo(String memberId);

	public String isHasCollection(String compName, String compAddress);

	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId);

	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String agentType,String province,String city);

	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone,String agentType,String province,String city);

	public String insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId);
	
	public String findIdentity(@Param("idNum") String idNum,String memberPhone);

	public String insertShopkeeperAudit3(String roleId, String memberId, String compName, String compId);

	public String insertClerkAudit3(String compName, String roleId, String memberId, String compId);

	public String createCompDS(String compName, String compAddress, String memberName, String roleId, String latitude, String longitude, String memberId, String compId, String auditId, String phone, String agentType, String province, String city);
}

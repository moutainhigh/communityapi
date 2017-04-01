package net.okdi.apiV2.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CollectionService {

	public Map<String, Object> getMemberInfo(String memberId);

	public Map<String,Object> isHasCollection(String compName, String compAddress);

	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId);

	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId);

	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone);

	public Map<String,Object> insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId);
	
	public Map<String,Object> findIdentity(@Param("idNum") String idNum);

}

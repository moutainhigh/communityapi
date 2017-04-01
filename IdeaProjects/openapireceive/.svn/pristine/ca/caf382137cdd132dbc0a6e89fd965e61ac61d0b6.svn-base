package net.okdi.apiV1.service;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.BasEmployeeAudit;

public interface QueryNearInfoService {

	public List<Map<String,Object>> queryCompInfoByRoleId(Double longitude, Double latitude, String roleId);
	
	public List<Map<String,Object>> queryCompInfoByCompName(Double longitude, Double latitude, String roleId,String compName);

	public Map<String,Object> queryFromPosition(Double longitude, Double latitude);

	public int queryVerifyCode(String mobile, String randomCode);

	public Map<String,Object> queryShareInfo(String compTypeNum);

	public Map<String,Object> getValidationStatus(String mobile);

	public void invitationIntoCompInfo(String fromMemberPhone, String toMemberPhone, String invitationType);

	public void initVirtualCompInfo();

	public Map<String,Object> invitationHaveNetInfo(Long memberId, Long netId,Short roleId);
	public Map<String, Object> queryRoleInfoAndRaltion(Long memberId);

	public Map<String,Object> queryAuthenticationInfo(String mobile);

	public void deleteWrongData(Long auditId);

	public void deleteRedisInfo(String redisName,String key);

	public Map<String,Object> queryPasswordByMobile(String mobile);

	public List<BasEmployeeAudit> queryWrongDataByMobile(String mobile);

	public Map<String,Object> queryUnReadMessage(String mobile);

	List<Map<String, Object>> queryWechatCompInfoByRoleId(Double longitude,Double latitude);
}

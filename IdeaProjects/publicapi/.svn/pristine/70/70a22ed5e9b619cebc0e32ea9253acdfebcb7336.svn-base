package net.okdi.apiV1.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface QueryNearInfoService {

	public String queryCompInfoByRoleId(String longitude, String latitude, String roleId);

	public String queryCompInfoByCompName(Double longitude, Double latitude, String roleId, String compName);

	public String queryVerifyCode(String mobile,HttpServletRequest req, HttpServletResponse resp);

	public String queryShareInfo(String compTypeNum);

	public String queryVerifyCodeIsRight(String mobile, String verifyCode);

	public String invitationIntoCompInfo(String fromMemberPhone, String toMemberPhone,String invitationType);

	public String initVirtualCompInfo();

	public String invitationHaveNetInfo(String memberId, String netId,String roleId);

	public String queryAuthenticationInfo(String mobile);

	public String deleteWrongData(String auditId);

	public String deleteRedisInfo(String redisName,String key);

	public String queryPasswordByMobile(String mobile);

	public String queryWrongDataByMobile(String mobile);

	public String queryUnReadMessage(String mobile);

}

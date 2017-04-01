package net.okdi.apiV1.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV1.service.OutletsService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutletsServiceImpl implements OutletsService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	@Override
	public String querySiteDetailed(Long compid) {
		
		
		if(PubMethod.isEmpty(compid)){
			return PubMethod.paramError("publicapi.QueryNearInfoController.queryCompInfoByRoleId.001", "经纬度异常");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compid", compid);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"outlets/queryOpenSiteDetail", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertBasCompinfo(String comp_name, String comp_address,
			String responsible_telephone, String phone, String roleid,
			String longitude, String latitude,String member_id,String responsible, String agentType) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("comp_name", comp_name);
		map.put("comp_address", comp_address);
		map.put("responsible_telephone", responsible_telephone);
		map.put("phone", phone);
		map.put("roleid", roleid);
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		map.put("member_id", member_id);
		map.put("responsible", responsible);
		map.put("agentType", agentType);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"outlets/insertBasCompinfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String invite(String fromMemberId, String fromMemberPhone,
			Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId, Integer flag) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromMemberId", fromMemberId);
		map.put("fromMemberPhone", fromMemberPhone);
		map.put("fromMemberRoleid", fromMemberRoleid);
		map.put("toMemberPhone", toMemberPhone);
		map.put("toRoleId", toRoleId);
		map.put("flag", flag);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("outlets/invite", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String apply(Long roleid, Long memberid, Long compId) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleid", roleid);
		map.put("memberid", memberid);
		map.put("compId", compId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("outlets/apply", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String findShopowner(String memberTelephone) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberTelephone", memberTelephone);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("outlets/findShopowner", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String saveSite(Long compid, Long roleid, Long memberid, String managerId, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("compid", compid);
		map.put("roleid", roleid);
		map.put("memberid", memberid);
		map.put("managerId", managerId);
		map.put("mobile", mobile);
		String result = null;
		System.out.println("saveSite  方法进来了          3");
		result = openApiHttpClient.doPassSendStr("outlets/saveSiteOpen", map);
		System.out.println("saveSite  方法进来了          3");
		return result;
	}

	@Override
	public String managerAauditShopAssistant(Long memberId, Long compId,
			String roleId, String moblie, String auditOpinion, Long auditUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("roleId", roleId);
		map.put("moblie", moblie);
		map.put("auditOpinion", auditOpinion);
		map.put("auditUser", auditUser);
		String result = null;
		result = openApiHttpClient.doPassSendStr("outlets/managerAauditShopAssistant", map);
		return result;
	}

}

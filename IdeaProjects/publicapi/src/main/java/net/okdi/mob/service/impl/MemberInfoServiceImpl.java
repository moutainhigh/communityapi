package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.service.MemberInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	@Override
	public String querySiteMemberList(Long compId, Long logMemberId) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId.toString());
		map.put("logMemberId",logMemberId.toString());
		String result = openApiHttpClient.doPassSendStr("memberInfo/querySiteMemberList", map);
		return result;
	}

	@Override
	public String queryPendingAuditMemberList(Long compId, Long logMemberId) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId.toString());
		map.put("logMemberId",logMemberId.toString());
		String result = openApiHttpClient.doPassSendStr("memberInfo/queryPendingAuditMemberList", map);
		return result;
	}

	@Override
	public String siteMemberToexamine(Long logId, String memberPhone,
			Long userId, Short flag, Long compId, String memberName,
			Long memberId, String refuseDesc, String areaColor, Short roleId) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("logId",logId.toString());
		map.put("memberPhone",memberPhone);
		map.put("userId",userId.toString());
		map.put("flag",flag.toString());
		map.put("compId",compId.toString());
		map.put("memberName",memberName);
		map.put("memberId",memberId.toString());
		map.put("refuseDesc",refuseDesc);
		map.put("areaColor",areaColor);
		map.put("roleId",roleId.toString());
		String result = openApiHttpClient.doPassSendStr("memberInfo/siteMemberToexamine", map);
		return result;
	}

	@Override
	public String insertMemberInfo(Long compId, String associatedNumber,
			String memberName, Short roleId, String areaColor, Long userId,
			Short memberSource) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId.toString());
		map.put("associatedNumber",associatedNumber);
		map.put("memberName",memberName);
		map.put("roleId",roleId);
		map.put("areaColor",areaColor);
		map.put("userId",userId.toString());
		map.put("memberSource",memberSource.toString());
		String result = openApiHttpClient.doPassSendStr("memberInfo/insertMemberInfo", map);
		return result;
	}

	@Override
	public String queryMemberInfoById(Long memberId) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("memberId",memberId.toString());
		String result = openApiHttpClient.doPassSendStr("memberInfo/queryMemberInfoById", map);
		return result;
	}

	@Override
	public String updateMemberInfo(Long compId, Long memberId, Short roleId,
			Short employeeWorkStatusFlag, String areaColor,Long userId) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId.toString());
		map.put("memberId",memberId.toString());
		map.put("roleId",roleId.toString());
		map.put("userId",userId.toString());
		map.put("employeeWorkStatusFlag",employeeWorkStatusFlag.toString());
		map.put("areaColor",areaColor);
		String result = openApiHttpClient.doPassSendStr("memberInfo/updateMemberInfo", map);
		return result;
	}

	@Override
	public String deleteSiteMember(Long userId, Long memberId, Long compId,
			String memberName, String memberPhone) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("userId",userId.toString());
		map.put("memberId",memberId.toString());
		map.put("compId",compId.toString());
		map.put("memberName",memberName);
		map.put("memberPhone",memberPhone);
		String result = openApiHttpClient.doPassSendStr("memberInfo/deleteSiteMember", map);
		return result;
	}
	
	@Override
	public String checkForExistence(Long compId, String associatedNumber) {
		Map <String,Object> map = new HashMap<String,Object>();
		map.put("compId",compId.toString());
		map.put("associatedNumber",associatedNumber);
		String result = openApiHttpClient.doPassSendStr("memberInfo/checkForExistence", map);
		return result;
	}

	
}

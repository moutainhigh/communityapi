package net.okdi.apiV2.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV2.service.CollectionService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;

@Service
public class CollectionServiceImpl implements CollectionService {
	public static final Log logger = LogFactory
			.getLog(ExpressRegisterServiceImpl.class);

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	private Map<String, Object> map = new HashMap<String, Object>();

	@Override
	public String getMemberInfo(String memberId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("memberId", memberId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/getMemberInfo/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String isHasCollection(String compName, String compAddress) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/isHasCollection/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/insertClerkAudit2/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/insertClerkAudit1/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", compId);
		map.put("phone", phone);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/insertShopkeeperAudit1/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertShopkeeperAudit2(String compName, String compAddress,
			String memberName, String idNum, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("idNum", idNum);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/insertShopkeeperAudit2/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String findIdentity(String idNum) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("idNum", idNum);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collection/findIdentity/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
}
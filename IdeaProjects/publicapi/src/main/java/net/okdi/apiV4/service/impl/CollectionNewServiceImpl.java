package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.okdi.apiV2.service.impl.ExpressRegisterServiceImpl;
import net.okdi.apiV4.service.CollectionNewService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
@Service
public class CollectionNewServiceImpl implements CollectionNewService {
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
					"collectionNew/getMemberInfo/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		logger.info("getMemberInfo getMemberInfo >>>>>>>>>>>>>>>>>>>>>>>>: "+memberId);
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
					"collectionNew/isHasCollection/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		logger.info("isHasCollection isHasCollection >>>>>>>>>>>>>>>>>>>>>>>>: ");
		return result;
	}

	@Override
	public String insertClerkAudit2(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertClerkAudit2/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertClerkAudit1(String compName, String compAddress,
			String memberName, String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String agentType,String province,String city) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		map.put("agentType", agentType);
		map.put("province", province);
		map.put("city", city);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertClerkAudit1/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 店员 代收点不存在 _insertClerkAudit1: "+result);
		return result;
	}

	@Override
	public String insertShopkeeperAudit1(String compName, String compAddress,
			String memberName,  String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId,String phone,String agentType,String province,String city) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", compId);
		map.put("phone", phone);
		map.put("agentType", agentType);
		map.put("province", province);
		map.put("city", city);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertShopkeeperAudit1/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 店长 代收点不存在 :insertShopkeeperAudit1:"+result);
		return result;
	}

	@Override
	public String insertShopkeeperAudit2(String compName, String compAddress,
			String memberName,  String roleId, String latitude,
			String longitude, String memberId, String compId, String auditId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("compAddress", compAddress);
		map.put("memberName", memberName);
		map.put("roleId", roleId);
		map.put("latitude", latitude);
		map.put("longitude", longitude);
		map.put("memberId", memberId);
		map.put("compId", compId);
		map.put("auditId", auditId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertShopkeeperAudit2/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String findIdentity(String idNum,String memberPhone) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("idNum", idNum);
		map.put("memberPhone", memberPhone);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/findIdentity/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertShopkeeperAudit3(String roleId, String memberId, String compName, String compId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("roleId", roleId);
		map.put("memberId", memberId);
		map.put("compId", compId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertShopkeeperAudit3/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String insertClerkAudit3(String compName, String roleId, String memberId, String compId) {
		// TODO Auto-generated method stub
		map.clear();
		map.put("compName", compName);
		map.put("roleId", roleId);
		map.put("memberId", memberId);
		map.put("compId", compId);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr(
					"collectionNew/insertClerkAudit3/", map);

		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}

	@Override
	public String createCompDS(String compName, String compAddress, String memberName, String roleId, String latitude, String longitude, String memberId, String compId, String auditId, String phone, String agentType, String province, String city) {
		// TODO Auto-generated method stub
				map.clear();
				map.put("compName", compName);
				map.put("compAddress", compAddress);
				map.put("memberName", memberName);
				map.put("roleId", roleId);
				map.put("latitude", latitude);
				map.put("longitude", longitude);
				map.put("memberId", memberId);
				map.put("compId", compId);
				map.put("auditId", compId);
				map.put("phone", phone);
				map.put("agentType", agentType);
				map.put("province", province);
				map.put("city", city);
				String result = null;
				try {
					result = openApiHttpClient.doPassSendStr(
							"collectionNew/createCompDS/", map);

				} catch (Exception e) {
					return PubMethod.sysErrorUS();
				}
				logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>> 创建代收点 :createCompDS:"+result);
				return result;
	}
}

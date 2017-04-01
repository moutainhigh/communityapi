package net.okdi.apiV1.service.impl;

import net.okdi.apiV1.service.ExpressSiteService;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.PubMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExpressSiteServiceImpl implements ExpressSiteService {
    
	
	
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	/**
	 * 
	 * <dt><span class="strong">方法描述:</span></dt><dd>添加网点信息</dd>
	 * <dt><span class="strong">作者:</span></dt><dd>guoqiang.jing</dd>
	 * @param memberId 创建人ID
	 * @param netId 网络ID-快递公司id
	 * @param compTypeNum 网点类型 1006站点 1050营业分部 
	 * @param compName 网点名称
	 * @param compTelephone 网点电话-负责人电话
	 * @param longitude 精度
	 * @param latitude 纬度
	 * county  区县
	 * @param address 网点详细地址
	 * @return	 
	 * @since v1.0
	 */
	public String saveCompInfo(Long memberId, Long netId, String compTypeNum,
			String compName, String compTelephone, Double longitude, Double latitude,
			String county,String address) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("memberId",String.valueOf(memberId));
		map.put("netId",String.valueOf(netId));
		map.put("compTypeNum",compTypeNum);
		map.put("compName",compName);
		map.put("compTelephone",compTelephone);
		map.put("longitude",String.valueOf(longitude));
		map.put("latitude",String.valueOf(latitude));
		map.put("county",county);
		map.put("address",address);
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("expressSite/saveCompInfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
		
	}
	@Override
	public String queryCompVerifyInfo(Long compId) {
		
        Map<String, String> map = new HashMap<String, String>();
		map.put("loginCompId",String.valueOf(compId));//登录网点ID
		String result = null;
		try {
			result =openApiHttpClient.doPassSendStr("expressSite/queryCompInfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
		
	}
	@Override
	public String queryJoinState(String member_phone) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_phone",member_phone);//手机号13810362693
		String result = null;
		try {
			result= openApiHttpClient.doPassSendStr("expressSite/queryJoinState", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	 
		
		
	}
	@Override
	public String applyJoin(String member_id, String audit_comp,
			String application_role_type, String audit_item) {
		// TODO Auto-generated method stub
		
       Map<String, String> map = new HashMap<String, String>();
		
		map.put("member_id",member_id);//id 3435341
		map.put("audit_comp",audit_comp);  //5980
		map.put("application_role_type",application_role_type);
		map.put("audit_item",audit_item);
	
		String result = null;
		try {
			result= openApiHttpClient.doPassSendStr("expressSite/applyJoin", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	
	public String applyJoinNoSign(String member_id, String belongToNetId,String application_role_type,
			 String toMemberPhone) {
		// TODO Auto-generated method stub
		
       Map<String, String> map = new HashMap<String, String>();
		
		map.put("member_id",member_id);//id 3435341
		map.put("belongToNetId",belongToNetId);  //5980
		map.put("application_role_type",application_role_type);  //5980
		//map.put("fromMemberPhone",fromMemberPhone);
		map.put("toMemberPhone",toMemberPhone);
		//map.put("toRoleId",String.valueOf(toRoleId));
		
		String result = null;
		try {
			result= openApiHttpClient.doPassSendStr("expressSite/applyJoinNoSign", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	
	@Override
	public String invite(Long fromMemberId, String fromMemberPhone,
			  Integer fromMemberRoleid, String toMemberPhone, Integer toRoleId)  {
		
       Map<String, String> map = new HashMap<String, String>();
       map.put("fromMemberId",String.valueOf(fromMemberId));
		map.put("fromMemberPhone",fromMemberPhone);
		map.put("fromMemberRoleid",String.valueOf(fromMemberRoleid));
		map.put("toMemberPhone",toMemberPhone);
		map.put("toRoleId",String.valueOf(toRoleId));
		String result = null;
		try {
			result= openApiHttpClient.doPassSendStr("expressSite/invite", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String submitCompVerifyInfo(String loginCompId, String responsible,
			String responsibleTelephone, String responsibleNum,
			String frontImg, String businessLicenseImg, String shopImg,Long memberId) {
		
        Map<String, String> map = new HashMap<String, String>();
		
			map.put("loginCompId",loginCompId);//登录网点ID 3435104
			
			map.put("responsible",responsible);
			map.put("responsibleTelephone",responsibleTelephone);
			map.put("responsibleNum",responsibleNum);
			
			map.put("frontImg",frontImg);
			map.put("holdImg",businessLicenseImg);
			map.put("reverseImg",shopImg);
			map.put("memberId",String.valueOf(memberId));
			String result = null;
			try {
				result = openApiHttpClient.doPassSendStr("expressSite/submitCompVerifyInfo", map);
			} catch (Exception e) {
				return PubMethod.sysErrorUS();
			}
			return result;
	}
	@Override
	public String auditSite(String compId, String compStatus) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("compId",compId);//登录网点ID 3435104
		map.put("compStatus",compStatus); //1
		String result = null;
		try {
			result= openApiHttpClient.doPassSendStr("expressSite/auditSite", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String submitBasCompInfo(Long loginCompId, String compName,
			String belongToNetId, String county, Double longitude,
			Double latitude, String address, String responsibleTelephone,String member_id) {
		
	   Map<String, String> map = new HashMap<String, String>();
		
		map.put("loginCompId",String.valueOf(loginCompId));
		map.put("compName",String.valueOf(compName));
		map.put("belongToNetId",belongToNetId);
		map.put("longitude",String.valueOf(longitude));
		map.put("latitude",String.valueOf(latitude));
		map.put("county",county);
		map.put("address",address);
		map.put("responsibleTelephone",responsibleTelephone);
		map.put("member_id",member_id);
		
		String result = null;
		try {
			result = openApiHttpClient.doPassSendStr("expressSite/submitBasCompInfo", map);
		} catch (Exception e) {
			return PubMethod.sysErrorUS();
		}
		return result;
	}
	@Override
	public String queryMyMessage(Long memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId", memberId + "");//
		String result= openApiHttpClient.doPassSendStr("expressSite/queryMyMessage/v5", map);
		return result;
	}

	@Override
	public String queryMyMessage(String phone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("phone", phone);//
		String result= openApiHttpClient.doPassSendStr("expressSite/queryMyMessage", map);
		return result;
	}

	@Override
	public String deleleMyAllMessage(String memberPhone) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberPhone",memberPhone);//
		String result= openApiHttpClient.doPassSendStr("expressSite/deleteMyAllMessage", map);
		return result;
	}
	@Override
	public String deleleMyOneMessage(Long pushId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pushId",pushId.toString());//
		String result= openApiHttpClient.doPassSendStr("expressSite/deleteMyOneMessage", map);
		return result;
	}
	@Override
	public String readOneMessage(Long pushId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("pushId",pushId.toString());//
		String result= openApiHttpClient.doPassSendStr("expressSite/readOneMessage", map);
		return result;
	}

	@Override
	public String queryMessageDetails(Long id, String memberId) {
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("expressSite/queryMessageDetails", map);
	}
	@Override
	public String queryMessageNewest(String memberId) {		
		Map<String, String> map = new HashMap<String, String>();
		map.put("memberId",memberId);
		String result= openApiHttpClient.doPassSendStr("expressSite/queryMessageNewest", map);
		return result;
	}
	@Override
	public String addNoticeState(String Id, String memberId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Id",Id);
		map.put("memberId",memberId);
		String result= openApiHttpClient.doPassSendStr("expressSite/addNoticeState", map);
		return result;
	}

}

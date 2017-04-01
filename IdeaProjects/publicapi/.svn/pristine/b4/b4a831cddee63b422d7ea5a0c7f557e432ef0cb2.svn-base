/**  
 * @Project: publicapi
 * @Title: MemberCollectExpMemberServiceImpl.java
 * @Package net.okdi.mob.service.impl
 * @author amssy
 * @date 2015-1-19 上午09:48:15
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.core.base.BaseController;
import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.mob.entity.MemberCollectExpMember;
import net.okdi.mob.service.MemberCollectExpMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author amssy
 * @version V1.0
 */
@Service
public class MemberCollectExpMemberServiceImpl implements MemberCollectExpMemberService {
	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	/**
	 * @Method: addMemberCollectExpMember 
	 * @param memberCollectExpMember 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#addMemberCollectExpMember(net.okdi.mob.entity.MemberCollectExpMember) 
	 */
	@Override
	public String addMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember) {
       Map <String,Object> map = new HashMap<String,Object>();
       map.put("createUserId", memberCollectExpMember.getCreateUserId()+"");
       map.put("expMemberName", memberCollectExpMember.getExpMemberName()+"");
       map.put("expMemberPhone", memberCollectExpMember.getExpMemberPhone()+"");
       map.put("expMemberAddressId",memberCollectExpMember.getExpMemberAddressId()+"");
       map.put("expMemberDetaileDisplay", memberCollectExpMember.getExpMemberDetaileDisplay()+"");
       map.put("expMemberDetailedAddress", memberCollectExpMember.getExpMemberDetailedAddress()+"");
       map.put("netId", memberCollectExpMember.getNetId()+"");
       map.put("compId", memberCollectExpMember.getCompId()+"");
       map.put("remark", memberCollectExpMember.getRemark()+"");
       map.put("casMemberId", memberCollectExpMember.getCasMemberId()+"");
       String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/addMemberCollectExpMember", map);
       return result;
	}

	/**
	 * @Method: deleteMemberCollectExpMember 
	 * @param id 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#deleteMemberCollectExpMember(java.lang.Long) 
	 */
	@Override
	public String deleteMemberCollectExpMember(Long id) {
		 Map<String,Object> map = new HashMap<String,Object>();
		 map.put("MemberCollectExpMemberId", id);
		 String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/deleteMemberCollectExpMember", map);
         return result;
	}

	/**
	 * @Method: getMemberCollectExpMember 
	 * @param id
	 * @return 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#getMemberCollectExpMember(java.lang.Long) 
	 */
	@Override
	public String getMemberCollectExpMember(Long id,Double lng,Double lat) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("memberCollectExpMemberId", id);
        map.put("lng", lng);
        map.put("lat",lat);
        String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/getMemberCollectExpMember",map);
        return result;
	}

	/**
	 * @Method: getMemberCollectExpMemberList 
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#getMemberCollectExpMemberList(java.lang.Long) 
	 */
	@Override
	public String getMemberCollectExpMemberList(Long memberId) {
        Map<String,Object>map = new HashMap<String,Object>();
        map.put("memberId", memberId);
        String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/getMemberCollectExpMemberList",map);
		return result;
	}

	/**
	 * @Method: updateMemberCollectExpMember 
	 * @param memberCollectExpMember 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#updateMemberCollectExpMember(net.okdi.mob.entity.MemberCollectExpMember) 
	 */
	@Override
	public String updateMemberCollectExpMember(MemberCollectExpMember memberCollectExpMember) {
		 Map <String,Object> map = new HashMap<String,Object>();
	       map.put("id", memberCollectExpMember.getId()+"");
		   map.put("createUserId", memberCollectExpMember.getCreateUserId()+"");
	       map.put("expMemberName", memberCollectExpMember.getExpMemberName()+"");
	       map.put("expMemberPhone", memberCollectExpMember.getExpMemberPhone()+"");
	       map.put("expMemberAddressId",memberCollectExpMember.getExpMemberAddressId()+"");
	       map.put("expMemberDetaileDisplay", memberCollectExpMember.getExpMemberDetaileDisplay()+"");
	       map.put("expMemberDetailedAddress", memberCollectExpMember.getExpMemberDetailedAddress()+"");
	       map.put("netId", memberCollectExpMember.getNetId()+"");
	       map.put("compId", memberCollectExpMember.getCompId()+"");
	       map.put("remark", memberCollectExpMember.getRemark()+"");
	       map.put("casMemberId", memberCollectExpMember.getCasMemberId()+"");
	       String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/updateMemberCollectExpMember", map);
	       return result;
	}

	/**
	 * @Method: ifCollectionAndRegister 
	 * @param memberId
	 * @param memberPhone
	 * @return 
	 * @see net.okdi.mob.service.MemberCollectExpMemberService#ifCollectionAndRegister(java.lang.Long, java.lang.String) 
	*/
	@Override
	public String ifCollectionAndRegister(Long memberId, String memberPhone,Double lng,Double lat) {
		Map <String,Object>memberMap = new HashMap<String,Object>();
	       memberMap.put("expMemberPhone", memberPhone);
	       memberMap.put("lng", lng);
	       memberMap.put("lat", lat);
	       String memberResult = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/queryExpMemberInfo", memberMap);
	       JSONObject obj = JSON.parseObject(memberResult);

		Map <String,Object>map = new HashMap<String,Object>();
       map.put("memberId", memberId);
       map.put("expMemberPhone",memberPhone);
       String result = openApiHttpClient.doPassSendStr("memberCollectExpMemberController/ifCollection", map);
       JSONObject jobj = JSON.parseObject(result);
              Map <String,Object>resultMap = new HashMap<String,Object>();
       resultMap.put("isRegister", obj.get("data")==null?false:true);
       resultMap.put("isCollection", jobj.getJSONObject("data").get("flag"));
       resultMap.put("memberInfo", obj.get("data"));
       resultMap.put("id", jobj.getJSONObject("data").getString("id"));
       resultMap.put("distance", obj.getJSONObject("data")==null?"0":obj.getJSONObject("data").get("distance")==null?"0":obj.getJSONObject("data").get("distance"));
       System.out.println(jsonSuccess(resultMap));
       return jsonSuccess(resultMap);
	}
	
	protected String jsonSuccess(Object mapObject) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != mapObject) {
			allMap.put("data", mapObject);
		}
		String result = JSON.toJSONString(allMap,BaseController.s).replaceAll(":null", ":\"\"");
		return result;
	}

}

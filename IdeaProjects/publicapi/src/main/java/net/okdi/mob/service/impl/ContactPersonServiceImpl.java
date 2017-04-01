/**  
 * @Project: mob
 * @Title: ExpParGatewayService.java
 * @Package net.okdi.mob.service
 * @Description: TODO
 * @author chuanshi.chai
 * @date 2014-11-4 上午9:17:48
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.okdi.core.passport.OpenApiHttpClient;
import net.okdi.core.util.service.ConstPool;
import net.okdi.mob.service.CommonService;
import net.okdi.mob.service.ContactPersonService;

/**
 * @ClassName ExpParGatewayService
 * @Description TODO
 * @author chuanshi.chai
 * @date 2014-11-4
 * @since jdk1.6
*/
@Service("contactPersonService")
public class ContactPersonServiceImpl implements ContactPersonService{

	@Autowired
	private OpenApiHttpClient openApiHttpClient;

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ConstPool constPool;
	
	
	@Override
	public String getMemberGroupJDW(Long memberId) {
		Map<String, Object>  paraMeterMap  = new HashMap<String, Object>();
		paraMeterMap.put("memberId",memberId);
		return openApiHttpClient.doPassSendStr("contact/getMemberGroupJDW",paraMeterMap);
	}
	
	/**
	 * @Method: addGroupTag 
	 * @Description: TODO
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#addGroupTag(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String addGroupTag(Long memberId,String groupName) {
		Map map = new HashMap();
		map.put("memberId", memberId); 
		map.put("groupName", groupName); 
		return openApiHttpClient.doPassSendStr("contact/addGroup/", map);
	}
	
	/**
	 * @Method: getContactGroup 
	 * @Description: 获得联系人分组
	 * @param memberId 用户ID
	 * @return json
	 * @see net.okdi.api.service.ContactService#getContactGroup(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getContactGroup(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		//return openApiHttpClient.doPassSendStr("contact/getGroups/", map);
		Map m =  openApiHttpClient.doPassSendObj("contact/getGroups/", map);
		//{"data":{"groupList":[{"createTime":1416814650000,"groupId":5,"groupName":"朋友"},{"createTime":1416814846000,"groupId":6,"groupName":"同学"},{"createTime":1416814866000,"groupId":8,"groupName":"家人"}]},
		//"success":true}
		Map result = new HashMap();
		List listGroup = new ArrayList();
		if(m.get("success")!=null&&"true".equals(m.get("success").toString())){
			JSONObject jsonObj = JSON.parseObject(m.get("data").toString());
			JSONArray jsonArray = jsonObj.getJSONArray("groupList");
			for(int i = 0;i<jsonArray.size();i++){
				JSONObject subJson = JSON.parseObject(jsonArray.get(i).toString());
				String s = subJson.get("groupId").toString();
				Map paraMap = new HashMap();
				paraMap.put("memberId", memberId);
				paraMap.put("groupId", Long.parseLong(s));
				//System.out.println(s);
				Map resMap = openApiHttpClient.doPassSendObj("contact/getMemberContactList",paraMap);
				String totalCount = null;
				if(resMap.get("success")!=null && "true".equals(resMap.get("success").toString())){
					if("".equals(resMap.get("totalCount").toString().trim())){
						totalCount = "0";
					}else{
						totalCount = resMap.get("totalCount").toString();
					}
				}
				subJson.put("totalCount", totalCount);
				System.out.println(totalCount);
				System.out.println("subJson:"+subJson);
				listGroup.add(subJson);
			}
			result.put("groupList", listGroup);
		}
		m.put("data", result);
		System.out.println(result);
		System.out.println(m);
		return JSON.toJSONString(m);
	}

	@Override
	public String deleteContactGroup(Long memberId, Long tagId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("tagId", tagId);
		return openApiHttpClient.doPassSendStr("contact/deleteGroup/", map);
	}

	@Override
	public String addContactMob(Long memberId, String json, MultipartFile[] myfiles) {
//		String detaileDisplay = null;
//		String detailedAddress = null;
//		String zipCode = null;
//		Map getJson = JSONObject.parseObject(json);
//		try{
//			detaileDisplay=JSON.parseObject(JSONArray.parseArray(getJson.get("addr").toString()).get(0).toString()).get("detaileDisplay").toString();
//			detailedAddress=JSON.parseObject(JSONArray.parseArray(getJson.get("addr").toString()).get(0).toString()).get("detailedAddress").toString();
//		}finally{
//		}
//		getJson.put("defaultPhone", "");
//		getJson.put("defaultAddressId", "");
//		getJson.put("gender", "0");
//		getJson.put("birthday", "");
//		getJson.put("contactDetaileDisplay", detaileDisplay);
//		getJson.put("contactDetailedDddress", detailedAddress);
//		getJson.put("zipCode", "");
//		getJson.put("reMark", "");
		//getJson.put("nameAbbr", "");
		//getJson.put("contactName", "");
		//json = JSON.toJSONString(getJson);
		
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("json", json);
		//msg= "{\"addr\":[{\"addressId\":\"1100210\",\"detaileDisplay\":\"北京市海淀区城区\",\"detailedAddress\":\"田村路138号\",\"latitude\":\"120.121\",\"longitude\":\"120.121\",\"zipCode\":\"11111\"},{\"addressId\":\"1100210\",\"detaileDisplay\":\"北京市海淀区城区\",\"detailedAddress\":\"田村路138号\",\"latitude\":\"120.121\",\"longitude\":\"120.121\",\"zipCode\":\"11111\"},{\"addressId\":\"1100210\",\"detaileDisplay\":\"北京市海淀区城区\",\"detailedAddress\":\"田村路138号\",\"latitude\":\"120.121\",\"longitude\":\"120.121\",\"zipCode\":\"11111\"}],\"comm\":[{\"isDefulat\":\"0\",\"num\":\"13161340623\",\"tagId\":\"0\"},{\"isDefulat\":\"0\",\"num\":\"572354908@QQ.COM\",\"tagId\":\"1\"},{\"isDefulat\":\"0\",\"num\":\"572354908\",\"tagId\":\"2\"}],\"createUserId\":\"1314\",\"defaultPhone\":\"13161340623\",\"gender\":\"0\",\"groupTag\":\"123\",\"birthday\":\"123123123123\", \"contactDetaileDisplay\": \"北京市海淀区\",\"contactDetailedDddress\": \"田村路18号\",\"reMark\": \"备注\",\"nameAbbr\": \"zhangmengnan\",\"name\":\"张梦楠\"}";
		//{"addr":[{"addressId":"1100210","detaileDisplay":"北京市海淀区城区","detailedAddress":"田村路138号","latitude":"120.121","longitude":"120.121","zipCode":"11111"},{"addressId":"1100210","detaileDisplay":"北京市海淀区城区","detailedAddress":"田村路138号","latitude":"120.121","longitude":"120.121","zipCode":"11111"},{"addressId":"1100210","detaileDisplay":"北京市海淀区城区","detailedAddress":"田村路138号","latitude":"120.121","longitude":"120.121","zipCode":"11111"}],"comm":[{"isDefulat":"0","num":"13161340623","tagId":"0"},{"isDefulat":"0","num":"572354908@QQ.COM","tagId":"1"},{"isDefulat":"0","num":"572354908","tagId":"2"}],"createUserId":"1314","defaultPhone":"13161340623","gender":"0","groupTag":"123","birthday":"123123123123", "contactDetaileDisplay": "北京市海淀区","contactDetailedDddress": "田村路18号","reMark": "备注","nameAbbr": "zhangmengnan","name":"张梦楠"}
		//Map m = openApiHttpClient.doPassSendObj("contact/add/mob/", map);
		//{"id":"14522411593760768","success":true}
			//return openApiHttpClient.doPassSendStr("contact/add/mob/", map);
		Map resultMap =  openApiHttpClient.doPassSendObj("contact/add/mob/", map);
		if(myfiles !=null&&resultMap!=null&&resultMap.get("success")!=null&&"true".equals(resultMap.get("success").toString())){
		//Map resultMap = new HashMap();
			Long id = Long.parseLong(resultMap.get("id").toString());
			//Long id=3333333333L;
			//resultMap.put("id",id);
			commonService.uploadPic("contact",id,myfiles);
			resultMap.put("contactUrl", constPool.getReadPath()+constPool.getContact()+resultMap.get("id")+".jpg");
		}
		return JSON.toJSONString(resultMap);
	}

	@Override
	public String deleteMemberConcate(Long memberId, Long contactId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("ContactId", contactId);
		return openApiHttpClient.doPassSendStr("contact/deleteMemberContact/", map);
	}

	@Override
	public String getContactConmunicationTypeList(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("contact/getContactConmunicationTypeList/", map);
	}

	@Override
	public String addPhoneTagType(String tagName, Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("tagName", tagName);
		return openApiHttpClient.doPassSendStr("contact/addPhoneTagType/", map);
	}
	
	@Override
	public String deleteCommunicationTagType(Long tagId, Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("tagId", tagId);
		return openApiHttpClient.doPassSendStr("contact/deleteCommunicationTagType/", map);
	}

	@Override
	public String addContactToGroup(Long contactId, Long tagId) {
		Map map = new HashMap();
		map.put("contactId", contactId);
		map.put("tagId", tagId);
		return openApiHttpClient.doPassSendStr("contact/addContactToGroup/", map);
	}

	/**
	 * @Method: deleteContactGroupRela 
	 * @Description: TODO
	 * @param contactId
	 * @param groupId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#deleteContactGroupRela(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteContactGroupRela(Long contactId, Long groupId) {
		Map map = new HashMap();
		map.put("contactId", contactId);
		map.put("groupId", groupId);
		return openApiHttpClient.doPassSendStr("contact/deleteContactGroup/", map);
	}
	
	/**编辑联系人
	 * @Method: updateContactInfo 
	 * @Description: TODO
	 * @param contactId
	 * @param groupId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#deleteContactGroupRela(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String updateContactInfo(Long memberId,Long contactId,String msg ,MultipartFile[] myfiles){
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("contactId", contactId);
		map.put("msg", msg);
		//return openApiHttpClient.doPassSendStr("contact/updateContactInfo/", map);
		Map resultMap = openApiHttpClient.doPassSendObj("contact/updateContactInfo/", map);
		if(myfiles !=null&&myfiles.length != 0 &&resultMap!=null&&resultMap.get("success")!=null&&"true".equals(resultMap.get("success").toString())){
			commonService.uploadPic("contact",contactId,myfiles);
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * 修改分组名
	 * @Method: updateMemberGroupName 
	 * @Description: TODO
	 * @param memberId
	 * @param groupId
	 * @param newGroupName
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#updateMemberGroupName(java.lang.Long, java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	 */
	@Override
	public String updateMemberGroupName(Long memberId, Long groupId, String newGroupName) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("groupId", groupId);
		map.put("newGroupName", newGroupName);
		return openApiHttpClient.doPassSendStr("contact/updateMemberGroupName/", map);
	}

	@Override
	public String getContactMsg(Long contactId, Long memberId) {
		Map map = new HashMap();
		map.put("contactId", contactId);
		map.put("memberId", memberId);
		//return openApiHttpClient.doPassSendObj("contact/getContact", map);
		String str = openApiHttpClient.doPassSendStr("contact/getContact", map);
		JSONObject jso = JSONObject.parseObject(str);
		JSONObject finObj = jso.getJSONObject("data");
		JSONArray finJsonArray = new JSONArray();
		JSONArray addrArrs = finObj.getJSONArray("addr");
		for(int i = 0 ;i<addrArrs.size();i++){
			JSONObject myjso = addrArrs.getJSONObject(i);
			if(true){
				finJsonArray.add(myjso);
			}
		}
		finObj.put("addr", finJsonArray);
		String urlId = finObj.getJSONObject("memberContact").getString("id");
		finObj.getJSONObject("memberContact").put("contactUrl", constPool.getReadPath()+constPool.getContact()+urlId+".jpg");
		
		//{"data":[{"address_type_name":"常用地址","id":14186057666397184},{"address_type_name":"办公","id":14186057715680256},{"address_type_name":"家庭","id":14186057715680258},{"address_type_name":"其他","id":14186057719612416},{"address_type_name":"未分类","id":14186057719612418}],"success":true}
		String addTagStr = getContactConmunicationTypeList(memberId);
		System.out.println(addTagStr);
		JSONObject addTagObj = JSONObject.parseObject(addTagStr);
		Map<String,String> tagMap = new HashMap<String,String>();
		JSONArray myJsa = addTagObj.getJSONObject("data").getJSONArray("phone");
		for(int i = 0 ;i< myJsa.size();i++){
			JSONObject tempJsonObj = myJsa.getJSONObject(i);
			tagMap.put(tempJsonObj.getString("id"),tempJsonObj.getString("customName"));
		}
		//JSONArray jsong = new JSONArray();
		JSONArray addrArray = finObj.getJSONArray("addr");
		for(int i=0;i<addrArray.size();i++){
			JSONObject tagObj = addrArray.getJSONObject(i);
			String tagId = tagObj.getString("addressTagId");
			tagObj.put("addressTagName", tagMap.get(tagId));
			//tagObj.put("addressTagName", "sdafff");
		}
		finObj.put("addr", addrArray);
		//System.out.println(addTagStr);
		
		return JSON.toJSONString(jso);
	}

	@Override
	public String getMemberContactList(Long memberId, String groupId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("groupId", groupId);
		//return openApiHttpClient.doPassSendStr("contact/getMemberContactList",map);
		Map resMap = openApiHttpClient.doPassSendObj("contact/getMemberContactList",map);
		resMap.put("contactUrl", constPool.getReadPath()+constPool.getContact());
		return JSON.toJSONString(resMap);
	}

	/**
	 * @Method: getAddressTypeList 
	 * @Description: TODO
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#getAddressTypeList(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getAddressTypeList(Long memberId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("contact/getAddressTypeList",map);
	}

	@Override
	public String getGroups(Long memberId){
		Map map = new HashMap();
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("contact/getGroups",map);
	}

	@Override
	public String updateCommTagName(Long memberId, String tagName, Long tagId) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("tagName", tagName);
		map.put("tagId", tagId);
		return openApiHttpClient.doPassSendStr("contact/updateCommTagName",map);
	}
	@Override
	public String queryAllContact(Long memberId){
		Map map = new HashMap();
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("contact/queryAllContact",map);
	}

	/**
	 * @Method: moveContactAsWx 
	 * @param contactIds
	 * @param groupId
	 * @param memberId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#moveContactAsWx(java.lang.String, java.lang.Long, java.lang.Long) 
	*/
	@Override
	public String moveContactAsWx(String contactIds, Long groupId, Long memberId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contactIds", contactIds);
		map.put("groupId", groupId);
		map.put("memberId", memberId);
		return openApiHttpClient.doPassSendStr("contact/moveContactAsWx",map);
	}
	
	@Override
	public String moveContact(Long memberId,Long tagId,Long contactId){
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("tagId", tagId);
		map.put("contactId", contactId);
		return openApiHttpClient.doPassSendStr("contact/moveContact",map);
	}

	/**
	 * @Method: judgeHasAlreadyAdd 
	 * @param memberId
	 * @param json
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#judgeHasAlreadyAdd(java.lang.Long, java.lang.String) 
	*/
	@Override
	public String judgeHasAlreadyAdd(Long memberId, String json) {
		Map map = new HashMap();
		map.put("memberId", memberId);
		map.put("json", json);
		return openApiHttpClient.doPassSendStr("contact/judgeHasAlreadyAdd",map);
	}

	/**
	 * @Method: getContactAddressInfo 
	 * @param contactId
	 * @return 
	 * @see net.okdi.mob.service.ContactPersonService#getContactAddressInfo(java.lang.Long) 
	*/
	@Override
	public String getContactAddressInfo(Long contactId) {
		Map map = new HashMap();
		map.put("contactId", contactId);
		return openApiHttpClient.doPassSendStr("contact/getContactAddressInfo",map);

	}
}

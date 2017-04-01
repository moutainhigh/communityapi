/**  
 * @Project: openapi
 * @Title: ContactServiceImpl.java
 * @Package net.okdi.api.service.impl
 * @Description: TODO
 * @author mengnan.zhang
 * @date 2014-10-27 下午03:35:51
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.api.dao.MemberAddressTypeTagInfoMapper;
import net.okdi.api.dao.MemberCommTypeTagInfoMapper;
import net.okdi.api.dao.MemberContactAddrMapper;
import net.okdi.api.dao.MemberContactCommMapper;
import net.okdi.api.dao.MemberContactMapper;
import net.okdi.api.dao.MemberContactTagRelationMapper;
import net.okdi.api.dao.MemberInfoMapper;
import net.okdi.api.dao.MemberTagMapper;
import net.okdi.api.entity.MemberAddressTypeTagInfo;
import net.okdi.api.entity.MemberCommTypeTagInfo;
import net.okdi.api.entity.MemberContact;
import net.okdi.api.entity.MemberContactTagRelationKey;
import net.okdi.api.entity.MemberTag;
import net.okdi.api.service.ContactService;
import net.okdi.api.vo.PhoneContactVO;
import net.okdi.core.common.ehcache.EhcacheService;
import net.okdi.core.util.PiyinUtil;
import net.okdi.core.util.PubMethod;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.amssy.common.util.primarykey.IdWorker;

/**
 * @ClassName ContactServiceImpl
 * @Description 
 * @author mengnan.zhang
 * @date 2014-10-27
 * @since jdk1.6
 */
@SuppressWarnings("unchecked")
@Service
public class ContactServiceImpl implements ContactService {
	//用户持有分组标签模型操作类
	@Autowired
	public MemberTagMapper memberTagMapper;
	//用户持有通讯标签类
	@Autowired
	public MemberCommTypeTagInfoMapper memberCommTypeTagInfoMapper;
	//用户持有地址标签
	@Autowired
	public MemberAddressTypeTagInfoMapper memberAddressTypeTagInfoMapper;
	//联系人基本信息Mapper
	@Autowired
	public MemberContactMapper memberContactMapper;
	//联系人分组关系吧
	@Autowired
	public MemberContactTagRelationMapper memberContactTagRelationMapper;
	//联系人持有地址信息
	@Autowired
	public MemberContactAddrMapper memberContactAddrMapper;
	//联系人通讯信息Mapper
	@Autowired
	public MemberContactCommMapper memberContactCommMapper;
	//缓存service
	@Autowired
	public EhcacheService ehcacheService;
	//用户信息
	public MemberInfoMapper memberInfoMapper;
	
	 private static SerializerFeature[] features = {SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect};  
	/**
	 * @Method: addAddressGroup 
	 * @Description: 添加地址分组标签
	 * @param tagName
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#addAddressGroup(java.lang.String, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String addAddressGroup(String tagName, Long memberId){
		//TODO
		ehcacheService.removeAll("contactInfoCache");
		ehcacheService.removeAll("contactAddressList");
		Long id = IdWorker.getIdWorker().nextId();
		memberAddressTypeTagInfoMapper.insert(new MemberAddressTypeTagInfo(id, memberId, tagName));
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("id", id);
		return JSON.toJSONString(map,features);
	}

	/**
	 * @Method: addContact 
	 * @Description: 添加联系人
	 * @param memberId 操作人ID
	 * @param msg
	 * @return 
	 * @see net.okdi.api.service.ContactService#addContact(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String addContact(Long memberId, String msg,Long contactIdE) {
	//TODO
    ehcacheService.removeAll("contactListCache");
	String contactId = this.createContact(memberId, msg,contactIdE);
	List list = this.createaddContactAddressInfo(Long.parseLong(contactId), msg,"addr");
	if(list!=null&&list.size()>0){
	this.addContactAddressInfo(list);
	}
	this.addCommInfo(this.createaddContactAddressInfo(Long.parseLong(contactId), msg,"comm"));
	String groupIds = JSON.parseObject(msg).getString("groupIds");
	if(groupIds!=null&&!"".equals(groupIds)){
	String [] groupId = groupIds.split(",");
	for (int i = 0;i<groupId.length;i++){
		Long group = Long.parseLong(groupId[i]);
		this.addContactToGroup(memberId, Long.parseLong(contactId),group);
	}
	}
	Map <String,Object>map = new HashMap<String,Object>();
	map.put("success", true);
	map.put("id", contactId);
	return JSON.toJSONString(map,features);
	}

    /**
	 * @Method: addContactBaseMsg 
	 * @Description: 添加联系人基本信息
	 * @param createUserId
	 * @param contactName
	 * @param gender
	 * @param contactPhone
	 * @param contactAddressId
	 * @param contactDetaileDisplay
	 * @param contactDetailedDddress
	 * @param brithday
	 * @param remark 
	 * @param casMemberId
	 * @param erpCustomerId
	 * @return json
	 * @see net.okdi.api.service.ContactService#addContactBaseMsg(java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@SuppressWarnings("deprecation")
	@Override
	
	public String addContactBaseMsg(Long createUserId, String contactName,
			short gender, String contactPhone, Long contactAddressId,
			String contactDetaileDisplay, String contactDetailedDddress,
			String birthday, String remark, Long casMemberId, Long erpCustomerId,String nameAbbr,Long contactId) {
		//TODO
		ehcacheService.removeAll("contactListCache");
		Long id = contactId;
		if(id == null||id==0){
        id = IdWorker.getIdWorker().nextId();
		}
		Date birthdayTure = null;
		if(birthday!=null&&!birthday.equals("")&&!birthday.equals("0")){
			birthdayTure = new Date(birthday);
			}
		else{
			birthdayTure = new Date();
			}
		MemberContact memberContact = new MemberContact(
				id, 
				createUserId, 
				contactName, 
				gender, 
				contactPhone, 
				contactAddressId, 
				contactDetaileDisplay, 
				contactDetailedDddress, 
				(short)0, nameAbbr, 
				new Date(), 
				birthdayTure, 
				remark, 
				casMemberId, 
				erpCustomerId);
		memberContactMapper.insert(memberContact);
//		Long addressId = IdWorker.getIdWorker().nextId();
//		Map <String,Object>contactAdderessInfo = new HashMap<String,Object>();
//		contactAdderessInfo.put("id", addressId);
//		contactAdderessInfo.put("contactId", id);
//		contactAdderessInfo.put("addressTagId", 1);
//		contactAdderessInfo.put("addressId", contactAddressId);
//		contactAdderessInfo.put("detailedAddress",contactDetailedDddress);
//		contactAdderessInfo.put("detaileDisplay",contactDetaileDisplay );
//		contactAdderessInfo.put("longitude", null);
//		contactAdderessInfo.put("latitude", null);
//		contactAdderessInfo.put("zipCode", null);
//		contactAdderessInfo.put("isDefault", 0);
//		contactAdderessInfo.put("createTime", new Date());
//		List<Map<String,Object>>list = new ArrayList<Map<String,Object>>();
//		list.add(contactAdderessInfo);
//		 this.addContactAddressInfo(list);
		return id+"";
	}
	                                        
	
	/**
	 * @Method: addContactCommunicationTag 
	 * @Description: 用户添加联系人通讯方式标签
	 * @param tagName
	 * @param memberId
	 * @param tagType
	 * @return json
	 * @see net.okdi.api.service.ContactService#addContactCommunicationTag(java.lang.String, java.lang.Long, java.lang.Integer) 
	 * @since jdk1.6
	*/
	@Override
	public String addContactCommunicationTag(String tagName, Long memberId,
			Integer tagType,Short dataType) {
		//TODO
		ehcacheService.removeAll("contactCommList");
		Long id = IdWorker.getIdWorker().nextId();
		MemberCommTypeTagInfo memberCommTypeTagInfo = new MemberCommTypeTagInfo(id, memberId, dataType, tagName, tagType);
		memberCommTypeTagInfoMapper.insert(memberCommTypeTagInfo);
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("id",id );
		return JSON.toJSONString(map,features);
	}

	/**
	 * @Method: addGroupTag 
     * @Description: 用户添自己加联系人分组标签
     * @param memberId 操作用户memberid
     * @param groupName 新添加的组信息
     * @return json 
	 * @see net.okdi.api.service.ContactService#addGroupTag(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String addGroupTag(Long memberId, String groupName) {
		//TODO
		ehcacheService.remove("contactGroupCache", memberId.toString());
		Long id = IdWorker.getIdWorker().nextId();
	    MemberTag memberTag = new MemberTag(id, groupName, memberId, new Date(), null);
	    memberTagMapper.insert(memberTag);
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("id", id);
	    return jsonSuccess(map);
	}

	/**
	 * @Method: deleteAddressGroup 
	 * @Description: 
	 * @param tagId
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteAddressGroup(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteAddressGroup(Long tagId, Long memberId) {
		//TODO
		ehcacheService.removeAll("contactInfoCache");
//		this.deleteAddressInfoByTagId(tagId, null);
		memberContactAddrMapper.updateMemberContactAddressTag(tagId);
		MemberAddressTypeTagInfo memberAddressTypeTagInfo = new MemberAddressTypeTagInfo(tagId, memberId, "");
		memberAddressTypeTagInfoMapper.deleteByPrimaryKey(memberAddressTypeTagInfo);
		return jsonSuccess(null);
	}

	/**
	 * @Method: deleteCommunicationTag 
	 * @Description: 
	 * @param tagId
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteCommunicationTag(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteCommunicationTag(Long tagId, Long memberId) {
		ehcacheService.removeAll("contactCommList");
		ehcacheService.removeAll("contactInfoCache");
		memberContactCommMapper.updateCommIdByTagId(tagId);
//		deleteCommByTagId(tagId);
		MemberCommTypeTagInfo memberCommTypeTagInfo = new MemberCommTypeTagInfo(tagId, memberId,null, null, null);
		memberCommTypeTagInfoMapper.delTag(memberCommTypeTagInfo);
		this.deleteAddressGroup(tagId, memberId);
		return jsonSuccess(null);
	}

	/**
	 * @Method: deleteContactGroup 
	 * @Description: 删除联系人分组
	 * @param memberId
	 * @param tagId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteContactGroup(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteContactGroup(Long memberId, Long tagId) {
		//TODO
		ehcacheService.remove("contactGroupCache",memberId.toString());
		ehcacheService.removeAll("contactInfoCache");
		MemberTag memberTag = new MemberTag(tagId, null, memberId, null, null);
		memberContactTagRelationMapper.deleteContactGroupRela(tagId);
		memberTagMapper.deleteByPrimaryKey(memberTag);
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("success", true);
	    return JSON.toJSONString(map,features);
	}

	/**
	 * @Method: getAddressGroup 
	 * @Description:获得地址标签
	 * @param memberId hdw 好递网信息标签
	 * @return json
	 * @see net.okdi.api.service.ContactService#getAddressGroup(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getAddressGroup(Long memberId) {
		//TODO
		StringBuffer result = new StringBuffer("");
		if(this.getFromCache("contactAddressList", result, memberId+"hdw")){return result.toString();};
		MemberAddressTypeTagInfo memberAddressTypeTagInfo = new MemberAddressTypeTagInfo(null, memberId, null);
		List<Map<String,Object>>list = memberAddressTypeTagInfoMapper.getAddressTagByMemberId(memberAddressTypeTagInfo);
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("success",true);
		map.put("data", list);
		ehcacheService.put("contactAddressList", memberId+"hdw", JSON.toJSONString(map,features));
		return JSON.toJSONString(map,features);
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
		//TODO
		StringBuffer result = new StringBuffer("");
		if(this.getFromCache("contactGroupCache", result, memberId.toString())){return result.toString();};
		List<Map<String,Object>>list = memberTagMapper.getMemberTagByMemberId(memberId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupList",list);
		ehcacheService.put("contactGroupCache", memberId.toString(), jsonSuccess(map));
		return jsonSuccess(map);
	}

	/**
	 * @Method: deleteMemberConcate 
	 * @Description: 删除联系人信息
	 * @param memberId
	 * @param ContactId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteMemberConcate(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteMemberConcate(Long memberId, Long contactId) {
		//TODO
		ehcacheService.removeAll("contactGroupCache");
		ehcacheService.removeAll("contactListCache");
		ehcacheService.remove("contactInfoCache",memberId+"_"+contactId+"");
		this.deleteAddressInfoByTagId(null, contactId);
		this.deleteMemberContactComm(null, contactId);
		this.deleteContactGroupRela(contactId, null);
		this.deleteMemberContactBaseInfo( contactId);
		return jsonSuccess(null);
	}

	/**
	 * @Method: getContactConmunicationTypeList 
	 * @Description: 获得用户持有所有联系人通讯类型标签集合
	 * @param memberId
	 * @return json
	 * @see net.okdi.api.service.ContactService#getContactConmunicationTypeList(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getContactConmunicationTypeList(Long memberId) {
       //TODO
		StringBuffer result = new StringBuffer("");
		String key = memberId+"";
		if(this.getFromCache("contactCommList", result, key)){return result.toString();};

	   List<MemberCommTypeTagInfo> list =  memberCommTypeTagInfoMapper.getMemberCommtypeByMemberId(memberId);
	   List<MemberCommTypeTagInfo> phoneList = new ArrayList<MemberCommTypeTagInfo>();
	   List<MemberCommTypeTagInfo> emailList = new ArrayList<MemberCommTypeTagInfo>();
	   List<MemberCommTypeTagInfo> soList = new ArrayList<MemberCommTypeTagInfo>();
       for(MemberCommTypeTagInfo mem:list){
	    switch (mem.getDataType()) {
		case 0:
			phoneList.add(mem);
			break;
		case 1:
			emailList.add(mem);
			break;
		case 2:
			soList.add(mem);
			break;	
		default:
			break;
		}
       }
       Map <String,Object>map = new HashMap<String,Object>();
       map.put("phone", phoneList);
       map.put("email", emailList);
       map.put("so",soList);
       Map<String,Object>data = new HashMap<String,Object>();
       data.put("succes", true);
       data.put("data", map);
       ehcacheService.put("contactCommList", key, JSON.toJSONString(data,features));
       return JSON.toJSONString(data,features);
	}

	/**
	 * @Method: getContactMsg 
	 * @Description: 获得联系人信息
	 * @param contactId
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#getContactMsg(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getContactMsg(Long contactId, Long memberId) {
		//TODO
		StringBuffer result = new StringBuffer("");
		if(this.getFromCache("contactInfoCache", result, memberId+"_"+contactId)){return result.toString();};
		MemberContact memberContact = this.getContactBaseInfo(contactId);
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>>list = memberContactTagRelationMapper.getGroupNameByContactId(contactId);
        map.put("memberContact", memberContact);
        map.put("addr", this.getContactAddressInfo(contactId));
	    map.put("comm",  this.getContactCommInfo(contactId));
	    map.put("groupList", list);
	    data.put("success", true);
	    data.put("data", map);
	    ehcacheService.put("contactInfoCache", memberId+"_"+contactId,JSON.toJSONString(data,features));
       return JSON.toJSONString(data,features);
	}

	/**
	 * @Method: getMemberContactList 
	 * @Description: 不分页查询联系人列表
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#getMemberContactList(java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String getMemberContactList(Long memberId,String groupId) {
		//TODO
        return this.getMemberContactListByPage(memberId, null, null,false,groupId);
		
	}

	/**
	 * @Method: getMemberContactListByPage 
	 * @Description: 分页查询查询联系人
	 * @param memberId
	 * @param pageSize
	 * @param CurrentPage
	 * @return 
	 * @see net.okdi.api.service.ContactService#getMemberContactListByPage(java.lang.Long, java.lang.Integer, java.lang.Integer) 
	 * @since jdk1.6
	*/
	@Override
	public String getMemberContactListByPage(Long memberId, Integer pageSize,
			Integer currentPage,boolean byPage,String groupId) {
		//TODO
		StringBuffer result = new StringBuffer("");
		String key = memberId+"_"+pageSize+"_"+currentPage+"_"+byPage+"_"+groupId;
		if(this.getFromCache("contactListCache", result, key)){return result.toString();};
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberId", memberId);;
		map.put("startNum",null);
		map.put("pageSize",null);
		map.put("groupId", "0".equals(groupId)||"".equals(groupId)?null:groupId);
		if(byPage){
			if(pageSize == null){
				pageSize = 10;
			}
		map.put("startNum", pageSize*(currentPage-1));
		map.put("pageSize", pageSize);
		}
		List <Map<String, Object>> list = memberContactMapper.getMemberContactListByPage(map);
		Map <String,Object>count = new HashMap<String,Object>();
		count.put("memberId", memberId);
		count.put("startNum", null);
		count.put("pageSize", null);
		count.put("groupId", "0".equals(groupId)||"".equals(groupId)?null:groupId);
		List <Map<String,Object>>counts = memberContactMapper.getMemberContactListByPage(count);
		map.clear();
		map.put("success", true);
		map.put("data", list);
		map.put("totalCount",counts.size());
		ehcacheService.put("contactListCache", key, JSON.toJSONString(map));
		String oldValue = ehcacheService.get("contactListKeyCache", memberId+"", String.class);
		if(PubMethod.isEmpty(oldValue)){ehcacheService.put("contactListKeyCache", memberId+"", key);}else{
		ehcacheService.put("contactListKeyCache", memberId+"", oldValue+","+key);
		}
		return JSON.toJSONString(map);
	}

	/**
	 * @Method: moveContact 
	 * @Description: 修改联系人分组
	 * @param memberId
	 * @param tagId
	 * @param contactId
	 * @return 
	 * @see net.okdi.api.service.ContactService#moveContact(java.lang.Long, java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String moveContact(Long memberId, Long tagId, Long contactId) {
		//TODO
		ehcacheService.removeAll("contactGroupCache");
		ehcacheService.remove("contactInfoCache","_"+contactId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tagId", tagId);
		map.put("contactId", contactId);
		map.put("createUserId", memberId);
		memberContactTagRelationMapper.updateRelation(map);
		return jsonSuccess(map);
	}

	/**
	 * @Method: addContactAddressInfo 
	 * @Description: 批量添加联系人地址信息
	 * @param list
	 * @return 
	 * @see net.okdi.api.service.ContactService#addContactAddressInfo(java.util.List) 
	 * @since jdk1.6
	*/
	@Override
	public String addContactAddressInfo(List<Map<String,Object>> list) {
		memberContactAddrMapper.batchAddAddressInfo(list);
	    return jsonSuccess(null);
	}

	/**
	 * 
	 * @Method: deleteAddressInfoByTagId 
	 * @Description: 删除联系人指定标签地址信息/删除指定标签所有地址信息
	 *               删除联系人地址信息时传双参数，用户删除标签时先删除所有引用标签的地址信息传单参数
	 * @param tagId
	 * @param contactId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteAddressInfoByTagId(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	 */
	@Override
	public String deleteAddressInfoByTagId(Long tagId,Long contactId) {
		//TODO
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tagId", tagId);
		map.put("contactId", contactId);
		memberContactAddrMapper.deleteAddressInfoByTagId(map);
	    return jsonSuccess(map);
	}

	/**
	 * @Method: addCommInfo 
	 * @Description: 批量添加联系人通讯信息
	 * @param list
	 * @return 
	 * @see net.okdi.api.service.ContactService#addCommInfo(java.util.List) 
	 * @since jdk1.6
	*/
	@Override
	public String addCommInfo(List<Map<String, Object>> list) {
		//TODO
		try{
		memberContactCommMapper.batchAddMemberContactComm(list);
		}catch (Exception e) {
		e.printStackTrace();
		}
		return JSON.toJSONString(new HashMap<String,Object>().put("success", true));
	}

	/**
	 * @Method: deleteMemberContactComm 
	 * @Description: 删除联系人通讯信息
	 * @param map 
	 * @see net.okdi.api.service.ContactService#deleteMemberContactComm(java.util.Map) 
	 * @since jdk1.6
	*/
	@Override
	public void deleteMemberContactComm(Long tagId,Long contactId) {
		//TODO
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tagId", tagId);
		map.put("contactId",contactId);
		memberContactCommMapper.deleteMemberContactComm(map);	
	}
    /**
     * @Method: createContact 
     * @Description: 添加默认地址封装类
     * @param memberId
     * @param contactInfo
     * @return
     * @author mengnan.zhang
     * @date 2014-10-30 下午01:33:24
     * @since jdk1.6
     */
	public String createContact(Long memberId,String contactInfo,Long contactIdE){
		JSONObject text = JSON.parseObject(contactInfo);
		String contactName = text.get("contactName").toString();
		short gender = Short.parseShort(text.getString("gender")==null?"0":text.getString("gender"));//Edit ccs
		String contactPhone = text.getString("defaultPhone");
		Long contactAddressId = text.getLong("defaultAddressId");
		String contactDetaileDisplay = text.getString("contactDetaileDisplay");
		String contactDetailedDddress = text.getString("contactDetailedDddress");
		String birthday = text.getString("birthday");
		String remark = text.getString("reMark");
		Long casMemberId = this.ifRegister(contactPhone);
		Long erpCustomerId = text.getLong("erpCustomerId")==null?0:text.getLong("erpCustomerId");
		String nameAbbr = text.getString("nameAbbr");
		return this.addContactBaseMsg(memberId, contactName, gender, contactPhone, contactAddressId, contactDetaileDisplay, contactDetailedDddress, birthday, remark, casMemberId, erpCustomerId, nameAbbr,contactIdE);
	}
	/**
	 * @Method: createaddContactAddressInfo 
	 * @Description: 鏋勯�娣诲姞鑱旂郴浜洪�璁湴鍧�柟娉曞弬鏁版柟娉�
	 * @param contactId
	 * @param contactInfo
	 * @param key
	 * @return
	 * @author mengnan.zhang
	 * @date 2014-10-30 涓嬪崍02:53:16
	 * @since jdk1.6
	 */
    public List<Map<String, Object>> createaddContactAddressInfo(Long contactId,String contactInfo,String key){
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	JSONObject  obj = JSON.parseObject(contactInfo);
    	JSONArray array = obj.getJSONArray(key);
    	for(int i = 0;i<array.size();i++){
    		Map<String, Object> map = array.getJSONObject(i);
    		map.put("id", IdWorker.getIdWorker().nextId());
     		map.put("createTime",new Date());
     		map.put("contactId", contactId);
     		if("comm".equals(key)&&map.get("num")==null){
     			map.put("num", " ");
     		}
//     		if("addr".equals(key)){
//     			map.put("isDefault", map.get("is"));
//     		}
    		list.add(map);
    	}
    	return list;
    }
    /**
     * @Method: deleteMemberContactBaseInfo 
     * @Description: 删除联系人基本信息
     * @param id
     * @author mengnan.zhang
     * @date 2014-10-30 下午03:14:03
     * @since jdk1.6
     */
    public void deleteMemberContactBaseInfo(Long id ){
    	memberContactMapper.deleteByPrimaryKey(id);
    }
    /**
     * @Method: getContactCommInfo 
     * @Description: 获得联系人联系方式信息
     * @param memberId
     * @return
     * @author mengnan.zhang
     * @date 2014-10-30 下午03:22:02
     * @since jdk1.6
     */
    public List<Map<String,Object>> getContactCommInfo(Long memberId){
    return memberContactCommMapper.getContactCommInfo(memberId);
    }
	/**
	 * 
	 * @Method: getContactAddressInfo 
	 * @Description: 获得联系人所有地址信息
	 * @param contactId
	 * @return
	 * @author mengnan.zhang
	 * @date 2014-10-30 下午03:48:39
	 * @since jdk1.6
	 */
    public List<Map<String,Object>> getContactAddressInfo(Long contactId){
    	return memberContactAddrMapper.getContactAddressInfo(contactId);
    }
    /**
     * @Method: getContactBaseInfo 
     * @Description: 获得联系人的基本信息
     * @param contactId
     * @return
     * @author mengnan.zhang
     * @date 2014-11-3 下午03:25:51
     * @since jdk1.6
     */
    public MemberContact getContactBaseInfo(Long contactId){
    	return memberContactMapper.getMemberContactInfo(contactId);
    }
    /**
     * @Method: deleteContactGroupRela 
     * @Description: 删除联系人与组关联关系
     * @param tagId
     * @author mengnan.zhang
     * @date 2014-11-3 下午03:29:35
     * @since jdk1.6
     */
    public void deleteContactGroupRela(Long tagId){
    	//TODO
    	memberContactTagRelationMapper.deleteContactGroupRela(tagId);
    }
    /**
     * @Method: addContactGroup 
     * @Description: 添加联系人到某个分组
     * @param memberId
     * @param tagId
     * @return
     * @author mengnan.zhang
     * @date 2014-11-4 上午11:10:06
     * @since jdk1.6
     */
    public String addContactToGroup(Long memberId,Long contactId,Long tagId){
    	//TODO
    	String keys = ehcacheService.get("contactListKeyCache", memberId+"", String.class);
    	this.removeAll("contactListCache",keys);
    	
    	ehcacheService.remove("contactInfoCache", memberId+"_"+contactId);
    	MemberContactTagRelationKey record = new MemberContactTagRelationKey();
    	record.setContactId(contactId);
    	record.setTagId(tagId);
    	memberContactTagRelationMapper.insert(record);
    	Map <String,Object>map = new HashMap<String,Object>();
    	return jsonSuccess(map);
    }
   
    
    private String jsonSuccess(Object map) {
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("success", true);
		if (null != map) {
			allMap.put("data", map);
		}
		return JSON.toJSONString(allMap);
	}

	/**
	 * @Method: deleteContactGroupRela 
	 * @Description: 删除联系人和分组关系
	 * @param contactId
	 * @param groupId
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteContactGroupRela(java.lang.Long, java.lang.Long) 
	 * @since jdk1.6
	*/
	@Override
	public String deleteContactGroupRela(Long contactId, Long groupId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("contactId", contactId);
		map.put("tagId", groupId);
		memberContactTagRelationMapper.delete(map);
		return jsonSuccess(map);
	}

	/**
	 * @Method: updateMemberGroupName 
	 * @Description: 用户修改持有分组名称
	 * @param memberId
	 * @param groupId
	 * @param newGroupName
	 * @return 
	 * @see net.okdi.api.service.ContactService#updateMemberGroupName(java.lang.Long, java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String updateMemberGroupName(Long memberId, Long groupId,
		String newGroupName) {
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("tagId", groupId);
		map.put("tagName", newGroupName);
		memberTagMapper.updateMemberGroupName(map);
		return jsonSuccess(map);
	}

	/**
	 * @Method: updateContactInfo 
	 * @Description: 
	 * @param contactId
	 * @param msg
	 * @return 
	 * @see net.okdi.api.service.ContactService#updateContactInfo(java.lang.Long, java.lang.String) 
	 * @since jdk1.6
	*/
	@Override
	public String updateContactInfo(Long memberId,Long contactId, String msg) {
        this.deleteMemberConcate(memberId, contactId);
		String result = this.addContact(memberId, msg,contactId);
		return result;
	}

	/**
	 * @Method: deleteMemberContactBatch 
	 * @param memberId
	 * @param ContactIds
	 * @return 
	 * @see net.okdi.api.service.ContactService#deleteMemberContactBatch(java.lang.Long, java.lang.String) 
	*/
	@Override
	public String deleteMemberContactBatch(Long memberId, String ContactIds) {
		String [] contactIds = ContactIds.split(",");
		for (int i = 0;i<contactIds.length;i++){
			Long contactId = Long.parseLong(contactIds[i]);
			this.deleteMemberConcate(memberId, contactId);
		}
		return jsonSuccess(null);
	}

	/**
	 * @Method: getMemberGroupJDW 
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#getMemberGroupJDW(java.lang.Long) 
	*/
	@Override
	public String getMemberGroupJDW(Long memberId) {
		StringBuffer result = new StringBuffer();
		if(this.getFromCache("", result, "JDW"+memberId)){return result.toString();};
		List <Map <String,Object>>list = memberTagMapper.getMemberGroupJDW(memberId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("groupList",list);
		return jsonSuccess(map);
	}

	/**
	 * @Method: ifExitJDW 
	 * @param memberId
	 * @param contactName
	 * @return 
	 * @see net.okdi.api.service.ContactService#ifExitJDW(java.lang.Long, java.lang.Long) 
	*/
	@Override
	public String ifExitJDW(Long memberId, String contactName) {
		Map <String,Object>map = new HashMap<String,Object>();
		map.put("memberId", memberId);
		map.put("contactName", contactName);
		List<Map<String,Object>>list = memberContactMapper.ifExist(map);
		map.clear();
		if(list.size()>0){
		map.put("isExist", true);
		}else{map.put("ifExist",false );}
		return jsonSuccess(map);
	}
    
	private boolean getFromCache(String cacheName,StringBuffer result,String key){
		String JSONString = ehcacheService.get(cacheName, key,String.class);
		if(JSONString!=null&&!JSONString.equals("null")){
		result.append(JSONString);
		}
		return !PubMethod.isEmpty(JSONString);
	}
	
	private void removeAll(String cacheName,String keys){
		if(keys!=null){
		String [] allKey = keys.split(",");
	    for (int i = 0;i<allKey.length;i++){
	    	ehcacheService.remove(cacheName, allKey[i]);
	    }
		}
	}

	/**
	 * @Method: updateCommTagName 
	 * @param tagId
	 * @param memberId
	 * @param tagName
	 * @return 
	 * @see net.okdi.api.service.ContactService#updateCommTagName(java.lang.Long, java.lang.Long, java.lang.String) 
	*/
	@Override
	public String updateCommTagName(Long tagId, Long memberId, String tagName) {
		ehcacheService.removeAll("contactGroupCache");
		ehcacheService.removeAll("contactInfoCache");
		ehcacheService.remove("contactCommList",memberId+"");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tagId",tagId);
		map.put("memberId",memberId);
		map.put("tagName",tagName);
		memberCommTypeTagInfoMapper.updateTagName(map);
		memberContactAddrMapper.updateMemberContactAddressName(tagId, memberId, tagName);
		return jsonSuccess(null);
	}

	/**
	 * 得到所有的联系人的所有信息
	 * @param memberId 
	 */
	@Override
	public String queryAllContact(Long memberId) {
		String jsonMap = this.getMemberContactListByPage(memberId, null, null, false, null);
		System.out.println(jsonMap);
		//{"data":[{"birthday":1417590272000,"contactAddressId":1757253,"contactDetaileDisplay":"河南-许昌市-长葛市-董村镇","contactDetailedAddress":"wwwwwwwwwwwwwwwwwwww","contactName":"wwwwwwwwww","contactPhone":"13598415461","createTime":1417590272000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":117097693519872,"level6AddressId":" ","remark":" "},{"birthday":1417590322000,"contactAddressId":1757253,"contactDetaileDisplay":"河南-许昌市-长葛市-董村镇","contactDetailedAddress":"wwwwwwwwwwwwwwwwwwww","contactName":"wwwwwwwwww","contactPhone":"15843466245","createTime":1417590322000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":117097798377472,"level6AddressId":" ","remark":" "},{"birthday":1416794910000,"contactAddressId":11000206,"contactDetaileDisplay":"北京-海淀区","contactDetailedAddress":"田村路 43号","contactName":"1352093290","contactPhone":"13520932905","createTime":1416794910000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":14428712185631744,"level6AddressId":" ","remark":" "},{"birthday":1416822119000,"contactAddressId":11000201,"contactDetaileDisplay":"北京-东城区","contactDetailedAddress":"东内大街 1","contactName":"1","contactPhone":"13520932905","createTime":1416822119000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":14435844866711552,"level6AddressId":" ","remark":" "},{"birthday":1416822227000,"contactAddressId":11000203,"contactDetaileDisplay":"北京-朝阳区","contactDetailedAddress":"双桥路 100号","contactName":"2","contactPhone":"13520932905","createTime":1416822227000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":14435873021501440,"level6AddressId":" ","remark":" "},{"birthday":1416823070000,"contactAddressId":11000203,"contactDetaileDisplay":"北京-朝阳区","contactDetailedAddress":"双桥路 100号","contactName":"5","contactPhone":"13520932905","createTime":1416823070000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":14436094099070976,"level6AddressId":" ","remark":" "},{"birthday":1416823706000,"contactAddressId":11000220,"contactDetaileDisplay":"天津-河北区","contactDetailedAddress":"南口路 11号","contactName":"5","contactPhone":"13520932905","createTime":1416823706000,"createUserId":14428546794787840,"dataMode":0,"gender":1,"id":14436260692893696,"level6AddressId":" ","remark":" "}],"success":true,"totalCount":7}
		JSONObject resultMap = JSONObject.parseObject(jsonMap);
		if (resultMap.get("success") != null && "true".equals(resultMap.get("success").toString())) {
			if (resultMap.get("data") != null) {
				JSONArray contractArray = resultMap.getJSONArray("data");
				for (int i = 0; i < contractArray.size(); i++) {
					Long contractId = Long.parseLong(contractArray.getJSONObject(i).get("id").toString());
					
					String contractDetail = getContactMsg(contractId, memberId);
					JSONObject contractMap = JSONObject.parseObject(contractDetail);
					if (contractMap.get("success") != null && "true".equals(contractMap.get("success").toString())) {
						JSONObject obj = contractMap.getJSONObject("data");
						contractArray.getJSONObject(i).put("allDetail", obj);
					}
				}
				//contractArray.
			}
		}
		return JSON.toJSONString(resultMap);
	}

	/**
	 * @Method: moveContactAsWx 
	 * @param contactIds
	 * @param groupId
	 * @param memberId
	 * @return 
	 * @see net.okdi.api.service.ContactService#moveContactAsWx(java.lang.String, java.lang.Long, java.lang.Long) 
	*/
	@Override
	public String moveContactAsWx(String contactIds, Long groupId, Long memberId) {
		String keys = ehcacheService.get("contactListKeyCache", memberId+"", String.class);
		ehcacheService.removeAll("contactGroupCache");
		this.removeAll("contactListCache",keys);
		List <Long>list = memberContactTagRelationMapper.selectContactIds(memberId);
		if(list.size()>0){
		memberContactTagRelationMapper.deleteOnesContactByTagId(list, groupId);
		}
       if(!PubMethod.isEmpty(contactIds)){
       String [] contactIds2 = contactIds.split(",");
       if(contactIds2.length>0){
       for(int i = 0;i<contactIds2.length;i++){
    	   ehcacheService.remove("contactInfoCache", memberId+"_"+contactIds2[i]);
       MemberContactTagRelationKey record = new MemberContactTagRelationKey();
     	record.setContactId(Long.parseLong(contactIds2[i]));
    	record.setTagId(groupId);
   	    memberContactTagRelationMapper.insert(record);
       }
       }
       }
		return jsonSuccess(null);
	}
	public long ifRegister(String phone){
		return memberTagMapper.getMemberId(phone)==null?0:memberTagMapper.getMemberId(phone);
	}

	/**
	 * @Method: judgeHasAlreadyAdd 
	 * @param memberId
	 * @param json
	 * @return 
	 * @throws BadHanyuPinyinOutputFormatCombination 
	 * @see net.okdi.api.service.ContactService#judgeHasAlreadyAdd(java.lang.Long, java.lang.String) 
	*/
	@Override
	public String judgeHasAlreadyAdd(Long memberId, String json) throws BadHanyuPinyinOutputFormatCombination {
		StringBuffer sb = new StringBuffer("");
		String key = memberId+"_"+null+"_"+null+"_"+false+"_"+null;
		if(!getFromCache("contactListCache", sb, key)){
		
		sb.append(this.getMemberContactList(memberId, null));
		}
		String result = sb.toString();	
		JSONObject jobj = JSON.parseObject(result);
		List<Map<String, Object>> list = (List<Map<String, Object>>) jobj.get("data"); 
		List<PhoneContactVO> phoneMap =  JSON.parseArray(json,PhoneContactVO.class);
//		Set<PhoneContactVO> isAdd = new TreeSet<PhoneContactVO>(
//				new Comparator() {
//			@Override
//			public int compare(Object o1, Object o2) {
//				PhoneContactVO p1 = (PhoneContactVO) o1;
//				PhoneContactVO p2 = (PhoneContactVO) o2;
//				return p1.getLetter().compareTo(p2.getLetter()) ;
//			}
//		});
		if(phoneMap!=null){
		for(int i =0;i<phoneMap.size();i++){
			phoneMap.get(i).setLetter(PiyinUtil.getFullNameSpell(phoneMap.get(i).getName()));
			for(int j =0;j<list.size();j++){
			  List <String>elist = (List<String>) phoneMap.get(i).getPhone();	
			   if(elist.contains(String.valueOf(list.get(j).get("contactPhone")))&&phoneMap.get(i).getName().equals(list.get(j).get("contactName"))){
				   phoneMap.get(i).setContactId(Long.parseLong(list.get(j).get("id").toString()));
			   }
			}
		}
//		Iterator<PhoneContactVO> it = isAdd.iterator();
//		while (it.hasNext()){
//			phoneMap.remove(it.next());
//		}
	    Collections.sort(phoneMap, new Comparator() {  
            public int compare(Object a, Object b) { 
            	PhoneContactVO p1 = (PhoneContactVO) a;
            	PhoneContactVO p2 = (PhoneContactVO) b;
              return p1.getLetter().compareTo(p2.getLetter()) ;   
            }  
         });   
//		Map<String,Object> resultMap = new HashMap<String,Object>();
//		resultMap.put("isAdd", null);
//		resultMap.put("notAdd", phoneMap);
		ehcacheService.remove("contactListCache", key);
		return JSON.toJSONString(phoneMap,SerializerFeature.DisableCircularReferenceDetect);
		}
		return JSON.toJSONString(null);
	}

	/**
	 * @Method: updateMemberContactAddress 
	 * @param tagId
	 * @param memberId
	 * @param tagName
	 * @return 
	 * @see net.okdi.api.service.ContactService#updateMemberContactAddress(java.lang.Long, java.lang.Long, java.lang.String) 
	*/
	@Override
	public String updateMemberContactAddress(Long tagId, Long memberId,
			String tagName) {
		memberContactAddrMapper.updateMemberContactAddressName(tagId, memberId, tagName);
		return jsonSuccess(null);
	}

	@Override
	public String deleteAddressGroup(Long tagId, Long memberId, Boolean Delete) {
		ehcacheService.removeAll("contactInfoCache");
		memberContactAddrMapper.updateMemberContactAddressTag(tagId);
		MemberAddressTypeTagInfo memberAddressTypeTagInfo = new MemberAddressTypeTagInfo(tagId, memberId, "");
		memberAddressTypeTagInfoMapper.deleteByPrimaryKey(memberAddressTypeTagInfo);
		return jsonSuccess(null);
	}
	
}

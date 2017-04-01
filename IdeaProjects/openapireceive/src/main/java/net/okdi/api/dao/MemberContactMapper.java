package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.MemberContact;

public interface MemberContactMapper {
   
	int deleteByPrimaryKey(Long id);

    int insert(MemberContact record);

    List<Map<String,Object>> getMemberContactListByPage(Map<String,Object> map);
    
    MemberContact getMemberContactInfo(Long contactId);
    
    List<Map<String,Object>> ifExist(Map<String,Object>map);
    
}
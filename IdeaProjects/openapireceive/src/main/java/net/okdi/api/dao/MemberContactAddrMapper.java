package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface MemberContactAddrMapper {
	
	void batchAddAddressInfo(List<Map<String,Object>> list);
	
	void deleteAddressInfoByTagId(Map<String,Object>map);
	
	List<Map<String,Object>> getContactAddressInfo(Long contactId);

    void updateMemberContactAddressName(@Param("tagId")Long tagId,@Param("memberId")Long memberId,@Param("tagName")String tagName);

    void updateMemberContactAddressTag(@Param("tagId")Long tagId);
}
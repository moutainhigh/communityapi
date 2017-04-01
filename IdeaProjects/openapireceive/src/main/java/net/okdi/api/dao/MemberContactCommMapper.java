package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface MemberContactCommMapper {
	
   void batchAddMemberContactComm(List<Map<String,Object>>list);
   
   void deleteMemberContactComm(Map <String,Object>map);
   
   List<Map<String,Object>> getContactCommInfo(Long contactId);
   
   void deleteCommByTagId(Long tagId);
   
   void updateCommIdByTagId(@Param("tagId")Long tagId);
}
package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.api.entity.MemberContactTagRelationKey;

public interface MemberContactTagRelationMapper {
    int deleteByPrimaryKey(MemberContactTagRelationKey key);

    int insert(MemberContactTagRelationKey record);

    int insertSelective(MemberContactTagRelationKey record);
    
    void updateRelation(Map<String,Object>map);
    
    void deleteContactGroupRela(Long tagId);
    
    void delete(Map<String,Object>map);
    
    List<Map<String,Object>> getGroupNameByContactId(Long contactId);
    
    void deleteOnesContactByTagId(@Param("list")List list,@Param("tagId")Long tagId);
    
    List<Long> selectContactIds(Long memberId);
}
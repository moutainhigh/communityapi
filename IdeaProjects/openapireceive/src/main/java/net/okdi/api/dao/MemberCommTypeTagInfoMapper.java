package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.MemberCommTypeTagInfo;
import net.okdi.core.base.BaseDao;

public interface MemberCommTypeTagInfoMapper extends BaseDao {
 

	void doAddTag(Map<String, Object> map);

	void delTag(MemberCommTypeTagInfo memberCommTypeTagInfo);
	
	List< MemberCommTypeTagInfo> getMemberCommtypeByMemberId(Long memberId);
    
	void insert(MemberCommTypeTagInfo memberCommTypeTagInfo);
	
	void updateTagName(Map<String,Object>map);
}
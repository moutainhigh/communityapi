package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface QueryUserInfoMapper {
	public Map<String,Object> getUserInfo(@Param(value="memberPhone")Long memberPhone);
	
	public List<String> userIdConfirm(@Param(value="memberId")Long memberId, @Param(value="auditItem")Short auditItem);
	
	public void clearNormalRole(@Param(value="memberId")Long memberId, @Param(value="compId")Long compId);
	
	public void clearStationRole(@Param(value="roleId")Short roleId,@Param(value="memberId")Long memberId, @Param(value="compId")Long compId);
	
	public List<Map<String,Object>> queryStationUserInfo(@Param(value="compId")Long compId);
	
	public void clearMemberInfoRole(@Param(value="memberId")Long memberId);
	
	public Long validTaskHandler(@Param(value="memberId")Long memberId);
	
	public Long validPlaintInfo(@Param(value="memberId")Long memberId);
	
	public void roleTransfer(@Param(value="memberId")Long memberId, @Param(value="roleId")Short roleId);
}

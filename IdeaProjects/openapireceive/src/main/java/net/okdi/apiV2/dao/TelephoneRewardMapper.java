package net.okdi.apiV2.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface TelephoneRewardMapper {
	public Map getStrategy(@Param("memberId")String memberId);

	public String queryPhoneByMemberId(@Param("memberId")String memberId);

	public String queryCreateTimeByMemberId(@Param("memberId")String memberId);
	


}
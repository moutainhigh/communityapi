package net.okdi.apiV2.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.okdi.apiV2.entity.DailyRewardActivity;
import net.okdi.apiV2.entity.TelephoneReward;

public interface DailyRewardActivityMapperV2 {

	public String updateProvinceRewardPriceOrNum(DailyRewardActivity dailyRewardActivity);

	public List<Map<String, Object>> queryDailyRewardActivityList();

	public Map<String,Object> queryRewardActivity(@Param("province")String province, @Param("roleId")String roleId);

	
}

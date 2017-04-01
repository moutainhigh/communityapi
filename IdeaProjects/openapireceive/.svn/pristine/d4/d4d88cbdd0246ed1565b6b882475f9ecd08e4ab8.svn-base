package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.entity.ParLogisticSearch;
import net.okdi.core.base.BaseDao;

public interface ParLogisticSearchMapper extends BaseDao{

	int decideGoods(Map<String, Object> paras);

	String findCodeByNetId(Long netId);

	List<ParLogisticSearch> findList(Map<String, Object> paras);

	 public void deleteById(Map<String, Object> paras);

	public void updateByPrimaryKeySelective(ParLogisticSearch pl);

	List<Long> findIdListPerFourHour();
	void batchUpdate(List<ParLogisticSearch> list);
	List<ParLogisticSearch> findParLogistic(List ids);

	List<ParLogisticSearch> findByMemberIdNetBill(ParLogisticSearch parfind);

	void updateByMemberNetBill(ParLogisticSearch parResult);

	List<ParLogisticSearch> findUnPushed();

	void updatePushData(List<Map> lsm);
}

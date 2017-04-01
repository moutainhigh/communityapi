package net.okdi.schedule.service;


import java.util.List;
import java.util.Map;

import net.okdi.schedule.entity.ParLogisticSearch;



public interface ParLogisticSearchService {
	ParLogisticSearch findParLogisticSearch(Long netId,String expBillNo);
	//未完成的非电商引流的数据
	Map findUnfinishParList(List listIds);
	
	void uptParList(ParLogisticSearch parLogisticSearch);
	
	List findIdListPerFourHour();
	
	void batchUpdate(List<ParLogisticSearch> lsParLogisticSearch);
	
	void pushExtMsg(Map map);
	List<ParLogisticSearch> findUnPushed();
	void updatePushData(String string);
}

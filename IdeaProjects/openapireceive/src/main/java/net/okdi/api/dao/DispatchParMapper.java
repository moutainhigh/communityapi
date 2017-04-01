package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import net.okdi.api.vo.VO_DispatchPar;
import net.okdi.api.vo.VO_DispatchSingleParInfo;

public interface DispatchParMapper {
    
	Integer findCount(Map<String, Object> map);
    List<VO_DispatchPar> findDispatches(Map<String, Object> map);
    
	VO_DispatchSingleParInfo findParById(Map<String, Object> map);
	void insertPar(Map<String,Object> vo);
	void insertParAddress(Map<String,Object> vo);
    List<VO_DispatchPar> findParByExpWaybill(Map<String,Object> map);
	void updatePar(Map<String, Object> map);
	void updateParAddress(Map<String, Object> map);
}
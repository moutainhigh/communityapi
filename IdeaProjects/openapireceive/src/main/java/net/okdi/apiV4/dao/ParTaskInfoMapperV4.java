package net.okdi.apiV4.dao;

import java.util.List;
import java.util.Map;

import net.okdi.core.base.BaseDao;


public interface ParTaskInfoMapperV4 extends BaseDao {

	List ifHasPickUp(List<String> item);

	void changSendPerson(Map<String, Object> params);

	List ifHasPickUpByParcelIdAndMemberId(Map map);

	void updateByPrimaryKeySelective(Map<String, Object> map);

	void addSendTaskInfo(Map<String, Object> map);

	public List<Map<String, Object>> queryTakePackList(Integer currentPage,
			Integer pageSize);

	public Map<String, Object> queryTakeTaskDetailByTaskId(Long taskId);
	
	public List<Map<String,Object>> queryNearCompInfo(Map<String, Object> map);

	Long findMemberIdByPhone(String resPhone);
    
    
}
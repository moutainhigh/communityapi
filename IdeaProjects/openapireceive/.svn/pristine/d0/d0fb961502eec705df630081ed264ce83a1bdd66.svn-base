/**  
 * @Project: openapi
 * @Title: SendTaskMappper.java
 * @Package net.okdi.api.dao
 * @author amssy
 * @date 2014-12-9 下午07:12:17
 * @Copyright: 2014 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.api.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * @author mengnan.zhang
 * @version V1.0
 */
public interface SendTaskMapper {
	public Long addSendTaskInfo(Map<String,Object>map);
	
	public Long updateByPrimaryKeySelective(Map<String,Object>map);
	
	public void changSendPerson(Map<String,Object>map);
	
	public List<Map> ifHasPickUp(@Param("expWayBillNum")String expWayBillNum,@Param("netId")Long netId);

	public List<Object> ifHasTakePickUp(@Param("expWayBillNum")String expWayBillNum,@Param("netId")Long netId);
	
	public List<Object> ifParcelExist(@Param("expWayBillNum")String expWayBillNum,@Param("netId")Long netId);
	
	public List<Map> ifParcelSign(@Param("expWayBillNum")String expWayBillNum,@Param("netId")Long netId);
	
	public void updateParcel(@Param("taskId")Long taskId,@Param("parcelId")Long parcelId,@Param("createUserId")Long createUserId);

	public List<Map<String,Object>> querySendTaskList(Long memberId); 
    
	public void finishSendTask(Long taskId);
	
	public void updateTaskStatus(Long taskId);
	
	public List ifHasPickUpByParcelId(List item);
	
	public List ifHasPickUpByParcelIdAndMemberId(Map map);
}

/**  
 * @Project: publicapi
 * @Title: BroadcastService.java
 * @Package net.okdi.mob.service
 * @author amssy
 * @date 2015-1-20 下午03:53:44
 * @Copyright: 2015 All rights reserved.
 * @since jdk1.6
 * @version V1.0  
*/
package net.okdi.mob.service;

/**
 * @author amssy
 * @version V1.0
 */
public interface BroadcastService {
    
	public String addBroadcastOwn(String jsonString);
	
	public String addBroadcastOwnOperation(String jsonString);
	
	public String addBroadcast(String jsonString,Long memberId);
	
	public String addBroadcastOperation(String jsonString,Long memberId);
	
	public String queryBroadcastList(Long loginMemberId);
	
	public String queryBroadcastDetail(Long broadcastId);
	
	public String cancelBroadcast(Long broadcastId);
	
	public String timeoutBroadcast(Long broadcastId);
	
	public String broadcastRestart(Long broadcastId);
	
	public String broadcastRestartForTask(Long taskId);
	
	public String createTaskEC(String broadcastParam);
	
	public String createTaskPersonal(Long broadcastId, Long quotateId, String appointTime, String contactTel, String senderPhone);
	
	public String deleteBroadcast(Long broadcastId, Long loginMemberId);
	
	public String deleteTakeTask(Long taskId, Long loginMemberId);
	
	public String queryTaskDetail(Long taskId);
	
	public String scanParcel(Long id, Long taskId, String expWaybillNum, String phone);
	
	public String broadcastCreateParcelPersonal(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress);
	
	public String broadcastCreateParcelEC(Long taskId, String expWaybillNum, String phone, Long addresseeAddressId, String addresseeAddress);
	
	public String deleteParcel(Long id, Long taskId);
	
	public String getAllOnLineMember();
	
	
}

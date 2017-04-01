package net.okdi.apiV3.service;

import java.math.BigDecimal;

public interface SmallBellService {
	
	String query(Long memberId, Integer pageSize,Integer pageNo);
	
	String queryTaskRob(Long memberId, Integer pageSize,Integer pageNo);

    String queryUnReadTaskRob(Long memberId);

    String deleteTaskRob(Long id);

	String resend(Long memberId, String msgIds);

	String taskInfo(String memberId, String taskId);

	String delete(Long memberId, String msgId);
	
	String createTaskIgomo(Long fromCompId, Long coopNetId,String appointDesc,Long actorMemberId,String contactName,String contactMobile
			,String contactAddress,BigDecimal contactAddrLongitude,BigDecimal contactAddrLatitude,String actorPhone,String openId,Byte taskSource,
			Long memberId,String parcelStr,Byte taskFlag,Integer howFast,Byte parEstimateCount,Long assignNetId);
	
	Long queryCount(Long memberId);
	
	Long queryRobCount(Long memberId);

	String wechatRob(Long memberId, String netName, String netId, String phone, String mname, Long taskId);

	String deleteMessage(String msgId, String memberId);

}

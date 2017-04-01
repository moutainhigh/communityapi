package net.okdi.apiV2.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TaskRecordService {

	public String queryReceiveCount(String memberId);
	
	public String querySendCount(String memberId);
	
	public String queryNoticeCount(String memberId) ;

}

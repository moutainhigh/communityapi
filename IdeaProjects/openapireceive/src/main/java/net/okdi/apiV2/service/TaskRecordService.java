package net.okdi.apiV2.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TaskRecordService {

	public int queryReceiveCount(String memberId);
	
	public int querySendCount(String memberId);

}

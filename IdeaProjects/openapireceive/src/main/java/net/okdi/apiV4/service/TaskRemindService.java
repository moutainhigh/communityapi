package net.okdi.apiV4.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TaskRemindService {
	
	List<Map> queryTaskRemind(Long memberId,Integer pageNo,Integer pageSize) throws ParseException;

	void removeMsgLoger(Long msgId, Long memberId);

	void removeById(Long msgId, Long memberId);
	
	String unRead(String msgId,Long memberId);
	
	Long unReadCount(Long memberId) throws ParseException;

	List<Map> queryTaskRob(Long memberId, Integer pageNo, Integer pageSize) throws ParseException ;
}

package net.okdi.apiV1.service;

import java.util.List;
import java.util.Map;

import net.okdi.apiV1.entity.PushMessageInfoItem;

public interface PushMessageService {

	public void savePushMessageInfo(String title, String message,
			String extraParam, List arrPhones, String pushWay,String platform,Short useType) ;

	public String receiveSendTypeValue(Long id, String sendPhone, Short flag);

	public Map<String, Object> queryPushMessageListInfo(String startTime,
			String endTime, String title, String content, Integer currentPage,
			Integer pageSize);

	public List<PushMessageInfoItem> queryPushPhoneAllById(String id);

	public Map<String, Object> queryAnnounceMessageListInfo(String startTime,
			String endTime, String title, String content, Integer currentPage1,
			Integer pageSize1);

	public void saveAnnounceMessageInfo(String announceType, String title,
			String creator, String content, String pushWay);

	public void updateAnnounceStatus(Long id);

}

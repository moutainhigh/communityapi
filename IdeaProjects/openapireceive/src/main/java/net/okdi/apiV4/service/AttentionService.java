package net.okdi.apiV4.service;

import java.util.List;
import java.util.Map;

public interface AttentionService {

	List<Map<String, Object>> queryAttentionList(Long memberId);

	List<Map<String, Object>> queryContactList(Long memberId, Short roleId,Long compId,Integer currentPage,Integer pageSize);

	void cancelAttention(Long fromMemberId, Long toMemberId);

	List<Map<String, Object>> queryDynamicNotice(Long memberId, Integer currentPage,
			Integer pageSize);

	Map<String, Object> leaderboard(Long memberId,Short tagType, Integer currentPage, Integer pageSize,Short filter);

}

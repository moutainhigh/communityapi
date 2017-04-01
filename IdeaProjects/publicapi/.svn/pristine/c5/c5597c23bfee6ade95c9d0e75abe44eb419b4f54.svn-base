package net.okdi.apiV4.service;


public interface AttentionService {

	String queryAttentionList(Long memberId);

	String queryContactList(Long memberId,String visitorMemberId,Integer currentPage,Integer pageSize,String searchKey,Short mark);

	String cancelAttention(Long fromMemberId, Long toMemberId);

	String queryDynamicNotice(Long memberId, Integer currentPage,
			Integer pageSize);

	String leaderboard(Long memberId,Short tagType, Integer currentPage, Integer pageSize,Short filter);

	String chooseContactList(Long memberId, Long communityId,Integer currentPage, Integer pageSize);

	String deleteAttention(Long attentionId);

	String messageList(Long memberId);

	String searchMember(String searchName, Long memberId);

	String queryAttCount(Long memberId);
	//2016-12-16新增
	String searchCommunity(String searchName, Long memberId);

}

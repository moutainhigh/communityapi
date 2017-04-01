package net.okdi.apiV4.service;

public interface AttentionContactsService {

	String queryNearMemberForAttention(Double lng, Double lat, Integer howFast,String memberId);

	String queryContactsBookList(Long memberId, String jsonData);

}

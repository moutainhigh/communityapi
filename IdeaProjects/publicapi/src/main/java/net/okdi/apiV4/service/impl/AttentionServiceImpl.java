package net.okdi.apiV4.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.okdi.apiV4.service.AttentionService;
import net.okdi.core.passport.OpenApiHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AttentionServiceImpl implements AttentionService {

	@Autowired
	private OpenApiHttpClient openApiHttpClient;
	@Override
	public String queryAttentionList(Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName="attention/queryAttentionList";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String chooseContactList(Long memberId, Long communityId,
			Integer currentPage, Integer pageSize) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("communityId", communityId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String methodName="attention/chooseContactList";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String queryContactList(Long memberId,String visitorMemberId, Integer currentPage, Integer pageSize,String searchKey,Short mark) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("visitorMemberId", visitorMemberId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		map.put("searchKey", searchKey);
		map.put("mark", mark);
		String methodName="attention/queryContactList";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}

	@Override
	public String cancelAttention(Long fromMemberId, Long toMemberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("fromMemberId", fromMemberId);
		map.put("toMemberId", toMemberId);
		String methodName="attention/cancelAttention";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}

	@Override
	public String queryDynamicNotice(Long memberId, Integer currentPage,
			Integer pageSize) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String methodName="attention/queryDynamicNotice";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}

	@Override
	public String leaderboard(Long memberId, Short tagType,
			Integer currentPage, Integer pageSize, Short filter) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("tagType", tagType);
		map.put("filter", filter);
		map.put("currentPage", currentPage);
		map.put("pageSize", pageSize);
		String methodName="attention/leaderboard";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String deleteAttention(Long attentionId) {
		Map<String,Object> map = new HashMap<>();
		map.put("attentionId", attentionId);
		String methodName="attention/deleteAttention";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String messageList(Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName="attention/messageList";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String searchMember(String searchName, Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("searchName", searchName);
		map.put("memberId", memberId);
		String methodName="attention/searchMember";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String queryAttCount(Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("memberId", memberId);
		String methodName="attention/queryAttCount";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}
	@Override
	public String searchCommunity(String searchName, Long memberId) {
		Map<String,Object> map = new HashMap<>();
		map.put("searchName", searchName);
		map.put("memberId", memberId);
		String methodName="attention/searchCommunity";
		return openApiHttpClient.doPassSendStrToCommunity(methodName, map);
	}

  }





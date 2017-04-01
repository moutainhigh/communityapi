package net.okdi.apiV4.service;

import java.util.List;
import java.util.Map;

import net.okdi.apiV4.entity.Card;
import net.okdi.apiV4.entity.Vo_Card;

public interface CardService {

	String publishCard(Long cardId,Long createorMemberId, String type, String content,
			String imageUrl, String video);

	String cardReadCountAdd(Long cardId);

	String cardLikeCountAdd(Long cardId, Long memberId);

	Vo_Card queryCardDetial(Long cardId, Long memberId);

	List<Map<String, Object>> queryHotCard(Long memberId,Integer currentPage,Integer pageCount);

	String replyCard(Long cardId, String type, Long fromMemberId,
			Long toMemberId, String fromMemberName, String toMemberName,String content);

	String deleteCardComment(Long cardId, Long memberId);

	Card findLatestCard(Long memberId);
	
	Map<String, Object> taHomePage(Long memberId, Long taMemberId);

	String addAttention(Long fromMemberId, Long toMemberId);


}

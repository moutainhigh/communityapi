package net.okdi.apiV4.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CardService {

	String cardReadCountAdd(Long cardId);

	String cardLikeCountAdd(Long cardId, Long memberId,String type);

	String queryCardDetial(Long cardId, Long memberId,Integer currentPage,Integer pageCount);

	String queryHotCard(Long memberId,Integer currentPage,Integer pageCount);

	String publishCard(Long createorMemberId, String type,String source, String content,
			MultipartFile image1, MultipartFile image2, MultipartFile image3,
			MultipartFile image4, MultipartFile video);

	String replyCard(Long cardId, String type, Long fromMemberId,
			Long toMemberId, String fromMemberName, String toMemberName,String content,Long forCommentId);

	String deleteCardComment(Long cardCommentId,Long cardId);

	String taHomePage(Long memberId, Long taMemberId,Integer currentPage,Integer pageCount);

	String addAttention(Long fromMemberId, Long toMemberId,String fromMemberName,String toMemberName);

	String publishCard1(String createorMemberId, String type, String source,
			String content, List<MultipartFile> video,String creatorName);

	String cardReport(Long informerId,Long beReportId,String informerName,String beReportName,Long cardId,
			Long reasonId, String resonText,String reportCont);

	String queryReasonList();

	String deleteCard(Long creatorMemberId, Long cardId);

	String queryCardReader(Long cardId, Long memberId,Integer currentPage,Integer pageCount);

	String queryCardLike(Long cardId, Long memberId, Integer currentPage,
			Integer pageCount);
}

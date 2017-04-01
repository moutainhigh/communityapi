package net.okdi.apiV4.service;

import org.springframework.web.multipart.MultipartFile;

import net.okdi.apiV4.vo.CommunityVO;

public interface ExpressCommunityService {

	/**
	 * 创建群
	 * @Method: createCommunity
	 * @param commVo
	 */
	String createCommunity(CommunityVO commVo, MultipartFile logo);
	
	String asyncUpdateCommunMemberLatestCard(Long memberId, String textContent, Integer cardType, Long pubTime);

	/**
	 * 管理员同意加入群: 更新申请状态, 加入群, 发送即时消息
	 * @Method: asyncHandleJoinCommunReq
	 * @param communId
	 * @param masterId
	 * @param reqId
	 */
	String asyncHandleJoinCommunReq(Long communId, Long masterId, Long reqId, String name);

	String findCommunityById(Long id);

	String nearCommunity(Long memberId, double lng, double lat, int pageSize, int currPage, int index);

	String communityDetail(Long id, Long currMemberId);

	String communityMemberList(Long id, Integer currPage, Integer index);

	String setNotice(Long mid, Long cid, String notice);

	//String chatSetting(Long currMemberId, Long contactId);

	String myJoinCommunity(Long mid, double lng, double lat);

	String applyJoinCommunity(Long id, Long memberId);

	String topCommunity(Long cid, Long mid, int isTop);
	
	String interruptCommunity(Long cid, Long mid, int isInter);
	
	String changeMaster(Long cid, Long mid, Long newMasterId);
	
	String truncLog(Long mid, Long targetId, Integer op);
	
	String quitCommunity(Long cid, Long mid);
	
	String interruptContact(Long contactId, Long mid, int isInter);
	
	String joinCommunityReqList(Long master, int currPage);

	String topContast(String contactId, String mid, int isTop);

	String deleteJoinCommunityReq(String communId, String masterId, String reqId);

	String chatSetting(String mid, String targetId);

	String addMember(Long cid, String memberJson);

	String setNoticeOrRename(Long mid, Long cid, String noticeOrName, Integer type);

	String handleJoinCommunityReq(Long communId, Long masterId, Long reqId);
	
	String searchCommunityMember(Long cid, String name);

	/**
	 * 群主删除群成员
	 * @param cid 圈子Id
	 * @param masterId 群主MemberId
	 * @param reqId 成员Id
	 */
	String removeCommunityMember(Long cid, Long masterId, Long reqId);

	String queryCommLabelList(Long cid, Long mid);

	String insertCommLabelList(Long cid, Long mid, String labels);

	String deleteCommLabelList(Long cid, Long mid, String labels);
}

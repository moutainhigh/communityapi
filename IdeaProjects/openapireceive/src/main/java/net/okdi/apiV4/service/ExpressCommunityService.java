package net.okdi.apiV4.service;

import net.okdi.apiV4.entity.ExpressCommunity;

public interface ExpressCommunityService {

	void asyncUpdateCommunMemberLatestCard(Long memberId, String textContent, Integer cardType, Long pubTime);

	/**
	 * 管理员同意加入圈子: 更新申请状态, 加入圈子, 发送即时消息
	 * @Method: asyncHandleJoinCommunReq
	 * @param communId
	 * @param masterId
	 * @param reqId
	 */
	void asyncHandleJoinCommunReq(Long communId, Long masterId, Long reqId, String name);

	ExpressCommunity findCommunityById(Long id);

}

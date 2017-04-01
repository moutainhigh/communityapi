package net.okdi.core.common.handlemessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.okdi.apiV4.service.ExpressCommunityService;

/**
 * 
 * @ClassName: ExpressCommunityMsgHandler
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月16日 下午4:46:10
 * @version V1.0
 */
public class ExpressCommunityMsgHandler {

	private @Autowired ExpressCommunityService expressCommunityService;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpressCommunityMsgHandler.class);
	
	@Transactional(rollbackFor = {Exception.class})
	public void handleMessage(String message) {
		JSONObject msg = JSON.parseObject(message);
		
		String type = msg.getString("type");
		if ("1".equals(type)) { //管理员同意加入圈子: 更新申请状态, 加入圈子, 发送即时消息
			joinCommunReq(msg);
			return;
		}
		if ("2".equals(type)) { //发帖时同步更新圈子成员中的最新动态
			logger.info("队列中异步更新圈子成员中的最新动态::{}", msg);
			updateCommonCard(msg);
			return;
		}
	}

	private void joinCommunReq(JSONObject msg) {
		Long communId = msg.getLong("communId");
		Long masterId = msg.getLong("masterId");
		Long reqId = msg.getLong("reqId");
		String name = msg.getString("name");
		expressCommunityService.asyncHandleJoinCommunReq(communId, masterId, reqId, name);
	}

	private void updateCommonCard(JSONObject msg) {
		Long memberId = msg.getLong("memberId");
		String textContent = msg.getString("textContent");
		Integer cardType = msg.getInteger("cardType");
		Long pubTime = msg.getLong("pubTime");
		expressCommunityService.asyncUpdateCommunMemberLatestCard(memberId, textContent, cardType, pubTime);
	}
	
	
}







package net.okdi.apiV4.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amssy.common.util.primarykey.IdWorker;

import net.okdi.apiV4.service.ExpressCommunityService;
import net.okdi.apiV4.vo.CommunityVO;
import net.okdi.core.util.service.ConstPool;
import net.okdi.httpClient.AbstractHttpClient;

@Service
public class ExpressCommunityServiceImpl extends AbstractHttpClient<String> implements ExpressCommunityService {

	private @Autowired ConstPool constPool;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExpressCommunityServiceImpl.class);
	
	@Override
	public String createCommunity(CommunityVO commVo, MultipartFile logo) {
		
		String logoSavePath = constPool.getCommunitySavePath();
		
		Long id = IdWorker.getIdWorker().nextId();
		try {
			uploadFile(logo, logoSavePath + id + ".jpg");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("communityName", commVo.getCommunityName());
		params.put("communityIntroduce", commVo.getCommunityIntroduce());
		params.put("communityNotice", commVo.getCommunityNotice());
		params.put("communityProvince", commVo.getCommunityProvince());
		params.put("communityTown", commVo.getCommunityTown());
		params.put("commlabels", commVo.getCommlabels());
		params.put("communityAddress", commVo.getCommunityAddress());
		params.put("lat", commVo.getLat());
		params.put("lng", commVo.getLng());
		params.put("membs", commVo.getMembs());
		params.put("mid", commVo.getMid());
		params.put("mname", commVo.getMname());
		params.put("id", id);
		System.out.println("{{{{{{{{{{{{{{{{{{{{{{}}}}}}}}}}}}}}}}}}}}}}==创建群 createCommunity =======参数params: "+params);
		String methodName = "community/create";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String asyncUpdateCommunMemberLatestCard(Long memberId, String textContent, Integer cardType, Long pubTime) {
		return null;
	}

	@Override
	public String asyncHandleJoinCommunReq(Long communId, Long masterId, Long reqId, String name) {
		return null;
	}

	@Override
	public String findCommunityById(Long id) {
		return null;
	}

	@Override
	public String nearCommunity(Long memberId, double lng, double lat, int pageSize, int currPage, int index) {
		Map<String, Object> params = new HashMap<>();
		params.put("lat", lat);
		params.put("lng", lng);
		params.put("mid", memberId);
		params.put("pageSize", pageSize);
		params.put("currPage", currPage);
		params.put("index", index);
		
		String methodName = "community/near";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String communityDetail(Long id, Long currMemberId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("mid", currMemberId);
		
		String methodName = "community/detail";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String communityMemberList(Long id, Integer currPage, Integer index) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("currPage", currPage);
		params.put("index", index);
		
		String methodName = "community/memlist";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String setNotice(Long mid, Long cid, String notice) {
		return null;
	}
	@Override
	public String myJoinCommunity(Long mid, double lng, double lat) {
		Map<String, Object> params = new HashMap<>();
		params.put("lat", lat);
		params.put("lng", lng);
		params.put("mid", mid);
		
		String methodName = "community/myjoin";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String applyJoinCommunity(Long id, Long memberId) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("mid", memberId);
		
		String methodName = "community/join/apply";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}
	
	@Override
	public String handleJoinCommunityReq(Long communId, Long masterId, Long reqId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", communId);
		params.put("mid", masterId);
		params.put("reqId", reqId);
		String methodName = "community/join/agree";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}
	
	
	
	private void uploadFile(MultipartFile file, String dest) throws IOException {
		InputStream is = file.getInputStream();
		LOGGER.debug("上传图片  :: " + dest);
		FileUtils.copyInputStreamToFile(is, new File(dest));
	}

	@Override
	public String topCommunity(Long cid, Long mid, int isTop) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		params.put("isTop", isTop);
		String methodName = "community/top";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String interruptCommunity(Long cid, Long mid, int isInter) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		params.put("isInter", isInter);
		String methodName = "community/inter";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String changeMaster(Long cid, Long mid, Long newMasterId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		params.put("newMasterId", newMasterId);
		String methodName = "community/change";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String truncLog(Long mid, Long targetId, Integer op) {
		Map<String , Object> params = new HashMap<>();
		params.put("mid", mid);
		params.put("targetId", targetId);
		params.put("op", op);
		String methodName = "community/trunclog";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String quitCommunity(Long cid, Long mid) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		String methodName = "community/quit";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String interruptContact(Long contactId, Long mid, int isInter) {
		Map<String, Object> params = new HashMap<>();
		params.put("contactId", contactId);
		params.put("mid", mid);
		if(1 == isInter){//true
			params.put("isInter", true);
		}else if(0 == isInter){
			params.put("isInter", false);
		}
		String methodName = "community/interruptcontact";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String joinCommunityReqList(Long master, int currPage) {
		Map<String, Object> params = new HashMap<>();
		params.put("master", master);
		params.put("currPage", currPage);
		String methodName = "community/join/reqlist";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String topContast(String contactId, String mid, int isTop) {
		Map<String, Object> params = new HashMap<>();
		params.put("contactId", contactId);
		params.put("mid", mid);
		params.put("isTop", isTop);
		String methodName = "community/topContast";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}


	@Override
	public String deleteJoinCommunityReq(String communId, String masterId,
			String reqId) {
		Map<String, Object> params = new HashMap<>();
		params.put("communId", communId);
		params.put("masterId", masterId);
		params.put("reqId", reqId);
		String methodName = "community/join/del";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String chatSetting(String mid, String targetId) {
		Map<String, Object> params = new HashMap<>();
		params.put("mid", mid);
		params.put("targetId", targetId);
		String methodName = "community/chatSetting";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String addMember(Long cid, String memberJson) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mjson", memberJson);
		String methodName = "community/addmember";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String setNoticeOrRename(Long mid, Long cid, String noticeOrName, Integer type) {
		Map<String, Object> params = new HashMap<>();
		params.put("mid", mid);
		params.put("cid", cid);
		params.put("noticeOrName", noticeOrName);
		params.put("type", type);
		String methodName = "community/notice-name";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String searchCommunityMember(Long cid, String name) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("name", name);
		String methodName = "community/searchmember";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String removeCommunityMember(Long cid, Long masterId, Long reqId) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("masterId", masterId);
		params.put("reqId", reqId);
		String methodName = "community/removeCommunityMember";
		String url = constPool.getCommunityApiUrl() + methodName;
		return Post(url, params);
	}

	@Override
	public String queryCommLabelList(Long cid, Long mid) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		String methodName = "community/queryCommLabelList";
		String url = constPool.getCommunityApiUrl() + methodName;
		
		//String url = "http://localhost:8080/communityapi/community/queryCommLabelList";
		return Post(url, params);
	
	}

	@Override
	public String insertCommLabelList(Long cid, Long mid, String labels) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		params.put("labels", labels);
		String methodName = "community/insertCommLabelList";
		String url = constPool.getCommunityApiUrl() + methodName;//别忘了打开
		
		//String url = "http://localhost:8080/communityapi/community/insertCommLabelList";
		return Post(url, params);
	}

	@Override
	public String deleteCommLabelList(Long cid, Long mid, String labels) {
		Map<String, Object> params = new HashMap<>();
		params.put("cid", cid);
		params.put("mid", mid);
		params.put("labels", labels);
		String methodName = "community/deleteCommLabelList";
		String url = constPool.getCommunityApiUrl() + methodName;
		
		//String url = "http://localhost:8080/communityapi/community/deleteCommLabelList";
		return Post(url, params);
	
	}
	
	
}














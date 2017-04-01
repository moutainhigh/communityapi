package net.okdi.apiV4.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Distance;
import org.springframework.data.mongodb.core.geo.GeoResult;
import org.springframework.data.mongodb.core.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.Metrics;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.amssy.common.util.primarykey.IdWorker;
import com.mongodb.WriteResult;

import net.okdi.apiV4.entity.Card;
import net.okdi.apiV4.entity.CommunityMember;
import net.okdi.apiV4.entity.ExpressCommunity;
import net.okdi.apiV4.entity.ExpressCommunity.CommunityMaster;
import net.okdi.apiV4.entity.ExpressMemberInfo;
import net.okdi.apiV4.entity.JoinCommunityRequest;
import net.okdi.apiV4.service.CardService;
import net.okdi.apiV4.service.CommunityMemberService;
import net.okdi.apiV4.service.ExpressCommunityService;
import net.okdi.apiV4.service.ExpressMemberInfoService;
import net.okdi.apiV4.vo.CommonResponse;
import net.okdi.apiV4.vo.CommunityStatusCode.CommunityIden;
import net.okdi.apiV4.vo.CommunityStatusCode.CommunityStat;
import net.okdi.apiV4.vo.CommunityVO;
import net.okdi.apiV4.vo.ResultCode;
import net.okdi.apiV4.vo.ResultCode.Community;
import net.okdi.apiV4.vo.ResultCode.JoinCommunity;
import net.okdi.apiV4.vo.ResultCode.MemberInfo;
import net.okdi.core.common.handlemessage.MessageSenderRabbit;
import net.okdi.core.common.redis.RedisRepository;
import net.okdi.core.exception.ServiceException;

/**
 * 圈子
 * @ClassName: ExpressCommunityServiceImpl
 * @Description: TODO
 * @author hang.yu
 * @date 2016年4月15日 上午11:45:06
 * @version V1.0
 */
public class ExpressCommunityServiceImpl implements ExpressCommunityService {

	private @Autowired MongoTemplate mongoTemplate;
	
	private @Autowired RedisRepository redisRepo;
	
	private @Autowired MessageSenderRabbit rabbitMq;
	
	private @Autowired CardService cardService;
	
	private @Autowired ExpressMemberInfoService expressMemberInfoService;
	
	private @Autowired CommunityMemberService communityMemberService;
	
	private @Value("${}") String COMMON_ROUTING_KEY;
	
	private static final String REDIS_KEY_COMMUNITY = "community:";
	
	private static final String REDIS_KEY_JOINREQUEST = "community.joinreq:";
	
	/**
	 * 管理员的加圈申请列表
	 */
	private static final String REDIS_KEY_HANDLEJOINREQUEST_LIST = "community.handlejoinreqlist:";
	
	private static final String REDIS_KEY_HANDLEJOINREQUEST_DETAIL = "community.handlejoinreqdetail:";
	
	private static final String REDIS_KEY_COMMMEMBERS = "community.members:";
	
	
	private static final double MAXDISTANCE = 3.0;
	
	private static final int NEAR_COMMUNITY_NUM = 10;
	
	private static final int PAGE_SIZE = 20;
	
	private static final Logger logger = LoggerFactory.getLogger(ExpressCommunityServiceImpl.class);
	
	/**
	 * 创建圈子
	 * @Method: createCommunity
	 * @param community
	 */
	public void createCommunity(CommunityVO commVo) {
		
		ExpressCommunity community = new ExpressCommunity();
		BeanUtils.copyProperties(commVo, community);
		
		Date ts = new Date();
		community.setId(IdWorker.getIdWorker().nextId());
		Double[] loc = new Double[] { commVo.getCommunityLongitude(), commVo.getCommunityLatitude() };
		community.setLoc(loc);
		community.setCreateTime(ts);
		community.setStat(CommunityStat.DEFAULT);
		community.setIden(CommunityIden.USER);
		
		/*
		 * 设置管理员  当前创建人
		 */
		CommunityMaster master = new ExpressCommunity().new CommunityMaster();
		master.setMid(commVo.getMid());		//用户id
		master.setMname(commVo.getMname()); //用户名
		master.setUpdateTime(ts);
		master.setCardType(0);
		
		Card latestCard = cardService.findLatestCard(commVo.getMid());
		if (latestCard == null || latestCard.getCardId() == null || latestCard.getCardId() == 0) {
			master.setLatestContent(""); //?? 查询最新动态
		} else {
			master.setLatestContent(latestCard.getContent()); //?? 查询最新动态
			master.setCardType(Integer.valueOf(latestCard.getType()));
		}
		community.setMaster(master);
		
		/**
		 * 添加选择的联系人
		 */
		
		
		mongoTemplate.insert(community);
	}
	
	/**
	 * 附近圈子
	 * @Method: nearCommunity
	 * @param lng
	 * @param lat
	 * @return
	 */
	public List<CommonResponse> nearCommunity(Long memberId, double lng, double lat, int pageSize, int currPage) {
		logger.info("memberId({})查询附近第[{}]页的快递圈 :: {}, {}", memberId, currPage, lng, lat);
		
		currPage = currPage < 1 ? 1 : currPage;
		pageSize = pageSize < 1 ? NEAR_COMMUNITY_NUM : pageSize;
		
		Query query = new Query();
		query.addCriteria(where("stat").is(CommunityStat.DEFAULT));
		query.fields().include("_id").include("communityName").include("memberNum");
		NearQuery near = NearQuery.near(lng, lat)
				.maxDistance(new Distance(MAXDISTANCE, Metrics.KILOMETERS))
				.skip((currPage - 1) * pageSize).num(pageSize).spherical(true)
				.inKilometers().query(query);
		
		if (logger.isDebugEnabled()) {
			logger.info("查询附近圈子  => ", near);
		}
		
		ExpressMemberInfo member = expressMemberInfoService.findByMid(memberId);
		Set<Long> joinCommuns = member.getJoinCommuns() == null ? new HashSet<Long>() : member.getJoinCommuns();
		Map<Long, String> map = new HashMap<>();
		Iterator<Long> jcIter = joinCommuns.iterator();
		while (jcIter.hasNext()) {
			map.put(jcIter.next(), "1");
		}
		
		GeoResults<ExpressCommunity> nearResults = mongoTemplate.geoNear(near, ExpressCommunity.class);
		Iterator<GeoResult<ExpressCommunity>> iter = nearResults.iterator();
		List<CommonResponse> returnRes = new ArrayList<>();
		while (iter.hasNext()) {
			GeoResult<ExpressCommunity> result = iter.next();
			ExpressCommunity comm = result.getContent();
			
			CommonResponse resp = CommonResponse.get()
					.put("id", comm.getId())
					.put("name", comm.getCommunityName())
					.put("dis", result.getDistance().getValue())
					.put("num", comm.getMemberNum())
					.put("in", map.get(comm.getId()) == null ? 0 : 1); //0:未加入  1:已加入
			
			returnRes.add(resp);
		}
		return returnRes;
	}
	
	/**
	 * 圈子主页
	 * @Method: communityDetail
	 * @param id
	 * @param currMemberId
	 */
	public CommonResponse communityDetail(Long id, Long currMemberId) {
		ExpressCommunity community = findCommunityById(id);
		
		Double score = redisRepo.zget(REDIS_KEY_COMMMEMBERS + id, currMemberId.toString());
		int isIn = score == null ? 0 : 1; //是否在圈子中

		//是否是管理员
		CommunityMaster master = community.getMaster();
		int isMaster = String.valueOf(currMemberId).equals(master.getMid()) ? 1 : 0;
		
		//是否置顶了  是否屏蔽消息了
		Map<String, Object> topOrInterru = expressMemberInfoService.isTopOrInterru(currMemberId, id);
		Integer memberNum = community.getMemberNum();
		CommonResponse resp = CommonResponse.get()
				.put("detail", community).put("num", memberNum == null ? 0 : memberNum)
				.put("in", isIn).put("cm", isMaster).append(topOrInterru);
		return resp;
	}
	
	/**
	 * 圈子更换管理员
	 * @Method: changeMaster
	 * @param cid: 圈子id
	 * @param mid: 原群主id
	 * @param newMasterId: 新群主id
	 * @return
	 */
	public CommonResponse changeMaster(Long cid, Long mid, Long newMasterId) {
		
		logger.info("memberId({})更换圈子({})的群主为({})", mid, cid);
		
		CommonResponse resp = CommonResponse.get();
		
		ExpressCommunity community = findCommunityById(cid);
		CommunityMaster oldMaster = community.getMaster();
		if (!String.valueOf(mid).equals(oldMaster.getMid())) {
			return resp.rc(Community.NOT_MASTER_CHANGE).err(Community.NOT_MASTER_CHANGEMASTER);
		}
		
		
		return resp;
	}
	
	/**
	 * 圈子成员列表
	 * @Method: communityMemberList
	 * @param id
	 * @param currPage
	 */
	public List<CommunityMember> communityMemberList(Long id, Integer currPage) {
		ExpressCommunity community = findCommunityById(id);
		
		return communityMemberService.communityMemberList(community, currPage);
	}
	
	/**
	 * 申请加入圈子
	 * @Method: applyJoinCommunity
	 * @param id
	 * @param memberId
	 */
	public void applyJoinCommunity(Long id, Long memberId) {
		ExpressCommunity community = findCommunityById(id);
		
		//判断是否已经加入圈子
		Double score = redisRepo.zget(REDIS_KEY_COMMMEMBERS + id, memberId.toString());
		if (null != score) {
			throw new ServiceException(JoinCommunity.HAS_IN, JoinCommunity.HAS_IN_MSG);
		}
		
		//判断是否申请过, 待定
		JoinCommunityRequest req = getJoinReq(community.getId(), memberId);
		if (null != req) {
			throw new ServiceException(JoinCommunity.REQUESTED, JoinCommunity.REQUESTED_MSG);
		}
		saveJoinRequest(community, memberId);
		
		//给管理员推送申请消息
	}

	/**
	 * 圈子管理员master的加圈申请列表
	 * @Method: joinCommunityReqList
	 * @param master
	 */
	public List<JoinCommunityRequest> joinCommunityReqList(Long master, int currPage) {
		int start = (currPage - 1) * PAGE_SIZE;
		int end = currPage * PAGE_SIZE;
		String key = REDIS_KEY_HANDLEJOINREQUEST_LIST + master;
		String detailKey = REDIS_KEY_HANDLEJOINREQUEST_DETAIL + master;
		
		List<JoinCommunityRequest> resList = new ArrayList<>();
		List<Object> communIds = redisRepo.zgetObject(key, start, end);
		if (communIds == null || communIds.size() < 1) {
			Query query = Query.query(where("commMaster").is(master).and("status").ne(JoinCommunityRequest.CHANGE_MASTER));
			query.skip(currPage * PAGE_SIZE).limit(PAGE_SIZE).with(new Sort(Direction.DESC, "proposeTime"));
			resList = mongoTemplate.find(query, JoinCommunityRequest.class);
			
			Map<String, Double> valueScorePair = new HashMap<>();
			Map<String, String> reqListMap = new HashMap<>();
			for (JoinCommunityRequest req : resList) {
				valueScorePair.put(req.getCommunityId().toString(), Double.valueOf(req.getProposeTime().toString()));
				reqListMap.put(req.getCommunityId().toString(), JSON.toJSONString(req));
			}
			redisRepo.zMset(key, valueScorePair);
			redisRepo.hMset(detailKey, reqListMap);
			return resList;
		}
		List<JoinCommunityRequest> reqs = redisRepo.hMget(detailKey, communIds, JoinCommunityRequest.class);
		sortJoinRequest(reqs);
		return reqs;
	}
	
	/**
	 * 处理reqId的加圈申请
	 * @Method: findJoinCommunityReq
	 * @param communId	: 圈子id
	 * @param masterId	: 管理员id
	 * @param reqId		: 申请人id
	 */
	public boolean handleJoinCommunityReq(Long communId, Long masterId, Long reqId) {
		
		findCommunityById(communId);
		
		String key = REDIS_KEY_HANDLEJOINREQUEST_DETAIL + masterId;
		
		JoinCommunityRequest joinReq = redisRepo.hget(key, communId.toString(), JoinCommunityRequest.class);
		if (null == joinReq) {
			Query query = Query.query(where("communityId").is(communId)
					.and("proposer").is(reqId)
					.and("commMaster").is(masterId)
					.and("status").ne(JoinCommunityRequest.CHANGE_MASTER));
			joinReq = mongoTemplate.findOne(query, JoinCommunityRequest.class);
		}
		if (null == joinReq) {
			throw new ServiceException(JoinCommunity.NO_SUCH_REQUEST, JoinCommunity.NO_SUCH_REQUEST_MSG);
		}
		
		// 1. 设置为已同意
		joinReq.setStatus(1);
		// 2. 更新redis
		redisRepo.hset(key, communId.toString(), joinReq);
		// 3. 更新数据库  下面三步在队列异步执行
		// 4. 把这个人添加到圈子成员列表中
		// 5. 给圈子发送即时消息, 通知圈子里的所有成员, xxx加入该圈子
		handleJoinCommunReqInMq(communId, masterId, reqId, joinReq.getProposerName());
		return true;
	}
	
	/**
	 * 设置圈子公告
	 * @Method: setNotice
	 * @param mid
	 * @param cid
	 * @param notice
	 */
	public CommonResponse setNotice(Long mid, Long cid, String notice) {
		
		logger.info("memberId({})设置圈子({})的公告 => ", mid, cid, notice);
		
		CommonResponse resp = CommonResponse.get();
		
		ExpressCommunity community = findCommunityById(cid);
		CommunityMaster master = community.getMaster();
		if (!String.valueOf(mid).equals(master.getMid())) {
			return resp.rc(Community.NOT_MASTER).err(Community.NOT_MASTER_SETNOTICE);
		}
		Query query = Query.query(where("_id").is(cid));
		Update update = new Update();
		update.set("communityNotice", notice);
		WriteResult wr = mongoTemplate.updateFirst(query, update, ExpressCommunity.class);
		if (null != wr.getError()) {
			return resp.rc(ResultCode.Success.FAIL).err(ResultCode.Success.FAIL_MSG);
		}
		community.setCommunityNotice(notice);
		redisRepo.set(REDIS_KEY_COMMUNITY + community.getId(), community);
		return resp;
	}
	
	/**
	 * 置顶圈子
	 * @Method: topCommunity
	 * @param cid
	 *            圈子id
	 * @param mid
	 *            操作人id
	 * @param isTop
	 *            true置顶 false取消置顶
	 * @return
	 */
	public CommonResponse topCommunity(Long cid, Long mid, boolean isTop) {
		
		ExpressMemberInfo member = expressMemberInfoService.findByMid(mid);
		if (member == null) { //mid不存在
			return CommonResponse.get(MemberInfo.NO_SUCH_MEMB, MemberInfo.NO_SUCH_MEMB_MSG);
		}
		// 当前操作人是不是这个圈子的成员
		
		expressMemberInfoService.topCommunity(cid, member, isTop);
		return null;
	}
	
	/**
	 * 置顶联系人
	 * @Method: topCommunity
	 * @param contactId
	 *            联系人id
	 * @param mid
	 *            操作人id
	 * @param isTop
	 *            true置顶 false取消置顶
	 * @return
	 */
	public CommonResponse topContact(Long contactId, Long mid, boolean isTop) {
		return expressMemberInfoService.topContast(mid, contactId, isTop);
	}
	
	
	/**
	 * 圈子消息免打扰
	 * @Method: interruptCommunity
	 * @param cid
	 * @param mid
	 * @param isInter
	 * @return
	 */
	public CommonResponse interruptCommunity(Long cid, Long mid, boolean isInter) {
		logger.info("memberId({})置顶圈子({})...{}", mid, cid, isInter);
		
		ExpressMemberInfo member = expressMemberInfoService.findByMid(mid);
		if (member == null) {
			logger.info("memberId({})置顶圈子({})时, memberId不存在", mid, cid);
			return CommonResponse.get(MemberInfo.NO_SUCH_MEMB, MemberInfo.NO_SUCH_MEMB_MSG);
		}
		// 是不是圈子的成员
		
		
		return expressMemberInfoService.interrupt(cid, member, isInter);
	}
	
	/**
	 * 联系人消息免打扰
	 * @Method: interruptCommunity
	 * @param targetId
	 * @param mid
	 * @param isInter
	 * @return
	 */
	public CommonResponse interruptContact(Long contactId, Long mid, boolean isInter) {
		logger.info("memberId({})置顶联系人({})...{}", mid, contactId, isInter);
		
		ExpressMemberInfo member = expressMemberInfoService.findByMid(mid);
		if (member == null) {
			logger.info("memberId({})置顶圈子({})时, memberId不存在", mid, contactId);
			return CommonResponse.get(MemberInfo.NO_SUCH_MEMB, MemberInfo.NO_SUCH_MEMB_MSG);
		}
		ExpressMemberInfo contact = expressMemberInfoService.findByMid(mid);
		if (contact == null) {
			logger.info("memberId({})置顶圈子({})时, 联系人不存在", mid, contactId);
			return CommonResponse.get(MemberInfo.NO_SUCH_CONTAST, MemberInfo.NO_SUCH_CONTAST_MSG);
		}
		return expressMemberInfoService.interrupt(contactId, member, isInter);
	}
	
	/**
	 * 清除聊天记录
	 * @Method: truncLog
	 * @param mid
	 * @param targetId
	 * @param op
	 *            operation 1:联系人聊天记录 2:圈子聊天记录
	 * @return
	 */
	public CommonResponse truncLog(Long mid, Long targetId, Integer op) {
		if (op == 1) { //联系人聊天记录
			ExpressMemberInfo targetMember = expressMemberInfoService.findByMid(targetId);
			if (targetMember == null || targetMember.getId() == null || targetMember.getId() == 0) {
				return CommonResponse.get(MemberInfo.NO_SUCH_CONTAST, MemberInfo.NO_SUCH_CONTAST_MSG);
			}
		} else {
			findCommunityById(targetId);
		}
		return expressMemberInfoService.truncLog(mid, targetId);
	}
	
	/**
	 * 加入圈子
	 * @Method: addToCommonity
	 * @param communId
	 * @param memId
	 * @param memName
	 * @param carType
	 * @param content
	 * @param pubTime
	 * @return
	 */
	public boolean addToCommonity(Long communId, Long memId, String memName, int carType, String content, Long pubTime) {
		CommunityMember communMember = new CommunityMember();
		communMember.setMid(memId);
		communMember.setMname(memName);
		communMember.setCardType(carType);
		communMember.setJoinTime(new java.util.Date());
		communMember.setLatestContent(content);
		communMember.setPubTime(new Date(pubTime));
		
		Query query = Query.query(where("_id").is(communId));
		Update update = new Update();
		update.addToSet("members", communMember);
		mongoTemplate.updateFirst(query, update, ExpressCommunity.class);
		ExpressCommunity ec = getCommunityFromRedis(communId);
		if (ec != null) {
			List<CommunityMember> membs = ec.getMembers();
			membs.add(communMember);
			Map<String, Double> valueScorePair = new HashMap<>();
			valueScorePair.put(memId.longValue() + "", Double.valueOf(String.valueOf(communMember.getJoinTime().getTime())));
			putCommunToRedis(ec, valueScorePair);
		}
		return true;
	}
	
	/**
	 * 圈子置顶聊天
	 * @Method: topCommunity
	 * @param memberId
	 * @param communId
	 * @return
	 */
	public boolean topCommunity(Long memberId, Long communId) {
		
		// 1. 更新memberId的topCommun字段(数组), 把communId addToset
		
		// 2. 更新缓存
		return true;
	}
	
	public boolean excludeCommunity(Long memberId, Long communId) {
		
		
		return true;
	}
	
	/*******************************************异步***********************************************/
	
	/**
	 * 管理员同意加入圈子: 更新申请状态, 加入圈子, 发送即时消息
	 * @Method: asyncHandleJoinCommunReq
	 * @param communId
	 * @param masterId
	 * @param reqId
	 */
	@Override
	public void asyncHandleJoinCommunReq(Long communId, Long masterId, Long reqId, String name) {
		
		logger.info("队列中异步处理id为::{}::加圈请求::{}, 管理员是::{}", reqId, communId, masterId);
		
		Query query = Query.query(where("communityId").is(communId)
				.and("proposer").is(reqId)
				.and("commMaster").is(masterId)
				.and("status").ne(JoinCommunityRequest.CHANGE_MASTER));
		
		Update update = new Update();
		update.set("status", 1);
		// 3. 更新数据库
		mongoTemplate.updateFirst(query, update, JoinCommunityRequest.class);
		
		// 4. 把这个人添加到圈子成员列表中  ??还需要查询这个人发布的最后一条帖子
		addToCommonity(communId, reqId, name, 1, "", 0L);
		// 5. 给圈子发送即时消息, 通知圈子里的所有成员, xxx加入该圈子
		
	}
	
	private void handleJoinCommunReqInMq(Long communId, Long masterId, Long reqId, String name) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "1"); //发帖时, 同步更新圈子
		jsonObj.put("communId", communId);
		jsonObj.put("masterId", masterId);
		jsonObj.put("reqId", reqId);
		jsonObj.put("name", name);
		asyncMessageSend(COMMON_ROUTING_KEY, jsonObj.toJSONString());
	}
	
	/****************************************为冗余提供的接口********************************************/
	
	/**
	 * 更新memberId所属的所有圈子的最新动态
	 * @Method: asyncUpdateCommunMemberLatestCard
	 * @param memberId
	 * @param textContent
	 * @param cardType
	 */
	@Override
	public void asyncUpdateCommunMemberLatestCard(Long memberId, String textContent, Integer cardType, Long pubTime) {
		
		logger.info("队列中异步更新圈子成员中的最新动态::{}", memberId);
		
		Query query = Query.query(where("members.id").is(memberId));
		Update update = new Update();
		update.set("members.$.latestContent", textContent);
		update.set("members.$.cardType", cardType);
		update.set("members.$.pubTime", new Date(pubTime));
		WriteResult wr = mongoTemplate.updateMulti(query, update, ExpressCommunity.class);
		wr.getError();
		
		//更新缓存
		
	}
	
	/**
	 * 更新memberId所属的所有圈子的最新动态
	 * @Method: asyncUpdateCommunMemberLatestCard
	 * @param memberId
	 * @param textContent
	 * @param cardType
	 */
	public void updateCommunMemberLatestCard(Long memberId, String textContent, Integer cardType, Long pubTime) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("type", "2"); //发帖时, 同步更新圈子
		jsonObj.put("memberId", memberId);
		jsonObj.put("textContent", textContent == null ? "" : textContent);
		jsonObj.put("cardType", cardType);
		jsonObj.put("pubTime", pubTime);
		asyncMessageSend(COMMON_ROUTING_KEY, jsonObj.toJSONString());
	}
	
	
	/**********************************************************************************************/
	
	/**
	 * 根据圈子id查询圈子
	 * @Method: findCommunityById
	 * @param id
	 * @return
	 */
	@Override
	public ExpressCommunity findCommunityById(Long id) {
		String key = REDIS_KEY_COMMUNITY + id;
		ExpressCommunity community = redisRepo.get(key, ExpressCommunity.class);
		if (null != community) {
			return community;
		}
		Query communityQuery = Query.query(where("_id").is(id));
		community = mongoTemplate.findOne(communityQuery, ExpressCommunity.class);
		if (null == community) {
			putCommunToRedis(new ExpressCommunity(), new HashMap<String, Double>());
			throw new ServiceException(Community.COMMUNITY_NOT_EXIST, Community.COMMUNITY_NOT_EXIST_MSG);
		}
		List<CommunityMember> members = community.getMembers();
		if (null == members) {
			members = new ArrayList<>();
		}
		
		Iterator<CommunityMember> iter = members.iterator();
		Map<String, Double> valueScorePair = new HashMap<>();
		while (iter.hasNext()) {
			CommunityMember member = iter.next();
			valueScorePair.put(member.getMid().toString(), Double.valueOf(member.getJoinTime().getTime() + ""));
		}
		putCommunToRedis(community, valueScorePair);
		return community;
	}

	private void saveJoinRequest(ExpressCommunity community, Long memberId) {
		String commName = community.getCommunityName();
		Long master = community.getMaster().getMid();
		JoinCommunityRequest request = new JoinCommunityRequest(community.getId(), commName, master);
		request.setProposer(memberId);
		//request.setProposerName(proposerName);	?? 还需要查询这个人的姓名
		request.setStatus(0);
		request.setProposeTime(System.currentTimeMillis());
		mongoTemplate.insert(request);
		//圈子管理员的加圈申请通知
		//redisRepo.hset(REDIS_KEY_JOINREQUEST + community.getId(), memberId.toString(), request);
	}
	
	private ExpressCommunity getCommunityFromRedis(Long id) {
		String key = REDIS_KEY_COMMUNITY + id;
		ExpressCommunity community = redisRepo.get(key, ExpressCommunity.class);
		return community;
	}
	
	private void putCommunToRedis(ExpressCommunity community, Map<String, Double> valueScorePair) {
		redisRepo.set(REDIS_KEY_COMMUNITY + community.getId(), community);
		redisRepo.zMset(REDIS_KEY_COMMMEMBERS + community.getId(), valueScorePair);
	}
	
	/**
	 * 获取memberId申请加入圈子
	 * @Method: getJoinReqFromRedis
	 * @param id
	 * @param memberId
	 * @return
	 */
	private JoinCommunityRequest getJoinReq(Long commid, Long memberId) {
		String key = REDIS_KEY_JOINREQUEST + commid;
		JoinCommunityRequest req = redisRepo.hget(key, memberId.toString(), JoinCommunityRequest.class);
		if (null != req) {
			return req;
		}
		Query requestQuery = Query.query(where("communityId").is(commid).and("proposer").is(memberId));
		req = mongoTemplate.findOne(requestQuery, JoinCommunityRequest.class);
		redisRepo.hset(key, memberId.toString(), req);
		return req;
	}
	
	private void asyncMessageSend(String routingKey, String msg) {
		rabbitMq.setPushRecordKey(routingKey);
		rabbitMq.sendDataToQueue(msg);
	}
	
	@SuppressWarnings("unused")
	private void sortCommunMember(List<CommunityMember> subList) {
		Collections.sort(subList, new Comparator<CommunityMember>() {
			public int compare(CommunityMember o1, CommunityMember o2) {
				return o2.getJoinTime().compareTo(o1.getJoinTime());
			}
		});
	}
	
	private void sortJoinRequest(List<JoinCommunityRequest> reqs) {
		Collections.sort(reqs, new Comparator<JoinCommunityRequest>() {
			@Override
			public int compare(JoinCommunityRequest o1, JoinCommunityRequest o2) {
				return o2.getProposeTime().compareTo(o1.getProposeTime());
			}
		});
	}
}




















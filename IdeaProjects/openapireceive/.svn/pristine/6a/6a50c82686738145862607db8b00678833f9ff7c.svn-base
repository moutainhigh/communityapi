package net.okdi.apiV4.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import net.okdi.apiV4.entity.ExpressCommunity;
import net.okdi.apiV4.entity.ExpressMemberInfo;
import net.okdi.apiV4.entity.ExpressMemberInfo.TopCommunity;
import net.okdi.apiV4.entity.ExpressMemberInfo.TruncLog;
import net.okdi.apiV4.service.ExpressCommunityService;
import net.okdi.apiV4.service.ExpressMemberInfoService;
import net.okdi.apiV4.vo.CommonResponse;
import net.okdi.apiV4.vo.ResultCode.Community;
import net.okdi.apiV4.vo.ResultCode.MemberInfo;
import net.okdi.apiV4.vo.ResultCode.Success;
import net.okdi.core.common.redis.RedisRepository;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;

/**
 * 快递用户   对应MySQL member_info表
 * @ClassName: ExpressMemberInfoServiceImpl
 * @Description: TODO
 * @author hang.yu
 * @date 2016年5月21日 下午4:46:41
 * @version V1.0
 */
public class ExpressMemberInfoServiceImpl implements ExpressMemberInfoService {

	private @Autowired MongoTemplate mongoTemplate;
	
	private @Autowired RedisRepository redisRepo;
	
	private @Autowired ExpressCommunityService expressCommunityService;
	
	private static final String REDIS_MEMBERINFO_KEY = "express.memberinf:";
	
	private static final Logger logger = LoggerFactory.getLogger(ExpressMemberInfoServiceImpl.class);
	
	/**
	 * 初始化导入, MySQL中的数据导入到mongo
	 * @Method: initExport
	 */
	public void initExport() {
		
	}
	
	/**
	 * 注册时同步到mongo
	 * @Method: addWhenRegiter
	 * @param memberId
	 * @param mname
	 * @param phone
	 */
	public void addWhenRegiter(Long memberId, String mname, String phone) {
		ExpressMemberInfo em = new ExpressMemberInfo();
		em.setId(memberId);
		em.setMname(PubMethod.isEmpty(mname) ? "okdi" + memberId : mname);
		em.setPhone(phone);
		em.setTs(new Date());
		mongoTemplate.save(em);
	}
	
	@Override
	public ExpressMemberInfo findByMid(Long memberId) {
		
		logger.info("查询memberId :: {} 的个人信息...", memberId);
		
		String key = REDIS_MEMBERINFO_KEY + memberId;
		
		ExpressMemberInfo memberInfo = redisRepo.get(key, ExpressMemberInfo.class);
		if (null != memberInfo) {
			if (memberInfo.getId() == null || memberInfo.getId() == 0) {
				return null;
			}
			return memberInfo;
		}
		Query query = Query.query(where("_id").is(memberId));
		memberInfo = mongoTemplate.findOne(query, ExpressMemberInfo.class);
		putMemberInfoToRedis(key, memberInfo == null ? new ExpressMemberInfo() : memberInfo);
		return memberInfo;
	}
	
	/** 
	 * 加入圈子时, 用户表中记录下这个人所加的圈子id
	 * @Method: joinCommunity
	 * @param cid
	 * @param mid
	 * @return
	 */
	public String joinCommunity(Long cid, Long mid) {
		String key = REDIS_MEMBERINFO_KEY + mid;
		
		ExpressMemberInfo member = findByMid(mid);
		if (member == null || member.getId() == null || member.getId() == 0) {
			logger.info("memberId({})申请加入圈子({})时, memberId不存在", mid, cid);
			return MemberInfo.NO_SUCH_MEMB;
		}
		
		ExpressCommunity community = expressCommunityService.findCommunityById(cid);
		if (community.getId() == null || community.getId() == 0) {
			return Community.COMMUNITY_NOT_EXIST;
		}
		
		Query query = Query.query(where("_id").is(mid));
		Update update = new Update();
		update.addToSet("joinCommuns", cid);
		WriteResult wr = mongoTemplate.updateFirst(query, update, ExpressMemberInfo.class);
		if (wr.getError() == null || wr.getN() == 0) {
			return Success.FAIL;
		}
		
		Set<Long> joinCommuns = member.getJoinCommuns();
		joinCommuns.add(cid);
		putMemberInfoToRedis(key, member);
		return Success.OK;
	}
	
	/**
	 * 退出圈子时, 移除
	 * @Method: leaveCommunity
	 * @param cid
	 * @param mid
	 * @return
	 */
	public String leaveCommunity(Long cid, Long mid) {
		
		logger.info("memberId({})退出圈子({})", mid, cid);
		
		ExpressMemberInfo member = findByMid(mid);
		if (member == null || member.getId() == null || member.getId() == 0) {
			logger.info("memberId({})退出圈子({})时, memberId不存在", mid, cid);
			return MemberInfo.NO_SUCH_MEMB;
		}
		
		Query query = Query.query(where("_id").is(mid));
		Update update = new Update();
		update.pull("joinCommuns", cid);
		mongoTemplate.updateFirst(query, update, ExpressMemberInfo.class);
		
		Set<Long> joinCommuns = member.getJoinCommuns();
		if (joinCommuns == null || joinCommuns.size() == 0) {
			return Success.OK;
		}
		Iterator<Long> iter = joinCommuns.iterator();
		while (iter.hasNext()) {
			Long id = iter.next();
			if (id.longValue() == cid.longValue()) {
				iter.remove();
				break;
			}
		}
		member.setJoinCommuns(joinCommuns);
		putMemberInfoToRedis(REDIS_MEMBERINFO_KEY + mid, member);
		return Success.OK;
	}
	
	/**
	 * 置顶圈子
	 * @Method: topCommunity
	 * @param cid: 圈子id
	 * @param mid: memberId
	 * @param isTop true: 置顶   false:取消置顶
	 * @return
	 */
	@Override
	public CommonResponse topCommunity(Long cid, ExpressMemberInfo member, boolean isTop) {
		
		logger.info("memberId({})置顶圈子({})...", member.getId(), cid);
		
		Map<Long, TopCommunity> tops = member.getTopCommuns() == null ? new HashMap<Long, TopCommunity>() : member.getTopCommuns();
		
		if (isTop) { //置顶
			member.setTopCommuns(topCommnuity(cid, tops));
			return doTop(member);
		}
		// 取消置顶
		if (tops.size() == 0) {
			return CommonResponse.get();
		}
		tops.remove(cid);
		member.setTopCommuns(tops);
		return doTop(member);
	}
	
	/**
	 * 置顶单聊
	 * @Method: topContast
	 * @param mid			: 操作人id
	 * @param targetMid		: 目标人id
	 * @param isTop			: true置顶 false取消置顶
	 * @return
	 */
	@Override
	public CommonResponse topContast(Long mid, Long targetMid, boolean isTop) {
		
		logger.info("memberId({})置顶联系人({})...{}", mid, targetMid, isTop);
		
		ExpressMemberInfo member = findByMid(mid);
		if (member == null) {
			logger.info("memberId({})置顶联系人({})时, memberId不存在", mid, targetMid);
			return CommonResponse.get(MemberInfo.NO_SUCH_MEMB, MemberInfo.NO_SUCH_MEMB_MSG);
		}
		
		ExpressMemberInfo contastMember = findByMid(mid);
		if (contastMember == null) {
			logger.info("memberId({})置顶联系人({})时, 联系人不存在", mid, targetMid);
			return CommonResponse.get(MemberInfo.NO_SUCH_CONTAST, MemberInfo.NO_SUCH_CONTAST_MSG);
		}
		
		Map<Long, TopCommunity> tops = member.getTopCommuns() == null ? new HashMap<Long, TopCommunity>() : member.getTopCommuns();
		
		if (isTop) { //置顶
			member.setTopCommuns(topCommnuity(targetMid, tops));
			return doTop(member);
		}
		// 取消置顶
		if (tops.size() == 0) {
			return CommonResponse.get();
		}
		tops.remove(targetMid);
		member.setTopCommuns(tops);
		return doTop(member);
	}

	/**
	 * 圈子消息免打扰
	 * @Method: interrupt
	 * @param cid
	 * @param mid
	 * @param isInter
	 * @return
	 */
	@Override
	public CommonResponse interrupt(Long targetId, ExpressMemberInfo member, boolean isInter) {
		
		Set<Long> ics = member.getInterrupt() == null ? new HashSet<Long>() : member.getInterrupt();
		if (isInter) {
			ics.add(targetId);
			member.setInterrupt(ics);
			return doInterrupt(member, targetId, isInter);
		}
		if (ics.size() == 0) {
			return CommonResponse.get();
		}
		ics.remove(targetId);
		member.setInterrupt(ics);
		return doInterrupt(member, targetId, isInter);
	}
	
	/**
	 * 情空聊天记录
	 * @Method: truncLog
	 * @param mid
	 *            当然操作人id
	 * @param targetId
	 *            圈子id或者联系人id
	 * @return
	 */
	@Override
	public CommonResponse truncLog(Long mid, Long targetId) {
		logger.info("memberId({})清除圈子({})聊天记录...", mid, targetId);
		
		CommonResponse resp = CommonResponse.get();
		
		ExpressMemberInfo member = findByMid(mid);
		if (member == null || member.getId() == null || member.getId() == 0) {
			return resp.rc(MemberInfo.NO_SUCH_MEMB).err(MemberInfo.NO_SUCH_MEMB_MSG);
		}
		
		Map<Long, TruncLog> truncLogs = member.getTruncLog() == null ? new HashMap<Long, TruncLog>() : member.getTruncLog();
		TruncLog truncLog = truncLogs.get(targetId);
		if (truncLog == null) {
			truncLog = new ExpressMemberInfo().new TruncLog(targetId, new Date());
		} else {
			truncLog.setTs(new Date());
		}
		truncLogs.put(targetId, truncLog);
		
		Query query = Query.query(where("_id").is(mid));
		Update update = new Update();
		update.set("truncLog", truncLogs);
		WriteResult wr = mongoTemplate.updateFirst(query, update, ExpressMemberInfo.class);
		if (null != wr.getError()) {
			return resp.rc(Success.FAIL).err(Success.FAIL_MSG);
		}
		member.setTruncLog(truncLogs);
		putMemberInfoToRedis(REDIS_MEMBERINFO_KEY + member.getId(), member);
		return resp;
	}
	
	/**
	 * 是否置顶, 是否消息免打扰
	 * @Method: isTopOrInterru
	 * @param mid
	 * @param targetId: 圈子id或者联系人id
	 * @return
	 */
	@Override
	public Map<String, Object> isTopOrInterru(Long mid, Long targetId) {
		Map<String, Object> map = new HashMap<>();
		// 是否置顶   0否1是
		map.put("top", 0);
		// 是否消息免打扰
		map.put("interru", 0);
		
		ExpressMemberInfo member = findByMid(mid);
		if (member == null || member.getId() == null || member.getId() == 0) {
			throw new ServiceException(MemberInfo.NO_SUCH_MEMB, MemberInfo.NO_SUCH_MEMB_MSG);
		}
		
		Map<Long, TopCommunity> topCommuns = member.getTopCommuns();
		int top = topCommuns.get(targetId) == null ? 0 : 1;
		map.put("top", top);
		
		Set<Long> ics = member.getInterrupt();
		if (ics.contains(targetId)) {
			map.put("interru", 1);
		}
		return map;
	}
	
	/**
	 * 置顶圈子
	 * @Method: topCommnuity
	 * @param cid
	 * @param member
	 * @return
	 */
	private Map<Long, TopCommunity> topCommnuity(Long cid, Map<Long, TopCommunity> tops) {
		TopCommunity topc = tops.get(cid);
		if (topc != null) {
			topc.setTs(new Date());
		} else {
			topc = new ExpressMemberInfo().new TopCommunity(cid, new Date());
		}
		tops.put(cid, topc);
		return tops;
	}
	
	//===========================================================================================//
	
	private CommonResponse doTop(ExpressMemberInfo member) {
		CommonResponse resp = CommonResponse.get();
		
		Query query = Query.query(where("_id").is(member.getId()));
		Update update = new Update();
		update.set("topCommuns", member.getTopCommuns());
		WriteResult wr = mongoTemplate.updateFirst(query, update, ExpressMemberInfo.class);
		if (null != wr.getError()) {
			resp.rc(Success.FAIL).err(Success.FAIL_MSG);
			return resp;
		}
		putMemberInfoToRedis(REDIS_MEMBERINFO_KEY + member.getId(), member);
		return resp;
	}
	
	private CommonResponse doInterrupt(ExpressMemberInfo member, Long cid, boolean isInter) {
		CommonResponse resp = CommonResponse.get();
		
		Query query = Query.query(where("_id").is(member.getId()));
		Update update = new Update();
		update = isInter ? update.addToSet("interrupt", cid) : update.pull("interrupt", cid);
		WriteResult wr = mongoTemplate.updateFirst(query, update, ExpressMemberInfo.class);
		if (null != wr.getError()) {
			return resp.rc(Success.FAIL).err(Success.FAIL_MSG);
		}
		putMemberInfoToRedis(REDIS_MEMBERINFO_KEY + member.getId(), member);
		return resp;
	}
	
	private void putMemberInfoToRedis(String key, ExpressMemberInfo member) {
		redisRepo.set(key, member == null ? new ExpressMemberInfo() : member);
	}
}










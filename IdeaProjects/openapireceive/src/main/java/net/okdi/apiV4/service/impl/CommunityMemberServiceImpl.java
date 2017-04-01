package net.okdi.apiV4.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.alibaba.fastjson.JSON;

import net.okdi.apiV4.entity.CommunityMember;
import net.okdi.apiV4.entity.ExpressCommunity;
import net.okdi.apiV4.service.CommunityMemberService;
import net.okdi.core.common.redis.RedisRepository;

/**
 * 圈子成员
 * @ClassName: CommunityMemberServiceImpl
 * @Description: TODO
 * @author hang.yu
 * @date 2016年5月26日 下午5:09:38
 * @version V1.0
 */
public class CommunityMemberServiceImpl implements CommunityMemberService {

	private @Autowired RedisRepository redisRepo;
	
	private @Autowired MongoTemplate mongoTemplate;
	
	private static final int PAGE_SIZE = 20;
	
	/**
	 * 圈子成员列表分页：key => 固定key + 圈子id
	 */
	private static final String REDIS_COMMUNITY_MEMBERPAGE_KEY = "community.memberpage:";
	
	/**
	 * 圈子成员列表：key => 固定key + 圈子id
	 */
	private static final String REDIS_COMMUNITY_MEMBERLIST_KEY = "community.memberlist:";
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommunityMemberServiceImpl.class);
	
	public CommunityMember getMemberById(Long cid, Long mid) {
		
		CommunityMember cm = getMemberFromRedis(cid, mid);
		if (cm != null) {
			return cm;
		}
		return null;
	}
	
	/**
	 * 圈子成员列表
	 * @Method: communityMemberList
	 * @param id
	 * @param currPage
	 * @return
	 */
	@Override
	public List<CommunityMember> communityMemberList(ExpressCommunity community, Integer currPage) {
		
		List<CommunityMember> members = findCommunityMember(community.getId(), currPage);
		
		if (currPage != 1) {
			return members;
		}
		
		CommunityMember master = new CommunityMember();
		master.setMid(community.getMaster().getMid());
		master.setLatestContent(community.getMaster().getLatestContent());
		master.setCardType(community.getMaster().getCardType());
		master.setMname(community.getMaster().getMname());
		master.setJoinTime(community.getMaster().getUpdateTime());
		members.add(0, master);
		return members;
	}
	
	private List<CommunityMember> findCommunityMember(Long cid, int currPage) {
		int start = (currPage - 1) * PAGE_SIZE;
		int end = currPage * PAGE_SIZE;
		String pageKey = REDIS_COMMUNITY_MEMBERPAGE_KEY + cid;
		String listKey = REDIS_COMMUNITY_MEMBERLIST_KEY + cid;
		
		List<Object> memberIds = redisRepo.zgetObject(pageKey, start, end);
		if (memberIds.size() < 1) {
			Query query = Query.query(Criteria.where("communityId").is(cid))
					.skip(currPage * PAGE_SIZE).limit(currPage == 1 ? PAGE_SIZE - 1 : PAGE_SIZE)
					.with(new Sort(Direction.ASC, "joinTime"));
			List<CommunityMember> memberPage = mongoTemplate.find(query, CommunityMember.class);
			putMemberPageToRedis(cid, memberPage);
			return memberPage;
		}
		List<CommunityMember> members = redisRepo.hMget(listKey, memberIds, CommunityMember.class);
		sort(members);
		return members;
	}
	
	private void putMemberPageToRedis(Long cid, List<CommunityMember> memberPage) {
		Set<TypedTuple<String>> tuples = new HashSet<>();
		
		Map<String, String> map = new HashMap<>();
		for (CommunityMember cm : memberPage) {
			Double socre = Double.valueOf(cm.getJoinTime().getTime());
			
			TypedTuple<String> tuple = new DefaultTypedTuple<String>(cm.getMid().toString(), socre);
			tuples.add(tuple);
			map.put(cm.getMid().toString(), JSON.toJSONString(cm));
		}
		
		redisRepo.zMset(REDIS_COMMUNITY_MEMBERPAGE_KEY + cid, tuples);
		redisRepo.hMset(REDIS_COMMUNITY_MEMBERLIST_KEY + cid, map);
	}
	
	private void putCommMemberToRedis(Long cid, CommunityMember cm) {
		redisRepo.hset(REDIS_COMMUNITY_MEMBERLIST_KEY + cid, cm.getMid().toString(), cm);
	}
	
	private CommunityMember getMemberFromRedis(Long cid, Long mid) {
		return redisRepo.hget(REDIS_COMMUNITY_MEMBERLIST_KEY + cid, mid.toString(), CommunityMember.class);
	}
	
	private CommunityMember findMemberFromDB(Long cid, Long mid) {
		Query query = Query.query(Criteria.where("communityId").is(cid).and("mid").is(mid));
		CommunityMember cm = mongoTemplate.findOne(query, CommunityMember.class);
		return null;
	}
	
	private void sort(List<CommunityMember> members) {
		Collections.sort(members, new Comparator<CommunityMember>() {
			@Override
			public int compare(CommunityMember o1, CommunityMember o2) {
				return o1.getJoinTime().compareTo(o2.getJoinTime());
			}
		});
	}
}






















package net.okdi.apiV4.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.okdi.apiV4.entity.Attention;
import net.okdi.apiV4.entity.Card;
import net.okdi.apiV4.entity.CardComment;
import net.okdi.apiV4.entity.CardLike;
import net.okdi.apiV4.entity.DynamicNotice;
import net.okdi.apiV4.entity.Vo_Card;
import net.okdi.apiV4.service.CardService;
import net.okdi.core.common.redis.RedisRepository;
import net.okdi.core.exception.ServiceException;
import net.okdi.core.util.PubMethod;
import net.okdi.core.util.service.ConstPool;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.amssy.common.util.primarykey.IdWorker;

@Service
public class CardServiceImpl implements CardService{
	private static Logger logger = Logger.getLogger(CardServiceImpl.class);
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private RedisRepository redisRepo;
	@Autowired
	private ConstPool constPool;
	
	@Override
	public String publishCard(Long cardId,Long createorMemberId, String type, String content,
			String imageUrl, String videoUrl) {
		Card card=new Card();
		card.setCardId(cardId);
		card.setReadCount(0);
		card.setStatus("0");
		card.setCreateTime(new Date());
		card.setUpCount(0);
		card.setType(type);
		card.setCreateorMemberId(createorMemberId);
		card.setContent(content);
		if(!PubMethod.isEmpty(imageUrl)){
			String[] list=imageUrl.split(",");
			card.setImageUrl(Arrays.asList(list));
		}else{
			card.setImageUrl(null);
		}
		card.setVideoUrl(videoUrl);
		mongoTemplate.save(card);
		return String.valueOf(cardId);
	}

	@Override
	public String cardReadCountAdd(Long cardId) {
		Query query=new Query();
		query.addCriteria(Criteria.where("cardId").is(cardId));
		Update update=new Update();
		update.inc("readCount", 1);
		mongoTemplate.updateFirst(query, update, Card.class);
		return String.valueOf(cardId);
	}

	@Override
	public String cardLikeCountAdd(Long cardId, Long memberId) {
		//查询是否已经点过赞
		Query queryLike=new Query();
		queryLike.addCriteria(Criteria.where("cardId").is(cardId).and("memberId").is(memberId));
		CardLike like=mongoTemplate.findOne(queryLike, CardLike.class);
		if(!PubMethod.isEmpty(like)){
				throw new ServiceException("你已经点过赞了");
			
		}
		Query query=new Query();
		query.addCriteria(Criteria.where("cardId").is(cardId));
		Update update=new Update();
		update.inc("upCount", 1);
		mongoTemplate.updateFirst(query, update, Card.class);
		CardLike cardLike=new CardLike();
		cardLike.setCardId(cardId);
		cardLike.setCardLikeId(IdWorker.getIdWorker().nextId());
		cardLike.setCreateTime(new Date());
		cardLike.setMemberId(memberId);
		mongoTemplate.save(cardLike);
		Card card=mongoTemplate.findOne(query, Card.class);
		
		DynamicNotice dynamicNotice=new DynamicNotice();
		dynamicNotice.setCreateTime(new Date());
		dynamicNotice.setIsRead((short)1);
		dynamicNotice.setNoticeId(IdWorker.getIdWorker().nextId());
		dynamicNotice.setCardId(cardId);
		dynamicNotice.setType((short)1);
		dynamicNotice.setFromMemberId(memberId);
		dynamicNotice.setToMemberId(card.getCreateorMemberId());
		mongoTemplate.save(dynamicNotice);
		
		return "1";
	}

	@Override
	public Vo_Card queryCardDetial(Long cardId, Long memberId) {
		Vo_Card cards=new Vo_Card();
		Query query=new Query();
		query.addCriteria(Criteria.where("cardId").is(cardId));
		Card card=mongoTemplate.findOne(query, Card.class);
		cards.setCardId(card.getCardId());
		cards.setCreateorMemberId(card.getCreateorMemberId());
		cards.setCreateTime(card.getCreateTime());
		cards.setContent(card.getContent());
		cards.setImageUrl(card.getImageUrl());
		cards.setBaseUrl(constPool.getReadPath()+constPool.getExpressCardUrl());
		//评论详情稍后加;
		Query cQuery=new Query();
		cQuery.addCriteria(Criteria.where("cardId").is(cardId));
		List<CardComment> comments=mongoTemplate.find(cQuery, CardComment.class);
		cards.setComments(comments);
		return cards;
	}

	@Override
	public List<Map<String, Object>> queryHotCard(Long memberId,Integer currentPage,Integer pageCount) {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Query query=new Query();
		query.with(new Sort(Direction.DESC,"createTime")).with(new Sort(Direction.DESC,"readCount"));
		query.skip((currentPage-1)*pageCount);
		query.limit(pageCount);
		List<Card> cards=mongoTemplate.find(query, Card.class);
		for(Card card:cards){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("content", card.getContent());
			map.put("creatorMemberId", card.getCreateorMemberId());
			map.put("baseUrl", constPool.getReadPath()+constPool.getExpressCardUrl());
			map.put("imageUrl", card.getImageUrl());
			map.put("videoUrl", card.getVideoUrl());
			map.put("type", card.getType());
			map.put("createTime", card.getCreateTime());
			map.put("readCount", card.getReadCount());
			long upLike=mongoTemplate.count(new Query().addCriteria(
					Criteria.where("cardId").is(card.getCardId()).and("memberId").is(memberId)), CardLike.class);
			map.put("isUpLike", upLike);
			//评论数
			Query cQuery=new Query();
			cQuery.addCriteria(Criteria.where("careId").is(card.getCardId()));
			Long commentCount=mongoTemplate.count(cQuery, CardComment.class);
			map.put("commentCount", commentCount);
			list.add(map);
		}
		return list;
	}

	@Override
	public String replyCard(Long cardId, String type, Long fromMemberId,
			Long toMemberId, String fromMemberName, String toMemberName,String content) {
		CardComment cardComment=new CardComment();
		cardComment.setCardCommentId(IdWorker.getIdWorker().nextId());
		cardComment.setCardId(cardId);
		Date date=new Date();
		cardComment.setCommentTime(date);
		cardComment.setCommentType(type);
		cardComment.setFromMemberId(fromMemberId);
		cardComment.setFromMemberName(fromMemberName);
		cardComment.setToMemberId(toMemberId);
		cardComment.setToMemberName(toMemberName);
		cardComment.setContent(content);
		mongoTemplate.save(cardComment);
		
		DynamicNotice dynamicNotice=new DynamicNotice();
		dynamicNotice.setCardId(cardId);
		dynamicNotice.setCreateTime(date);
		dynamicNotice.setFromMemberId(fromMemberId);
		dynamicNotice.setIsRead((short)1);
		dynamicNotice.setNoticeId(IdWorker.getIdWorker().nextId());
		dynamicNotice.setToMemberId(toMemberId);
		dynamicNotice.setType((short)3);
		mongoTemplate.save(dynamicNotice);
		return "1";
	}

	@Override
	public String deleteCardComment(Long cardId, Long memberId) {
		Query query=new Query();
		query.addCriteria(Criteria.where("cardId").is(cardId).and("fromMemberId").is(memberId));
		mongoTemplate.remove(query, CardComment.class);
		return "1";
	}

	/**
	 * 查询最新发表的动态
	 * @Method: findLatestCard
	 * @param memberId
	 * @return
	 */
	@Override
	public Card findLatestCard(Long memberId) {
		Card latestCard = redisRepo.get("lastestCard:" + memberId, Card.class);
		if (latestCard != null) {
			return latestCard;
		}
		Query query = Query.query(Criteria.where("createorMemberId").is(memberId));
		query.with(new Sort(Direction.DESC, "createTime"));
		latestCard = mongoTemplate.findOne(query, Card.class);
		redisRepo.set("lastestCard:" + memberId, latestCard);
		return latestCard;
	}
	@Override
	public Map<String, Object> taHomePage(Long memberId, Long taMemberId) {
		Map<String, Object> map=new HashMap<String, Object>();
		Query query =new Query();
		query.addCriteria(Criteria.where("createorMemberId").is(taMemberId));
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		
		List<Card> cards=mongoTemplate.find(query, Card.class);
		for(Card card:cards){
			Map<String, Object> mapCard=new HashMap<String, Object>();
			mapCard.put("upCount", card.getUpCount());
			mapCard.put("content", card.getContent());
			mapCard.put("createTime", card.getCreateTime());
			mapCard.put("imageUrl", card.getImageUrl());
			mapCard.put("videoUrl", card.getVideoUrl());
			mapCard.put("taMemberName", null);//TODO 查询他的姓名
			Query queryC=new Query();
			queryC.addCriteria(Criteria.where("cardId").is(card.getCardId()));
			Long count=mongoTemplate.count(queryC, CardComment.class);
			mapCard.put("cardCommentCout", count);
			list.add(mapCard);
			
		}
		map.put("cards", list);
		map.put("taMemberId", taMemberId);
		map.put("baseUrl", constPool.getReadPath());
		//是否关注
		
		return map;
	}

	@Override
	public String addAttention(Long fromMemberId, Long toMemberId) {
		//查询是否关注过
		Query query=new Query();
		query.addCriteria(Criteria.where("fromMemberId").is(fromMemberId).and("toMemberId").is(toMemberId));
		Attention atten=mongoTemplate.findOne(query, Attention.class);
		if(!PubMethod.isEmpty(atten)){
			throw new ServiceException("已经关注过此人");
		}
		Attention attention=new Attention();
		attention.setAttentionId(IdWorker.getIdWorker().nextId());
		attention.setCreateTime(new Date());
		attention.setFromMemberId(fromMemberId);
		attention.setToMemberId(toMemberId);
		attention.setIsRead((short)1);
		//判断对方是否关注我(toMemberId是否关注fromMemberId) 0 否 1 是
		Query query2=Query.query(Criteria.where("fromMemberId").is(toMemberId).and("toMemberId").is(fromMemberId));
		Attention att = mongoTemplate.findOne(query2, Attention.class);
		if(PubMethod.isEmpty(att)){
			attention.setIsAttention((short)1);
		}else{
			attention.setIsAttention((short)0);
		}
		mongoTemplate.save(attention);
		//给被关注者推送加关注消息 TODO
		return "1";
	}

	
}











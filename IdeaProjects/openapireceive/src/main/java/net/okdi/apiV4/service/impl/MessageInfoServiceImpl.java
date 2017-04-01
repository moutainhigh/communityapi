package net.okdi.apiV4.service.impl;

import net.okdi.apiV1.entity.AnnounceMessageInfo;
import net.okdi.apiV4.service.MessageInfoService;
import net.okdi.core.common.ehcache.RedisService;
import net.okdi.core.util.PubMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoServiceImpl implements MessageInfoService{

	@Autowired
	private RedisService redisService;
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Long queryMessageTime() {
		AnnounceMessageInfo announceMessageInfo=redisService.get("messageInfo", "newestTime", AnnounceMessageInfo.class);
		if(PubMethod.isEmpty(announceMessageInfo)){
			Query query=new Query();
			query.addCriteria(new Criteria());
			query.addCriteria(new Criteria("status").is(0));
			query.with(new Sort(Direction.DESC,"createTime"));//修改排序字段，为创建时间
			announceMessageInfo=new AnnounceMessageInfo();
			announceMessageInfo=mongoTemplate.findOne(query,AnnounceMessageInfo.class);
			redisService.put("messageInfo", "newestTime", announceMessageInfo);
			return announceMessageInfo.getCreateTime().getTime();
		}
		return announceMessageInfo.getCreateTime().getTime();
	}
	
}

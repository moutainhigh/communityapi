package net.okdi.apiV4.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import net.okdi.apiV4.entity.ChinaRegion;
import net.okdi.apiV4.service.ChinaRegionService;
import net.okdi.core.common.redis.RedisRepository;

public class ChinaRegionServiceImpl implements ChinaRegionService{

	private @Autowired MongoTemplate mongoTemplate;
	
	private @Autowired RedisRepository redisRepo;
	
	private static final String REDIS_REGION_KEY = "region";
	
	private static final Logger logger = LoggerFactory.getLogger(ChinaRegionServiceImpl.class);
	
	@Override
	public List<ChinaRegion> findAllRegion() {
		
		List<ChinaRegion> allRegion = redisRepo.hGetAll(REDIS_REGION_KEY, ChinaRegion.class);
		if (null != allRegion) {
			return allRegion;
		}
		allRegion = mongoTemplate.findAll(ChinaRegion.class);
		for (ChinaRegion cr : allRegion) {
			redisRepo.hsetNoTime(REDIS_REGION_KEY, cr.getName(), cr);
		}
		return allRegion;
	}
	
	@Override
	public ChinaRegion findByName(String name) {
		
		ChinaRegion cr = redisRepo.hget(REDIS_REGION_KEY, name, ChinaRegion.class);
		if (cr != null) {
			return cr;
		}
		int reties = 1;
		while (null == cr || reties < 2) {
			logger.info("redis中{}为null, 查询全部地区并重试, 基数 => ", name, reties);
			findAllRegion();
			cr = redisRepo.hget(REDIS_REGION_KEY, name, ChinaRegion.class);
			reties++;
		}
		logger.info("查询name为{}的id, 重试 {}次任然为null, 直接返回null", name, reties);
		return null;
	}
	
	
}

















package net.okdi.core.common.redis;

import java.util.List;

import net.okdi.api.entity.BasCompInfo;
import net.okdi.api.service.InitCacheService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;

public class RedisService {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private RedisTemplate redisTemplate;	

	// 获取spring注入的bean对象
	private WebApplicationContext springContext;
	
	//写入公司信息到redis
	public void loadCompanyInfo() {
//		Cache cache = manager.getCache(cacheName);
//		if (cache == null) {
//			return;
//		}
//		Element element = new Element(key, value);
//		cache.put(element);
//		logger.debug("写入缓存日志----->缓存名称:"+cacheName+",缓存key:"+key);
		
//		redisTemplate.opsForHash().put(user.getObjectKey(), user.getKey(), user);
		
		
		
		List<BasCompInfo> compList = (List<BasCompInfo>) ((InitCacheService) springContext.getBean("initCacheService")).getCompList();
		for (int i = 0; i < compList.size(); i++) {
			net.okdi.api.entity.BasCompInfo entity = compList.get(i);
//			ehcacheService.put("compCache", String.valueOf(entity.getCompId()), JSON.toJSONString(entity));
			redisTemplate.opsForHash().put("companyRedis", entity.getCompId(), JSON.toJSONString(entity));
		}
		
	}	
	
	
	
}

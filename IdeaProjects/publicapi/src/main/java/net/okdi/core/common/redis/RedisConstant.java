package net.okdi.core.common.redis;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service("redisConstant")
public class RedisConstant{
		
	private Map<String,Long> redisExpireTimeMap;


	//判断cacheName是否存在
	public boolean isExistCache(String cacheName){
		if(redisExpireTimeMap.containsKey(cacheName)){
			return true;
		}else{
			return false;
		}
	}
	
	//根据cacheName获取过期
	public long getExpireTime(String cacheName){
		if(redisExpireTimeMap.containsKey(cacheName)){
			return redisExpireTimeMap.get(cacheName);
		}else{
			return -1;
		}
	}
	public RedisConstant() {
	}
	public RedisConstant(Map<String, Long> redisExpireTimeMap) {
		super();
		this.redisExpireTimeMap = redisExpireTimeMap;
	}
	
}

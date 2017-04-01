package net.okdi.api.service.impl;

import java.util.List;

import net.okdi.api.service.EhcacheService;
import net.okdi.core.util.PubMethod;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class EhcacheServiceImpl implements EhcacheService {

	@Autowired
	private CacheManager manager;

	/**
	 * @Method: put
	 * @Description: 根据缓存名称、唯一key、值缓存数据
	 * @param cacheName
	 *            名称
	 * @param key
	 *            唯一key
	 * @param value
	 *            缓存的对象
	 */
	public void put(String cacheName, String key, String value) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		Element element = new Element(key, value);
		cache.put(element);
	}
	
	/**
	 * @Method: put
	 * @Description: 根据缓存名称、唯一key、值缓存数据
	 * @param cacheName
	 *            名称
	 * @param key
	 *            唯一key
	 * @param value
	 *            缓存的对象
	 */
	public void put(String cacheName, String key, Object value) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		Element element = new Element(key,JSON.toJSONString(value));
		cache.put(element);
	}

	/**
	 * @Method: get
	 * @Description: 根据缓存名称、唯一key获取缓存的值
	 * @param cacheName
	 *            名称
	 * @param key
	 *            唯一key
	 * @return 缓存对象
	 */
	public <T> T get(String cacheName, String key, Class<T> className) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return null;
		}
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		if (PubMethod.isEmpty(className) || String.class == className) {
			return (T) element.getObjectValue();
		}
		return JSON.parseObject(element.getObjectValue() == null ? null : element.getObjectValue()
				.toString(), className);
	}

	/**
	 * @Method: remove
	 * @Description: 根据缓存名称、唯一key删除缓存对象
	 * @param cacheName
	 *            名称
	 * @param key
	 *            唯一key
	 * @return
	 */
	public void remove(String cacheName, String key) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		cache.remove(key);
	}

	/**
	 * 清空多个缓存
	 * 
	 * @param cacheName
	 *            缓存名称
	 * @param keys
	 *            key列表
	 */
	public void remove(String cacheName, List<String> keys) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		if (keys == null || keys.size() == 0) {
			return;
		}
		for (String key : keys) {
			cache.remove(key);
		}
	}

	/**
	 * @Method: removeAll
	 * @Description: 删除对应缓存名称下的所有缓存
	 * @param cacheName
	 *            名称
	 * @return
	 */
	public void removeAll(String cacheName) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		cache.removeAll();
	}
}

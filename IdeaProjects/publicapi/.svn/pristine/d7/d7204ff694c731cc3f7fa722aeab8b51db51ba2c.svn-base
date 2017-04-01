package net.okdi.core.common.ehcache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.okdi.core.util.PubMethod;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;

public class EhcacheServiceImpl implements EhcacheService{

	private static Logger logger = Logger.getLogger(EhcacheServiceImpl.class);

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
		Element element = new Element(key,value);
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
		return JSON.parseObject(element.getObjectValue() == null ? null : element.getObjectValue().toString(), className);
	}
	

	/**
	 * @Method: getByName
	 * @Description: 根据缓存名称判断缓存里面是否有值
	 * @param cacheName
	 *            名称
	 * @return 缓存对象是否存在 false:不存在 true:存在
	 */
	public boolean getByName(String cacheName) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return false;
		}
		Map<Object, Element> map = cache.getAll(cache.getKeys());
		if (map.size() == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * @Method: getByKey
	 * @Description: 根据缓存名称和key判断缓存里面是否有值
	 * @param cacheName
	 *            名称
	 * @param key
	 *            唯一key
	 * @return 缓存对象是否存在 false:不存在 true:存在
	 */
	public boolean getByKey(String cacheName,String key) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return false;
		}
		Element element = cache.get(key);
		if (element == null) {
			return false;
		}
		return true;
	}

	/**
	 * @Method: get
	 * @Description: 获取对应名称下的所有缓存
	 * @param cacheName
	 *            名称
	 * @return 缓存对象
	 */
	public <T> List<T> getAll(String cacheName, Class<T> className) {
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return null;
		}
		Map<Object, Element> map = cache.getAll(cache.getKeys());
		List<T> list = new ArrayList<T>();
		for (Map.Entry<Object, Element> entry : map.entrySet()) {
			Object object = entry.getValue().getObjectValue();
			if (PubMethod.isEmpty(className) || String.class == className) {
				list.add((T) object);
			} else {
				list.add((T) JSON.parseObject(object == null ? null : object.toString(), className));
			}
		}
		return list;
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
	 * @param cacheName 缓存名称
	 * @param keys key列表
	 */
	public void remove(String cacheName,List<String> keys){
		Cache cache = manager.getCache(cacheName);
		if (cache == null) {
			return;
		}
		if(keys==null||keys.size()==0){
			return;
		}
		for(String key:keys){
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
	//查询所有的CACHE名称
	@Override
	public String[] findAllName() {
		
		return manager.getCacheNames();
	}
	//根据cache名称查询所有的key和value
	public List getAllKeyAndValue(String cacheName,int numPerPage,int pageNum,Long totalCount){
		Cache cache = manager.getCache(cacheName);
		List keyValueList=new ArrayList<Map>();
		if (cache == null) {
			return null;
		}
		List<String> list=cache.getKeys();
		if(list.size()==0){
			return null;
		}
		int j=0;
		if(pageNum==(int)(totalCount/numPerPage)+1){
			j=numPerPage-(int)(totalCount%numPerPage);
		}
		for(int i=numPerPage*(pageNum-1);i<numPerPage*pageNum-j;i++){
			Map map=new HashMap<String, String>();
			map.put("key", list.get(i));
			map.put("value", cache.get(list.get(i)).getObjectValue());
			keyValueList.add(map);
		}
		/*for(String key:list){
			Map map=new HashMap<String, String>();
			map.put("key", key);
			map.put("value", cache.get(key).getObjectValue());
			keyValueList.add(map);
		}*/
		return keyValueList;
		
	}
	//根据Cache和key查询value
	public List getValueByKey(String cacheName,String key){
		Cache cache = manager.getCache(cacheName);
		List keyValueList=new ArrayList<Map>();
		Map map=new HashMap<String, String>();
		map.put("key", key);
		map.put("value", cache.get(key).getObjectValue());
		keyValueList.add(map);
		return keyValueList;
	}
	//根据cache名称查询key数量
	public Long getKeyList(String cacheName){
		Cache cache = manager.getCache(cacheName);
		return (long)(cache.getKeys().size());
	}
	//只返回value
	public String onlyValue(String cacheName,String key){
		Cache cache = manager.getCache(cacheName);
		return (String)cache.get(key).getObjectValue();
	}
}

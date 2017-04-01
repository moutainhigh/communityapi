package net.okdi.core.common.ehcache;

import java.util.List;

public interface RedisService {
	public String[] findAllName();
	public List getAllKeyAndValue(String cacheName, int numPerPage, int pageNum, Long totalCount);
	public List getValueByKey(String cacheName,String key);
	public Long getKeyList(String cacheName);
	public String onlyValue(String cacheName,String key);
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
		public void put(String cacheName, String key, String value);
		
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
		public void put(String cacheName, String key, Object value);
		
		/**
		 * @Method: get
		 * @Description: 根据缓存名称、唯一key获取缓存的值
		 * @param cacheName
		 *            名称
		 * @param key
		 *            唯一key
		 * @return 缓存对象
		 */
		public <T> T get(String cacheName, String key, Class<T> className);
		

		/**
		 * @Method: getByName
		 * @Description: 根据缓存名称判断缓存里面是否有值
		 * @param cacheName
		 *            名称
		 * @return 缓存对象是否存在 false:不存在 true:存在
		 */
		public boolean getByName(String cacheName);

		/**
		 * @Method: getByKey
		 * @Description: 根据缓存名称和key判断缓存里面是否有值
		 * @param cacheName
		 *            名称
		 * @param key
		 *            唯一key
		 * @return 缓存对象是否存在 false:不存在 true:存在
		 */
		public boolean getByKey(String cacheName, String key);

		/**
		 * @Method: get
		 * @Description: 获取对应名称下的所有缓存
		 * @param cacheName
		 *            名称
		 * @return 缓存对象
		 */
		public <T> List<T> getAll(String cacheName, Class<T> className);

		/**
		 * @Method: remove
		 * @Description: 根据缓存名称、唯一key删除缓存对象
		 * @param cacheName
		 *            名称
		 * @param key
		 *            唯一key
		 * @return
		 */
		public void remove(String cacheName, String key);

		/**
		 * 清空多个缓存
		 * 
		 * @param cacheName
		 *            缓存名称
		 * @param keys
		 *            key列表
		 */
		public void remove(String cacheName, List<String> keys);

		/**
		 * @Method: removeAll
		 * @Description: 删除对应缓存名称下的所有缓存
		 * @param cacheName
		 *            名称
		 * @return
		 */
		public void removeAll(String cacheName);
		public void lpush(String key,Object obj);
		public <T> List<T> lpop(String key,Class<T> className);
}

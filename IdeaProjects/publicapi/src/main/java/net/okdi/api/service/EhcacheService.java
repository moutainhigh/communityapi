package net.okdi.api.service;

public interface EhcacheService {
	
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
	 * @Method: removeAll
	 * @Description: 删除对应缓存名称下的所有缓存
	 * @param cacheName
	 *            名称
	 * @return
	 */
	public void removeAll(String cacheName);

}
